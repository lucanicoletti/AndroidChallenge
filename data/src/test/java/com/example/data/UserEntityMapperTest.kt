package com.example.data

import com.example.data.entities.AddressEntity
import com.example.data.entities.CompanyEntity
import com.example.data.entities.UserEntity
import com.example.data.mappers.AddressEntityMapper
import com.example.data.mappers.CompanyEntityMapper
import com.example.data.mappers.UserEntityMapper
import com.example.domain.models.AddressDomainModel
import com.example.domain.models.CompanyDomainModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class UserEntityMapperTest {

    companion object {
        private const val ID = 33
        private const val NAME = "Luca"
        private const val USERNAME = "luca.nicoletti"
        private const val EMAIL = "luca.nicolett@gmail.com"
        private const val PHONE = "07564168666"
        private const val WEBSITE = "www.google.com"

        private const val EXPECTED_IMAGE_URL = "https://api.adorable.io/avatars/256/$ID.png"
    }

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var addressEntity: AddressEntity
    private lateinit var companyEntity: CompanyEntity

    private lateinit var userEntity: UserEntity

    @Before
    fun `prepare for test`() {
        addressEntity = mock(AddressEntity::class.java)
        companyEntity = mock(CompanyEntity::class.java)

        userEntity = UserEntity(
            ID,
            NAME,
            USERNAME,
            EMAIL,
            addressEntity,
            PHONE,
            WEBSITE,
            companyEntity
        )
    }

    @Test
    fun `verify id is correct in domain model after mapping`() {
        val addressMapper = mock(AddressEntityMapper::class.java)
        val companyMapper = mock(CompanyEntityMapper::class.java)
        val addressDomainModel = mock(AddressDomainModel::class.java)
        val companyDomainModel = mock(CompanyDomainModel::class.java)

        Mockito.`when`(addressMapper.mapToDomain(addressEntity))
            .thenReturn(addressDomainModel)
        Mockito.`when`(companyMapper.mapToDomain(companyEntity))
            .thenReturn(companyDomainModel)

        val mapper = UserEntityMapper(addressMapper, companyMapper)
        val domainModel = mapper.mapToDomain(userEntity)

        assert(domainModel.id == ID)
    }

    @Test
    fun `verify name is correct in domain model after mapping`() {
        val addressMapper = mock(AddressEntityMapper::class.java)
        val companyMapper = mock(CompanyEntityMapper::class.java)
        val addressDomainModel = mock(AddressDomainModel::class.java)
        val companyDomainModel = mock(CompanyDomainModel::class.java)

        Mockito.`when`(addressMapper.mapToDomain(addressEntity))
            .thenReturn(addressDomainModel)
        Mockito.`when`(companyMapper.mapToDomain(companyEntity))
            .thenReturn(companyDomainModel)

        val mapper = UserEntityMapper(addressMapper, companyMapper)
        val domainModel = mapper.mapToDomain(userEntity)

        assert(domainModel.name == NAME)
    }

    @Test
    fun `verify username is correct in domain model after mapping`() {
        val addressMapper = mock(AddressEntityMapper::class.java)
        val companyMapper = mock(CompanyEntityMapper::class.java)
        val addressDomainModel = mock(AddressDomainModel::class.java)
        val companyDomainModel = mock(CompanyDomainModel::class.java)

        Mockito.`when`(addressMapper.mapToDomain(addressEntity))
            .thenReturn(addressDomainModel)
        Mockito.`when`(companyMapper.mapToDomain(companyEntity))
            .thenReturn(companyDomainModel)

        val mapper = UserEntityMapper(addressMapper, companyMapper)
        val domainModel = mapper.mapToDomain(userEntity)

        assert(domainModel.userName == USERNAME)
    }

    @Test
    fun `verify phone is correct in domain model after mapping`() {
        val addressMapper = mock(AddressEntityMapper::class.java)
        val companyMapper = mock(CompanyEntityMapper::class.java)
        val addressDomainModel = mock(AddressDomainModel::class.java)
        val companyDomainModel = mock(CompanyDomainModel::class.java)

        Mockito.`when`(addressMapper.mapToDomain(addressEntity))
            .thenReturn(addressDomainModel)
        Mockito.`when`(companyMapper.mapToDomain(companyEntity))
            .thenReturn(companyDomainModel)

        val mapper = UserEntityMapper(addressMapper, companyMapper)
        val domainModel = mapper.mapToDomain(userEntity)

        assert(domainModel.phone == PHONE)
    }

    @Test
    fun `verify website is correct in domain model after mapping`() {
        val addressMapper = mock(AddressEntityMapper::class.java)
        val companyMapper = mock(CompanyEntityMapper::class.java)
        val addressDomainModel = mock(AddressDomainModel::class.java)
        val companyDomainModel = mock(CompanyDomainModel::class.java)

        Mockito.`when`(addressMapper.mapToDomain(addressEntity))
            .thenReturn(addressDomainModel)
        Mockito.`when`(companyMapper.mapToDomain(companyEntity))
            .thenReturn(companyDomainModel)

        val mapper = UserEntityMapper(addressMapper, companyMapper)
        val domainModel = mapper.mapToDomain(userEntity)

        assert(domainModel.website == WEBSITE)
    }

    @Test
    fun `verify imageUrl is built correctly in domain model after mapping`() {
        val addressMapper = mock(AddressEntityMapper::class.java)
        val companyMapper = mock(CompanyEntityMapper::class.java)
        val addressDomainModel = mock(AddressDomainModel::class.java)
        val companyDomainModel = mock(CompanyDomainModel::class.java)

        Mockito.`when`(addressMapper.mapToDomain(addressEntity))
            .thenReturn(addressDomainModel)
        Mockito.`when`(companyMapper.mapToDomain(companyEntity))
            .thenReturn(companyDomainModel)

        val mapper = UserEntityMapper(addressMapper, companyMapper)
        val domainModel = mapper.mapToDomain(userEntity)

        assert(domainModel.imageUrl == EXPECTED_IMAGE_URL)
    }

}