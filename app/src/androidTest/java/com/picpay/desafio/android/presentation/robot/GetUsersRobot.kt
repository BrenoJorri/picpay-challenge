package com.globo.search.presentation.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.globo.search.R
import com.globo.search.utils.custommatchers.withBoldText
import com.globo.search.utils.selectTabAtPosition
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString

fun searchRobot(func: SearchRobot.() -> Unit) = SearchRobot().apply { func() }

class SearchRobot : BaseRobot() {

    fun verifySearchBarIsVisible() =
        onView(
            allOf(
                withId(R.id.search_bar),
                withParent(withId(R.id.header_include))
            )
        )
            .isVisible()

    fun verifySearchBarHint() {
        onView(withId(R.id.search_src_text))
            .check(matches(withHint(containsString("Pesquisar no"))))
    }

    fun clickSearchBar() {
        onView(
            allOf(
                withId(R.id.search_bar),
                withParent(withId(R.id.header_include))
            )
        )
            .perform(click())
    }

    fun verifyRecentSearchIsVisible() =
        onView(withId(R.id.recent_search_title))
            .isVisible()

    fun verifyRecentSearchIsNotVisible() =
        onView(withId(R.id.recent_search_title))
            .isNotVisible()

    fun clickRecentItem(itemPosition: Int) {
        onView(withId(R.id.recent_search_recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemPosition,
                    click()
                )
            )
    }

    fun clickDeleteSearchItem() {
        onView(withId(R.id.search_item_delete))
            .perform(click())
    }

    fun typeSearchQuery(query: String) {
        onView(withId(R.id.search_src_text))
            .perform(click())
            .perform(typeText(query))
    }

    fun verifySuggestionListIsVisible() =
        onView(withId(R.id.search_list))
            .isVisible()

    fun verifySuggestionListIsNotVisible() =
        onView(withId(R.id.search_list))
            .isNotVisible()

    fun verifySuggestionIsBold() {
        onView(
            allOf(
                withParent(
                    allOf(
                        isDescendantOfA(withId(R.id.search_list)),
                        withClassName(containsString("ConstraintLayout")),
                        withParentIndex(0)
                    )
                ),
                withId(R.id.search_item_text),
                withBoldText(R.id.search_item_text)
            )
        )
            .isVisible()
    }

    fun clickSearchByQuery() {
        onView(withId(R.id.search_list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(
                        withSubstring("Buscar")
                    ), click()
                )
            )
    }

    fun verifySearchResultIsVisible() {
        onView(withId(R.id.search_view_pager))
            .isVisible()
    }

    fun selectResultTab(tabPosition: Int) {
        onView(withId(R.id.search_tab_layout))
            .perform(selectTabAtPosition(tabPosition))
    }

    fun verifyTabIsSelected(tabPosition: Int) {
        onView(
            allOf(
                isDescendantOfA(withId(R.id.search_tab_layout)),
                withClassName(containsString("TabView")),
                withParentIndex(tabPosition)
            )
        )
            .check(matches(isSelected()))
    }

    fun verifyExploreItemsAreDisplayed(totalItems: Int) {
        (0 until totalItems).forEach {
            onView(getExploreItem(it))
                .isVisible()
        }
    }

    fun verifyExploreItemLayout(itemPosition: Int, title: String) {
        onView(
            allOf(
                withParent(getExploreItem(itemPosition)),
                withId(R.id.explore_item_title)
            )
        )
            .isVisible()
            .check(matches(withSubstring(title)))

        onView(
            allOf(
                withParent(getExploreItem(itemPosition)),
                withId(R.id.explore_item_thumbnail)
            )
        )
            .isVisible()

        onView(
            allOf(
                withParent(getExploreItem(itemPosition)),
                withId(R.id.explore_item_thumbnail_gradient)
            )
        )
            .isNotVisible()
    }

    private fun getExploreItem(itemPosition: Int) =
        allOf(
            withParentIndex(itemPosition),
            withClassName(containsString("CardView"))
        )

    fun clickExploreItem(itemPosition: Int) {
        onView(withId(R.id.search_explore_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemPosition,
                    click()
                )
            )
    }
}