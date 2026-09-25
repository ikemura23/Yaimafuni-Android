package com.yaeyama.linerchecker.di

import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

/**
 * アプリで使う FirebaseDatabase を生成する
 *
 * ディスク永続化を有効にし、最後に取得したデータを端末に保存する。
 * オフライン時やアプリ再起動直後でも保存済みのデータをすぐに表示でき、
 * オンラインに戻ると自動で最新データに同期される。
 * setPersistenceEnabled は他の操作より先に呼ぶ必要があるため、インスタンス生成時に設定する。
 */
internal fun createFirebaseDatabase(): FirebaseDatabase =
    Firebase.database(Firebase.app).apply {
        setPersistenceEnabled(true)
    }
