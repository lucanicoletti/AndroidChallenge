package com.lnicolet.data.mappers

import com.lnicolet.data.entities.AddressEntity
import com.lnicolet.data.entities.CompanyEntity
import com.lnicolet.data.entities.UserEntity
import com.lnicolet.domain.model.AddressDomainModel
import com.lnicolet.domain.model.CompanyDomainModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
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


    @Mock
    lateinit var addressMapper: AddressEntityMapper
    @Mock
    lateinit var companyMapper: CompanyEntityMapper
    @Mock
    lateinit var addressDomainModel: AddressDomainModel
    @Mock
    lateinit var companyDomainModel: CompanyDomainModel

    private lateinit var addressEntity: AddressEntity
    private lateinit var companyEntity: CompanyEntity

    private lateinit var userEntity: UserEntity

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
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
        Mockito.`when`(addressMapper.mapToDomain(addressEntity))
            .thenReturn(addressDomainModel)
        Mockito.`when`(companyMapper.mapToDomain(companyEntity))
            .thenReturn(companyDomainModel)

        val mapper = UserEntityMapper(addressMapper, companyMapper)
        val domainModel = mapper.mapToDomain(userEntity)

        assert(domainModel.imageUrl == EXPECTED_IMAGE_URL)
    }

}