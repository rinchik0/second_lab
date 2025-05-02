package org.example.Inputs;

import org.example.Interfaces.Input;

import java.util.ArrayList;

/**
 * Класс тестового ввода, то есть генерирующий значения и сохраняющий все, что должно было считаться.
 */
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

    /**
     * Метод, позволяющий повторить по отдельным вызовам ввод заново (тех же данных, что уже были использованы).
     * @return очередное значение
     */
    public Double repeatDouble() {
        current++;
        return numbers.get(current);
    }
}
