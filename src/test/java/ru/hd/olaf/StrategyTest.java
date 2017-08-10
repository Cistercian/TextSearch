package test.java.ru.hd.olaf;

import junit.framework.Assert;
import main.java.ru.hd.olaf.evaluator.QueryEvaluator;
import main.java.ru.hd.olaf.finder.Strategy.BMHStrategy;
import main.java.ru.hd.olaf.finder.Strategy.PrimitiveStrategy;
import main.java.ru.hd.olaf.finder.StringFinder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Olaf on 10.08.2017.
 *
 * Пытаемся увидеть разницу в скорости поиска используемых алгоритмов
 */
public class StrategyTest extends Assert {

    private static final StringFinder PRIMITIVE_FINDER = new StringFinder(new PrimitiveStrategy());
    private static final StringFinder BMH_FINDER = new StringFinder(new BMHStrategy());
    private static final StringBuilder FILE_CONTENT = new StringBuilder();
    private static final String QUERY = "Compliments";

    @BeforeClass
    public static void getFileContent() {
        ClassLoader classLoader = TextSearchTest.class.getClassLoader();
        File file = new File(classLoader.getResource("file.txt").getFile());
        try {
            if (file.isFile()) {
                List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
                lines.stream().forEach(FILE_CONTENT::append);

                //аккуратней с OutOfMemory!
                for (int i = 0; i < 10; i++) {
                    FILE_CONTENT.append(FILE_CONTENT.toString());
                }
                FILE_CONTENT.append(QUERY);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBMHStrategy() {
        assertTrue(QueryEvaluator.executeQuery(FILE_CONTENT.toString(), QUERY, PRIMITIVE_FINDER));
    }

    @Test
    public void testPrimitiveStrategy() {
        assertTrue(QueryEvaluator.executeQuery(FILE_CONTENT.toString(), QUERY, PRIMITIVE_FINDER));
    }

}
