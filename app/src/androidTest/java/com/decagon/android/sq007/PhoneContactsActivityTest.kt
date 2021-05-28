package com.decagon.android.sq007

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PhoneContactsActivityTest {

    @get: Rule
    val activityRule : ActivityScenarioRule<PhoneContactsActivity> = ActivityScenarioRule(PhoneContactsActivity::class.java)
    //Rule will be called every time a test is run. This saves the stress of manually launching activity
    //inside every test we write

    @Test
    fun test_Is_Activity_In_View() {
        //Verify that phone contacts activity is displayed
        Espresso.onView(withId(R.id.phone_contacts_activity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_Is_Fragment_Container_In_View() {
        //Verify that fragment container is in view
        Espresso.onView(withId(R.id.fragment_container_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_Is_Saved_Contacts_Menu_Button_In_View() {
        //Verify that saved contacts menu button is in view
        Espresso.onView(withId(R.id.saved_contacts_menu))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}