package org.example;

public class Mathematics {
    private static Mathematics singleton;
    private String arithmeticString;
    private Mathematics() {}
    public static Mathematics getInstance() {
        if (singleton == null)
            singleton = new Mathematics();
        return singleton;
    }
    public void setArithmeticString(String string) {
        arithmeticString = string;
    }
}
