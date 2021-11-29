package com.lnicolet.androidchallenge.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lnicolet.androidchallenge.postlist.mappers.PostsMapper
import com.lnicolet.androidchallenge.postlist.viewmodels.PostListViewState
import com.lnicolet.androidchallenge.postlist.viewmodels.PostsListViewModel
import com.lnicolet.androidchallenge.utils.CoroutineTestRule
import com.lnicolet.androidchallenge.utils.TestDispatcherProvider
import com.lnicolet.domain_lib.models.PostDomainModel
import com.lnicolet.domain_lib.models.UserDomainModel
import com.lnicolet.domain_lib.repositories.PostsRepository
import com.lnicolet.domain_lib.repositories.UsersRepository
import com.lnicolet.domain_lib.usecases.PostsAndUsersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Luca Nicoletti
 * on 18/04/2019
 */


@RunWith(MockitoJUnitRunner::class)
class PostListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = CoroutineTestRule()
    @get:Rule
    var liveDataRule = InstantTaskExecutorRule()

    @Mock
    lateinit var postsRepository: PostsRepository
    @Mock
    lateinit var userRepository: UsersRepository
    @Mock
    lateinit var postsMapper: PostsMapper
    @Mock
    lateinit var postsListViewStateObserver: Observer<PostListViewState>
    @Mock
    lateinit var mockPostDomainModel: PostDomainModel
    @Mock
    lateinit var mockUserDomainModel: UserDomainModel

    private lateinit var postAndUserUseCase: PostsAndUsersUseCase
    private lateinit var postsListViewModel: PostsListViewModel
    private val testDispatcherProvider = TestDispatcherProvider()

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that success response create correct view state`() = runBlockingTest {
        // Arrange
        val validPostsResponse = listOf(mockPostDomainModel, mockPostDomainModel, mockPostDomainModel)
        val validUsersResponse = listOf(mockUserDomainModel, mockUserDomainModel, mockUserDomainModel)
        Mockito.`when`(postsRepository.getPosts())
            .thenReturn(validPostsResponse)
        Mockito.`when`(userRepository.getUsers())
            .thenReturn(validUsersResponse)

        // Act
        setupViewModel()

        // Assert
        Mockito.verify(postsListViewStateObserver).onChanged(PostListViewState.Success(postsMapper.mapToPresentation(validPostsResponse)))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that error response create correct view state`() = runBlockingTest {
        // Arrange
        val message = "Unknown error"
        Mockito.`when`(postsRepository.getPosts())
            .thenThrow(IllegalStateException(message))

        // Act
        setupViewModel()

        // Assert
        Mockito.verify(postsListViewStateObserver).onChanged(PostListViewState.Error(message))
    }

    private fun setupViewModel() {
        postAndUserUseCase = PostsAndUsersUseCase(postsRepository, userRepository)
        postsListViewModel = PostsListViewModel(postAndUserUseCase, postsMapper, testDispatcherProvider)
        setupObservers()
    }

    private fun setupObservers() {
        postsListViewModel.postListViewState.observeForever(postsListViewStateObserver)
    }
}