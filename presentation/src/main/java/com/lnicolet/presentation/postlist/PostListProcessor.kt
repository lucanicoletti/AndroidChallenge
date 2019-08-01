package com.lnicolet.presentation.postlist

import com.lnicolet.domain.usecase.PostsAndUsersUseCase
import com.lnicolet.presentation.postlist.mapper.PostsMapper
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostListProcessor @Inject constructor(
    private val postsAndUsersUseCase: PostsAndUsersUseCase,
    private val postsMapper: PostsMapper
) {

    private val postsProcessor: ObservableTransformer<PostListAction.LoadPostList, PostListResult> =
        ObservableTransformer {
            it.switchMap {
                postsAndUsersUseCase.getPostsWithUsers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { list ->
                        PostListResult.LoadPostListTask.success(postsMapper.mapToView(list))
                    }
                    .onErrorReturn {
                        PostListResult.LoadPostListTask.failure()
                    }
                    .toObservable()
                    .startWith(PostListResult.LoadPostListTask.loading())
            }
        }

    var actionProcessor: ObservableTransformer<PostListAction, PostListResult>

    init {
        this.actionProcessor = ObservableTransformer { outerObservable ->
            outerObservable.publish { innerObservable ->
                innerObservable.ofType(PostListAction.LoadPostList::class.java)
                    .compose(postsProcessor)
                    .mergeWith(innerObservable.filter { it !is PostListAction.LoadPostList }
                        .flatMap {
                            Observable.error<PostListResult>(
                                IllegalArgumentException("Unknown Action type")
                            )
                        })
            }
        }
    }
}