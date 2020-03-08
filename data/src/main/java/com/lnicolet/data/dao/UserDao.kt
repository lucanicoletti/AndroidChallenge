package com.lnicolet.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lnicolet.data.entities.UserEntity
import io.reactivex.Observable

@Dao
interface UserDao {

    @Query("SELECT * from user_table WHERE id = :id")
    fun getUserById(id: Int): UserEntity

    @Query("SELECT * from user_table ")
    fun getUsers(): Observable<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(addressEntity: UserEntity)

    @Query("DELETE FROM user_table")
    fun deleteAll()
}