package com.example.data.mappers

import com.example.data.entities.CompanyEntity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CompanyEntityMapperTest {

    companion object {
        private const val NAME = "Babylon"
        private const val CATCH_PHRASE = "Catch phrase test"
        private const val BS = "BS"
    }

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var companyEntity: CompanyEntity

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
        companyEntity = CompanyEntity(NAME, CATCH_PHRASE, BS)
    }

    @Test
    fun `verify name is correct in domain model after mapping`() {
        val mapper = CompanyEntityMapper()
        val domainModel = mapper.mapToDomain(companyEntity)

        assert(domainModel.name == NAME)
    }

    @Test
    fun `verify bs is correct in domain model after mapping`() {
        val mapper = CompanyEntityMapper()
        val domainModel = mapper.mapToDomain(companyEntity)

        assert(domainModel.bs == BS)
    }

    @Test
    fun `verify catch_phrase is correct in domain model after mapping`() {
        val mapper = CompanyEntityMapper()
        val domainModel = mapper.mapToDomain(companyEntity)

        assert(domainModel.catchPhrase == CATCH_PHRASE)
    }
}