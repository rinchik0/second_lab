package org.example.Outputs;

import org.example.Interfaces.Output;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс вывода для тестирования, то есть все строки вместо печати сохраняются в список для последующего прочтения.
 */
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

    /**
     * Метод, позволяющий повторить полный вывод операций печати.
     * @return список выведенного текста
     */
    public List<String> getOutputMessages() {
        return outputMessage;
    }
}
