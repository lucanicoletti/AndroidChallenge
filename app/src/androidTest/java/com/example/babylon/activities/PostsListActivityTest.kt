package com.example.babylon.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.babylon.R
import com.example.babylon.idlingresources.EspressoIdlingResource
import com.example.babylon.postlist.activities.PostsListActivity
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
    var activityRule: ActivityTestRule<PostsListActivity> = ActivityTestRule(PostsListActivity::class.java)

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
        onView(withId(R.id.rv_posts)).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(postsListIdlingResource.idlingResource)
    }

    @Test
    fun testLoadingIsDisplayed() {
        IdlingRegistry.getInstance().register(loadingIdlingResource.idlingResource)
        onView(withId(R.id.pb_loading_list)).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(loadingIdlingResource.idlingResource)
    }
}