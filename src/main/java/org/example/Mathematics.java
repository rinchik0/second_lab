package org.example;

import java.util.*;

/**
 * Класс, решающий проблему обработки выражения.
 * Позволяет хранить, получать сохраненное выражение, считать его значение.
 * Содержит также некоторые вспомогательные методы для этого.
 */
public class Mathematics {
    private static Mathematics singleton;
    private String expression;
    private boolean hasString, isCorrect;
    /**
     * Множество для хранения математических функций.
     */
    private static final Set<String> functions = Set.of("sin", "cos", "tan", "sqrt", "abs");
    /**
     * Мап для хранения приоритетов математических операторов.
     */
    private static final Map<Character, Integer> priority = Map.of('+',1,'-',1,'*',2,
            '/',2,'^', 3);
    /**
     * Приватный конструктор для того, чтобы ограничить доступ к созданию объектов класса.
     */
    private Mathematics() {}
    /**
     * Метод, возвращающий экземпляр класса. Если вызывается впервые, то сначала объект создается.
     * @return экземпляр класса
     */
    public static Mathematics getInstance() {
        if (singleton == null) {
            singleton = new Mathematics();
            singleton.hasString = false;
        }
        return singleton;
    }
    /**
     * Метод назначения строки выражения.
     * @param string строка-выражение
     */
    public void setExpression(String string) {
        expression = string;
        hasString = true;
    }
    /**
     * Метод получения строки выражения, хранящейся в классе.
     * @return строка-выражение
     */
    public String getExpression() {
        return expression;
    }
    /**
     * Метод проверки соответствия скобок в выражении.
     * @return true, если всем открывающим скобкам соответствуют закрывающие скобки и наоборот,
     * false - в обратном случае
     */
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
    /**
     * Метод проверки символьного состава выражения.
     * @return true, если использовались только цифры, знаки поддерживаемых математических операторов и
     * латинские буквы для названий переменных, false - в обратном случае
     */
    public boolean isSyntaxCorrect() {
        return !expression.matches(".*[^0-9+\\-*/^()a-zA-Z].*");
    }
    /**
     * Метод проверки корректности выражения.
     * @return true, если строка может быть математическим выражение, false - не может
     */
    public boolean isExpressionCorrect() {
        if (hasString)
            return isSyntaxCorrect() && isBracketsCorrect();
        return false;
    }
    /**
     * Метод конвертации строки-выражения в постфиксную нотацию с помощью алгоритма Дейкстры.
     * @return постфиксную нотацию
     */
    public List<String> conversionToPostfixNotation() {
        Stack<Character> operations = new Stack<Character>();
        List<String> postfixNotation = new ArrayList<String>();
        int number = 0;
        boolean lastIsDigit = false;
        for (int i = 0; i < expression.length(); i++) {
            if (Character.isDigit(expression.charAt(i))) {
                number = number * 10 + Character.getNumericValue(expression.charAt(i));
                lastIsDigit = true;
            }
            else
                if (lastIsDigit) {
                    postfixNotation.add(Integer.toString(number));
                    number = 0;
                    lastIsDigit = false;
                }
                if (expression.charAt(i) == '(')
                    operations.push('(');
                if (expression.charAt(i) == ')') {
                    while (!operations.empty() && operations.peek() != '(')
                        postfixNotation.add(Character.toString(operations.pop()));
                    operations.pop();
                }
                if (priority.containsKey(expression.charAt(i))) {
                    while (!operations.empty() && operations.peek() != '(' &&
                            priority.get(expression.charAt(i)) <= priority.get(operations.peek()))
                        postfixNotation.add(Character.toString(operations.pop()));
                    operations.push(expression.charAt(i));
                }
        }
        if (lastIsDigit)
            postfixNotation.add(Integer.toString(number));
        while (!operations.empty())
            postfixNotation.add(Character.toString(operations.pop()));
        return postfixNotation;
    }
    public Integer calculate() {
        if (isExpressionCorrect()) {
            List<String> elements = conversionToPostfixNotation();
            Stack<Integer> numbers = new Stack<Integer>();
            for (String element : elements) {
                try {
                    numbers.push(Integer.parseInt(element));
                } catch (NumberFormatException e) {
                    int a = numbers.pop();
                    int b = numbers.pop();
                    switch (element) {
                        case "+" -> numbers.push(b + a);
                        case "-" -> numbers.push(b - a);
                        case "/" -> numbers.push(b / a);
                        case "*" -> numbers.push(b * a);
                    }
                }
            }
            return numbers.pop();
        }
        return null;
    }
}
