package org.example.Inputs;

import org.example.Interfaces.Input;

import java.util.ArrayList;

public class CustomInput implements Input {
    private final ArrayList<Double> numbers;
    private int current;
    public CustomInput() {
        numbers = new ArrayList<>();
        current = -1;
    }
    @Override
    public String nextLine() {
        return null;
    }
    @Override
    public Double nextDouble() {
        double current = (double) ((int) (Math.random() * 1000 / 10)) / 10;
        numbers.add(current);
        return current;
    }
    public Double repeatDouble() {
        current++;
        return numbers.get(current);
    }
}
