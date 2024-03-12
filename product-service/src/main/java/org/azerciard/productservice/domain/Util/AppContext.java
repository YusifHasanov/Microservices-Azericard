package org.azerciard.productservice.domain.Util;

public class AppContext {
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> userIdHolder = new ThreadLocal<>();

    public static void setToken(String token) {
        tokenHolder.set(token);
    }

    public static void setId(String id) {
        userIdHolder.set(id);
    }

    public static String getToken() {
        return tokenHolder.get();
    }

    public static String getId() {
        return userIdHolder.get();
    }

    public static void clearAll() {
        tokenHolder.remove();
        userIdHolder.remove();
    }

    public static void clearId() {
        userIdHolder.remove();
    }

    public static void cleartoken() {
        tokenHolder.remove();
    }
}