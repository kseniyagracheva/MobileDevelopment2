**Отчет по практике №5**
-----

*Retrofit*
-----

Т.К. Retrofit - библиотека для работы с веб-сервисами, то необходимо разрешить приложению доступ к интернету. Для этого необходимо добавить разрешение в манифест-файл. 

    <uses-permission android:name="android.permission.INTERNET"/>

Также была добавлена имплементация конвертера в файл грэйдл.
Далее был создан класс Todo.java, в котором была описана модель при помощи объекта POJO. Это простой Java объект, который не зависит ни от каких фреймворков и используется для моделирования данных и их переноса между сервисами в виде JSON. Здесь были поисаны параметры модели, а также созданы геттеры и сеттеры для получения и изменения этих параметров.
    
    public class Todo {
        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("completed")
        @Expose
        private Boolean completed;
    
        public Integer getUserId() {
            return userId;
        }
        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    
        public Integer getId() {
            return id;
        }
    
        public void setId(Integer id){
            this.id = id;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title){
            this.title = title;
        }
    
        public Boolean getCompleted() {
            return completed;
        }
    
        public void setCompleted(Boolean completed) {
            this.completed = completed;
        }
    }

Затем был создан интерфейса, в котором описаны методы работы с данным объектом ApiInterface. Здесь описан метод для получения списка объектов, а также метод для изменения конкретного объекта по id при помощи запроса PATCh.

     public interface ApiInterface {
      @GET("todos")
      Call<List<Todo>> getTodos();
  
      @PATCH("todos/{id}")
      Call<Todo> updateTodo(
              @Path("id") int todoId,
              @Body Todo todo
      );
    }

Далее был создан TodoViewHolder, в котором были описаны объекты View и был добавлен слушатель на чекбокс. При изменении состояния чекбокса будет отправлен запрос на сервер для изменения состояния объекта.

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        CheckBox checkBoxCompleted;
        public TodoViewHolder(@NonNull View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
        }
        public void bind(final Todo todo, final ApiInterface apiInterface, final int position){
            textViewTitle.setText(todo.getTitle());
    
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

Затем был создан адаптер TodoApadter для отображения списка объектов:

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
Затем в MainActivity была добавлена логика загрузки данных из API и создания объекта recycler view

    public class MainActivity extends AppCompatActivity {
        public static final String TAG = "MainActivity";
        public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
        private RecyclerView recyclerView;
        private TodoAdapter todoAdapter;
        private ApiInterface apiInterface;
        
    
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
    
            recyclerView = findViewById(R.id.itemsRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInterface = retrofit.create(ApiInterface.class);
            Call<List<Todo>> call = apiInterface.getTodos();
            call.enqueue(new Callback<List<Todo>>() {
                @Override
                public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                    if (response.isSuccessful() && response.body()!=null){
                        List<Todo> todos = response.body();
                        todoAdapter = new TodoAdapter(MainActivity.this, todos, apiInterface);
                        recyclerView.setAdapter(todoAdapter);
                    }
                    else{
                        Log.e(TAG, "onResponse: " + response.code());
                    }
                }
    
                @Override
                public void onFailure(Call<List<Todo>> call, Throwable t) {
                    Log.e(TAG, "onFailure" + t.getMessage());
                }
            });
    
        }
    }

В результате получилось следующее. При изменении состояния чекбокса отправляется апрос на сервер и в консоли выводится лог.

<img width="465" height="817" alt="image" src="https://github.com/user-attachments/assets/15cbd12b-06a8-46d3-9be0-8f7c54052b2a" />
<img width="1897" height="117" alt="image" src="https://github.com/user-attachments/assets/a3556a31-91d9-4328-93db-2e158966c19a" />

Затем в TodoViewHolder были внесены изменения для отображения картинок при помощи Picasso

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

Теперь в списке отображается картинка

<img width="366" height="814" alt="image" src="https://github.com/user-attachments/assets/26f7d66a-6589-43c2-bee3-747d51cc2b54" />

*RingStore*
-----






