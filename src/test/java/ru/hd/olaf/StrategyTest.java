package test.java.ru.hd.olaf;

import junit.framework.Assert;
import main.java.ru.hd.olaf.evaluator.QueryEvaluator;
import main.java.ru.hd.olaf.finder.Strategy.BMHStrategy;
import main.java.ru.hd.olaf.finder.Strategy.PrimitiveStrategy;
import main.java.ru.hd.olaf.finder.TextFinder;
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

    private static final TextFinder PRIMITIVE_FINDER = new TextFinder(new PrimitiveStrategy());
    private static final TextFinder BMH_FINDER = new TextFinder(new BMHStrategy());

    private static String FILE_CONTENT;
    private static final String QUERY = "Compliments";

    @BeforeClass
    public static void getFileContent() {
        ClassLoader classLoader = TextSearchTest.class.getClassLoader();
        try {
            File file = new File(classLoader.getResource("file.txt").getFile());
            if (file.isFile()) {
                List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
                StringBuilder fileContent = new StringBuilder();
                lines.stream().forEach(fileContent::append);

                //аккуратней с OutOfMemory!
//                for (int i = 0; i < 10; i++) {
//                    fileContent.append(fileContent);
//                }
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
