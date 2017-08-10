package main.ru.hd.olaf.util;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 *
 * Типы допустимых логических операций.
 * Их порядок важен для определения приоритета (используется ordinal())
 */
public enum OperationType {
    OPEN_BRACKET,
    CLOSE_BRACKET,
    OR,
    AND,
    NOT;

    public static OperationType getOperation(String operation){
        if (operation == null || operation.length() == 0)
            throw new IllegalArgumentException();

        try {
            return OperationType.valueOf(operation);
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
