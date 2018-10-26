package com.nikkuts.lastfmapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.nikkuts.lastfmapp.utils.ViewActionRecyclerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        Intents.init();
    }

    @Test
    public void ensureArtistSearchActivityStarts() {
        onView(withId(R.id.search_button))
                .perform(click());

        intended(hasComponent(ArtistSearchActivity.class.getName()));
    }

    @Test
    public void ensureAlbumInfoActivityStarts() {
        onView(withId(R.id.main_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(LocalAlbumInfoActivity.class.getName()));
    }

    @Test
    public void ensureElementDeletesFromRecyclerView() {
        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.main_recycler_view);
        int itemCountBefore = recyclerView.getAdapter().getItemCount();
        if (itemCountBefore > 0) {
            onView(withId(R.id.main_recycler_view)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(0, ViewActionRecyclerView.clickChildViewWithId(R.id.checkbox_like)));

            assertEquals(recyclerView.getAdapter().getItemCount() + 1, itemCountBefore);
        }
        else {
            fail("There is no saved albums");
        }
    }

    @Test
    public void ensureElementDeletesFromAlbumInfoActivity() {
        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.main_recycler_view);
        int itemCountBefore = recyclerView.getAdapter().getItemCount();
        if (itemCountBefore > 0) {

            onView(withId(R.id.main_recycler_view)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(0, click()));

            onView(withId(R.id.floating_action_button)).perform(click());

            Espresso.pressBack();

            assertEquals(recyclerView.getAdapter().getItemCount() + 1, itemCountBefore);
        }
        else {
            fail("There is no saved albums");
        }
    }

    @After
    public void release(){
        Intents.release();
    }
}

