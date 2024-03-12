package com.yaeyama.linerchecker.ext

import android.os.Build
import android.os.Bundle
import java.io.Serializable

fun <T : Serializable?> Bundle.getSerialize(key: String?, m_class: Class<T>): T? {
    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializable(key, m_class)
    } else {
        this.getSerializable(key) as? T
    }
}
