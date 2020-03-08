package com.lnicolet.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lnicolet.data.entities.CompanyEntity

@Dao
interface CompanyDao {
    @Query("SELECT * from company_table ORDER BY name")
    fun getCompanyOrderedByName(): List<CompanyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(addressEntity: CompanyEntity)

    @Query("DELETE FROM company_table")
    fun deleteAll()
}