package com.github.fhanko.interview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        lateinit var instance: AppDatabase
            private set
        fun init(context: Context) {
            instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "app_data"
            ).build()
        }
    }
}