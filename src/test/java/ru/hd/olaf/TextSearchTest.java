package test.java.ru.hd.olaf;

import junit.framework.Assert;
import main.java.ru.hd.olaf.evaluator.QueryEvaluator;
import main.java.ru.hd.olaf.finder.Strategy.BMHStrategy;
import main.java.ru.hd.olaf.finder.Strategy.PrimitiveStrategy;
import main.java.ru.hd.olaf.finder.TextFinder;
import org.junit.Test;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 *
 * Главный файл с тесткейсами, показывающий логику работы приложения.
 */
public class TextSearchTest extends Assert {

    private static final TextFinder PRIMITIVE_FINDER = new TextFinder(new PrimitiveStrategy());
    private static final TextFinder BMH_FINDER = new TextFinder(new BMHStrategy());

    private static String query;

    private void testCondition1(TextFinder finder) {
        query = "авто AND (ВАЗ OR ГАЗ)";

        assertTrue(QueryEvaluator.executeQuery("автомобиль ВАЗ-2101", query, finder));
        assertTrue(QueryEvaluator.executeQuery("автомобиль ГАЗ-53", query, finder));
        assertFalse(QueryEvaluator.executeQuery("автомобиль УАЗ", query, finder));
    }

    private void testCondition2(TextFinder finder) {
        query = "мото ямаха NOT урал";

        assertTrue(QueryEvaluator.executeQuery("мотоцикл ямаха 2013 г.в.", query, finder));
        assertFalse(QueryEvaluator.executeQuery("мотоцикл ямаха с прицепом урал", query, finder));
    }

    private void testCondition3(TextFinder finder) {
        query = "мото AND ямаха OR урал";

        assertTrue(QueryEvaluator.executeQuery("мотоцикл ямаха 2013 г.в.", query, finder));
        assertTrue(QueryEvaluator.executeQuery("прицеп урал", query, finder));
        assertTrue(QueryEvaluator.executeQuery("мотоцикл урал", query, finder));
        assertFalse(QueryEvaluator.executeQuery("мотоцикл хонда", query, finder));
    }

    @Test
    public void testPrimitiveStrategy() {
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
    public void testUnconditionalQuery1() {
        query = "ямаха1) AND мото  1985";
        QueryEvaluator.executeQuery("автомобиль УАЗ", query, PRIMITIVE_FINDER);
    }

    @Test(expected = RuntimeException.class)
    public void testUnconditionalQuery2() {
        query = "ямаха1 AND мото AND OR 1985";
        QueryEvaluator.executeQuery("автомобиль УАЗ", query, PRIMITIVE_FINDER);
    }

    @Test(expected = RuntimeException.class)
    public void testUnconditionalQuery3() {
        query = "ямаха1 AND мото AND 1985 (NOT)";
        QueryEvaluator.executeQuery("автомобиль УАЗ", query, PRIMITIVE_FINDER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameters() {
        QueryEvaluator.executeQuery(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameters1() {
        QueryEvaluator.executeQuery(null, "query", PRIMITIVE_FINDER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameters2() {
        QueryEvaluator.executeQuery("string", null, PRIMITIVE_FINDER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullParameters3() {
        QueryEvaluator.executeQuery("string", "string", null);
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
        QueryEvaluator.executeQuery("", query, PRIMITIVE_FINDER);
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
