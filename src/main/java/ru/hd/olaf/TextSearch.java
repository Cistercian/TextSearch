package main.java.ru.hd.olaf;

import main.java.ru.hd.olaf.evaluator.QueryEvaluator;
import main.java.ru.hd.olaf.finder.Strategy.BMHStrategy;
import main.java.ru.hd.olaf.finder.StringFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class TextSearch {

    public static void main(String[] args) {

        StringFinder finder = new StringFinder(new BMHStrategy());

        String text = "автомобиль УАЗ-2101 полуямаха";
        String query = "ямаха OR урал AND фиат";

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
                throw new FileNotFoundException();
        } catch (NullPointerException e) {
            throw new FileNotFoundException();
        }
    }

}
