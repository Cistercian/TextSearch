package test.java.ru.hd.olaf;

import junit.framework.Assert;
import main.java.ru.hd.olaf.evaluator.QueryEvaluator;
import main.java.ru.hd.olaf.finder.Strategy.BMHStrategy;
import main.java.ru.hd.olaf.finder.Strategy.PrimitiveStrategy;
import main.java.ru.hd.olaf.finder.StringFinder;
import org.junit.Test;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class TextSearchTest extends Assert {

    private static final StringFinder PRIMITIVE_FINDER = new StringFinder(new PrimitiveStrategy());
    private static final StringFinder BMH_FINDER = new StringFinder(new BMHStrategy());

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
        testCondition1(PRIMITIVE_FINDER);
        testCondition2(PRIMITIVE_FINDER);
        testCondition3(PRIMITIVE_FINDER);
    }

    @Test
    public void testBMHStrategy() {
        testCondition1(BMH_FINDER);
        testCondition2(BMH_FINDER);
        testCondition3(BMH_FINDER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnconditionalQuery() {
        query = "ямаха1) AND мото  1985";
        QueryEvaluator.executeQuery("автомобиль УАЗ", query, PRIMITIVE_FINDER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameters() {
        QueryEvaluator.executeQuery(null, null, null);
    }

    @Test
    public void testQueryBracketsWithoutSpaces() {
        query = "УАЗ(авто OR мото)";
        assertTrue(QueryEvaluator.executeQuery("автомобиль УАЗ", query, PRIMITIVE_FINDER));
    }

    @Test
    public void testQueryDoubleBrackets() {
        query = "УАЗ((авто OR мото))";
        assertTrue(QueryEvaluator.executeQuery("автомобиль УАЗ", query, PRIMITIVE_FINDER));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongQuery() {
        query = "OR";
        QueryEvaluator.executeQuery("автомобиль ваз", query, PRIMITIVE_FINDER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyText() {
        query = "query";
        QueryEvaluator.executeQuery("", query, BMH_FINDER);
    }

    @Test
    public void testEmptyTextAndEmptyQuery() {
        assertTrue(QueryEvaluator.executeQuery("", "", PRIMITIVE_FINDER));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLongQuery() {
        query = "too_long_query_for_execute";
        QueryEvaluator.executeQuery("too short text", query, PRIMITIVE_FINDER);
    }

}
