package com.example.staffapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Staff la 1 table trong DB
* */
@Entity (tableName = "staff")
class Staff {
    @PrimaryKey(autoGenerate = true) var id: Int? = -1
    @ColumnInfo(name = "full_name") var fullName: String = ""
    @ColumnInfo(name = "age") var age: Int = -1
    @ColumnInfo(name = "country") var country: String = ""

    constructor(id: Int?, fullName: String, age: Int, country: String) {
        this.id = id
        this.fullName = fullName
        this.age = age
        this.country = country
    }
}