package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MathematicsTest {
    @Test
    void setGetExpressionFirst() {
        Mathematics math = Mathematics.getInstance();

        String expected = "1 + 2";

        math.setExpression(expected);

        String actual = math.getExpression();

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void setGetExpressionSecond() {
        Mathematics math = Mathematics.getInstance();

        String expected = "1 + 2";

        math.setExpression(expected);

        expected += " + 3";

        String actual = math.getExpression();

        Assertions.assertNotEquals(expected, actual);
    }
    @Test
    void isBracketsCorrectFirst() {
        Mathematics math = Mathematics.getInstance();
        math.setExpression("(1+2)-1+2+3*(5+(6+8*(7+10)))");

        boolean actual = math.isBracketsCorrect();

        boolean expected = true;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void isBracketsCorrectSecond() {
        Mathematics math = Mathematics.getInstance();
        math.setExpression("(1+2)-1+2+3*(5+(6+8*(7+10))");

        boolean actual = math.isBracketsCorrect();

        boolean expected = false;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void isSyntaxCorrectFirst() {
        Mathematics math = Mathematics.getInstance();
        math.setExpression("4#5");

        boolean actual = math.isSyntaxCorrect();

        boolean expected = false;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void isSyntaxCorrectSecond() {
        Mathematics math = Mathematics.getInstance();
        math.setExpression("(1/2)-1+2log12+3z*(5y+(6+8*(7sin(x)+10))");

        boolean actual = math.isSyntaxCorrect();

        boolean expected = true;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void conversionToPostfixNotation() {
        Mathematics math = Mathematics.getInstance();
        math.setExpression("(1/2)-12+3*(5+(6+8*(7+10)))+50");

        List<String> actual = math.conversionToPostfixNotation();

        List<String> expected = List.of("1", "2", "/", "12", "-", "3", "5", "6", "8", "7",
                "10", "+", "*", "+", "+", "*", "+", "50", "+");

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void calculate() {
        Mathematics math = Mathematics.getInstance();
        math.setExpression("14+4*(2-5)");

        int actual = math.calculate();

        int expected = 2;

        Assertions.assertEquals(expected, actual);
    }
}