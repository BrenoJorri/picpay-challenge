package com.picpay.desafio.android.presentation

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.R
import com.picpay.desafio.android.presentation.robot.usersRobot
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @Before
    fun setUp() {
        launchActivity<MainActivity>()
    }

    @Test
    fun mainActivity_shouldPresentLoading() {
        onView(withId(R.id.user_list_progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun mainActivity_shouldPresentUsersList() {
        usersRobot {
            verifyViewIsVisible(R.id.user_list_progress_bar)
            Thread.sleep(REQUEST_TIME)
            verifyViewIsVisible(R.id.recyclerView)
        }
    }

    @Test
    fun userListAdapter_ShouldHaveCorrectUsername() {
        usersRobot {
            Thread.sleep(REQUEST_TIME)
            verifyViewIsVisible(R.id.recyclerView)
            verifyDescendantViewIsVisible(R.id.username, -1)
            verifyTextInRecyclerPosition(R.id.recyclerView, text = "@eduardo.santos")
        }
    }

    @Test
    fun userListAdapter_ShouldHaveCorrectName() {
        usersRobot {
            Thread.sleep(REQUEST_TIME)
            verifyViewIsVisible(R.id.recyclerView)
            verifyDescendantViewIsVisible(R.id.name, -1)
            verifyTextInRecyclerPosition(R.id.recyclerView, position = 32, text = "@mario.campos")
        }
    }


    companion object {
        private const val REQUEST_TIME = 1500L
    }
}