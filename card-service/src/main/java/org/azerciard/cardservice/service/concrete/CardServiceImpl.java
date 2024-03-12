package org.azerciard.cardservice.service.concrete;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.azerciard.cardservice.domain.Util.AESEncryptionService;
import org.azerciard.cardservice.domain.Util.AppContext;
import org.azerciard.cardservice.domain.Util.CardDetailsGenerator;
import org.azerciard.cardservice.domain.model.dto.request.RequestById;
import org.azerciard.cardservice.domain.model.dto.response.BaseResult;
import org.azerciard.cardservice.domain.model.dto.response.SuccessResult;
import org.azerciard.cardservice.dto.CardDetail;
import org.azerciard.cardservice.dto.rabbitmq.BalanceSuccessMessage;
import org.azerciard.cardservice.dto.rabbitmq.PaymentRequestMessage;
import org.azerciard.cardservice.dto.request.CardIdListRequest;
import org.azerciard.cardservice.dto.response.CardReadDetailResponse;
import org.azerciard.cardservice.dto.response.CardReadResponse;
import org.azerciard.cardservice.entity.Card;
import org.azerciard.cardservice.exception.CardNotFoundException;
import org.azerciard.cardservice.mapper.CardMapper;
import org.azerciard.cardservice.repository.CardRepository;
import org.azerciard.cardservice.service.abstracts.CardService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CardServiceImpl implements CardService {

    final CardRepository cardRepository;
    final private RabbitTemplate rabbitTemplate;
    final CardMapper cardMapper;


    @Override
    public BaseResult<Boolean> addNewCard() {
        String userId = AppContext.getId();

        BigDecimal balance = new BigDecimal(20);
        CardDetail cardDetail = generateUniqueCardDetails();

        Card card = Card.builder()
                .cardNumber(cardDetail.cardNumber())
                .cvv(cardDetail.cvv())
                .userId(userId)
                .expirationDate(LocalDate.now().plusYears(5))
                .balance(balance)
                .build();
        cardRepository.save(card);

        return new SuccessResult<>(true);
    }

    @Override
    public BaseResult<List<CardReadResponse>> getCardsByUser() {
        String userId = AppContext.getId();

        List<CardReadResponse> cards = cardRepository.findAllByUserId(userId).stream()
                .map(card -> {
                    CardReadResponse response = cardMapper.cardToCardReadResponse(card);
                    try {
                        response.setCardNumber(AESEncryptionService.decrypt(card.getCardNumber()));
                        response.setCvv(AESEncryptionService.decrypt(card.getCvv()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return response;
                }).toList();

        return new SuccessResult<>(cards);
    }

    @Override
    public BaseResult<CardReadResponse> getCardById(RequestById dto) {
        Card card = cardRepository.findById(dto.getId())
                .orElseThrow(CardNotFoundException::new);

        CardReadResponse response = cardMapper.cardToCardReadResponse(card);

        return new SuccessResult<>(response);

    }


    public BaseResult<List<CardReadDetailResponse>> getCardDetailsByUser(CardIdListRequest dto) {
        String userId = AppContext.getId();

        List<CardReadDetailResponse> cards = cardRepository.findAllByUserIdAndIdIn(userId, dto.getCardIds()).stream()
                .map(card -> {
                    CardReadDetailResponse response = cardMapper.cardToCardReadDetailResponse(card);
                    try {
                        response.setCardNumber(AESEncryptionService.decrypt(card.getCardNumber()));
                        response.setCvv(AESEncryptionService.decrypt(card.getCvv()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return response;
                }).toList();

        return new SuccessResult<>(cards);
    }

    @RabbitListener(queues = "balanceCheckQueue")
    public void receivePaymentRequest(PaymentRequestMessage request) {
        String cardId = checkAndUpdateCardBalance(request.getCvv(), request.getCardNumber(), request.getAmount());
        System.out.println("balance: " + request.getAmount() + " cardId: " + " success: " + cardId != null);
        if (cardId != null) {
            BalanceSuccessMessage message =  BalanceSuccessMessage.builder()
                    .count(request.getCount())
                    .productId(request.getProductId())
                    .userId(request.getUserId())
                    .cardId(cardId)
                    .amount(request.getAmount())
                    .cardNumber(request.getCardNumber())
                    .cvv(request.getCvv())
                    .build();
            rabbitTemplate.convertAndSend("paymentExchange", "balance.success", message);
        } else {
            rabbitTemplate.convertAndSend("paymentExchange", "balance.failure", request);
        }
    }


    private String checkAndUpdateCardBalance(String cardCvv, String cardNumber, BigDecimal amount) {
        String encryptedCardNumber;
        String encryptedCVV;
        try {
            encryptedCardNumber = AESEncryptionService.encrypt(cardNumber);
            encryptedCVV = AESEncryptionService.encrypt(cardCvv);
        } catch (Exception e) {
            return null;
        }
        Card card = cardRepository.findByCardNumberAndCvv(encryptedCardNumber, encryptedCVV)
                .orElse(null);

        if (Objects.isNull(card))
            return null;

        System.out.println("card: " + card.getBalance());

        var balance = card.getBalance();

        if (balance == null || amount == null)
            return null;

        if (card.getBalance().compareTo(amount) < 0)
            return null;

        if (card.getExpirationDate().isBefore(LocalDate.now()))
            return null;

        card.setBalance(card.getBalance().subtract(amount));
        cardRepository.save(card);
        return card.getId();


    }

    private CardDetail generateUniqueCardDetails() {
        try {
            String encryptedCardNumber;
            String encryptedCVV;
            do {
                encryptedCardNumber = AESEncryptionService.encrypt(CardDetailsGenerator.generateCardNumber());
                encryptedCVV = AESEncryptionService.encrypt(CardDetailsGenerator.generateCVV());
            }
            while (cardRepository.existsByCardNumberAndCvv(encryptedCardNumber, encryptedCVV));

            return new CardDetail(encryptedCardNumber, encryptedCVV);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
