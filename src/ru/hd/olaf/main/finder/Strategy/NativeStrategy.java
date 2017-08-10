package ru.hd.olaf.main.finder.Strategy;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class NativeStrategy implements Strategy {

    @Override
    public boolean isContain(String text, String pattern) {
        return text.contains(pattern);
    }
}
