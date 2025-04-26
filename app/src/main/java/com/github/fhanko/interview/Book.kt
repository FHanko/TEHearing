package com.github.fhanko.interview

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val author: String,
    val isbn: String?
)

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Insert
    fun insert(book: Book)
}