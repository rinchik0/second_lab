package org.example;

import org.example.Interfaces.Input;
import org.example.Interfaces.Output;

import java.util.*;

/**
 * Класс, вычисляющий значение строки-выражения.
 * Хранит математические операции, константы.
 * Поддерживает использование переменных.
 */
public class Mathematics {
    private Input input;
    private Output output;
    private List<String> variablesNames;

    /**
     * Мап для хранения пар переменная-значение.
     */
    private Map<String, Double> variables;

    /**
     * Множество для хранения математических функций.
     */
    private static final Set<String> functions = Set.of("sin", "cos", "tg", "sqrt", "abs", "arcsin", "arccos", "arctg");

    /**
     * Множество для хранения пар константа-значение.
     */
    private static final Map<String, Double> constants = Map.of("pi", Math.PI);

    /**
     * Мап для хранения приоритетов математических операторов.
     */
    private static final Map<Character, Integer> priority = Map.of('+',1,'-',1,'*',2,
            '/',2,'^', 3, '~', 4);

    /**
     * Конструктор.
     * @param input входной поток
     * @param output выходной поток
     */
    public Mathematics(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    /**
     * Метод, требующий от входного потока значения переменных, использованных в выражении.
     * @return true, если успешно получены значения, false - в обратном случае
     * (помимо этого передает в выходной поток сообщение об ошибке).
     */
    private boolean requestValues() {
        for (String var : variablesNames) {
            if (constants.containsKey(var))
                variables.put(var, constants.get(var));
            else {
                output.print(ErrorMessages.REQUEST_VALUE + var);
                Double value = input.nextDouble();
                if (value == null) {
                    output.println(ErrorMessages.NO_VARIABLES);
                    return false;
                }
                variables.put(var, value);
            }
        }
        return true;
    }

    /**
     * Метод, вычисляющий значение переданного выражения.
     * @param expression строка-выражение
     * @return результат вычислений
     */
    public Double calculate(String expression) {
        variables = new HashMap<>();
        variablesNames = new ArrayList<>();
        Validator validator = new Validator(output);
        if (validator.isExpressionCorrect(expression)) {
            Parser parser = new Parser();
            List<String> elements = parser.conversionToPostfixNotation(expression, functions, priority, variablesNames);
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
                                        output.println(ErrorMessages.SQRT_ERROR);
                                        return null;
                                    }
                                }
                                case "abs" -> numbers.push(Math.abs(a));
                                case "arcsin" -> {
                                    if (a >= -1 && a <= 1)
                                        numbers.push(Math.asin(a));
                                    else {
                                        output.println(ErrorMessages.ARCSIN_ERROR);
                                        return null;
                                    }
                                }
                                case "arccos" -> {
                                    if (a >= -1 && a <= 1)
                                        numbers.push(Math.acos(a));
                                    else {
                                        output.println(ErrorMessages.ARCCOS_ERROR);
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
                                            output.println(ErrorMessages.DIVISION_BY_ZERO);
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
}
