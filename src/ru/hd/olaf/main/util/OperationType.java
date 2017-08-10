package ru.hd.olaf.main.util;

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
    NOT
}
