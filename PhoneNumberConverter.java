package com.yan.minicode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yan
 * @date 2020-03-03
 */
public enum PhoneNumberConverter {

    /**
     * single model instance
     */
    INSTANCE;

    /**
     * 能处理的最大参数值
     */
    private final static int MAX_NUM = 99;

    /**
     * 能处理的最小参数值
     */
    private final static int MIN_NUM = 0;

    /**
     * 跳过几个不处理的数字
     */
    private final static int SKIP_NUM = 2;

    /**
     * 手机数字按键-字母映射表
     * 索引跳过 0 & 1
     * index = 数字 - SKIP_NUM
     */
    private final static char[][] NUMBERS_MAP_LETTERS = {
            {'A', 'B', 'C'},
            {'D', 'E', 'F'},
            {'G', 'H', 'I'},
            {'J', 'K', 'L'},
            {'M', 'N', 'O'},
            {'P', 'Q', 'R'},
            {'S', 'T', 'U'},
            {'V', 'W', 'X', 'Y', 'Z'}
    };

    /**
     * 将传入数字映射为对应手机数字字母的笛卡尔积
     *
     * @param number 需要转换的数字，最小值为 0，最大值为 99
     * @return 手机数字按键对应字母的笛卡尔积，无法查找时返回空数组
     */
    public List<String> converter(int number) {
        if (number < MIN_NUM || number > MAX_NUM) {
            throw new IllegalArgumentException(String.format("parameter size must between %d-%d.", MIN_NUM, MAX_NUM));
        }

        char[] numbers = String.valueOf(number).toCharArray();

        int l = 0;
        char[][] letters = new char[numbers.length][];

        for (char num : numbers) {
            int tmp = num - '0';
            if (tmp == 1 || tmp == 0) {
                continue;
            }

            letters[l] = NUMBERS_MAP_LETTERS[tmp - SKIP_NUM];
            l++;
        }

        return cartesian(letters);
    }

    private List<String> cartesian(char[][] letters) {
        List<String> resultLists = new ArrayList<>();
        if (letters.length == 0) {
            resultLists.add("");
            return resultLists;
        } else {
            char[] firstGroup = letters[0];
            if (firstGroup == null) {
                resultLists.add("");
                return resultLists;
            }
            List<String> remainingLists = cartesian(Arrays.copyOfRange(letters, 1, letters.length));
            for (char condition : firstGroup) {
                for (String remain : remainingLists) {
                    resultLists.add(condition + remain);
                }
            }
        }
        return resultLists;
    }
}
