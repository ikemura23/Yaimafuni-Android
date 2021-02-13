package com.ikmr.banbara23.yaeyama_liner_checker.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.ikmr.banbara23.yaeyama_liner_checker.core.Event

/**
 * Eventを通してLiveDataを購読する
 */
fun <T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    observe(lifecycleOwner) { event ->
        // 実行していなければblockを実行する、実行済みならnullが返って動かない
        event?.getContentIfNotHandled()?.let(block)
    }
}
