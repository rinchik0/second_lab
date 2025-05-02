package org.example.Inputs;

import org.example.Interfaces.Input;

/**
 * Класс пустого ввода, то есть не позволяющий ничего прочитать.
 */
public class EmptyInput implements Input {
    @Override
    public String nextLine() {
        return null;
    }
    @Override
    public Double nextDouble() {
        return null;
    }
}
