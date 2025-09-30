**Отчет по практике №1**
---
**MovieProject**

В начале был создан новый проект Lesson9. В нем был создан новый модуль MovieProject. После в данном модуле были созданы новые директории domain, data и presentation.
Далее в директории domain были созданы папки models, usecases и repository. В папке data была создана директория repository. И файл MainActivity был перенесен в папку presentation.

<img width="561" height="502" alt="image" src="https://github.com/user-attachments/assets/e664ebe6-64ab-4a8e-8b68-0c11002387f7" />

Сначала была создана разметка, которая необходима для задания:

<img width="965" height="759" alt="image" src="https://github.com/user-attachments/assets/1b3e7462-c31c-4df3-a485-7c6ca1215b06" />

Затем были созданы соответствующие классы, которые соответствуют стандартам чистой архитектуры. Была создана сущность - класс Movie:

    public class Movie {
        private String name;
        public Movie(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
После чего был создан интерфейс MovieRepository в слое domain:

    public interface MovieRepository {
        public boolean saveMovie(Movie movie);
        public Movie getMovie();
    }
и его реализация в слое data:

    public class MovieRepositoryImpl implements MovieRepository {
        private static final String PREFS_NAME = "movie_preferences";
        private static final String KEY_MOVIE_NAME = "movie_name";
        private SharedPreferences sharedPreferences;
    
        public  MovieRepositoryImpl(Context context){
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        
        @Override
        public boolean saveMovie(Movie movie) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_MOVIE_NAME, movie.getName());
            editor.apply();
            return true;
        }
        
        @Override
        public Movie getMovie() {
            String movieName = sharedPreferences.getString(KEY_MOVIE_NAME, "Вы не сохранили ни одного фильма");
            return new Movie(movieName);
        }
    }
Инмлементация интерфейса в слое data необходима для работы с данными, поэтому в ней и просиходит образение к файлу shared preferences.
Затем были добавлены два сценария использования:
GetFavoriteMovieUseCase

    public class GetFavoriteMovieUseCase {
        private MovieRepository movieRepository;
        public GetFavoriteMovieUseCase(MovieRepository movieRepository) {
            this.movieRepository = movieRepository;
        }
        public Movie execute(){
            return movieRepository.getMovie();
        }
    }
и SaveMovieToFavoriteUseCase

    public class SaveMovieToFavoriteUseCase {
        private MovieRepository movieRepository;
        public SaveMovieToFavoriteUseCase(MovieRepository movieRepository) {
            this.movieRepository = movieRepository;
        }
        public boolean execute(Movie movie){
            return movieRepository.saveMovie(movie);
        }
    }
В конце была добавлена логика, которая связывает все это с уровнем presentation, то есть в файл MainActivity:

    public class MainActivity extends AppCompatActivity {
    
    private ActivityMainBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MovieRepository movieRepository = new MovieRepositoryImpl(this);

        binding.saveFavMovieButton.setOnClickListener(view -> {
            Boolean result = new SaveMovieToFavoriteUseCase(movieRepository).execute(new Movie(binding.enterFavMovieText.getText().toString()));
            binding.noDataText.setText(String.format("Save result %s", result));
        });

        binding.getFavMovieButton.setOnClickListener(view -> {
            Movie movie = new GetFavoriteMovieUseCase(movieRepository).execute();
            binding.noDataText.setText(String.format("Movie name: %s", movie.getName()));
        });
    }
В результате приложение работает:

<img width="383" height="843" alt="image" src="https://github.com/user-attachments/assets/8a865f10-8eaa-4d12-af9b-b4266e98ea57" />

<img width="399" height="846" alt="image" src="https://github.com/user-attachments/assets/2fd70a1c-4a91-4db3-9be8-1cd53b32b458" />

<img width="380" height="850" alt="image" src="https://github.com/user-attachments/assets/a1e37c93-d96a-4d27-8005-2e4720d0a0bb" />

<img width="386" height="847" alt="image" src="https://github.com/user-attachments/assets/b47df77d-19b2-42cc-bcd0-3aee8313187a" />

<img width="1275" height="927" alt="image" src="https://github.com/user-attachments/assets/5ce26a28-eeb8-4c6d-8634-844d1499ca64" />
