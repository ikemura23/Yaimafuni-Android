package com.ikmr.banbara23.yaeyama_liner_checker.model

/**
 * 会社のenum
 */
enum class Company(
    val code: String,
    val fullName: String
) {
    ANEI("anei", "安栄観光"),
    YKF("ykf", "八重山観光フェリー");

    override fun toString(): String {
        return "Company{" +
            "code='" + code + '\'' +
            ", name='" + fullName + '\'' +
            '}'
    }
}
