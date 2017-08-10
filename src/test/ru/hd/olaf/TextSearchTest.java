package test.ru.hd.olaf;

import junit.framework.Assert;
import main.ru.hd.olaf.evaluator.QueryEvaluator;
import main.ru.hd.olaf.finder.Strategy.BMHStrategy;
import main.ru.hd.olaf.finder.Strategy.NativeStrategy;
import main.ru.hd.olaf.finder.StringFinder;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class TextSearchTest extends Assert {

    private static final StringFinder nativeFinder = new StringFinder(new NativeStrategy());
    private static final StringFinder bmhFinder = new StringFinder(new BMHStrategy());

    private static String query;

    public void testCondition1(StringFinder finder) {
        query = "авто AND (ВАЗ OR ГАЗ)";

        assertTrue(QueryEvaluator.executeQuery("автомобиль ВАЗ-2101", query, finder));
        assertTrue(QueryEvaluator.executeQuery("автомобиль ГАЗ-53", query, finder));
        assertFalse(QueryEvaluator.executeQuery("автомобиль УАЗ", query, finder));
    }

    public void testCondition2(StringFinder finder) {
        query = "мото ямаха NOT урал";

        assertTrue(QueryEvaluator.executeQuery("мотоцикл ямаха 2013 г.в.", query, finder));
        assertFalse(QueryEvaluator.executeQuery("мотоцикл ямаха с прицепом урал", query, finder));
    }

    public void testCondition3(StringFinder finder) {
        query = "мото AND ямаха OR урал";

        assertTrue(QueryEvaluator.executeQuery("мотоцикл ямаха 2013 г.в.", query, finder));
        assertTrue(QueryEvaluator.executeQuery("прицеп урал", query, finder));
        assertTrue(QueryEvaluator.executeQuery("мотоцикл урал", query, finder));
        assertFalse(QueryEvaluator.executeQuery("мотоцикл хонда", query, finder));
    }

    @Test
    public void testNativeStrategy() {
        testCondition1(nativeFinder);
        testCondition2(nativeFinder);
        testCondition3(nativeFinder);
    }

    @Test
    public void testBMHStrategy() {
        testCondition1(bmhFinder);
        testCondition2(bmhFinder);
        testCondition3(bmhFinder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnconditionalQuery(){
        query = "ямаха1) AND мото  1985";

        assert(QueryEvaluator.executeQuery("автомобиль УАЗ", query, nativeFinder));
    }

    @Ignore
    @Test
    public void testTooBigText(){
        StringBuilder bigText = new StringBuilder();
        query = "мото AND ямаха OR урал";

        try {
            for (String line : Files.readAllLines(Paths.get("src_file.txt"),
                    Charset.forName("UTF-8"))){
                bigText.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(QueryEvaluator.executeQuery(bigText.toString(), query, bmhFinder));
    }

}
