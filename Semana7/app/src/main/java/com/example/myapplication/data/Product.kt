package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import androidx.room.ColumnInfo


@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "provider") val provider: String,
    @ColumnInfo(name = "brand") val brand: String,
    @ColumnInfo(name = "date") val date: String
)
