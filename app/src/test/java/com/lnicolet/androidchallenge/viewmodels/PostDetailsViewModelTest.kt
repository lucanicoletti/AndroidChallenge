package com.lnicolet.androidchallenge.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lnicolet.androidchallenge.postdetails.mappers.CommentMapper
import com.lnicolet.androidchallenge.postdetails.mappers.PostDetailMapper
import com.lnicolet.androidchallenge.postdetails.models.PostDetail
import com.lnicolet.androidchallenge.postdetails.models.User
import com.lnicolet.androidchallenge.postdetails.viewmodels.PostDetailsViewModel
import com.lnicolet.androidchallenge.postdetails.viewmodels.PostDetailsViewState
import com.lnicolet.androidchallenge.utils.CoroutineTestRule
import com.lnicolet.androidchallenge.utils.TestDispatcherProvider
import com.lnicolet.domain_lib.models.PostDetailDomainModel
import com.lnicolet.domain_lib.models.UserDomainModel
import com.lnicolet.domain_lib.repositories.CommentsRepository
import com.lnicolet.domain_lib.repositories.UsersRepository
import com.lnicolet.domain_lib.usecases.CommentsAndUserUseCase
import com.lnicolet.domain_lib.usecases.CommentsUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
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
class PostDetailsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineRule = CoroutineTestRule()
    @get:Rule
    var liveDataRule = InstantTaskExecutorRule()

    @Mock
    lateinit var commentsRepository: CommentsRepository

    @Mock
    lateinit var userRepository: UsersRepository

    @Mock
    lateinit var commentMapper: CommentMapper

    @Mock
    lateinit var postDetailMapper: PostDetailMapper

    @Mock
    lateinit var postDetailsViewStateObserver: Observer<PostDetailsViewState>

    private lateinit var commentsAndUserUseCase: CommentsAndUserUseCase
    private lateinit var commentsUseCase: CommentsUseCase
    private lateinit var postDetailsViewModel: PostDetailsViewModel
    private val testDispatcherProvider = TestDispatcherProvider()

    @Before
    fun `prepare for test`() {
        MockitoAnnotations.initMocks(this)
        setupViewModel()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that success response create correct view state when fetching user and comments`() = runBlockingTest {
        // Arrange
        val validDetailResponse = PostDetailDomainModel(
            UserDomainModel(1, "", "", "", mock(), "", "", mock(), ""),
            listOf()
        )
        Mockito.`when`(commentsRepository.getCommentsByPost(any()))
            .thenReturn(listOf())
        Mockito.`when`(userRepository.getUsersById(any()))
            .thenReturn(validDetailResponse.user)
        Mockito.`when`(postDetailMapper.mapToPresentation(validDetailResponse))
            .thenReturn(
                PostDetail(
                    User(1, "", "", "", "", "", ""),
                    listOf()
                )
            )

        // Act
        postDetailsViewModel.loadCommentsAndUserData(1, 1)

        // Assert
        postDetailsViewStateObserver.onChanged(
            PostDetailsViewState.SuccessBoth(
                postDetailMapper.mapToPresentation(validDetailResponse).user,
                postDetailMapper.mapToPresentation(validDetailResponse).commentList
            )
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that error response create correct view state when fetching user and comments`() = runBlockingTest {
        // Arrange
        Mockito.`when`(commentsRepository.getCommentsByPost(any())).thenThrow(SecurityException(""))

        // Act
        postDetailsViewModel.loadCommentsAndUserData(1, 1)

        // Assert
        Mockito.verify(postDetailsViewStateObserver)
            .onChanged(
                PostDetailsViewState.ErrorBoth("")
            )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that success response create correct view state when fetching only comments`() = runBlockingTest {
        // Arrange
        Mockito.`when`(commentsRepository.getCommentsByPost(any()))
            .thenReturn(listOf())

        // Act
        postDetailsViewModel.loadComments(1)

        // Assert
        Mockito.verify(postDetailsViewStateObserver)
            .onChanged(
                PostDetailsViewState.SuccessComments(listOf())
            )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `verify that error response create correct view state when fetching only comments`() = runBlockingTest {
        // Arrange
        Mockito.`when`(commentsRepository.getCommentsByPost(any())).thenThrow(IndexOutOfBoundsException(""))

        // Act
        postDetailsViewModel.loadComments(1)

        // Assert
        Mockito.verify(postDetailsViewStateObserver)
            .onChanged(
                PostDetailsViewState.ErrorComments("")
            )
    }

    private fun setupViewModel() {
        commentsAndUserUseCase = CommentsAndUserUseCase(commentsRepository, userRepository)
        commentsUseCase = CommentsUseCase(commentsRepository)
        postDetailsViewModel = PostDetailsViewModel(commentsAndUserUseCase, commentsUseCase, postDetailMapper, commentMapper, testDispatcherProvider)
        setupObservers()
    }

    private fun setupObservers() {
        postDetailsViewModel.postDetailsViewState.observeForever(
            postDetailsViewStateObserver
        )
    }
}