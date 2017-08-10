package main.ru.hd.olaf;

import main.ru.hd.olaf.evaluator.QueryEvaluator;
import main.ru.hd.olaf.finder.Strategy.BMHStrategy;
import main.ru.hd.olaf.finder.Strategy.NativeStrategy;
import main.ru.hd.olaf.finder.StringFinder;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class TextSearch {

    public static void main(String[] args) {

        StringFinder finder = new StringFinder(new BMHStrategy());

        String text = "автомобиль ВАЗ-2101";
        String query = "авто AND (ВАЗ OR ГАЗ)";

        System.out.println(QueryEvaluator.executeQuery(text, query, finder));

    }

}
