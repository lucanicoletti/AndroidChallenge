package com.lnicolet.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.lnicolet.data.CommentsApi
import com.lnicolet.data.entities.CommentEntity
import com.lnicolet.data.mappers.CommentEntityMapper
import com.lnicolet.data.utils.CoroutineTestRule
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CommentsRepositoryImplTest {

    companion object {
        private const val COMMENTS_LIST_OK = "[\n" +
            "  {\n" +
            "    \"postId\": 1,\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"id labore ex et quam laborum\",\n" +
            "    \"email\": \"Eliseo@gardner.biz\",\n" +
            "    \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"postId\": 1,\n" +
            "    \"id\": 2,\n" +
            "    \"name\": \"quo vero reiciendis velit similique earum\",\n" +
            "    \"email\": \"Jayne_Kuhic@sydney.com\",\n" +
            "    \"body\": \"est natus enim nihil est dolore omnis voluptatem numquam\\net omnis occaecati quod ullam at\\nvoluptatem error expedita pariatur\\nnihil sint nostrum voluptatem reiciendis et\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"postId\": 1,\n" +
            "    \"id\": 3,\n" +
            "    \"name\": \"odio adipisci rerum aut animi\",\n" +
            "    \"email\": \"Nikita@garfield.biz\",\n" +
            "    \"body\": \"quia molestiae reprehenderit quasi aspernatur\\naut expedita occaecati aliquam eveniet laudantium\\nomnis quibusdam delectus saepe quia accusamus maiores nam est\\ncum et ducimus et vero voluptates excepturi deleniti ratione\"\n" +
            "  }\n" +
            "]"
        private const val COMMENTS_LIST_OK_EMPTY = "[]"
        private const val COMMENTS_LIST_NON_OK = "{}"
    }

    @get:Rule
    val coroutineRule = CoroutineTestRule()
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner

    @Mock
    lateinit var commentsApi: CommentsApi

    private lateinit var commentsEntityMapper: CommentEntityMapper
    private lateinit var repository: CommentsRepositoryImpl

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
        commentsEntityMapper = CommentEntityMapper()

        repository = CommentsRepositoryImpl(commentsApi, commentsEntityMapper)
        setupLifeCycleOwner()
    }

    private fun setupLifeCycleOwner() {
        val lifecycle = LifecycleRegistry(lifeCycleOwner)
        Mockito.`when`(lifeCycleOwner.lifecycle).thenReturn(lifecycle)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @Test
    fun `verify that comments are fetched correctly with ok response`() = runBlockingTest {
        val listType = object : TypeToken<ArrayList<CommentEntity>>() {}.type
        val response = GsonBuilder().create().fromJson<ArrayList<CommentEntity>>(
            COMMENTS_LIST_OK, listType
        )

        Mockito.`when`(commentsApi.getComments())
            .thenReturn(response)

        val comments = repository.getComments()

        assert(comments.isNotEmpty())
    }

    @Test
    fun `verify that comments are fetched correctly with empty-ok response`() = runBlockingTest {
        val listType = object : TypeToken<ArrayList<CommentEntity>>() {}.type
        val response = GsonBuilder().create()
            .fromJson<ArrayList<CommentEntity>>(COMMENTS_LIST_OK_EMPTY, listType)

        Mockito.`when`(commentsApi.getComments())
            .thenReturn(response)

        val comments = repository.getComments()
        assert(comments.isEmpty())

    }

    @Test
    fun `verify that exception is thrown with non-ok response`() {
        val listType = object : TypeToken<ArrayList<CommentEntity>>() {}.type
        assertFailsWith(JsonSyntaxException::class) {
            val response = GsonBuilder().create().fromJson<ArrayList<CommentEntity>>(
                COMMENTS_LIST_NON_OK, listType
            )
        }
    }

    @Test
    fun `verify that comments by post id are fetched correctly with ok response`() = runBlockingTest {
        val listType = object : TypeToken<ArrayList<CommentEntity>>() {}.type
        val response = GsonBuilder().create().fromJson<ArrayList<CommentEntity>>(
            COMMENTS_LIST_OK, listType
        )

        Mockito.`when`(commentsApi.getCommentsByPost(any()))
            .thenReturn(response)

        val comments = repository.getCommentsByPost(1)
        assert(comments.isNotEmpty() && comments.all { commentDomainModel ->
            commentDomainModel.postId == 1
        })
    }

    @Test
    fun `verify that comments by post id are fetched correctly with empty-ok response`() = runBlockingTest {
        val listType = object : TypeToken<ArrayList<CommentEntity>>() {}.type
        val response = GsonBuilder().create()
            .fromJson<ArrayList<CommentEntity>>(COMMENTS_LIST_OK_EMPTY, listType)

        Mockito.`when`(commentsApi.getCommentsByPost(any()))
            .thenReturn(response)

        val comments = repository.getCommentsByPost(1)
        assert(comments.isEmpty())
    }
}