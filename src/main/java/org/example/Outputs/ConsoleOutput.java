package org.example.Outputs;

import org.example.Interfaces.Output;

public class ConsoleOutput implements Output {
    @Override
    public void print(String text) {
        System.out.print(text);
    }
    @Override
    public void println(String text) {
        System.out.println(text);
    }
}
