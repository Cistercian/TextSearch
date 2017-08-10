package main.ru.hd.olaf.finder.Strategy;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class NativeStrategy implements Strategy {

    @Override
    public boolean isContain(String text, String pattern) {
        if (text == null || pattern == null)
            throw new IllegalArgumentException();

        return text.contains(pattern);
    }
}
