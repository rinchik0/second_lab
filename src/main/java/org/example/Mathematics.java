package org.example;

import org.example.Interfaces.Input;
import org.example.Interfaces.Output;

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
    private Input input;
    private Output output;
    private List<String> variablesNames;
    private Map<String, Double> variables;
    /**
     * Множество для хранения математических функций.
     */
    private static final Set<String> functions = Set.of("sin", "cos", "tg", "sqrt", "abs", "arcsin", "arccos", "arctg");
    /**
     * Мап для хранения приоритетов математических операторов.
     */
    private static final Map<Character, Integer> priority = Map.of('+',1,'-',1,'*',2,
            '/',2,'^', 3, '~', 4);
    /**
     * Приватный конструктор для того, чтобы ограничить доступ к созданию объектов класса.
     */
    private Mathematics(Input input, Output output) {
        this.input = input;
        this.output = output;
    }
    /**
     * Метод, возвращающий экземпляр класса. Если вызывается впервые, то сначала объект создается.
     * @return экземпляр класса
     */
    public static Mathematics getInstance(Input input, Output output) {
        if (singleton == null) {
            singleton = new Mathematics(input, output);
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
        variables = new HashMap<>();
        variablesNames = new ArrayList<>();
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
        if (stack.empty())
            return true;
        else {
            output.println("Error! Wrong brackets!");
            return false;
        }
    }
    /**
     * Метод проверки символьного состава выражения.
     * @return true, если использовались только цифры, знаки поддерживаемых математических операторов и
     * латинские буквы для названий переменных, false - в обратном случае
     */
    public boolean isSyntaxCorrect() {
        if (expression.matches(".*[^0-9+\\-*./^()a-zA-Z].*")) {
            output.println("Error! Wrong syntax!");
            return false;
        }
        return true;
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
    public List<String> parseString() {
        List<String> members = new ArrayList<>();
        int integerPartOfNumber = 0, realPartOfNumber = 0;
        boolean afterDot = false, lastIsDigit = false, lastIsAlphabetic = false, lastIsBracket = false;
        String word = "";
        for (int i = 0; i < expression.length(); i++)
            if (Character.isDigit(expression.charAt(i))) {
                if (afterDot)
                    realPartOfNumber = realPartOfNumber * 10 + Character.getNumericValue(expression.charAt(i));
                else
                    integerPartOfNumber = integerPartOfNumber * 10 + Character.getNumericValue(expression.charAt(i));
                lastIsDigit = true;
                lastIsAlphabetic = false;
                lastIsBracket = false;
            }
            else {
                if (lastIsDigit)
                    if (expression.charAt(i) == '.')
                        afterDot = true;
                    else {
                        members.add(Integer.toString(integerPartOfNumber) + "." + Integer.toString(realPartOfNumber));
                        lastIsDigit = false;
                        afterDot = false;
                        integerPartOfNumber = 0;
                        realPartOfNumber = 0;
                    }
                if (Character.isAlphabetic(expression.charAt(i))) {
                    word += expression.charAt(i);
                    lastIsAlphabetic = true;
                    lastIsBracket = false;
                }
                else {
                    if (lastIsAlphabetic) {
                        members.add(word);
                        word = "";
                        lastIsAlphabetic = false;
                    }
                    if (expression.charAt(i) != '.') {
                        if ((lastIsBracket || members.isEmpty()) && expression.charAt(i) == '-')
                            members.add("~");
                        else
                            members.add(Character.toString(expression.charAt(i)));
                        lastIsBracket = expression.charAt(i) == '(';
                    }
                }
            }
        if (lastIsDigit)
            members.add(Integer.toString(integerPartOfNumber) + "." + Integer.toString(realPartOfNumber));
        if (lastIsAlphabetic)
            members.add(word);
        return members;
    }
    /**
     * Метод конвертации строки-выражения в постфиксную нотацию с помощью алгоритма Дейкстры.
     * @return постфиксную нотацию
     */
    public List<String> conversionToPostfixNotation() {
        Stack<String> operations = new Stack<>();
        List<String> postfixNotation = new ArrayList<>();
        List<String> members = parseString();
        for (String member : members) {
            if (functions.contains(member))
                operations.add(member);
            else if (member.charAt(0) == '(')
                operations.push("(");
            else if (member.charAt(0) == ')') {
                while (!operations.empty() && !operations.peek().equals("("))
                    postfixNotation.add(operations.pop());
                operations.pop();
                while (!operations.empty() && functions.contains(operations.peek()))
                    postfixNotation.add(operations.pop());
            } else if (priority.containsKey(member.charAt(0))) {
                while (!operations.empty() && operations.peek() != "(" &&
                        priority.get(member.charAt(0)) <= priority.get(operations.peek().charAt(0)))
                    postfixNotation.add(operations.pop());
                operations.push(member);
            } else
                try {
                    postfixNotation.add(Double.toString(Double.parseDouble(member)));
                }
                catch (NumberFormatException e) {
                    variablesNames.add(member);
                    postfixNotation.add(member);
                }
        }
        while (!operations.empty())
            postfixNotation.add(operations.pop());
        return postfixNotation;
    }
    private boolean requestValues() {
        for (String var : variablesNames) {
            if (var.equals("pi"))
                variables.put(var, Math.PI);
            else {
                output.print("Please, input value of " + var);
                Double value = input.nextDouble();
                if (value == null) {
                    output.println("Error! Unknown variable!");
                    return false;
                }
                variables.put(var, value);
            }
        }
        return true;
    }
    public Double calculate() {
        if (isExpressionCorrect()) {
            List<String> elements = conversionToPostfixNotation();
            if (requestValues()) {
                Stack<Double> numbers = new Stack<>();
                for (String element : elements) {
                    try {
                        if (variables.containsKey(element))
                            numbers.push(variables.get(element));
                        else numbers.push(Double.parseDouble(element));
                    } catch (NumberFormatException e) {
                        if (functions.contains(element)) {
                            double a = numbers.pop();
                            switch (element) {
                                case "sin" -> numbers.push(Math.sin(a));
                                case "cos" -> numbers.push(Math.cos(a));
                                case "tg" -> numbers.push(Math.tan(a));
                                case "sqrt" -> {
                                    if (a >= 0)
                                        numbers.push(Math.sqrt(a));
                                    else {
                                        output.println("Error! Number under the root is less than 0!");
                                        return null;
                                    }
                                }
                                case "abs" -> numbers.push(Math.abs(a));
                                case "arcsin" -> {
                                    if (a >= -1 && a <= 1)
                                        numbers.push(Math.asin(a));
                                    else {
                                        output.println("Error! Number to calculate the arcsine is greater than 1 or less than -1!");
                                        return null;
                                    }
                                }
                                case "arccos" -> {
                                    if (a >= -1 && a <= 1)
                                        numbers.push(Math.acos(a));
                                    else {
                                        output.println("Error! Number to calculate the arccosine is greater than 1 or less than -1!");
                                        return null;
                                    }
                                }
                                case "arctg" -> numbers.push(Math.atan(a));
                            }
                        } else {
                            if (element.equals("~"))
                                numbers.push(-numbers.pop());
                            else {
                                double a = numbers.pop();
                                double b = numbers.pop();
                                switch (element) {
                                    case "+" -> numbers.push(b + a);
                                    case "-" -> numbers.push(b - a);
                                    case "/" -> {
                                        if (a != 0)
                                            numbers.push(b / a);
                                        else {
                                            output.println("Error! Division by zero!");
                                            return null;
                                        }
                                    }
                                    case "*" -> numbers.push(b * a);
                                    case "^" -> numbers.push(Math.pow(b, a));
                                }
                            }
                        }
                    }
                }
                return numbers.pop();
            }
        }
        return null;
    }
    public static void reset() {
        singleton = null;
    }
}
