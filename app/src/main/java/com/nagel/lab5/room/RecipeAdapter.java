package com.nagel.lab5.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nagel.lab5.R;

import java.util.ArrayList;
import java.util.List;


public class RecipeAdapter extends ListAdapter<Recipe,RecipeAdapter.RecipeViewHolder> {
    private onItemClickListener listener;
    public RecipeAdapter() {super(DIFF_CALLBACK);}

    public RecipeAdapter(@NonNull DiffUtil.ItemCallback<Recipe> diffCallback) {
        super(diffCallback);
    }

    private static final DiffUtil.ItemCallback<Recipe> DIFF_CALLBACK = new DiffUtil.ItemCallback<Recipe>() {
        @Override
        public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getAuthor().equals(newItem.getAuthor()) &&
                    oldItem.getContent().equals(newItem.getContent()) &&
                    oldItem.getTime() == newItem.getTime();
        }
    };

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe,parent,false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe viewableRecipe = getItem(position);
        holder.txtTitle.setText(viewableRecipe.getTitle());
        holder.txtAuthor.setText(viewableRecipe.getAuthor());
        holder.txtContent.setText(viewableRecipe.getContent());
        holder.txtTime.setText(String.format("Cooking time: %s minutes",viewableRecipe.getTime()));
    }

    public Recipe getRecipePosition(int position){
        return getItem(position);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtAuthor;
        private TextView txtTime;
        private TextView txtContent;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtContent = itemView.findViewById(R.id.txtContent);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClickListener(getItem(position));
                    }
                }
            });
        }
    }
    public interface onItemClickListener{
        void onItemClickListener(Recipe recipe);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}

