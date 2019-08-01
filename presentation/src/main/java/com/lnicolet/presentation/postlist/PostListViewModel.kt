package com.lnicolet.presentation.postlist

import androidx.lifecycle.ViewModel
import com.lnicolet.presentation.base.BaseIntent
import com.lnicolet.presentation.base.BaseViewModel
import com.lnicolet.presentation.base.TaskStatus
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


open class PostListViewModel @Inject constructor(
    private val postListProcessor: PostListProcessor
) : ViewModel(), BaseViewModel<PostListIntent, PostListViewState> {

    private var intentsSubject: PublishSubject<PostListIntent> = PublishSubject.create()
    private val intentFilter: ObservableTransformer<PostListIntent, PostListIntent> =
        ObservableTransformer { outerObservable ->
            outerObservable.publish { innerObservable ->
                Observable.merge(
                    innerObservable.ofType(PostListIntent.InitialIntent::class.java).take(1),
                    innerObservable.filter { intent -> intent !is PostListIntent.InitialIntent }
                )
            }
        }

    private val reducer: BiFunction<PostListViewState, PostListResult, PostListViewState> =
        BiFunction { _, result ->
            when (result) {
                is PostListResult.LoadPostListTask -> {
                    when (result.status) {
                        TaskStatus.SUCCESS -> PostListViewState.Success(result.postList.orEmpty())
                        TaskStatus.FAILURE -> PostListViewState.Failed
                        TaskStatus.LOADING -> PostListViewState.InProgress
                    }
                }
            }
        }
    private val statesSubject: Observable<PostListViewState> = compose()

    override fun processIntents(intents: Observable<PostListIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<PostListViewState> = statesSubject

    private fun compose(): Observable<PostListViewState> =
        intentsSubject.compose(intentFilter)
            .map { this.actionFromIntent(it) }
            .compose(postListProcessor.actionProcessor)
            .scan<PostListViewState>(PostListViewState.Idle, reducer)
            .replay(1)
            .autoConnect(0)

    private fun actionFromIntent(intent: BaseIntent): PostListAction =
        when (intent) {
            is PostListIntent.InitialIntent -> PostListAction.LoadPostList
            is PostListIntent.ReloadIntent -> PostListAction.LoadPostList
            is PostListIntent.LoadPostListIntent -> PostListAction.LoadPostList
            else -> throw UnsupportedOperationException(
                "Ooops, that looks like an unknown intent: $intent"
            )
        }

}