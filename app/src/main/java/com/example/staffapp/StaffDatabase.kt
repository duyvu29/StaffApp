package com.example.staffapp

import androidx.room.Database
import androidx.room.RoomDatabase

// Class khoi tao database cho Room va chua 1 DAO interface
@Database(entities = [Staff::class], version = 1)
abstract class StaffDatabase : RoomDatabase() {
    abstract fun callDAO(): StaffDAO
}