package org.example.Outputs;

import org.example.Interfaces.Output;

import java.util.ArrayList;
import java.util.List;

public class TestOutput implements Output {
    private final List<String> outputMessage;
    public TestOutput() {
        outputMessage = new ArrayList<>();
    }
    @Override
    public void print(String text) {
        outputMessage.add(text);
    }
    @Override
    public void println(String text) {
        outputMessage.add(text);
    }
    public List<String> getOutputMessages() {
        return outputMessage;
    }
}
