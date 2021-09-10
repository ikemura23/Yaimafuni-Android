package com.ikmr.banbara23.yaeyama_liner_checker.ui.main

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TyphoonRepositoryOld
import com.ikmr.banbara23.yaeyama_liner_checker.repository.TyphoonUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel : ViewModel() {
    private val typhoonRepositoryOld: TyphoonRepositoryOld by lazy {
        // TODO: DI化したい
        val databaseReference = FirebaseDatabase.getInstance().reference.ref.child("typhoon/tenkijp")
        TyphoonRepositoryOld(databaseReference)
    }

    fun existsTyphoon(): Flow<Boolean> {
        return typhoonRepositoryOld.getTyphoonList().map { state ->
            when (state) {
                is TyphoonUiState.Success -> {
                    state.typhoonList.isNotEmpty()
                }
                else -> {
                    false
                }
            }
        }
    }
}