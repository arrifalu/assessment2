package org.d3if0105.foodiefiends.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if0105.foodiefiends.database.FoodieDao
import org.d3if0105.foodiefiends.model.Foodie

class MainViewModel(dao: FoodieDao) : ViewModel(){
    val data: StateFlow<List<Foodie>> = dao.getFoodie().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}