package com.picpay.desafio.android.presentation.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.picpay.desafio.android.RecyclerViewMatchers
import org.hamcrest.Matchers

fun usersRobot(func: UsersRobot.() -> Unit) = UsersRobot().apply { func() }

class UsersRobot : BaseRobot() {

    fun verifyViewIsVisible(id: Int) =
        onView(withId(id)).isVisible()

    fun verifyDescendantViewIsVisible(viewId: Int, parentViewId: Int): ViewInteraction =
        onView(Matchers.allOf(withId(viewId), withParent(withId(parentViewId))))

    fun verifyTextInRecyclerPosition(recyclerViewId: Int, position: Int = 0, text: String) {
        onView(withId(recyclerViewId))
            .check(
                matches(
                    RecyclerViewMatchers.atPosition(
                        position,
                        hasDescendant(withText(text))
                    )
                )
            )
    }
}