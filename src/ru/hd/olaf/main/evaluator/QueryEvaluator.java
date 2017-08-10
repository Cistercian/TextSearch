package ru.hd.olaf.main.evaluator;

import ru.hd.olaf.main.finder.StringFinder;
import ru.hd.olaf.main.util.OperationType;

import java.util.Stack;

/**
 * Created by d.v.hozyashev on 09.08.2017.
 *
 * Класс, осуществляющий выполнение логического условия поиска текста
 */
public class QueryEvaluator {

    //стек с булевыми параметрами
    private static final Stack<Boolean> values = new Stack<>();
    //стек с логическими операторами (NOT, AND, OR)
    private static final Stack<OperationType> operators = new Stack<>();

    /**
     * Главная функция - выполнение условия
     *
     * @param text Текст, в котором проверяем выполнение условия (Текст, в котором осуществляем поиск)
     * @param query Строка с логическим условием (WHERE ...)
     * @param finder Объект, реализующий алгоритм поиска совпадения в тексте
     * @return true при выполнении условия, false - иначе
     */
    public static boolean executeQuery(String text, String query, StringFinder finder) {
        //форматируем передаваемую строку - отделяем скобки пробелами от слов для поиска
        query = reformatQuery(query);

        //по словам парсим исходную строку с условиями
        String[] expressions = query.split(" ");
        boolean isNextWordIsOperator = false;
        for (int i = 0; i < expressions.length; i++) {

            String word = expressions[i];
            if (isValue(word)) {
                //считали искомое слово
                values.push(finder.isTextFound(text, word));

                //в том случае, если перед этим уже было искомое слово
                //добавляем в стек операций операцию по-умолчанию AND
                if (isNextWordIsOperator)
                    operators.push(OperationType.AND);

                isNextWordIsOperator = true;
            } else if (isOperator(word)) {
                //считали оператор
                OperationType operator = OperationType.valueOf(word);

                //случай с использованием скобок
                if (operator == OperationType.OPEN_BRACKET) {
                    //перед открывающейся скобкой должна быть операция. Иначе - добавляем в стек AND
                    if (isNextWordIsOperator)
                        operators.push(OperationType.AND);

                    //сохраняем скобку в стек и переходим к считыванию следующего слова
                    operators.push(operator);
                    continue;
                } else if (operator == OperationType.CLOSE_BRACKET) {
                    //считали закрывающуюся скобку - необходимо выполнить все имеющиеся операции в стеке
                    //до открывающейся скобки
                    while (operators.peek() != OperationType.OPEN_BRACKET) {
                        evaluate(values, operators.pop());
                    }
                    //убираем из стека открывающуюся скобку и переходим к следующему слову
                    operators.pop();
                    continue;
                }

                //если считали операцию NOT, перед которой не было логического условия AND или OR
                //добавляем его по-умолчанию
                if (isNextWordIsOperator && operator == OperationType.NOT)
                    operators.push(OperationType.AND);

                //если приоритет условия больше или равен тому, что уже лежит в стеке
                //то необходимо вычислить операцию из стека
                if (operators.size() > 0 && operator.ordinal() <= operators.peek().ordinal()) {
                    evaluate(values, operators.pop());
                }
                //сохраняем в стек текущую операцию
                operators.push(operator);
                isNextWordIsOperator = false;
            }
        }

        //обрабатываем весь стек операторов
        while (!operators.isEmpty()) {
            evaluate(values, operators.pop());
        }


        if (values.size() != 1)
            throw new IllegalArgumentException("Что-то пошло не так - нет итогового определенного результата");

        return values.pop();
    }

    /**
     * Функция при необходимости добавляет пробелы у скобок (для шаблонного парсинга)
     *
     * @param query исходная строка с условями
     * @return переформатированная строка с условиями
     */
    private static String reformatQuery(String query) {

        String reformattedQuery = query
                .replaceAll("(?<=\\S)(?=\\()", " ")
                .replaceAll("(?<=\\S)(?=\\))", " ")
                .replaceAll("(?<=\\()(?=\\S)", " ")
                .replaceAll("(?<=\\)(?=\\S))", " ");

        return reformattedQuery;
    }

    /**
     * Функция осуществляет проверку выполнения переданного условия по имеющимся в стеке параметрам
     *
     * @param values   стек с параметрами
     * @param operator текущая проверяемая операция (NOT/AND/OR)
     */
    private static void evaluate(Stack<Boolean> values, OperationType operator) {
        Boolean result = null;

        if (operator == OperationType.NOT) {
            //Операция исключения - считываем только последний параметр
            result = !values.pop();
        } else {
            //логическая операция AND или OR - сравниваем 2 последний параметра в стеке
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

    /**
     * Функция проверяет является ли переданная строка искомым словом
     *
     * @param text рассматриваемое слово
     * @return boolean
     */
    private static boolean isValue(String text) {
        if (!isOperator(text) && !"(".equals(text) && !")".equals(text))
            return true;
        else
            return false;
    }

    /**
     * Функция проверяет является ли переданная строка оператором
     *
     * @param text рассматриваемое слово
     * @return boolean
     */
    private static boolean isOperator(String text) {
        try {
            OperationType.valueOf(text);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

}
