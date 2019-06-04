package com.lnicolet.data.mappers

import com.lnicolet.data.entities.PostEntity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class PostEntityMapperTest {

    companion object {
        private const val USER_ID = 3
        private const val ID = 6
        private const val TITLE = "Title of the post"
        private const val BODY = "maybe a long post would not end like this."
    }

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var postEntity: PostEntity

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
        postEntity = PostEntity(USER_ID, ID, TITLE, BODY)
    }


    @Test
    fun `verify user_id is correct in domain model after mapping`() {
        val mapper = PostEntityMapper()
        val domainModel = mapper.mapToDomain(postEntity)

        assert(domainModel.userId == USER_ID)
    }

    @Test
    fun `verify id is correct in domain model after mapping`() {
        val mapper = PostEntityMapper()
        val domainModel = mapper.mapToDomain(postEntity)

        assert(domainModel.id == ID)
    }

    @Test
    fun `verify title is correct in domain model after mapping`() {
        val mapper = PostEntityMapper()
        val domainModel = mapper.mapToDomain(postEntity)

        assert(domainModel.title == TITLE)
    }

    @Test
    fun `verify body is correct in domain model after mapping`() {
        val mapper = PostEntityMapper()
        val domainModel = mapper.mapToDomain(postEntity)

        assert(domainModel.body == BODY)
    }
}