package com.nagel.lab5.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.android.material.snackbar.Snackbar;
import com.nagel.lab5.R;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etAuthor;
    private EditText etContent;
    private NumberPicker ntPicker;

    public static final String EXTRA_TITLE = "com.nagel.lab5.activity.EXTRA_TITLE";
    public static final String EXTRA_AUTHOR = "com.nagel.lab5.activity.EXTRA_AUTHOR";
    public static final String EXTRA_CONTENT = "com.nagel.lab5.activity.EXTRA_CONTENT";
    public static final String EXTRA_TIME = "com.nagel.lab5.activity.EXTRA_TIME";

    public static final int RESULT_SAVE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        etTitle = findViewById(R.id.etRecipeTitle);
        etAuthor = findViewById(R.id.etRecipeAuthor);
        etContent = findViewById(R.id.etRecipeContent);
        ntPicker = findViewById(R.id.timePicker);
        ntPicker.setMinValue(1);
        ntPicker.setMaxValue(300);
        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            setTitle(getString(R.string.edit));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.save_recipe){
            saveRecipe();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveRecipe(){
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        int time = ntPicker.getValue();
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(content)){
            Snackbar.make(findViewById(R.id.myCoordinatorAdd), getString(R.string.field_error), Snackbar.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_AUTHOR, author);
        data.putExtra(EXTRA_CONTENT, content);
        data.putExtra(EXTRA_TIME, time);

        setResult(RESULT_SAVE, data);
        finish();
    }


}