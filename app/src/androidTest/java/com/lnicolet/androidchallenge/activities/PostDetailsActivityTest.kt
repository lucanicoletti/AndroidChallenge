package com.lnicolet.androidchallenge.activities

import android.content.Intent
import androidx.appcompat.widget.AppCompatImageButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.lnicolet.androidchallenge.R
import com.lnicolet.presentation.postlist.model.User
import com.lnicolet.presentation.postlist.model.Post
import com.lnicolet.androidchallenge.idlingresources.EspressoIdlingResource
import com.lnicolet.androidchallenge.postdetails.activities.PostDetailsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Luca Nicoletti
 * on 21/04/2019
 */

@RunWith(AndroidJUnit4::class)
class PostDetailsActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<PostDetailsActivity> =
        ActivityTestRule(
            PostDetailsActivity::class.java,
            false,
            false
        )

    private lateinit var postIntent: Intent
    private lateinit var userResource: EspressoIdlingResource
    private lateinit var userLoadingResource: EspressoIdlingResource
    private lateinit var commentsResource: EspressoIdlingResource
    private lateinit var commentsLoadingResource: EspressoIdlingResource

    @Test
    fun verifyThatBackButtonIsPresent() {
        val targetContext = getInstrumentation().targetContext
        postIntent = PostDetailsActivity.getIntent(
            targetContext,
            Post(
                1,
                3,
                "title",
                "body",
                null
            )
        )
        activityRule.launchActivity(postIntent)
        onView(isAssignableFrom(AppCompatImageButton::class.java)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyUserIsBeingFetchedWithNullValue() {
        // This actually fails as Espresso is not fast enough to perform the check
        // The Endpoints responds too fast and the loading is hidden to display the user data
        // Idk how to make espresso faster :(
        val targetContext = getInstrumentation().targetContext
        postIntent = PostDetailsActivity.getIntent(
            targetContext,
            Post(
                1,
                3,
                "title",
                "body",
                null
            )
        )
        activityRule.launchActivity(postIntent)
        userLoadingResource = activityRule.activity.userLoadingIdlingResource
        IdlingRegistry.getInstance().register(userLoadingResource.idlingResource)
        onView(withId(R.id.pb_user_loading)).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(userLoadingResource.idlingResource)
    }

    @Test
    fun verifyUserIsNotBeingFetchedWithValue() {
        val targetContext = getInstrumentation().targetContext
        postIntent = PostDetailsActivity.getIntent(
            targetContext,
            Post(
                1,
                3,
                "title",
                "body",
                User(
                    3,
                    "Luca",
                    "luca_nicoletti",
                    "luca.nicolett@gmail.com",
                    "07564168666",
                    "no_web_site",
                    "empty_url"
                )
            )
        )
        activityRule.launchActivity(postIntent)
        userResource = activityRule.activity.userIdlingResource
        IdlingRegistry.getInstance().register(userResource.idlingResource)
        onView(withId(R.id.cv_user_image)).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(userResource.idlingResource)
    }

    @Test
    fun verifyUserAndCommentsAreBeingFetched() {
        // This actually fails as Espresso is not fast enough to perform the check
        // The Endpoints responds too fast and the loading is hidden to display the user data
        val targetContext = getInstrumentation().targetContext
        postIntent = PostDetailsActivity.getIntent(
            targetContext,
            Post(
                1,
                3,
                "title",
                "body",
                null
            )
        )
        activityRule.launchActivity(postIntent)
        commentsLoadingResource = activityRule.activity.commentsLoadingIdlingResource
        IdlingRegistry.getInstance().register(commentsLoadingResource.idlingResource)
        onView(withId(R.id.pb_comments_loading)).check(matches(isDisplayed()))
        onView(withId(R.id.pb_user_loading)).check(matches(isDisplayed()))
        IdlingRegistry.getInstance().unregister(commentsLoadingResource.idlingResource)
    }

    @Test
    fun verifyCommentsAreDisplayedCorrectly() {
        val targetContext = getInstrumentation().targetContext
        postIntent = PostDetailsActivity.getIntent(
            targetContext,
            Post(
                1,
                3,
                "title",
                "body",
                null
            )
        )
        activityRule.launchActivity(postIntent)
        commentsResource = activityRule.activity.commentsIdlingResource
        IdlingRegistry.getInstance().register(commentsResource.idlingResource)
        // I expect 7 children: 5 comments (so 5 customViews added) and 2 for: 1. Loading 2. No comments TextView
        onView(withId(R.id.cv_comments_container)).check(matches(hasChildCount(7)))
        IdlingRegistry.getInstance().unregister(commentsResource.idlingResource)
    }

}