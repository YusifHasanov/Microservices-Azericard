package org.azerciard.cardservice.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String message) {
        super(message);
    }
    public CardNotFoundException(){
        super("Card Not Found ");
    }

}
