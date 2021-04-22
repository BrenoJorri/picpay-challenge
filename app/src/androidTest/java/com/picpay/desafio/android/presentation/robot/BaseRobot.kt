package com.picpay.desafio.android.presentation.robot

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matchers.not

open class BaseRobot {

    fun ViewInteraction.isVisible(): ViewInteraction =
        this.check(matches(isDisplayed()))

    fun ViewInteraction.isNotVisible(): ViewInteraction =
        this.check(matches(not(isDisplayed())))
}