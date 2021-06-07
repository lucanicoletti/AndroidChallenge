package com.lnicolet.androidchallenge.mappers

import com.lnicolet.presentation.postdetail.mapper.CommentMapper
import com.lnicolet.domain.model.CommentDomainModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Created by Luca Nicoletti
 * on 19/04/2019
 */

class CommentsMapperTest {

    companion object {
        private const val POST_ID = 6
        private const val ID = 1
        private const val NAME = "username"
        private const val EMAIL = "email.email@email.com"
        private const val BODY = "body of the comment"
    }

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var commentDomainModel: CommentDomainModel

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
        commentDomainModel = CommentDomainModel(
            POST_ID,
            ID,
            NAME,
            EMAIL,
            BODY
        )
    }

    @Test
    fun `verify id is correct in domain model after mapping`() {
        val mapper = CommentMapper()
        val model = mapper.mapToView(commentDomainModel)

        assert(model.id == ID)
    }

    @Test
    fun `verify post_id is correct in domain model after mapping`() {
        val mapper = CommentMapper()
        val model = mapper.mapToView(commentDomainModel)

        assert(model.postId == POST_ID)
    }

    @Test
    fun `verify name is correct in domain model after mapping`() {
        val mapper = CommentMapper()
        val model = mapper.mapToView(commentDomainModel)

        assert(model.name == NAME)
    }

    @Test
    fun `verify body is correct in domain model after mapping`() {
        val mapper = CommentMapper()
        val model = mapper.mapToView(commentDomainModel)

        assert(model.body == BODY)
    }
}