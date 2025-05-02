package org.example;

import org.example.Inputs.EmptyInput;
import org.example.Outputs.EmptyOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidatorTest {
    @Test
    void isBracketsCorrectTrue() {
        Validator validator = new Validator(new EmptyOutput());

        boolean actual = validator.isBracketsCorrect("(1+2)-1+2+3*(5+(6+8*(7+10)))");

        boolean expected = true;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void isBracketsCorrectFalse() {
        Validator validator = new Validator(new EmptyOutput());

        boolean actual = validator.isBracketsCorrect("(1+2)-1+2+3*(5+(6+8*(7+10))");

        boolean expected = false;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void isSyntaxCorrectFalse() {
        Validator validator = new Validator(new EmptyOutput());

        boolean actual = validator.isSyntaxCorrect("4#5");

        boolean expected = false;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void isSyntaxCorrectTrue() {
        Validator validator = new Validator(new EmptyOutput());

        boolean actual = validator.isSyntaxCorrect("(1/2)-1+2log12+3z*(5y+(6+8*(7sin(x)+10))");

        boolean expected = true;

        Assertions.assertEquals(expected, actual);
    }
}
