package org.example.Interfaces;

/**
 * Интерфейс, реализующий выходной поток.
 */
public interface Output {
    /**
     * Метод печати текста без переноса на следующую строку.
     * @param text текст для печати
     */
    void print(String text);

    /**
     * Метод печати текста с переносом на следующую строку.
     * @param text текст для печати
     */
    void println(String text);
}
