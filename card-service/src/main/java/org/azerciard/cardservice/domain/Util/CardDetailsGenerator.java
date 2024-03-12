package org.azerciard.cardservice.domain.Util;

import java.security.SecureRandom;

public class CardDetailsGenerator  {
    private static final SecureRandom random = new SecureRandom();

    public static String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            int n = random.nextInt(10);
            cardNumber.append(n);
        }

        int lastDigit = calculateChecksum(cardNumber.toString());
        cardNumber.append(lastDigit);

        return cardNumber.toString();
    }
    public static String generateCVV() {
        int cvv = random.nextInt(900) + 100;
        return String.valueOf(cvv);
    }
    private static int calculateChecksum(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }


}
