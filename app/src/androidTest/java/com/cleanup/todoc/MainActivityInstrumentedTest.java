package com.cleanup.todoc;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.toDoList.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.cleanup.todoc.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    private TodocDatabase db;

    // Populates the test database with the projects
    private static RoomDatabase.Callback prepopulateDatabase() {
        return new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues projectContentValues = new ContentValues();

                projectContentValues.put("id", 1L);
                projectContentValues.put("name", "Projet Tartampion");
                projectContentValues.put("color", 0xFFEADAD1);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);

                projectContentValues.put("id", 2L);
                projectContentValues.put("name", "Projet Lucidia");
                projectContentValues.put("color", 0xFFB4CDBA);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);

                projectContentValues.put("id", 3L);
                projectContentValues.put("name", "Projet Circus");
                projectContentValues.put("color", 0xFFA3CED2);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);
            }
        };
    }

    @Before
    public void initDb() throws Exception {
        this.db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .addCallback(prepopulateDatabase())
                .build();
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }


    @Test
    public void addAndRemoveTask() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        // Check that lblTask is not displayed anymore
        assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
        // Check that recyclerView is displayed
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
        // Check that it contains one element only
        assertThat(Objects.requireNonNull(listTasks.getAdapter()).getItemCount(), equalTo(1));

        onView(withId(R.id.img_delete)).perform(click());

        // Check that lblTask is displayed
        assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
        // Check that recyclerView is not displayed anymore
        assertThat(listTasks.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void sortTasks() {
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());


        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Tâche example")));

        // Sort alphabetical
        onView(withId(R.id.action_filter)).perform(click());
        onView(ViewMatchers.withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Tâche example")));

        // Sort alphabetical inverted
        onView(withId(R.id.action_filter)).perform(click());
        onView(ViewMatchers.withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Tâche example")));

        // Sort old first
        onView(withId(R.id.action_filter)).perform(click());
        onView(ViewMatchers.withText(R.string.sort_oldest_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Tâche example")));

        // Sort recent first
        onView(withId(R.id.action_filter)).perform(click());
        onView(ViewMatchers.withText(R.string.sort_recent_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Tâche example")));

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.img_delete))
                .perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.img_delete))
                .perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.img_delete))
                .perform(click());

    }

    @Test
    public void sortTasksThenRotateScreen() throws InterruptedException {
        MainActivity activity = rule.getActivity();

       onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        // Sort alphabetical inverted
        onView(withId(R.id.action_filter)).perform(click());
        onView(ViewMatchers.withText(R.string.sort_alphabetical_invert)).perform(click());
        // Check that tasks are sorted
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Tâche example")));

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Test thats tasks remain sorted
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("aaa Tâche example")));


        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.img_delete))
                .perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.img_delete))
                .perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.img_delete))
                .perform(click());

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

}
