package ru.hd.olaf;

import ru.hd.olaf.evaluator.Stub;
import ru.hd.olaf.util.LogUtil;
import ru.hd.olaf.util.OperationType;

import java.util.Stack;

/**
 * Created by d.v.hozyashev on 09.08.2017.
 */
public class TextSearch {

    private static final String TEXT = "мотоцикл ямаха 2013";
    private static final String EXPRESSION = "мото AND ямаха1 OR NOT урал";

    private static final Stack<String> patterns = new Stack<>();
    private static final Stack<OperationType> operations = new Stack<>();
    private static final Stack<Boolean> results = new Stack<>();

    public static void main(String[] args) {

        Stack<Boolean> values = new Stack<>();
        Stack<OperationType> operators = new Stack<>();

        String[] expressions = EXPRESSION.split(" ");

        for (int i = 0; i < expressions.length; i++) {

            String word = expressions[i];
            if (isValue(word)) {
                values.push(Stub.isContains(TEXT, word));
            } else if (isOperator(word)) {
                OperationType operator = OperationType.valueOf(word);

                //если приоритет условия больше или равен тому, что уже лежит в стеке
                if (operators.size() > 0 && operators.peek().ordinal() <= operator.ordinal()) {
                    //необходимо вычислить значение
                    evaluate(values, operators.pop());
                }
                operators.push(operator);
            }
        }
        //обрабатываем стек операторов
        while (!operators.isEmpty()) {
            evaluate(values, operators.pop());
        }

        System.out.println("результат");
        LogUtil.printStack(values);

//        System.out.println("Парсим строку условий");
//        parsingExpression();
//
//        System.out.println("стек условий");
//        LogUtil.printStack(operations);
//
//        System.out.println("Результат:");
//        LogUtil.printStack(results);

        //теперь необходимо корректно по приоритету расчитать результат
        //обратная польская запись?

    }

    private static void evaluate(Stack<Boolean> values, OperationType operator) {
        Boolean result = null;
        if (operator == OperationType.NOT) {

            result = !values.pop();

        } else {
            Boolean valueLast = values.pop();
            Boolean valuePrevious = values.pop();

            switch (operator) {
                case AND:
                    result = valueLast && valuePrevious;
                    break;
                case OR:
                    result = valueLast || valuePrevious;
                    break;
            }
        }

        values.push(result);

    }

    private static boolean isValue(String text) {
        if (!isOperator(text) && !"(".equals(text) && !")".equals(text))
            return true;
        else
            return false;
    }

    private static boolean isOperator(String text) {
        try {
            OperationType.valueOf(text);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * парсим строку условий
     */
    private static void parsingExpression() {
        String[] expressions = EXPRESSION.split(" ");

        int step = 1;
        for (int i = 0; i < expressions.length; i++, step++) {
            //TODO: скобки
            String word = expressions[i];

            if ("NOT".equalsIgnoreCase(word)) {
                if (++i < expressions.length)
                    results.push(!Stub.isContains(TEXT, expressions[i]));
                else
                    throw new IllegalArgumentException();
            } else {
                if (step % 2 == 0) {
                    //ожидаем увидеть оператор
                    try {
                        OperationType operator = OperationType.valueOf(word);
                        operations.push(operator);
                    } catch (IllegalArgumentException e) {
                        //по дефолту записываем оператор AND
                        operations.push(OperationType.AND);

                        results.push(Stub.isContains(TEXT, word));
                    }
                } else
                    //считываем искомое слово
                    results.push(Stub.isContains(TEXT, word));
            }
        }
    }

    private static void getResult() {
        //TODO: проверка на корректные исходные данные

        for (OperationType operation : operations) {
            switch (operation) {
                case NOT:
                    results.push(!Stub.isContains(TEXT, patterns.pop()));
                    break;

            }
            if (operation == OperationType.AND) {

                results.push(Stub.isContains(TEXT, patterns.pop()) && Stub.isContains(TEXT, patterns.pop()));

            }
        }
    }

}
