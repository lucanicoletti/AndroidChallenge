package com.lnicolet.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.lnicolet.data.PostsApi
import com.lnicolet.data.entities.PostEntity
import com.lnicolet.data.entities.UserEntity
import com.lnicolet.data.mappers.PostEntityMapper
import com.lnicolet.data.utils.CoroutineTestRule
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
class PostsRepositoryImplTest {

    companion object {
        private const val POSTS_RESPONSE_OK = "[\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 1,\n" +
            "    \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
            "    \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 2,\n" +
            "    \"title\": \"qui est esse\",\n" +
            "    \"body\": \"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 3,\n" +
            "    \"title\": \"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\n" +
            "    \"body\": \"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 4,\n" +
            "    \"title\": \"eum et est occaecati\",\n" +
            "    \"body\": \"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 5,\n" +
            "    \"title\": \"nesciunt quas odio\",\n" +
            "    \"body\": \"repudiandae veniam quaerat sunt sed\\nalias aut fugiat sit autem sed est\\nvoluptatem omnis possimus esse voluptatibus quis\\nest aut tenetur dolor neque\"\n" +
            "  }\n" +
            "]"
        private const val POSTS_RESPONSE_OK_EMPTY = "[]"
        private const val POSTS_RESPONSE_NON_OK = "{\"test\": \"try this one\"}"
    }

    @get:Rule
    val coroutineRule = CoroutineTestRule()
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner
    @Mock
    lateinit var postsApi: PostsApi

    private lateinit var postEntityMapper: PostEntityMapper
    private lateinit var postRepository: PostsRepositoryImpl

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)

        postEntityMapper = PostEntityMapper()
        postRepository = PostsRepositoryImpl(postsApi, postEntityMapper)
    }

    @Test
    fun `verify that users are fetched correctly with ok response`() = runBlockingTest {
        val listType = object : TypeToken<ArrayList<PostEntity>>() {}.type
        val response =
            GsonBuilder().create()
                .fromJson<ArrayList<PostEntity>>(POSTS_RESPONSE_OK, listType)

        Mockito.`when`(postsApi.getPosts())
            .thenReturn(response)

        val posts = postRepository.getPosts()
        assert(posts.isNotEmpty())
    }

    @Test
    fun `verify that users are fetched correctly with empty-ok response`() = runBlockingTest {
        val listType = object : TypeToken<ArrayList<PostEntity>>() {}.type
        val response =
            GsonBuilder().create()
                .fromJson<ArrayList<PostEntity>>(POSTS_RESPONSE_OK_EMPTY, listType)

        Mockito.`when`(postsApi.getPosts())
            .thenReturn(response)

        val posts = postRepository.getPosts()
        assert(posts.isEmpty())
    }


    @Test
    fun `verify that exception is thrown with wrong response`() {
        val listType = object : TypeToken<ArrayList<PostEntity>>() {}.type

        assertFailsWith(JsonSyntaxException::class) {
            val response = GsonBuilder().create()
                .fromJson<ArrayList<UserEntity>>(POSTS_RESPONSE_NON_OK, listType)
        }
    }
}