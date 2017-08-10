package main.java.ru.hd.olaf.util;

import java.util.Deque;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class LogUtil {

    public static <T> void printDeque(Deque<T> deque) {
        deque.stream().forEach(System.out::println);
    }
}
