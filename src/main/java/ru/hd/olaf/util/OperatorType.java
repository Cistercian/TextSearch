package main.java.ru.hd.olaf.util;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 * <p>
 * Типы допустимых логических операций.
 * Их порядок важен для определения приоритета операции (используется ordinal())
 */
public enum OperatorType {
    OPEN_BRACKET,
    CLOSE_BRACKET,
    OR,
    AND,
    NOT;

    /**
     * Метод по сути расширяет valueOf для использования символов скобок как допустимых значений
     *
     * @param operation Стороковое представление оператора
     * @return true при парсинге оператора, иначе - false
     */
    public static OperatorType getOperator(String operation) {
        if (operation == null || operation.length() == 0)
            throw new IllegalArgumentException();

        try {
            return OperatorType.valueOf(operation);
        } catch (IllegalArgumentException e) {

            if (operation.charAt(0) == '(')
                return OPEN_BRACKET;
            else if (operation.charAt(0) == ')')
                return CLOSE_BRACKET;
            else
                throw new IllegalArgumentException();
        }
    }
}
