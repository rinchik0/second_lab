package org.example.Inputs;

import org.example.Interfaces.Input;

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
