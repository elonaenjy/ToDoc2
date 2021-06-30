package com.cleanup.todoc2.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc2.database.dao.ProjectDao;
import com.cleanup.todoc2.database.dao.TaskDao;
import com.cleanup.todoc2.model.Project;
import com.cleanup.todoc2.model.Task;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 2)
public abstract class TodocDatabase extends RoomDatabase {

    private static volatile TodocDatabase INSTANCE;

    // --- DAO ---
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    private static final int NUMBER_OF_THREADS = 5;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // --- INSTANCE ---
    public static final TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "ToDoc5.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues projectContentValues = new ContentValues();

//              Initialize Project
                projectContentValues.put("id", 1L);
                projectContentValues.put("name", "Projet Tartampion");
                projectContentValues.put("color", 0xFFEADAD1);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);

                projectContentValues.put("id", 2L);
                projectContentValues.put("name", "Projet Lucidia");
                projectContentValues.put("color", 0xFFB4CDBA);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);

                projectContentValues.put("id", 3L);
                projectContentValues.put("name", "Projet Circus ter");
                projectContentValues.put("color", 0xFFA3CED2);
                db.insert("Project", OnConflictStrategy.REPLACE, projectContentValues);

//              Initialize Task
                ContentValues taskContentValues = new ContentValues();

                long cpt = 0;
                long pid = 0;
                String pname = "";
                while (cpt < 10) {
                    Random random = new Random();
                    int nb;
                    pid = random.nextInt(3);
                    pid ++;
                    pname = "Tache no " +  String.valueOf(cpt);
                    taskContentValues.put("projectId", pid);
                    taskContentValues.put("name", pname);
                    taskContentValues.put("creationTimeStamp", new Date().getTime());

                    db.insert("Task", OnConflictStrategy.REPLACE, taskContentValues);
                    cpt ++;
                }
            }
        };
    }
}

