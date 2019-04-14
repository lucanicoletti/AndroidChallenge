package com.example.data

import com.example.data.entities.GeoEntity
import com.example.data.mappers.GeoEntityMapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class GeoEntityMapperTest {
    companion object {
        private const val LAT = "51.514231"
        private const val LNG = "-0.057124"
    }

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var geoEntity: GeoEntity

    @Before
    fun `prepare for test`() {
        geoEntity = GeoEntity(LAT, LNG)
    }

    @Test
    fun `verify LAT is correct in domain model after mapping`() {
        val mapper = GeoEntityMapper()

        val domainModel = mapper.mapToDomain(geoEntity)

        assert(domainModel.lat == LAT)
    }

    @Test
    fun `verify LNG is correct in domain model after mapping`() {
        val mapper = GeoEntityMapper()

        val domainModel = mapper.mapToDomain(geoEntity)

        assert(domainModel.lng == LNG)
    }
}