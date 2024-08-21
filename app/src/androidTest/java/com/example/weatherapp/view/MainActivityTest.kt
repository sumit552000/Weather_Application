package com.example.weatherapp.view

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import com.example.weatherapp.R
import org.junit.Test

class MainActivityTest{

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun lottieAnimationView_isDisplayed() {
        Espresso.onView(withId(R.id.lottieAnimationView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun cityName_isDisplayed() {
        Espresso.onView(withId(R.id.cityName))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun temperature_isDisplayed() {
        Espresso.onView(withId(R.id.temp))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun temperatureMaxAndMin_isDisplayed() {
        Espresso.onView(withId(R.id.max_temp))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.min_temp))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun dayAndDate_isDisplayed() {
        Espresso.onView(withId(R.id.day))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.date))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
