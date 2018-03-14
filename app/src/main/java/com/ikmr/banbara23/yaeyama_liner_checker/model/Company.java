
package com.ikmr.banbara23.yaeyama_liner_checker.model;

/**
 * 会社のenum
 */
public enum Company {
    ANEI("anei", "安栄観光"),
    YKF("ykf", "八重山観光フェリー");

    /**
     * フィールド変数
     */
    private String code;
    private String name;

    Company(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Company{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
