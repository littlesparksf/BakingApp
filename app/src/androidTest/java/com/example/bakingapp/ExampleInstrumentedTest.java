package com.example.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapp.Activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(
            MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run.  In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.bakingapp", appContext.getPackageName());
    }

//    @Test
//    public void intentTest(){
//
//        // Let the UI load completely first
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        //Recyclerview scroll to position
//        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.scrollToPosition(4));
//
//        //Perform Recyclerview click on item at position
//        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
//        //Recyclerview scroll to position
//        onView(withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.scrollToPosition(4));
//
//        //Perform Recyclerview click on item at position
//        onView(withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//
////        //Check if intent (RecipeActivity to RecipeDetailsActivity) has RECIPE_INTENT_EXTRA
////        intended(hasExtraWithKey(ConstantsUtil.RECIPE_INTENT_EXTRA));
////
////        //Perform click action on start cooking button
////        onView(withId(R.id.btn_start_cooking)).perform(ViewActions.click());
////
////        //Check if intent (RecipeDetailsActivity to CookingActivity) has RECIPE_INTENT_EXTRA
////        intended(hasComponent(RecipeActivity.class.getName()));
//    }
}
