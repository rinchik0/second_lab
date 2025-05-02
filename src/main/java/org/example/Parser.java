package org.example;

import java.util.*;

/**
 * Класс, обрабатывающий строку-выражение.
 */
public class Parser {
    /**
     * Метод-split. Разбивает строку на составляющие: числа, переменные, функции, операции, скобки.
     * @param expression строка-выражение
     * @return список составляющих строки
     */
    public List<String> parseString(String expression) {
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
     * Метод, представляющий строку-выражение в постфиксную нотацию при помощи алгоритма Дейкстры.
     * @param expression строка-выражение
     * @param functions список поддерживаемых функций
     * @param priority мап поддерживаемых математических операторов и их приоритетов
     * @param variablesNames список переменных (заполняется методом)
     * @return постфиксная нотация
     */
    public List<String> conversionToPostfixNotation(String expression, Set<String> functions,
                                                    Map<Character, Integer> priority, List<String> variablesNames) {
        Stack<String> operations = new Stack<>();
        List<String> postfixNotation = new ArrayList<>();
        List<String> members = parseString(expression);
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
}
