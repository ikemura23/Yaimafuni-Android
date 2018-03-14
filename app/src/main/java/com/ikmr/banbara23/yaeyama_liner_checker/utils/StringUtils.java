
package com.ikmr.banbara23.yaeyama_liner_checker.utils;

public class StringUtils {

    /**
     * 全角、半角スペースを削除
     *
     * @param value
     * @return
     */
    public static String trimAll(String value) {
        if (value == null) {
            return null;
        }
        int len = value.length();
        int st = 0;
        char[] val = value.toCharArray();

        while ((st < len) && (val[st] <= ' ' || val[st] == '　')) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= ' ' || val[len - 1] == '　')) {
            len--;
        }
        return ((st > 0) || (len < value.length())) ? value.substring(st, len) : value;
    }

    /**
     * 文中の半角、全角スペースを除去する
     *
     * @param value
     * @return
     */
    public static String replaceSpaceJ(String value) {
        StringBuilder sb = new StringBuilder();
        char[] val = value.toCharArray();
        for (char c : val) {
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (c == ' ' || c == '　') {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 「。」の入る文章に改行コードを付与して改行する
     *
     * @param value
     * @return
     */
    public static String replacePunctuation(String value) {
        StringBuilder sb = new StringBuilder();
        char[] val = value.toCharArray();
        for (int i = 0; i < value.length(); i++) {
            sb.append(val[i]);
            if (val[i] == '。' && i < value.length() - 1) {
                sb.append(System.getProperty("line.separator"));
            }
        }
        return sb.toString();
    }
}
