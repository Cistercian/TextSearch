package main.java.ru.hd.olaf.finder.Strategy;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 *
 * Реализация стандартного поиска подстроки
 */
public class PrimitiveStrategy implements Strategy {

    @Override
    public boolean isContain(String text, String pattern) {
        if (text == null || pattern == null)
            throw new IllegalArgumentException("Переданы некорректные параметры");

        if (pattern.length() > text.length())
            throw new IllegalArgumentException("Переданы некорректные параметры");

        return text.contains(pattern);
    }
}
