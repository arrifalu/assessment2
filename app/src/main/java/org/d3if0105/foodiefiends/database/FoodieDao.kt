package org.d3if0105.foodiefiends.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if0105.foodiefiends.model.Foodie

@Dao
interface FoodieDao {
    @Insert
    suspend fun insert(foodie: Foodie)
    @Update
    suspend fun update(foodie: Foodie)
    @Query("SELECT * FROM Foodie ORDER BY namaMenu ASC")
    fun getFoodie(): Flow<List<Foodie>>
    @Query("SELECT * FROM Foodie WHERE id = :id")
    suspend fun getFoodieByID(id: Long): Foodie?
    @Query("DELETE FROM Foodie WHERE id = :id")
    suspend fun deleteById(id:Long)
}