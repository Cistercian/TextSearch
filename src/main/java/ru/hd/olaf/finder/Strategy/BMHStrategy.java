package main.java.ru.hd.olaf.finder.Strategy;

/**
 * Created by d.v.hozyashev on 10.08.2017.
 * <p>
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
        for (int i = 0; i < Character.MAX_VALUE; i++) {
            offsets[i] = patternLen;
        }
        for (char i = 0; i < patternLen - 1; i++) {
            offsets[pattern.charAt(i)] = patternLen - i - 1;
        }

        int curOffset = patternLen - 1; //позиция в исходной строке, до которой передвигаем последний символ pattern
        int curPattern = curOffset;     //позиция в искомом слове, проверяемый на текущей итерации
        int curText;                    //текущая позиция в исходной строке для сравнения

        //ищем до тех пор, пока не найдем полного совпадению, или не дойдем curOffset'ом до конца строки
        while (curPattern >= 0 && curOffset <= textLen - 1) {
            //сбрасываем текущие позиции для новой итерации посимвольной сверки
            curPattern = patternLen - 1;
            curText = curOffset;

            //сдвигаем позиции сверяемых символов справа налево до тех пор, пока либо не сверим все символы,
            //либо не встретим расхождение символов
            while (curPattern >= 0 && text.charAt(curText) == pattern.charAt(curPattern)) {
                curPattern--;
                curText--;
            }

            //сдвигаем позицию подстановки искомого слова на позицию по таблице сдвига
//            curOffset += offsets.containsKey(text.charAt(curText)) ?
//                    offsets.get(text.charAt(curText)) :
//                    patternLen;
            if (curPattern >= 0)
                curOffset += offsets[text.charAt(curText)];
        }

        return curPattern < 0;
    }
}
