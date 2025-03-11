package com.rensystem.a06_simple_mvvm_arquitecture.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rensystem.a06_simple_mvvm_arquitecture.domain.model.Quote

@Entity(tableName = "quote_table")
data class QuoteEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int=0,

    @ColumnInfo(name = "quote")
    val quote:String,

    @ColumnInfo(name = "author")
    val author:String
)

//mapper
fun Quote.toDatabase() = QuoteEntity(quote = quote,author=author)
