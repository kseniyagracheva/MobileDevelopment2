package ru.mirea.gracheva.retrofitapp;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jspecify.annotations.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle;
    CheckBox checkBoxCompleted;

    ImageView image;
    public TodoViewHolder(@NonNull View itemView){
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
        image = itemView.findViewById(R.id.imageTodo);
    }
    public void bind(final Todo todo, final ApiInterface apiInterface, final int position){
        textViewTitle.setText(todo.getTitle());

        Picasso.get()
                .load("https://dummyimage.com/64x64/ff6b6b/ffffff.png&text=" + String.valueOf(todo.getId()))
                .placeholder(android.R.color.darker_gray)
                .error(android.R.color.holo_red_dark)
                .fit()
                .tag(this)
                .into(image);

        checkBoxCompleted.setOnCheckedChangeListener(null);
        checkBoxCompleted.setChecked(todo.getCompleted());

        checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todo.setCompleted(isChecked);

            Call<Todo> call = apiInterface.updateTodo(todo.getId(), todo);
            call.enqueue(new Callback<Todo>() {
                @Override
                public void onResponse(Call<Todo> call, Response<Todo> response) {
                    if (response.isSuccessful()){
                        Log.d("TodoAdapter", "Todo" + todo.getId() + " updated");
                    }
                }
                @Override
                public void onFailure(Call<Todo> call, Throwable t) {
                    Log.e("TodoAdapter", "Update failed: " + t.getMessage());
                    checkBoxCompleted.setChecked(!isChecked);
                    todo.setCompleted(!isChecked);
                }
            });
        });
    }
}
