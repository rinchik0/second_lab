package org.example.Inputs;

import org.example.Interfaces.Input;

import java.util.Scanner;

/**
 * Класс консольного ввода, то есть все данные запрашиваются с консоли.
 */
public class ConsoleInput implements Input {
    private Scanner scanner = new Scanner(System.in);
    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
    @Override
    public Double nextDouble() {
        return Double.parseDouble(scanner.nextLine());
    }
}
