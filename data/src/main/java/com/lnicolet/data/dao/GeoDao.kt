package com.lnicolet.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lnicolet.data.entities.GeoEntity

@Dao
interface GeoDao {

    @Query("SELECT * from geo_table")
    fun getGeoData(): List<GeoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(addressEntity: GeoEntity)

    @Query("DELETE FROM geo_table")
    fun deleteAll()
}