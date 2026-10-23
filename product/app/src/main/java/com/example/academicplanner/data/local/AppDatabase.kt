package com.example.academicplanner.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.academicplanner.data.local.dao.ModuleDao
import com.example.academicplanner.data.local.entity.ModuleEntity

/**
 * Main Room SQLite database.
 * Creates and manages the database(SQLite file) on the phone's storage.
 */
@Database(
    entities = [ModuleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moduleDao(): ModuleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        /**
        Singleton pattern used to return single instance of the database across the app.
        */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "academic_planner.db" //Name of the database
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}