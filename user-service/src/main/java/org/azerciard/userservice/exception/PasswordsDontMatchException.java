package org.azerciard.userservice.exception;

public class PasswordsDontMatchException  extends RuntimeException {
    public PasswordsDontMatchException() {
        super("Passwords don't match");
    }
}
