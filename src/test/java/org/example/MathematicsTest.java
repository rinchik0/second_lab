package org.example;

import org.example.Inputs.CustomInput;
import org.example.Inputs.EmptyInput;
import org.example.Outputs.EmptyOutput;
import org.example.Outputs.TestOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class MathematicsTest {
    @Test
    void calculate() {
        Mathematics math = new Mathematics(new EmptyInput(), new EmptyOutput());

        double actual = math.calculate("14+4*(2-5)");

        double expected = 14 + 4 * (2 - 5);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void calculateWithVariables() {
        CustomInput input = new CustomInput();
        Mathematics math = new Mathematics(input, new EmptyOutput());

        double actual = math.calculate("14+abc*(2-x)");

        double expected = 14 + input.repeatDouble() * (2 - input.repeatDouble());

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void calculateWithFunction() {
        CustomInput input = new CustomInput();
        Mathematics math = new Mathematics(input, new EmptyOutput());

        double actual = math.calculate("abs(-14)+abc*(2-x*cos(0)-sqrt(49))");

        double expected = Math.abs(-14) + input.repeatDouble() * (2 - input.repeatDouble() * Math.cos(0) - Math.sqrt(49));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void multiCalculating() {
        List<Double> actual = new ArrayList<>();
        Map<String, Double> expressions = new HashMap<>(Map.of("2+3*4", 14.0, "(2+3)*4", 20.0, "10/3", 10.0 / 3.0, "2^3+1", 9.0,
                "5-(-3)", 8.0, "-2*3", -6.0, "2^(-3)", 0.125, "sin(-pi/2)", Math.sin(-Math.PI / 2),
                "5-(-(-3))", 2.0, "sin(0)", 0.0));
        expressions.put("sin(pi/2)", Math.sin(Math.PI / 2));
        expressions.put("cos(0)", 1.0);
        expressions.put("cos(pi)", Math.cos(Math.PI));
        expressions.put("tg(0)", 0.0);
        expressions.put("tg(pi/4)", Math.tan(Math.PI / 4));
        expressions.put("arcsin(0.5)", Math.asin(0.5));
        expressions.put("arccos(0.5)", Math.acos(0.5));
        expressions.put("arctg(1)", Math.atan(1.0));
        expressions.put("sqrt(16)", 4.0);
        expressions.put("abs(-5)", 5.0);
        expressions.put("abs(3-10)", 7.0);
        expressions.put("sin(pi/6)+cos(pi/3)", Math.sin(Math.PI / 6) + Math.cos(Math.PI / 3));
        expressions.put("sqrt(abs(-9))*2", 6.0);
        expressions.put("arctg(1)+arccos(0.5)", Math.atan(1) + Math.acos(0.5));
        expressions.put("(5+3)/(2*sqrt(4))", 2.0);
        expressions.put("sin(0)+cos(0)+tg(0)", 1.0);
        Mathematics math = new Mathematics(new EmptyInput(), new EmptyOutput());

        for (String example : expressions.keySet())
            actual.add(math.calculate(example));

        List<Double> expected = new ArrayList<>(expressions.values());

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void multiCalculatingWithVariables() {
        CustomInput input = new CustomInput();
        List<Double> actual = new ArrayList<>();
        List<String> expressions = List.of("a+b*c", "sqrt(x)-abs(y)", "sin(alpha)");
        Mathematics math = new Mathematics(input, new EmptyOutput());

        for (String example : expressions)
            actual.add(math.calculate(example));

        List<Double> expected = List.of(input.repeatDouble() + input.repeatDouble() * input.repeatDouble(),
                Math.sqrt(input.repeatDouble()) - Math.abs(input.repeatDouble()), Math.sin(input.repeatDouble()));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void errorCalculating() {
        TestOutput output = new TestOutput();
        List<String> expressions = List.of("sqrt(-1)", "5/0", "arcsin(2)", "arccos(99)", "2+(3*4", "4#5", "2+x");

        Mathematics math = new Mathematics(new EmptyInput(), output);

        boolean result = true;
        for (String example : expressions)
            result = result & (math.calculate(example) == null);
        List<String> actual = output.getOutputMessages();

        List<String> expected = List.of("Error! Number under the root is less than 0!", "Error! Division by zero!",
                "Error! Number to calculate the arcsine is greater than 1 or less than -1!",
                "Error! Number to calculate the arccosine is greater than 1 or less than -1!", "Error! Wrong brackets!",
                "Error! Wrong syntax!", "Please, input value of x", "Error! Unknown variable!");

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(result);
    }
}