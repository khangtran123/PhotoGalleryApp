package com.example.khang.photogalleryapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;




//Created by Khang on 28/09/2017.


@RunWith(AndroidJUnit4.class)
public class EspressoTesting_SearchPhotoByTime {

    @Rule
    public ActivityTestRule<MainMenu> mActivityRule =
            new ActivityTestRule<>(MainMenu.class);

    /*@Test
    public void gettoAdvancedPage(){
        onView(withId(R.id.btnSearch)).perform(click());
    } */

    @Test
    public void ensureButtonisClicked() {
        /*int year = 2017;
        int month = 07;
        int day = 01;
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.txtStartDate)).perform(click()); */
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.txtStartDate))
                .perform(typeText("2016:08:25"), closeSoftKeyboard());
        onView(withId(R.id.txtEndDate))
                .perform(typeText("2015:11:29"), closeSoftKeyboard());
        onView(withId(R.id.btnSearch)).perform(click());
    }

}


