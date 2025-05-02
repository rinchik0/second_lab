package org.example.Interfaces;

/**
 * Интерфейс, реализующий входной поток.
 */
public interface Input {
    /**
     * Метод чтения строки.
     * @return прочитанная строка
     */
    String nextLine();

    /**
     * Метод чтения одного вещественного числа.
     * @return вещественное число
     */
    Double nextDouble();
}
