package com.example.c196.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196.DAO.AssessmentDAO;
import com.example.c196.DAO.CourseDAO;
import com.example.c196.DAO.TermDAO;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;

@Database(entities = {Terms.class, Courses.class, Assessments.class}, version=1, exportSchema = false)
public abstract class AppDBBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile AppDBBuilder INSTANCE;
    static AppDBBuilder getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDBBuilder.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDBBuilder.class, "AppDB")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return  INSTANCE;
    }
}
