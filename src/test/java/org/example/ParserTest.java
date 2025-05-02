package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParserTest {
    @Test
    void parseString() {
        Parser parser = new Parser();

        List<String> expected = List.of("abs", "(", "~", "14.0", ")", "+", "abc", "*", "(", "2.0", "-", "x", "*",
                "cos", "(", "0.0", ")", "-", "sqrt", "(", "49.0", ")", ")", "+", "6.0", "^", "3.0");

        List<String> actual = parser.parseString("abs(-14)+abc*(2-x*cos(0)-sqrt(49))+6^3");

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void conversionToPostfixNotation() {
        Parser parser = new Parser();
        List<String> variablesNames = new ArrayList<>();
        Set<String> functions = Set.of("sin", "cos", "tg", "sqrt", "abs", "arcsin", "arccos", "arctg");
        Map<Character, Integer> priority = Map.of('+',1,'-',1,'*',2,
                '/',2,'^', 3, '~', 4);

        List<String> actual = parser.conversionToPostfixNotation("(1/2)-12+3*(5+(6+8*(7+10)))+50", functions, priority,
                variablesNames);

        List<String> expected = List.of("1.0", "2.0", "/", "12.0", "-", "3.0", "5.0", "6.0", "8.0", "7.0",
                "10.0", "+", "*", "+", "+", "*", "+", "50.0", "+");

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void conversionToPostfixNotationWithVariables() {
        Parser parser = new Parser();
        List<String> variablesNames = new ArrayList<>();
        Set<String> functions = Set.of("sin", "cos", "tg", "sqrt", "abs", "arcsin", "arccos", "arctg");
        Map<Character, Integer> priority = Map.of('+',1,'-',1,'*',2,
                '/',2,'^', 3, '~', 4);

        List<String> actual = parser.conversionToPostfixNotation("(1/2)-12+x*(5+(6+8*(var+10)))+50", functions, priority,
                variablesNames);

        List<String> expected = List.of("1.0", "2.0", "/", "12.0", "-", "x", "5.0", "6.0", "8.0", "var",
                "10.0", "+", "*", "+", "+", "*", "+", "50.0", "+");
        List<String> expectedVariables = List.of("x", "var");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedVariables, variablesNames);
    }
    @Test
    void conversionToPostfixNotationWithFunction() {
        Parser parser = new Parser();
        List<String> variablesNames = new ArrayList<>();
        Set<String> functions = Set.of("sin", "cos", "tg", "sqrt", "abs", "arcsin", "arccos", "arctg");
        Map<Character, Integer> priority = Map.of('+',1,'-',1,'*',2,
                '/',2,'^', 3, '~', 4);

        List<String> actual = parser.conversionToPostfixNotation("(1/2)-12+x*(sin(30+60)+(6+8*(var+10)))+50",
                functions, priority, variablesNames);

        List<String> expected = List.of("1.0", "2.0", "/", "12.0", "-", "x", "30.0", "60.0", "+", "sin", "6.0",
                "8.0", "var", "10.0", "+", "*", "+", "+", "*", "+", "50.0", "+");
        List<String> expectedVariables = List.of("x", "var");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expectedVariables, variablesNames);
    }
}
