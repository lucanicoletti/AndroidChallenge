package com.lnicolet.presentation.postdetail

import com.lnicolet.domain.usecase.CommentsAndUserUseCase
import com.lnicolet.domain.usecase.CommentsUseCase
import com.lnicolet.presentation.postdetail.mapper.CommentsMapper
import com.lnicolet.presentation.postdetail.mapper.PostDetailMapper
import com.lnicolet.presentation.postlist.model.User
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class PostDetailProcessor @Inject constructor(
    private val commentsAndUserUseCase: CommentsAndUserUseCase,
    private val commentsUseCase: CommentsUseCase,
    private val postDetailMapper: PostDetailMapper,
    private val commentsMapper: CommentsMapper
) {

    private fun detailProcessor(params: CommentsAndUserUseCase.Params): ObservableTransformer<
        PostDetailAction, PostDetailResult> = ObservableTransformer { outerObservable ->
        outerObservable.switchMap { action ->
            when (action) {
                is PostDetailAction.LoadCommentsOnly -> {
                    loadOnlyComments(params.postId, action.user)
                }
                PostDetailAction.LoadCommentsAndUser -> {
                    loadCommentsAndUser(params)
                }
            }
        }
    }

    private fun loadCommentsAndUser(params: CommentsAndUserUseCase.Params)
        : Observable<PostDetailResult.LoadPostDetailTask> =
        commentsAndUserUseCase.getCommentsAndUser(params)
            .map {
                PostDetailResult.LoadPostDetailTask.bothFetched(postDetailMapper.mapToView(it))
            }
            .onErrorReturn {
                PostDetailResult.LoadPostDetailTask.failedBoth()
            }
            .toObservable()
            .startWith(PostDetailResult.LoadPostDetailTask.loadingBoth())

    private fun loadOnlyComments(postId: Int, user: User)
        : Observable<PostDetailResult.LoadPostDetailTask> =
        commentsUseCase.getCommentsByPostId(postId)
            .map {
                PostDetailResult.LoadPostDetailTask.commentsFetched(commentsMapper.mapToView(it))
            }
            .onErrorReturn {
                PostDetailResult.LoadPostDetailTask.failedComments()
            }
            .toObservable()
            .startWith(PostDetailResult.LoadPostDetailTask.loadingComments(user))

    lateinit var actionProcessor: ObservableTransformer<PostDetailAction, PostDetailResult>

    fun init(params: CommentsAndUserUseCase.Params) {
        this.actionProcessor = ObservableTransformer { outerObservable ->
            outerObservable.publish { innerObservable ->
                innerObservable.ofType(PostDetailAction::class.java)
                    .compose(detailProcessor(params))
                    .mergeWith(
                        innerObservable.filter {
                            it !is PostDetailAction.LoadCommentsAndUser ||
                                it !is PostDetailAction.LoadCommentsOnly
                        }.flatMap {
                            Observable.error<PostDetailResult>(
                                IllegalArgumentException("Unknown Action type")
                            )
                        }
                    )
            }
        }
    }

}