package com.example.data.mappers

import com.example.data.entities.CommentEntity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CommentEntityMapperTest {

    companion object {
        private const val POST_ID = 6
        private const val ID = 1
        private const val NAME = "username"
        private const val EMAIL = "email.email@email.com"
        private const val BODY = "body of the comment"
    }

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var commentEntity: CommentEntity

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
        commentEntity = CommentEntity(POST_ID, ID, NAME, EMAIL, BODY)
    }

    @Test
    fun `verify id is correct in domain model after mapping`() {
        val mapper = CommentEntityMapper()
        val domainModel = mapper.mapToDomain(commentEntity)

        assert(domainModel.id == ID)
    }

    @Test
    fun `verify post_id is correct in domain model after mapping`() {
        val mapper = CommentEntityMapper()
        val domainModel = mapper.mapToDomain(commentEntity)

        assert(domainModel.postId == POST_ID)
    }

    @Test
    fun `verify name is correct in domain model after mapping`() {
        val mapper = CommentEntityMapper()
        val domainModel = mapper.mapToDomain(commentEntity)

        assert(domainModel.name == NAME)
    }

    @Test
    fun `verify email is correct in domain model after mapping`() {
        val mapper = CommentEntityMapper()
        val domainModel = mapper.mapToDomain(commentEntity)

        assert(domainModel.email == EMAIL)
    }

    @Test
    fun `verify body is correct in domain model after mapping`() {
        val mapper = CommentEntityMapper()
        val domainModel = mapper.mapToDomain(commentEntity)

        assert(domainModel.body == BODY)
    }
}