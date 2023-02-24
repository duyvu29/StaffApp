package com.example.staffapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/*
* Chứa những câu query thao tác trực tiếp với RoomDB
* */
@Dao
interface StaffDAO {
    @Insert
    fun addStaff(staff: Staff)

    @Query("SELECT * FROM staff")
    fun getAllStaff(): List<Staff>

    @Query("UPDATE staff SET full_name = :fullName, age = :age, country = :country WHERE id = :id")
    fun updateStaff(fullName: String, age: Int, country: String, id: Int)

    @Query("DELETE FROM staff WHERE id = :staffId")
    fun deleteStaff(staffId: Int)

    @Query("DELETE FROM staff")
    fun deleteAllStaff()
}