package com.nagel.lab5.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nagel.lab5.R;

import java.util.ArrayList;
import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList = new ArrayList<>();

    @NonNull
    @Override



    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe viewableRecipe = recipeList.get(position);
        holder.txtTitle.setText(viewableRecipe.getTitle());
        holder.txtAuthor.setText(viewableRecipe.getAuthor());
        holder.txtContent.setText(viewableRecipe.getContent());
        holder.txtTime.setText(String.format("Cooking time: %s minutes", viewableRecipe.getTime()));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipeList(List<Recipe> recipeList){
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtAuthor;
        private TextView txtTime;
        private TextView txtContent;

        public RecipeViewHolder(@NonNull View itemView){
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtContent = itemView.findViewById(R.id.txtContent);
        }
    }
}
