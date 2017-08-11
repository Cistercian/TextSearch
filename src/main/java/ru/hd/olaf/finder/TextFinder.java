package main.java.ru.hd.olaf.finder;

import main.java.ru.hd.olaf.finder.Strategy.Strategy;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class TextFinder {
    private Strategy strategy;

    public TextFinder(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public boolean isTextFound(String text, String pattern) {
        return strategy.isContain(text, pattern);
    }
}
