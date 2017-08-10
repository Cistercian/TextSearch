package main.ru.hd.olaf.util;

import java.util.Stack;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 */
public class LogUtil {

    public static <T> void printStack(Stack<T> stack){
        for (T entity : stack){
            System.out.println(entity);
        }
    }
}
