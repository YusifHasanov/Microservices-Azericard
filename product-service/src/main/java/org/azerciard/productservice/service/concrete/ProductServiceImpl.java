package org.azerciard.productservice.service.concrete;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.azerciard.productservice.domain.model.dto.response.BaseResult;
import org.azerciard.productservice.domain.model.dto.response.SuccessResult;
import org.azerciard.productservice.dto.rabbitmq.BalanceCheckRequestMessage;
import org.azerciard.productservice.dto.rabbitmq.BalanceSuccessMessage;
import org.azerciard.productservice.dto.rabbitmq.PaymentRequestMessage;
import org.azerciard.productservice.dto.request.ProductCreateRequest;
import org.azerciard.productservice.dto.request.ProductIdListRequest;
import org.azerciard.productservice.dto.response.ProductCreateResponse;
import org.azerciard.productservice.dto.response.ProductReadDetailResponse;
import org.azerciard.productservice.dto.response.ProductReadResponse;
import org.azerciard.productservice.entity.Product;
import org.azerciard.productservice.exception.ProductAlreadyExistsException;
import org.azerciard.productservice.mapper.ProductMapper;
import org.azerciard.productservice.repository.ProductRepository;
import org.azerciard.productservice.service.abstracts.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;
    RabbitTemplate rabbitTemplate;

    @Override
    public BaseResult<ProductCreateResponse> createProduct(ProductCreateRequest dto) {

        if (productRepository.existsByName(dto.getName()))
            throw new ProductAlreadyExistsException();
        Product product = new Product();
        product = productMapper.toEntity(product, dto);
        productRepository.save(product);

        return new SuccessResult<>(productMapper.toResponse(product));
    }

    public BaseResult<List<ProductReadDetailResponse>> getProductDetailsByUser(ProductIdListRequest dto) {

        List<ProductReadDetailResponse> products = productRepository.findAllById(dto.getProductIds()).stream()
                .map(productMapper::toReadDetailResponse)
                .toList();

        return new SuccessResult<>(products);
    }

    @Override
    public BaseResult<List<ProductReadResponse>> readAll() {

        var products = productRepository.findByStockGreaterThan(BigDecimal.ZERO).stream()
                .map(productMapper::toReadResponse)
                .toList();

        return new SuccessResult<>(products);
    }


    @RabbitListener(queues = "paymentRequestQueue")
    public void handleMessage(PaymentRequestMessage request) {
        System.out.println("Product service received message");

        Optional<Product> product = productRepository.findById(request.getProductId());

        if (product.isPresent()) {
            Product product1 = product.get();

            BigDecimal stock = product1.getStock();
            BigDecimal count = request.getCount();
            BigDecimal price = product1.getPrice();


            if (stock != null && count != null && price != null) {
                BigDecimal totalPrice = count.multiply(price);
                System.out.println("Total price: " + totalPrice);
                System.out.println("stock stock: " + stock);
                String routingKey = stock.compareTo(count) >= 0 ? "balance.check" : "payment.failure";
                System.out.println("Routing key: " + routingKey);
                BalanceCheckRequestMessage message = BalanceCheckRequestMessage.builder()
                        .cvv(request.getCvv())
                        .cardNumber(request.getCardNumber())
                        .amount(totalPrice)
                        .productId(request.getProductId())
                        .count(request.getCount())
                        .userId(request.getUserId())
                        .build();
                rabbitTemplate.convertAndSend("paymentExchange", routingKey, message);
            } else {
                rabbitTemplate.convertAndSend("paymentExchange", "payment.failure", request);
            }
        } else {
            System.out.println("Product not found");
            rabbitTemplate.convertAndSend("paymentExchange", "payment.failure", request);
        }

    }

    @RabbitListener(queues = "stockCheckQueue")
    public void handleStockUpdate(BalanceSuccessMessage message) {

        Product product = productRepository.findById(message.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        BigDecimal stock = product.getStock();
        BigDecimal count = message.getCount();
        System.out.println("Stock: " + stock + " count: " + count);
        if (stock != null && count != null) {
            BigDecimal newStock = stock.subtract(count);
            product.setStock(newStock);
            productRepository.save(product);
            rabbitTemplate.convertAndSend("paymentExchange", "stock.update.success", message);
        } else {
            rabbitTemplate.convertAndSend("paymentExchange", "stock.update.success", message);
        }
    }

}
