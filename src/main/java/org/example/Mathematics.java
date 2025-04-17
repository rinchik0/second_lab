package org.example;

import java.util.*;

public class Mathematics {
    private static Mathematics singleton;
    private String expression;
    private boolean hasString, isCorrect;
    private static final Set<String> functions = Set.of("sin", "cos", "tan", "sqrt", "log", "abs");
    private Mathematics() {}
    public static Mathematics getInstance() {
        if (singleton == null) {
            singleton = new Mathematics();
            singleton.hasString = false;
        }
        return singleton;
    }
    public void setExpression(String string) {
        expression = string;
        hasString = true;
    }
    public String getExpression() {
        return expression;
    }
    public boolean isBracketsCorrect() {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(')
                stack.push(expression.charAt(i));
            if (expression.charAt(i) == ')') {
                if (stack.empty())
                    return false;
                else stack.pop();
            }
        }
        return stack.empty();
    }
    public boolean isSyntaxCorrect() {
        return !expression.matches(".*[^0-9+\\-*/^()a-zA-Z].*");
    }
    public boolean isExpressionCorrect() {
        if (hasString)
            return isSyntaxCorrect() && isBracketsCorrect();
        return false;
    }
//    public String conversionToPostfixNotation() {
//        Stack<Character> operations = new Stack<Character>();
//        List<String> numbers = new ArrayList<String>();
//        int number = 0;
//        for (int i = 0; i < expression.length(); i++) {
//            if (Character.isDigit(expression.charAt(i)))
//                number = number * 10 +
//        }
//    }
//    public double calculate() {
//
//    }
}
