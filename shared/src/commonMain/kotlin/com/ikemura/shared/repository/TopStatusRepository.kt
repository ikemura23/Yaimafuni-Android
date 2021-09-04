package com.ikemura.shared.repository

import dev.gitlive.firebase.database.FirebaseDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * トップ画面用の運行ステータスを取得するRepository
 */
class TopStatusRepository : KoinComponent {
    private val database: FirebaseDatabase by inject()
}