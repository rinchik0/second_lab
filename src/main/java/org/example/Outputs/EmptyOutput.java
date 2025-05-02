package org.example.Outputs;

import org.example.Interfaces.Output;

/**
 * Класс пустого вывода, то есть ничего не будет никуда выводиться.
 */
public class EmptyOutput implements Output {
    @Override
    public void print(String text) {}
    @Override
    public void println(String text) {}
}
