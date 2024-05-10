package org.d3if0105.foodiefiends.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if0105.foodiefiends.model.Foodie

@Database(entities = [Foodie::class], version = 1, exportSchema = false)
abstract class FoodieDb : RoomDatabase() {
    abstract val dao: FoodieDao

    companion object {
        @Volatile
        private var INSTANCE: FoodieDb? = null

        fun getInstance(context: Context): FoodieDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoodieDb::class.java,
                        "Foodie.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}