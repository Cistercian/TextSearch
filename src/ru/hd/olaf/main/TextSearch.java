package ru.hd.olaf.main;

import ru.hd.olaf.main.evaluator.QueryEvaluator;
import ru.hd.olaf.main.finder.Strategy.NativeStrategy;
import ru.hd.olaf.main.finder.StringFinder;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class TextSearch {

    public static void main(String[] args) {

        StringFinder finder = new StringFinder(new NativeStrategy());

        String text = "мотоцикл ямаха 1985 г.в.";
        String query = "ямаха AND мото NOT 1985";

        System.out.println(QueryEvaluator.executeQuery(text, query, finder));

    }

}
