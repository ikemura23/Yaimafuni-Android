package com.yaeyama.linerchecker.domain.statusdetail

/**
 * 運航会社
 *
 * @property code Firebase のパスに使うコード
 * @property fullName 会社の正式名称（画面の表示には androidApp の文字列リソースを使う）
 */
enum class Company(
    val code: String,
    val fullName: String,
) {
    ANEI("anei", "安栄観光"),
    YKF("ykf", "八重山観光フェリー"),
    ;

    override fun toString(): String {
        return "Company{" +
            "code='" + code + '\'' +
            ", name='" + fullName + '\'' +
            '}'
    }
}
