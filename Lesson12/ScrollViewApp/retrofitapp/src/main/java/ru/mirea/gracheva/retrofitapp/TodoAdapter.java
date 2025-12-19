package ru.mirea.gracheva.retrofitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.jspecify.annotations.NonNull;

import java.lang.reflect.AccessibleObject;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Todo> todos;

    private ApiInterface apiInterface;


    public TodoAdapter(Context context, List<Todo> todoList, ApiInterface apiInterface){
        this.layoutInflater = LayoutInflater.from(context);
        this.todos = todoList;
        this.apiInterface = apiInterface;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position){
        Todo todo = todos.get(position);
        holder.bind(todo, apiInterface, position);
    }
    @Override
    public int getItemCount(){
        return todos.size();
    }
}
