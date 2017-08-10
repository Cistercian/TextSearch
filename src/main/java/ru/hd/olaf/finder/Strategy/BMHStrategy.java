package main.java.ru.hd.olaf.finder.Strategy;

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
        if (text == null || pattern == null)
            throw new IllegalArgumentException("Переданы некорректные параметры");

        int textLen = text.length();
        int patternLen = pattern.length();

        if (textLen < patternLen)
            throw new IllegalArgumentException("Переданы некорректные параметры");

        //заполняем таблицу сдвига
//        Map<Character, Integer> offsets = new HashMap<>();
//        for (int i = 0; i < patternLen - 1; i++){
//            offsets.put(pattern.charAt(i), patternLen - i - 1);
//        }
        int[] offsets = new int[Character.MAX_VALUE];
        for (int i = 0; i < Character.MAX_VALUE; i++ ){
            offsets[i] = patternLen - 1;
        }
        for (int i = 0; i < patternLen - 1; i++){
            offsets[pattern.charAt(i)] = patternLen - i - 1;
        }

        int offsetPosition = patternLen - 1;            //позиция в исходной строке, до которой передвигаем последний символ pattern
        int matchedPatternPosition = offsetPosition;    //позиция в искомом слове, до которого символы совпадают
        int matchedTextPosition = offsetPosition;       //текущая позиция в исходной строке, которую сравниваем

        //ищем до тех пор, пока не найдем полного совпадению, или не дойдем offsetPosition'ом до конца строки
        while (matchedPatternPosition >= 0 && offsetPosition <= textLen - 1){
            //сбрасываем текущие позиции для новой итерации посимвольной сверки
            matchedPatternPosition = patternLen - 1;
            matchedTextPosition = offsetPosition;

            //сдвигаем позиции сверяемых символов влево до тех пор, пока либо не сверим все символы, либо не встретим
            //расхождение символов
            while (matchedPatternPosition >= 0 &&
                    text.charAt(matchedTextPosition) == pattern.charAt(matchedPatternPosition)){
                matchedPatternPosition--;
                matchedTextPosition--;
            }

            //сдвигаем позицию подстановки искомого слова на позицию по таблице сдвига
//            offsetPosition += offsets.containsKey(text.charAt(offsetPosition)) ?
//                    offsets.get(text.charAt(offsetPosition)) :
//                    patternLen;
            if (matchedTextPosition > 0)
                offsetPosition += offsets[text.charAt(matchedTextPosition)];
        }

        return matchedPatternPosition <= 0 ? true : false;
    }
}
