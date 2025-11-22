**Отчет по практической работе №4**
---
В данной практической работе было проведено знакомство с различными видами оформления списка с возможностью скроллинга.

*ScrollViewApp*
---
Сначала был создан новый модуль, в котором был создан элемент списка. У него есть картинка и текст. 

    <androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <ImageView
            android:id="@+id/imageView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:layout_marginTop="8dp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:srcCompat="@drawable/ic_launcher_foreground" />
        <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="16dp"
            android:layout_marginTop="32dp"
            android:text="TextView"
            app:layout_constraintStart_toEndOf="@+id/imageView"
            app:layout_constraintTop_toTopOf="parent" />
    </androidx.constraintlayout.widget.ConstraintLayout>

  <img width="1022" height="642" alt="image" src="https://github.com/user-attachments/assets/f588660e-0aba-4d27-91fb-152abc5e855b" />
Далее в разметку файла был добавлен элемент ScrollView, который затем будет динамически заполнен элементами списка

      <?xml version="1.0" encoding="utf-8"?>
      <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:app="http://schemas.android.com/apk/res-auto"
          xmlns:tools="http://schemas.android.com/tools"
          android:id="@+id/main"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          tools:context=".MainActivity">
      
          <ScrollView
              android:id="@+id/scrollView"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              app:layout_constraintBottom_toBottomOf="parent"
              app:layout_constraintEnd_toEndOf="parent"
              app:layout_constraintStart_toStartOf="parent"
              app:layout_constraintTop_toTopOf="parent">
              <LinearLayout
                  android:id="@+id/wrapper"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:orientation="vertical" />
          </ScrollView>
      
      </androidx.constraintlayout.widget.ConstraintLayout>
Затем в классе MainActivity было произведено заполнение данного списка элементами.
При помощи layoutInflater происходит преобразование элементов xml-разметки в элементы view.
ScrollView обеспечивает вертикальную прокрутку. Также был добавлен специальный метод scrollTo(), который прокручивает страницу вниз автоматически. В данном случае на 500 пикселей по оси y, т.е. вниз.

    public class MainActivity extends AppCompatActivity {
    
        private LinearLayout wrapper;
        private ScrollView scrollView;
    
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
    
            wrapper = findViewById(R.id.wrapper);
            scrollView = findViewById(R.id.scrollView);
    
            BigInteger value = BigInteger.ONE;
            for (int i = 1; i <= 100; i++) {
                value = value.shiftLeft(1);
    
                View view = getLayoutInflater().inflate(R.layout.item, null, false);
    
                TextView text = view.findViewById(R.id.textView);
                text.setText(String.format("Элемент %d: %s", i, value.toString()));
    
                ImageView img = view.findViewById(R.id.imageView);
                img.setImageResource(android.R.drawable.ic_dialog_info);
    
                wrapper.addView(view);
            }
    
            scrollView.post(() -> {
                scrollToPosition(500);
            });
    
        }
        public void scrollToPosition(int yPosition) {
            scrollView.scrollTo(0, yPosition);
        }
    }

<img width="373" height="822" alt="image" src="https://github.com/user-attachments/assets/043a20f0-5021-418b-80cc-a9e2acfb1746" />

*ListViewApp*
---
Затем был создан новый модуль, в котором присутствует элемент ListView для отображения списка.

    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/main"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
    
        <ListView
            android:id="@+id/book_list_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    
    </androidx.constraintlayout.widget.ConstraintLayout>
Данный элемент при заполнении требует адаптера, который был создан в MainActivity
В данном случае список отображает список из 31 одной книги. При создании активити создается адаптер, который связывает список с объектами книг и ListView

    public class MainActivity extends AppCompatActivity {
        public class Book {
            String title;
            String author;
            public Book(String title, String author) {
                this.title = title;
                this.author = author;
            }
        }
    
        private ListView listViewBooks;
    
        List<Book> books = Arrays.asList(
                new Book("1984", "Джордж Оруэлл"),
                new Book("451 градус по Фаренгейту", "Рэй Брэдбери"),
                new Book("Американская трагедия", "Теодор Драйзер"),
                new Book("Бесы", "Фёдор Достоевский"),
                new Book("Белая гвардия", "Михаил Булгаков"),
                new Book("Большие надежды", "Чарльз Диккенс"),
                new Book("Властелин колец", "Дж.Р.Р. Толкин"),
                new Book("Граф Монте-Кристо", "Александр Дюма"),
                new Book("Декамерон", "Джованни Боккаччо"),
                new Book("Дракула", "Брэм Стокер"),
                new Book("Золотой телёнок", "Илья Ильф и Евгений Петров"),
                new Book("Искупление", "Иэн Макьюэн"),
                new Book("Капитанская дочка", "Александр Пушкин"),
                new Book("Красное и чёрное", "Стэндал"),
                new Book("Лето злых духов Убумэ", "Нацухико Кёгоку"),
                new Book("Любовь к жизни", "Джек Лондон"),
                new Book("Мастер и Маргарита", "Михаил Булгаков"),
                new Book("Машина времени", "Герберт Уэллс"),
                new Book("Моби Дик", "Герман Мелвилл"),
                new Book("Над пропастью во ржи", "Дж. Д. Сэлинджер"),
                new Book("Ночь нежна", "Фрэнсис Скотт Фицджеральд"),
                new Book("Овод", "Этель Войнич"),
                new Book("Отверженные", "Виктор Гюго"),
                new Book("Портрет Дориана Грея", "Оскар Уайльд"),
                new Book("Преступление и наказание", "Фёдор Достоевский"),
                new Book("Путешествия Гулливера", "Джонатан Свифт"),
                new Book("Ревизор", "Николай Гоголь"),
                new Book("Три товарища", "Эрих Мария Ремарк"),
                new Book("Убить пересмешника", "Харпер Ли"),
                new Book("Фауст", "Иоганн Вольфганг Гёте"),
                new Book("Шерлок Холмс", "Артур Конан Дойл")
        );
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            listViewBooks = findViewById(R.id.book_list_view);
    
            ArrayAdapter<Book> adapter = new ArrayAdapter<Book>(this, android.R.layout.simple_list_item_2, android.R.id.text1, books) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
    
                    Book book = getItem(position);
                    text1.setText(book.title);
                    text2.setText(book.author);
    
                    return view;
                }
            };
            listViewBooks.setAdapter(adapter);
        }
    }


  <img width="370" height="812" alt="image" src="https://github.com/user-attachments/assets/bafd9064-0302-4cf4-828e-56f1bd60aa27" />

*RecyclerViewApp*
---
В данном модуле был использован компонент RecyclerViewApp, который предоставляет очень гибки функционал для работы со списком элементов.
Сначала был создан элемент списка, который содержит картинку, название и описание исторического события.
Затем в основной файл разметки был добавлен компонент RecyclerView, который в дальнейшем будет наполнен элементами.
Затем был создан отдельный класс HistoricalEvent:

    public class HistoricalEvent {
        private String title;
        private String description;
        private int imageResId;
    
        public HistoricalEvent(String title, String description, int imageResId) {
            this.title = title;
            this.description = description;
            this.imageResId = imageResId;
        }
    
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public int getImageResId() { return imageResId; }
    }
Затем был создан HistoricalEventListAdapter, который заполняет список элементами. Для этого он использует ViewHolder, который получает элементы из xml разметки по id.

    public class HistoricalEventListAdapter extends RecyclerView.Adapter<HistoricalEventListAdapter.EventViewHolder> {
    
        private final List<HistoricalEvent> events;
    
        public HistoricalEventListAdapter(List<HistoricalEvent> events) {
            this.events = events;
        }
    
        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.historical_event_item, parent, false);
            return new EventViewHolder(view);
        }
    
        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            HistoricalEvent event = events.get(position);
            holder.title.setText(event.getTitle());
            holder.description.setText(event.getDescription());
            holder.image.setImageResource(event.getImageResId());
        }
    
        @Override
        public int getItemCount() {
            return events.size();
        }
    
        public static class EventViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView title, description;
    
            public EventViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                title = itemView.findViewById(R.id.title);
                description = itemView.findViewById(R.id.description);
            }
        }
    }

Далее в MainActivity были объявлены компоненты RecyclerView и адаптера. Был заполнен список событий и передан адаптеру, а затем адаптер был передан recycler view

    public class MainActivity extends AppCompatActivity {
    
        private RecyclerView recyclerView;
        private HistoricalEventListAdapter adapter;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
    
            recyclerView = findViewById(R.id.eventsRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
    
            List<HistoricalEvent> events = new ArrayList<>();
            events.add(new HistoricalEvent("Путч 1991 года",
                    "Августовский путч 1991 года — это попытка государственного переворота, предпринятая консервативными членами высшего руководства СССР (известными как ГКЧП) с 18 по 21 августа 1991 года.",
                    R.drawable.putch));
            events.add(new HistoricalEvent("Поднятие флага над Рейхстагом",
                    "1 мая 1945 года было совершено водружение Знамени Победы над зданием Рейхстага в Берлине советскими воинами в конце Великой Отечественной войны",
                    R.drawable.flag));
            events.add(new HistoricalEvent("Взятие Бастилии",
                    "Взятие Бастилии произошло 14 июля 1789 года в Париже. Это событие является знаковым моментом и символическим началом Великой французской революции.",
                    R.drawable.prise_de_la_bastille));
            events.add(new HistoricalEvent("Бородинское сражение",
                    "Сражение произошло 26 августа (7 сентября по новому стилю) 1812 года у села Бородино, расположенного примерно в 125 км к западу от Москвы",
                    R.drawable.borodino_mihail_lermontov));
            events.add(new HistoricalEvent("Крещение Руси",
                    "Крещение Руси — это процесс принятия христианства в качестве государственной религии Древнерусским государством, ключевым событием которого стало массовое крещение жителей Киева в 988 году по инициативе князя Владимира Святославича. ",
                    R.drawable.kreshenie));
    
            adapter = new HistoricalEventListAdapter(events);
            recyclerView.setAdapter(adapter);
        }
    }

Таким образом получился такой список:

<img width="375" height="836" alt="image" src="https://github.com/user-attachments/assets/7cb13d08-6f06-4e9e-b412-ee1ef3eeea5d" />


---
**RingStore**
---

Теперь RecyclerView была внедрена в проект RingStore. RecyclerView была использована для отображения списка колец и металлов.
Для начала был создан элемент списка. Он имеет картинку, название металла и цену кольца:

<img width="1172" height="473" alt="image" src="https://github.com/user-attachments/assets/f48d62f3-6bad-454b-92eb-12e0d8e5ca54" />

Аналогичный элемент списка был создан для металла:
<img width="1173" height="454" alt="image" src="https://github.com/user-attachments/assets/653726dd-3b8d-4831-b9a9-8e32b04c3c48" />

Затем RecyclerView были внедрены в основные файлы разметки активностей. 

Затем был создан адаптер для колец, который получает список колец и содержит ViewHolder, который заполняет элементы списка, на основе данных, полученных из списка колец.

    public class RingsAdapter extends RecyclerView.Adapter<RingsAdapter.RingViewHolder>{
        private List<Ring> rings;
    
        public RingsAdapter(List<Ring> rings) {
            this.rings = rings;
        }
        public void updateData(List<Ring> newRings) {
            this.rings = newRings;
            notifyDataSetChanged();
        }
    
        public static class RingViewHolder extends RecyclerView.ViewHolder {
            private final ItemCardRingBinding binding;
    
            public RingViewHolder(ItemCardRingBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
            public void bind(Ring ring) {
                binding.metalTextView.setText(ring.getMetal());
                binding.priceTextView.setText(ring.getPrice() + " руб.");
            }
        }
        @NonNull
        @Override
        public RingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemCardRingBinding binding = ItemCardRingBinding.inflate(inflater, parent, false);
            return new RingViewHolder(binding);
        }
        @Override
        public void onBindViewHolder(@NonNull RingViewHolder holder, int position) {
            holder.bind(rings.get(position));
        }
        @Override
        public int getItemCount() {
            return rings.size();
        }
    }

Далее в RingListFragment происходит создание контейнера для списка и передача списка колец в адаптер при помощи ViewModel. Далее адаптер передается ringsRecyclerView.

    public class RingListFragment extends Fragment {
        private FragmentRingListBinding binding;
        private NavController navController;
        private RingListViewModel vm;
    
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            binding = FragmentRingListBinding.inflate(inflater, container, false);
            vm = new ViewModelProvider(this, new RingListViewModelFactory(requireContext())).get(RingListViewModel.class);
            return binding.getRoot();
        }
    
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            navController = Navigation.findNavController(view);
    
            binding.ringsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            vm.getRingsLiveData().observe(getViewLifecycleOwner(), rings->{
                requireActivity().runOnUiThread(()->{
                    RingsAdapter adapter = new RingsAdapter(rings);
                    binding.ringsRecyclerView.setAdapter(adapter);
                });
            });
            vm.getErrorLiveData().observe(getViewLifecycleOwner(), error ->{
                Toast.makeText(requireContext(),  error, Toast.LENGTH_SHORT).show();
            });
    
            vm.getRings();
    
            binding.backToUserInfoButton.setOnClickListener(v ->
                    navController.navigate(R.id.action_ringListFragment_to_userInfoFragment));
        }
    
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }

Аналогично был реализваон список с драгоценными металлами

<img width="383" height="829" alt="image" src="https://github.com/user-attachments/assets/f89536cf-cd67-4a19-b721-b77101e8c2fa" />

<img width="379" height="838" alt="image" src="https://github.com/user-attachments/assets/6acdcb42-5800-4f41-8099-ff3acd1abfb8" />
