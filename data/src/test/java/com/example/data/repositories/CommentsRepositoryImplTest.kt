package com.example.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.data.CommentsApi
import com.example.data.entities.CommentEntity
import com.example.data.mappers.CommentEntityMapper
import com.example.data.utils.RxSchedulerRule
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertFailsWith

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
    val rxRule = RxSchedulerRule()
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
    fun `verify that comments are fetched correctly with ok response`() {
        val listType = object : TypeToken<ArrayList<CommentEntity>>() {}.type
        val response = GsonBuilder().create().fromJson<ArrayList<CommentEntity>>(
            COMMENTS_LIST_OK, listType
        )

        Mockito.`when`(commentsApi.getComments())
            .thenReturn(Single.just(response))

        val observer = repository.getComments().test()
        observer.awaitTerminalEvent()

        observer
            .assertNoErrors()
            .assertComplete()
            .assertNoTimeout()
            .assertValue {
                it.isNotEmpty()
            }
    }

    @Test
    fun `verify that comments are fetched correctly with empty-ok response`() {
        val listType = object : TypeToken<ArrayList<CommentEntity>>() {}.type
        val response = GsonBuilder().create()
            .fromJson<ArrayList<CommentEntity>>(COMMENTS_LIST_OK_EMPTY, listType)

        Mockito.`when`(commentsApi.getComments())
            .thenReturn(Single.just(response))

        val observer = repository.getComments().test()
        observer.awaitTerminalEvent()

        observer
            .assertNoErrors()
            .assertComplete()
            .assertNoTimeout()
            .assertValue {
                it.isEmpty()
            }
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
    fun `verify that comments by post id are fetched correctly with ok response`() {
        val listType = object : TypeToken<ArrayList<CommentEntity>>() {}.type
        val response = GsonBuilder().create().fromJson<ArrayList<CommentEntity>>(
            COMMENTS_LIST_OK, listType
        )

        Mockito.`when`(commentsApi.getCommentsByPost(any()))
            .thenReturn(Single.just(response))

        val observer = repository.getCommentsByPost(1).test()
        observer.awaitTerminalEvent()

        observer
            .assertNoErrors()
            .assertComplete()
            .assertNoTimeout()
            .assertValue {
                it.isNotEmpty() && it.all { commentDomainModel ->
                    commentDomainModel.postId == 1
                }
            }
    }

    @Test
    fun `verify that comments by post id are fetched correctly with empty-ok response`() {
        val listType = object : TypeToken<ArrayList<CommentEntity>>() {}.type
        val response = GsonBuilder().create()
            .fromJson<ArrayList<CommentEntity>>(COMMENTS_LIST_OK_EMPTY, listType)

        Mockito.`when`(commentsApi.getCommentsByPost(any()))
            .thenReturn(Single.just(response))

        val observer = repository.getCommentsByPost(1).test()
        observer.awaitTerminalEvent()

        observer
            .assertNoErrors()
            .assertComplete()
            .assertNoTimeout()
            .assertValue {
                it.isEmpty()
            }
    }
}