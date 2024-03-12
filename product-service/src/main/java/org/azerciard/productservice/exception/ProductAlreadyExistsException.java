package org.azerciard.productservice.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    public ProductAlreadyExistsException() {
        super("Product already exists! with the same name. Please try another name.");
    }
}
