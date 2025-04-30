package org.example.Outputs;

import org.example.Interfaces.Output;

public class EmptyOutput implements Output {
    @Override
    public void print(String text) {}
    @Override
    public void println(String text) {}
}
