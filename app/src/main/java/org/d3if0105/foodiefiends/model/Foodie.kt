package org.d3if0105.foodiefiends.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foodie")
data class Foodie(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namaMenu: String,
    val kategori: String,
    val deskripsi: String

)
