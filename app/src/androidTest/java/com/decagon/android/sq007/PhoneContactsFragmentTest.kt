package com.decagon.android.sq007

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PhoneContactsFragmentTest {

    @Test
    fun test_Is_Phone_Contacts_Fragment_InView() {
        //Launch phoneContacts fragment
        launchFragmentInContainer<PhoneContactsFragment>(themeResId = R.style.Theme_DecagonAndroid007)
        //Verify that profile fragment is displayed
        onView(withId(R.id.phone_contacts_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun test_Is_RecyclerView_InView() {
        //Launch phoneContacts fragment
        launchFragmentInContainer<PhoneContactsFragment>(themeResId = R.style.Theme_DecagonAndroid007)
        //Verify that profile fragment is displayed
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

}