package ru.hd.olaf.main.finder.Strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 *
 * Реализация поиска в тексте по алгоритму Бойера-Мура-Хорспула
 */
public class BMHStrategy implements Strategy {

    @Override
    public boolean isContain(String text, String pattern) {
        int textLen = text.length();
        int patternLen = pattern.length();

        if (textLen < patternLen)
            throw new IllegalArgumentException("Переданы некорректные параметры");

        //заполняем таблицу сдвига
        Map<Character, Integer> offsets = new HashMap<>();
        for (int i = 0; i < patternLen - 1; i++){
            offsets.put(pattern.charAt(i), patternLen - i - 1);
        }

        int offsetPosition = patternLen - 1;        //позиция в исходной строке, до которой передвигаем последний символ pattern
        int matchedPatternPosition = offsetPosition;    //позиция в искомом слове, до которого символы совпадают
        int matchedTextPosition = offsetPosition;       //текущая позиция в исходной строке, которую сравниваем

        while (matchedPatternPosition >= 0 && offsetPosition <= textLen - 1){
            matchedPatternPosition = patternLen - 1;
            matchedTextPosition = offsetPosition;

            while (matchedPatternPosition >= 0 &&
                    text.charAt(matchedTextPosition) == pattern.charAt(matchedPatternPosition)){
                matchedPatternPosition--;
                matchedTextPosition--;
            }

            offsetPosition += offsets.containsKey(text.charAt(offsetPosition)) ?
                    offsets.get(text.charAt(offsetPosition)) :
                    patternLen;
        }

        return matchedPatternPosition <= 0 ? true : false;
    }
}
