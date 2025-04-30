package org.example;

import org.example.Inputs.CustomInput;
import org.example.Inputs.EmptyInput;
import org.example.Outputs.EmptyOutput;
import org.example.Outputs.TestOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class MathematicsTest {
    @AfterEach
    void tearDown() {
        Mathematics.reset();
    }
    @Test
    void setGetExpressionFirst() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());

        String expected = "1.0+2.0";

        math.setExpression(expected);

        String actual = math.getExpression();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void setGetExpressionSecond() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());

        String expected = "1.0+2.0";

        math.setExpression(expected);

        expected += "+3.0";

        String actual = math.getExpression();

        Assertions.assertNotEquals(expected, actual);
    }
    @Test
    void isBracketsCorrectFirst() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());
        math.setExpression("(1+2)-1+2+3*(5+(6+8*(7+10)))");

        boolean actual = math.isBracketsCorrect();

        boolean expected = true;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void isBracketsCorrectSecond() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());
        math.setExpression("(1+2)-1+2+3*(5+(6+8*(7+10))");

        boolean actual = math.isBracketsCorrect();

        boolean expected = false;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void isSyntaxCorrectFirst() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());
        math.setExpression("4#5");

        boolean actual = math.isSyntaxCorrect();

        boolean expected = false;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void isSyntaxCorrectSecond() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());
        math.setExpression("(1/2)-1+2log12+3z*(5y+(6+8*(7sin(x)+10))");

        boolean actual = math.isSyntaxCorrect();

        boolean expected = true;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void parseString() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());
        math.setExpression("abs(-14)+abc*(2-x*cos(0)-sqrt(49))+6^3");

        List<String> expected = List.of("abs", "(", "~", "14.0", ")", "+", "abc", "*", "(", "2.0", "-", "x", "*",
                "cos", "(", "0.0", ")", "-", "sqrt", "(", "49.0", ")", ")", "+", "6.0", "^", "3.0");

        List<String> actual = math.parseString();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void conversionToPostfixNotation() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());
        math.setExpression("(1/2)-12+3*(5+(6+8*(7+10)))+50");

        List<String> actual = math.conversionToPostfixNotation();

        List<String> expected = List.of("1.0", "2.0", "/", "12.0", "-", "3.0", "5.0", "6.0", "8.0", "7.0",
                "10.0", "+", "*", "+", "+", "*", "+", "50.0", "+");

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void conversionToPostfixNotationWithVariables() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());
        math.setExpression("(1/2)-12+x*(5+(6+8*(var+10)))+50");

        List<String> actual = math.conversionToPostfixNotation();

        List<String> expected = List.of("1.0", "2.0", "/", "12.0", "-", "x", "5.0", "6.0", "8.0", "var",
                "10.0", "+", "*", "+", "+", "*", "+", "50.0", "+");

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void conversionToPostfixNotationWithFunction() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());
        math.setExpression("(1/2)-12+x*(sin(30+60)+(6+8*(var+10)))+50");

        List<String> actual = math.conversionToPostfixNotation();

        List<String> expected = List.of("1.0", "2.0", "/", "12.0", "-", "x", "30.0", "60.0", "+", "sin", "6.0",
                "8.0", "var", "10.0", "+", "*", "+", "+", "*", "+", "50.0", "+");

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void calculate() {
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());
        math.setExpression("14+4*(2-5)");

        double actual = math.calculate();

        double expected = 14 + 4 * (2 - 5);

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void calculateWithVariables() {
        CustomInput input = new CustomInput();
        Mathematics math = Mathematics.getInstance(input, new EmptyOutput());
        math.setExpression("14+abc*(2-x)");

        double actual = math.calculate();

        double expected = 14 + input.repeatDouble() * (2 - input.repeatDouble());

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void calculateWithFunction() {
        CustomInput input = new CustomInput();
        Mathematics math = Mathematics.getInstance(input, new EmptyOutput());
        math.setExpression("abs(-14)+abc*(2-x*cos(0)-sqrt(49))");

        double actual = math.calculate();

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
        Mathematics math = Mathematics.getInstance(new EmptyInput(), new EmptyOutput());

        for (String example : expressions.keySet()) {
            math.setExpression(example);
            actual.add(math.calculate());
        }

        List<Double> expected = new ArrayList<>(expressions.values());

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void multiCalculatingWithVariables() {
        CustomInput input = new CustomInput();
        List<Double> actual = new ArrayList<>();
        List<String> expressions = List.of("a+b*c", "sqrt(x)-abs(y)", "sin(alpha)");
        Mathematics math = Mathematics.getInstance(input, new EmptyOutput());

        for (String example : expressions) {
            math.setExpression(example);
            actual.add(math.calculate());
        }

        List<Double> expected = List.of(input.repeatDouble() + input.repeatDouble() * input.repeatDouble(),
                Math.sqrt(input.repeatDouble()) - Math.abs(input.repeatDouble()), Math.sin(input.repeatDouble()));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void errorCalculating() {
        TestOutput output = new TestOutput();
        List<String> expressions = List.of("sqrt(-1)", "5/0", "arcsin(2)", "arccos(99)", "2+(3*4", "4#5", "2+x");

        Mathematics math = Mathematics.getInstance(new EmptyInput(), output);

        boolean result = true;
        for (String example : expressions) {
            math.setExpression(example);
            result = result & (math.calculate() == null);
        }
        List<String> actual = output.getOutputMessages();

        List<String> expected = List.of("Error! Number under the root is less than 0!", "Error! Division by zero!",
                "Error! Number to calculate the arcsine is greater than 1 or less than -1!",
                "Error! Number to calculate the arccosine is greater than 1 or less than -1!", "Error! Wrong brackets!",
                "Error! Wrong syntax!", "Please, input value of x", "Error! Unknown variable!");

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(result);
    }
}