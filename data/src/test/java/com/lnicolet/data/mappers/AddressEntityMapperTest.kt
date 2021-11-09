package com.lnicolet.data.mappers

import com.lnicolet.data.entities.AddressEntity
import com.lnicolet.data.entities.GeoEntity
import com.lnicolet.domain.models.GeoDomainModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class AddressEntityMapperTest {

    companion object {
        private const val CITY = "London"
        private const val STREET = "Bigland Street"
        private const val SUITE = "80"
        private const val ZIPCODE = "E12ND"

    }

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var geoMapper: GeoEntityMapper
    lateinit var geoEntity: GeoEntity
    private lateinit var addressEntity: AddressEntity

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
        geoEntity = GeoEntity(
            "",
            ""
        )
        addressEntity = AddressEntity(
            STREET,
            SUITE,
            CITY,
            ZIPCODE,
            geoEntity
        )
    }

    @Test
    fun `verify city is correct in domain model after mapping`() {
        val mapper = AddressEntityMapper(geoMapper)

        Mockito.`when`(geoMapper.mapToDomain(geoEntity))
            .thenReturn(GeoDomainModel("", ""))

        val domainModel = mapper.mapToDomain(addressEntity)
        assert(domainModel?.city == CITY)
    }

    @Test
    fun `verify street is correct in domain model after mapping`() {
        val mapper = AddressEntityMapper(geoMapper)

        Mockito.`when`(geoMapper.mapToDomain(geoEntity))
            .thenReturn(GeoDomainModel("", ""))

        val domainModel = mapper.mapToDomain(addressEntity)
        assert(domainModel?.street == STREET)
    }

    @Test
    fun `verify suite is correct in domain model after mapping`() {
        val mapper = AddressEntityMapper(geoMapper)

        Mockito.`when`(geoMapper.mapToDomain(geoEntity))
            .thenReturn(GeoDomainModel("", ""))

        val domainModel = mapper.mapToDomain(addressEntity)
        assert(domainModel?.suite == SUITE)
    }

    @Test
    fun `verify zipCode is correct in domain model after mapping`() {
        val mapper = AddressEntityMapper(geoMapper)

        Mockito.`when`(geoMapper.mapToDomain(geoEntity))
            .thenReturn(GeoDomainModel("", ""))

        val domainModel = mapper.mapToDomain(addressEntity)
        assert(domainModel?.zipCode == ZIPCODE)
    }
}