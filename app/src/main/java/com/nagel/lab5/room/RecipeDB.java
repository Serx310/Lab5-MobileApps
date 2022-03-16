package com.nagel.lab5.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ConcurrentModificationException;

@Database(entities = {Recipe.class},version = 1,exportSchema = false)
public abstract class RecipeDB extends RoomDatabase {
    private static final String DB_NAME = "recipe_db";
    private static RecipeDB instance;
    public abstract RecipeDao recipeDao();

    public static synchronized RecipeDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),RecipeDB.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private final RecipeDao recipeDao;
        public PopulateDBAsyncTask(RecipeDB db){recipeDao = db.recipeDao();}

        @Override
        protected Void doInBackground(Void... voids) {
            recipeDao.insert(new Recipe("Coleslaw","Me","very good dish",10));
            return null;
        }
    }
}
