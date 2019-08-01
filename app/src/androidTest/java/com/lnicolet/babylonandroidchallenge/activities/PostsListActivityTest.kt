package com.lnicolet.babylon.activities

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.lnicolet.babylonandroidchallenge.idlingresources.EspressoIdlingResource
import com.lnicolet.babylonandroidchallenge.postlist.activity.PostsListActivity
import com.lnicolet.babylonandroidchallenge.screens.PostListActivityScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Luca Nicoletti
 * on 20/04/2019
 */

@RunWith(AndroidJUnit4::class)
class PostsListActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<PostsListActivity> =
        ActivityTestRule(PostsListActivity::class.java)

    private lateinit var loadingIdlingResource: EspressoIdlingResource
    private lateinit var postsListIdlingResource: EspressoIdlingResource

    @Before
    fun setUp() {
        loadingIdlingResource = activityRule.activity.loadingIdlingResource
        postsListIdlingResource = activityRule.activity.postsListIdlingResource
    }

    @Test
    fun testListIsLoaded() {
        IdlingRegistry.getInstance().register(postsListIdlingResource.idlingResource)
        //onView(withId(R.id.rv_posts)).check(matches(isDisplayed()))
        onScreen<PostListActivityScreen> {
            postListRecycler {
                firstChild<PostListActivityScreen.Item> {
                    isDisplayed()
                    title { hasAnyText() }
                }
            }
        }
        IdlingRegistry.getInstance().unregister(postsListIdlingResource.idlingResource)
    }

    @Test
    fun testLoadingIsDisplayed() {
        IdlingRegistry.getInstance().register(loadingIdlingResource.idlingResource)
        //onView(withId(R.id.pb_loading_list)).check(matches(isDisplayed()))
        onScreen<PostListActivityScreen> {
            loader {
                isDisplayed()
            }
        }
        IdlingRegistry.getInstance().unregister(loadingIdlingResource.idlingResource)
    }
}