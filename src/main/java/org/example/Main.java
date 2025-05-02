package org.example;

import org.example.Inputs.ConsoleInput;
import org.example.Interfaces.Input;
import org.example.Outputs.ConsoleOutput;

public class Main {
    public static void main(String[] args) {
        Mathematics math = new Mathematics(new ConsoleInput(), new ConsoleOutput());
        System.out.println("Input expression");
        Input input = new ConsoleInput();
        System.out.println(math.calculate(input.nextLine()));
    }
}