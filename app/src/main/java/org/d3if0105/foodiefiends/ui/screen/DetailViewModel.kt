package org.d3if0105.foodiefiends.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0105.foodiefiends.database.FoodieDao
import org.d3if0105.foodiefiends.model.Foodie

class DetailViewModel(private val dao: FoodieDao) : ViewModel() {

    fun insert(namaMenu: String, kategori: String, deskripsi: String) {
        val foodie = Foodie (
            namaMenu = namaMenu,
            kategori = kategori,
            deskripsi = deskripsi
        )
        viewModelScope.launch ( Dispatchers.IO ) {
            dao.insert(foodie)
        }
    }
    suspend fun getFoodie(id: Long): Foodie? {
        return dao.getFoodieByID(id)
    }
    fun update(id: Long, namaMenu: String, kategori: String, deskripsi: String ) {
        val foodie = Foodie(
            id = id,
            namaMenu = namaMenu,
            kategori = kategori,
            deskripsi= deskripsi
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(foodie)
        }
    }
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
            }
        }
}