package main.ru.hd.olaf.finder;

import main.ru.hd.olaf.finder.Strategy.Strategy;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class StringFinder {
    private Strategy strategy;

    public StringFinder(Strategy strategy) {
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
