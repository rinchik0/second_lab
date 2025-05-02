package org.example;

import org.example.Interfaces.Output;

import java.util.Stack;

/**
 * Класс проверки валидации строки-выражения.
 */
public class Validator {
    private Output output;

    /**
     * Конструктор.
     * @param output выходной поток, куда в случае ошибок будет направлено сообщение об ошибке
     */
    public Validator(Output output) {
        this.output = output;
    }

    /**
     * Метод проверки соответствия скобок в выражении.
     * @param expression строка-выражение
     * @return true, если всем открывающим скобкам соответствуют закрывающие скобки и наоборот, false - в обратном случае
     */
    public boolean isBracketsCorrect(String expression) {
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
     * @param expression строка-выражение
     * @return true, если использовались только цифры, знаки поддерживаемых математических операторов
     * и латинские буквы для названий переменных, false - в обратном случае
     */
    public boolean isSyntaxCorrect(String expression) {
        if (expression.matches(".*[^0-9+\\-*./^()a-zA-Z].*")) {
            output.println("Error! Wrong syntax!");
            return false;
        }
        return true;
    }

    /**
     * Метод проверки корректности выражения.
     * @param expression строка-выражение
     * @return true, если строка может быть математическим выражение, false - не может
     */
    public boolean isExpressionCorrect(String expression) {
        return isSyntaxCorrect(expression) && isBracketsCorrect(expression);
    }
}
