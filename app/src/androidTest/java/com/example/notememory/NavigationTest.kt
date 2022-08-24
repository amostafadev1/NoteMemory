package com.example.notememory

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.containsString

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @Rule
    @JvmField
    val itemsActivity = ActivityTestRule(ItemsActivity::class.java)

    @Test
    fun selectNoteAfterNavigationDrawerChange() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_courses))

        val coursePosition = 0
        onView(withId(R.id.items_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CourseRecyclerAdapter.ViewHolder>(
                coursePosition, click()
            )
        )

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_notes))

        val notePosition = 0
        onView(withId(R.id.items_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<NoteRecyclerAdapter.ViewHolder>(
                notePosition, click()
            )
        )

        val note = DataManager.notes[notePosition]
        onView(withId(R.id.spinner_courses)).check(
            matches(withSpinnerText(containsString(note.course?.title)))
        )
        onView(withId(R.id.text_note_title)).check(
            matches(withText(containsString(note.title)))
        )
        onView(withId(R.id.text_note_text)).check(
            matches(withText(containsString(note.text)))
        )

    }
}