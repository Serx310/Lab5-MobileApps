package com.nagel.lab5.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeRepository {
    private final RecipeDao recipeDao;
    private final LiveData<List<Recipe>> allRecipes;

    public RecipeRepository(Application application) {
        RecipeDB db = RecipeDB.getInstance(application);
        recipeDao = db.recipeDao();
        allRecipes = recipeDao.getAllRecipes();
    }

    public void insert(Recipe recipe) {
        new InsertRecipeAsyncTask(recipeDao).execute(recipe);
    }

    public void update(Recipe recipe) {
        new UpdateRecipeAsyncTask(recipeDao).execute(recipe);
    }
    public void delete(Recipe recipe) {
        new DeleteRecipeAsyncTask(recipeDao).execute(recipe);
    }

    public void deleteAllRecipes(Recipe recipe) {
        new DeleteAllRecipeAsyncTask(recipeDao).execute(recipe);
    }

    public LiveData<List<Recipe>> getAllRecipes() {return allRecipes;}

    private class InsertRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private final RecipeDao recipeDao;
        public InsertRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.insert(recipes[0]);
            return null;
        }
    }

    public static class UpdateRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private final RecipeDao recipeDao;
        public UpdateRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }
        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.insert(recipes[0]);
            return null;
        }
    }

    public static class DeleteRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private final RecipeDao recipeDao;
        public DeleteRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.insert(recipes[0]);
            return null;
        }
    }

    public static class DeleteAllRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private final RecipeDao recipeDao;
        public DeleteAllRecipeAsyncTask(RecipeDao recipeDao) {
            this.recipeDao = recipeDao;
        }
        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.insert(recipes[0]);
            return null;
        }
    }
}


