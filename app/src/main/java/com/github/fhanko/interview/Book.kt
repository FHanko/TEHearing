package com.github.fhanko.interview

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

enum class ReadState { Unread, Partial, Read }

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String,
    val author: String,
    val isbn: String?,
    val notes: String?,
    val readState: ReadState
){
    @Ignore
    constructor(): this(null, "", "", null, null, ReadState.Unread)
}

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Query("SELECT * FROM books WHERE id == :id")
    fun getById(id: Int): Book?

    @Insert
    fun insert(book: Book)

    @Update
    fun update(book: Book)

    @Delete
    fun delete(book: Book)
}