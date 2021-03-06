package main.java.ru.hd.olaf;

import main.java.ru.hd.olaf.evaluator.QueryEvaluator;
import main.java.ru.hd.olaf.finder.Strategy.BMHStrategy;
import main.java.ru.hd.olaf.finder.TextFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 *
 * Точка входа в приложение. Для наглядности приведен пример разбора простой строки и содержимого файла
 * (не забудьте отметить папку resources доступной в classpath'e)
 */
public class TextSearch {

    public static void main(String[] args) {

        TextFinder finder = new TextFinder(new BMHStrategy());

        String text = "мотоцикл ямаха с прицепом урал";
        String query = "мото AND (ямаха NOT урал OR с (NOT л)) (ям NOT ом) OR ямаха1 OR NOT при1";

        try {
            boolean result = QueryEvaluator.executeQuery(text, query, finder);
            System.out.println("Результат обработки: " + result);
        } catch (Exception e) {
            System.out.println("Возникла ошибка: " + e.getMessage());
        }

        //читаем строку из файла
        try {
            text = getFileContent("src_file.txt");

            boolean result = QueryEvaluator.executeQuery(text, query, finder);
            System.out.println("Результат обработки строки из файла: " + result);
        } catch (Exception e) {
            System.out.println("Возникла ошибка: " + e.getMessage());
        }

    }

    private static String getFileContent(String fileName) throws IOException {
        ClassLoader classLoader = TextSearch.class.getClassLoader();
        try {
            File file = new File(classLoader.getResource(fileName).getFile());

            if (file.isFile()) {
                List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
                StringBuilder fileContent = new StringBuilder();

                lines.stream().forEach(fileContent::append);

                return fileContent.toString();
            } else
                throw new FileNotFoundException("Ошибка при открытии файла");
        } catch (NullPointerException e) {
            throw new FileNotFoundException("Файл не найден");
        }
    }

}
