package com.lnicolet.presentation.postdetail

import com.lnicolet.domain.usecase.CommentsAndUserUseCase
import com.lnicolet.domain.usecase.CommentsUseCase
import com.lnicolet.presentation.postdetail.mapper.CommentsMapper
import com.lnicolet.presentation.postdetail.mapper.PostDetailMapper
import com.lnicolet.presentation.postlist.model.User
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostDetailProcessor @Inject constructor(
    private val commentsAndUserUseCase: CommentsAndUserUseCase,
    private val commentsUseCase: CommentsUseCase,
    private val postDetailMapper: PostDetailMapper,
    private val commentsMapper: CommentsMapper
) {

    private fun detailProcessor():
            ObservableTransformer<PostDetailAction, PostDetailResult> =
        ObservableTransformer { outerObservable ->
        outerObservable.switchMap { action ->
            when (action) {
                is PostDetailAction.LoadCommentsOnly -> {
                    loadOnlyComments(action.postId, action.user)
                }
                is PostDetailAction.LoadCommentsAndUser -> {
                    loadCommentsAndUser(
                            CommentsAndUserUseCase.Params(action.userId, action.postId)
                    )
                }
            }
        }
    }

    private fun loadCommentsAndUser(params: CommentsAndUserUseCase.Params)
        : Observable<PostDetailResult.LoadPostDetailTask> =
        commentsAndUserUseCase.getCommentsAndUser(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                PostDetailResult.LoadPostDetailTask.commentsFetched(user, commentsMapper.mapToView(it))
            }
            .onErrorReturn {
                PostDetailResult.LoadPostDetailTask.failedComments()
            }
            .toObservable()
            .startWith(PostDetailResult.LoadPostDetailTask.loadingComments(user))

    val actionProcessor: ObservableTransformer<PostDetailAction, PostDetailResult>

    init {
        this.actionProcessor = ObservableTransformer { outerObservable ->
            outerObservable.publish { innerObservable ->
                innerObservable.ofType(PostDetailAction::class.java)
                    .compose(detailProcessor())
            }
        }
    }

}