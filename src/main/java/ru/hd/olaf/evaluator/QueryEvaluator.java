package main.java.ru.hd.olaf.evaluator;

import main.java.ru.hd.olaf.finder.StringFinder;
import main.java.ru.hd.olaf.util.OperatorType;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * Created by d.v.hozyashev on 09.08.2017.
 * <p>
 * Класс, осуществляющий поиск по тексту по логическим условиям.
 * Реализован по алгоритму Обратная польская нотация (далее ОПН)
 */
public class QueryEvaluator {

    /**
     * Главная функция - проверка условия в тексте
     *
     * @param text   Текст, в котором проверяем выполнение условия (в котором осуществляем поиск)
     * @param query  Строка с логическим условием (WHERE ...)
     * @param finder Объект, реализующий алгоритм поиска совпадения в тексте
     * @return true при выполнении условия, false - иначе
     */
    public static boolean executeQuery(String text, String query, StringFinder finder) {
        if (text == null || query == null || finder == null)
            throw new IllegalArgumentException("Ошибка переданных параметров.");

        //Выходная очередь с булевыми результатами расчета (частные результаты поиска по отдельно взятым словам условия
        //с последующим схлапыванием их в результирующее значение)
        Deque<Boolean> values = new ArrayDeque<>();
        //очередь с логическими операторами (NOT, AND, OR) + служебные символы '(', ')'
        Deque<OperatorType> operators = new ArrayDeque<>();

        //форматируем передаваемую строку - при необходимости вставляем пробелы между скобками и словами/операторами
        query = reformatQuery(query);

        //по словам парсим исходную строку query
        String[] expressions = query.split(" ");
        boolean isNextWordIsOperator = false;

        for (String word : expressions) {
            if (isValue(word)) {
                //считали слово, которое следует найти в тексте. Результат поиска кладем в очередь с результатами
                values.push(finder.isTextFound(text, word));

                //в том случае, если перед этим уже было искомое слово
                //добавляем в очередь операций операцию по-умолчанию AND
                if (isNextWordIsOperator) {
                    operators.push(OperatorType.AND);
                }

                isNextWordIsOperator = true;
            } else if (isOperator(word)) {
                //считали оператор
                OperatorType operator = OperatorType.getOperator(word);

                //случай с использованием скобок
                if (operator == OperatorType.OPEN_BRACKET) {
                    //перед открывающейся скобкой должна быть операция. Иначе - добавляем в очередь AND
                    if (isNextWordIsOperator) {
                        operators.push(OperatorType.AND);
                        isNextWordIsOperator = false;
                    }
                    //сохраняем скобку в очередь и переходим к считыванию следующего слова
                    operators.push(operator);
                    continue;
                } else if (operator == OperatorType.CLOSE_BRACKET) {
                    //считали закрывающуюся скобку - необходимо выполнить все имеющиеся операции в очереди
                    //до открывающейся скобки
                    try {
                        while (operators.peek() != OperatorType.OPEN_BRACKET) {
                            evaluate(values, operators.pop());
                        }
                    } catch (NoSuchElementException e) {
                        throw new IllegalArgumentException("Логическая ошибка парсинга условия - не найдена открывающая скобка");
                    }
                    //убираем из очереди открывающуюся скобку и переходим к следующему слову
                    operators.pop();
                    continue;
                }

                //если считали операцию NOT, перед которой не было логического условия AND или OR
                //добавляем по-умолчанию AND
                if (isNextWordIsOperator && operator == OperatorType.NOT)
                    operators.push(OperatorType.AND);

                //если приоритет текущего оператора меньше или равен тому, что уже лежит в очереди
                //то необходимо сначала вычислить операцию из очереди (условие алгоритма ОПН)
                if (operators.size() > 0 && operator.ordinal() <= operators.peek().ordinal()) {
                    evaluate(values, operators.pop());
                }
                //сохраняем текущую операцию
                operators.push(operator);
                isNextWordIsOperator = false;
            }
        }

        //обрабатываем всю оставшуюся очередь операторов
        while (!operators.isEmpty()) {
            evaluate(values, operators.pop());
        }

        if (values.size() != 1)
            throw new RuntimeException("Что-то пошло не так - нет итогового определенного результата");

        return values.pop();
    }

    /**
     * Функция при необходимости добавляет пробелы у скобок (для шаблонного парсинга)
     *
     * @param query исходная строка с условями
     * @return переформатированная строка с условиями
     */
    private static String reformatQuery(String query) {

        return query
                .replaceAll("(?<=\\S)(?=\\()", " ")
                .replaceAll("(?<=\\S)(?=\\))", " ")
                .replaceAll("(?<=\\()(?=\\S)", " ")
                .replaceAll("(?<=\\))(?=\\S)", " ");

    }

    /**
     * Функция осуществляет проверку выполнения переданного условия по имеющимся в очереди операндам
     *
     * @param values   очередь с операндами (результаты поиска слов в тексте)
     * @param operator текущая проверяемая операция (NOT/AND/OR)
     */
    private static void evaluate(Deque<Boolean> values, OperatorType operator) {
        if (values == null || values.size() == 0)
            throw new IllegalArgumentException("Логическая ошибка проверки условий - нет переменных для обработки");

        boolean result = false;

        if (operator == OperatorType.NOT) {
            //Операция исключения - считываем только последний операнд и инвертируем его
            result = !values.pop();
        } else {
            //логическая операция AND или OR - сравниваем 2 последних результата в очереди
            try {
                boolean valueLast = values.pop();
                boolean valuePrevious = values.pop();

                switch (operator) {
                    case AND:
                        result = valueLast && valuePrevious;
                        break;
                    case OR:
                        result = valueLast || valuePrevious;
                        break;
                }
            } catch (NoSuchElementException e) {
                throw new RuntimeException("Ошибка данных - невозможно проверить условие при отсутствии второго операнда");
            }
        }

        values.push(result);
    }

    /**
     * Функция проверяет является ли переданная строка оператором
     *
     * @param text рассматриваемое слово
     * @return boolean
     */
    private static boolean isOperator(String text) {
        try {
            OperatorType.getOperator(text);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Функция проверяет является ли переданная строка словом
     *
     * @param text рассматриваемая строка
     * @return boolean
     */
    private static boolean isValue(String text) {
        return !isOperator(text);
    }
}
