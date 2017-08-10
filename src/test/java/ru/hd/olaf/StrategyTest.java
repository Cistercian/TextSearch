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
 * <p>
 * Пытаемся увидеть разницу в скорости поиска используемых алгоритмов
 * в classpath'e должна быть доступна папка test/resources
 */
public class StrategyTest extends Assert {

    private static final StringFinder PRIMITIVE_FINDER = new StringFinder(new PrimitiveStrategy());
    private static final StringFinder BMH_FINDER = new StringFinder(new BMHStrategy());

    private static String FILE_CONTENT;
    private static final String QUERY = "Compliments";

    @BeforeClass
    public static void getFileContent() {
        ClassLoader classLoader = TextSearchTest.class.getClassLoader();
        File file = new File(classLoader.getResource("file.txt").getFile());
        try {
            StringBuilder fileContent = new StringBuilder();
            if (file.isFile()) {
                List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
                lines.stream().forEach(fileContent::append);

                //аккуратней с OutOfMemory!
                for (int i = 0; i < 10; i++) {
                    fileContent.append(fileContent.toString());
                }
                fileContent.append(QUERY);

                FILE_CONTENT = fileContent.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBMHStrategy() {
        assertTrue(QueryEvaluator.executeQuery(FILE_CONTENT, QUERY, BMH_FINDER));
    }

    @Test
    public void testPrimitiveStrategy() {
        assertTrue(QueryEvaluator.executeQuery(FILE_CONTENT, QUERY, PRIMITIVE_FINDER));
    }

}
