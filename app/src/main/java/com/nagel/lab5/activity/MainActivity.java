package com.nagel.lab5.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nagel.lab5.R;
import com.nagel.lab5.room.Recipe;
import com.nagel.lab5.room.RecipeAdapter;
import com.nagel.lab5.room.RecipeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int RESULT_EDIT = 200;
    private RecipeViewModel recipeViewModel;
    public static final int RESULT_SAVE = 100;

    ActivityResultLauncher activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if(result.getResultCode() == RESULT_SAVE){
                    Intent resultData = result.getData();
                    if(resultData != null){
                        String title = resultData.getStringExtra(AddRecipeActivity.EXTRA_TITLE);
                        String author = resultData.getStringExtra(AddRecipeActivity.EXTRA_AUTHOR);
                        String content = resultData.getStringExtra(AddRecipeActivity.EXTRA_CONTENT);
                        int time = resultData.getIntExtra(AddRecipeActivity.EXTRA_TIME, 1);

                        Recipe recipe = new Recipe(title, author, content, time);
                        recipeViewModel.insert(recipe);
                        Snackbar.make(findViewById(R.id.myCoordinatorMain), getString(R.string.save_db), Snackbar.LENGTH_SHORT).show();

                    }else{
                        Snackbar.make(findViewById(R.id.myCoordinatorMain), getString(R.string.save_err), Snackbar.LENGTH_SHORT).show();
                    }
                }else if(result.getResultCode() == RESULT_EDIT){
                    Intent resultData = result.getData();
                    if(resultData != null){
                        int id = resultData.getIntExtra(AddRecipeActivity.EXTRA_ID, -1);
                        if(id == -1){
                            Snackbar.make(findViewById(R.id.myCoordinatorMain), getString(R.string.recipe_err),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                        String title = resultData.getStringExtra(AddRecipeActivity.EXTRA_TITLE);
                        String author = resultData.getStringExtra(AddRecipeActivity.EXTRA_AUTHOR);
                        String content = resultData.getStringExtra(AddRecipeActivity.EXTRA_CONTENT);
                        int time = resultData.getIntExtra(AddRecipeActivity.EXTRA_TITLE, 1);
                        Recipe recipe = new Recipe(title, author, content, time);
                        recipe.setId(id);
                        recipeViewModel.update(recipe);

                    }else{
                        Snackbar.make(findViewById(R.id.myCoordinatorMain), getString(R.string.updated),
                                Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    Snackbar.make(findViewById(R.id.myCoordinatorMain), getString(R.string.recipe_closed),
                            Snackbar.LENGTH_SHORT).show();
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((FloatingActionButton)findViewById(R.id.fabNewRecipe)).setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
            activityResultLauncher.launch(intent);

        });

        RecyclerView recyclerView = findViewById(R.id.recycler_recipe_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final RecipeAdapter adapter = new RecipeAdapter();
        recyclerView.setAdapter(adapter);

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(this, adapter::submitList);
        //adding swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            //drag and drop
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                recipeViewModel.delete(adapter.getRecipePosition(viewHolder.getAbsoluteAdapterPosition()));
                Snackbar.make(findViewById(R.id.myCoordinatorMain), getString(R.string.recipe_deleted), Snackbar.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //add click to adapter
        adapter.setOnItemClickListener(new RecipeAdapter.onItemClickListener() {
            @Override
            public void onItemClickListener(Recipe recipe) {
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                intent.putExtra(AddRecipeActivity.EXTRA_TITLE, recipe.getTitle());
                intent.putExtra(AddRecipeActivity.EXTRA_AUTHOR, recipe.getAuthor());
                intent.putExtra(AddRecipeActivity.EXTRA_CONTENT, recipe.getContent());
                intent.putExtra(AddRecipeActivity.EXTRA_TIME, recipe.getTime());
                intent.putExtra(AddRecipeActivity.EXTRA_ID, recipe.getId());
                setResult(RESULT_EDIT, intent);
                activityResultLauncher.launch(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_recipe){
            recipeViewModel.deleteAllRecipes();
            Snackbar.make(findViewById(R.id.myCoordinatorMain), getString(R.string.recipes_deleted), Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}