package com.lnicolet.babylon.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.lnicolet.babylon.postlist.mappers.PostsMapper
import com.lnicolet.babylon.postlist.viewmodels.PostListViewState
import com.lnicolet.babylon.postlist.viewmodels.PostsListViewModel
import com.lnicolet.babylon.utils.RxSchedulerRule
import com.lnicolet.domain.models.PostDomainModel
import com.lnicolet.domain.models.UserDomainModel
import com.lnicolet.domain.repositories.PostsRepository
import com.lnicolet.domain.repositories.UsersRepository
import com.lnicolet.domain.usecases.CommentsAndUserUseCase
import com.lnicolet.domain.usecases.PostsAndUsersUseCase
import com.lnicolet.domain.usecases.PostsUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.powermock.modules.junit4.PowerMockRunner
import java.lang.Exception

/**
 * Created by Luca Nicoletti
 * on 18/04/2019
 */


@RunWith(PowerMockRunner::class)
class PostListViewModelTest {

    // Forces RxJava to execute on a specified thread for tests (Thanks to Tristan)
    @Rule
    val rxRule = RxSchedulerRule()
    @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
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

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
        setupLifecycleOwner()
    }

    @Test
    fun `verify that success response create correct view state`() {
        // Arrange
        val validPostsResponse = listOf(mockPostDomainModel, mockPostDomainModel, mockPostDomainModel)
        val validUsersResponse = listOf(mockUserDomainModel, mockUserDomainModel, mockUserDomainModel)
        Mockito.`when`(postsRepository.getPosts())
            .thenReturn(Single.just(validPostsResponse))
        Mockito.`when`(userRepository.getUsers())
            .thenReturn(Single.just(validUsersResponse))

        // Act
        setupViewModel()

        // Assert
        Mockito.verify(postsListViewStateObserver).onChanged(PostListViewState.Success(postsMapper.mapToPresentation(validPostsResponse)))
    }

    @Test
    fun `verify that error response create correct view state`() {
        // Arrange
        val message = "Unknown error"
        Mockito.`when`(postsRepository.getPosts())
            .thenReturn(Single.error(Exception(message)))
        Mockito.`when`(userRepository.getUsers())
            .thenReturn(Single.error(Exception(message)))

        // Act
        setupViewModel()

        // Assert
        Mockito.verify(postsListViewStateObserver).onChanged(PostListViewState.Error(message))
    }

    private fun setupViewModel() {
        postAndUserUseCase = PostsAndUsersUseCase(postsRepository, userRepository)
        postsListViewModel = PostsListViewModel(postAndUserUseCase, postsMapper)
        setupObservers()
    }

    private fun setupObservers() {
        postsListViewModel.postListViewState.observe(lifecycleOwner, postsListViewStateObserver)
    }

    private fun setupLifecycleOwner() {
        val lifecycle = LifecycleRegistry(lifecycleOwner)
        Mockito.`when`(lifecycleOwner.lifecycle).thenReturn(lifecycle)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

}