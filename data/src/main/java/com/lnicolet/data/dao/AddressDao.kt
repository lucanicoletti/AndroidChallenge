package com.lnicolet.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lnicolet.data.entities.AddressEntity

@Dao
interface AddressDao {
    @Query("SELECT * from address_table ORDER BY city ASC")
    fun getAddress(): List<AddressEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(addressEntity: AddressEntity)

    @Query("DELETE FROM address_table")
    fun deleteAll()
}