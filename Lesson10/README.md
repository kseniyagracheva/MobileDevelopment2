**Отчет по практике №2**
---
**Построение модульного проекта**

В данном задании необходимо было усовершенствовать структуру проекта. Проблема в реализации была на уровне data и была связана с отсутствием независимости сохранения данных. Для того, чтобы решить эту проблему, был создан новый интерфейс MovieStorage, который получал бы данные для последующего сохранения:

    public interface MovieStorage {
        public MovieStorageData getMovie();
        public boolean saveMovie(MovieStorageData movieStorageData);
    }

Затем была создана имплементация данного интерфейса SharedPrefMovieStorage, которая сохраняла бы данные в SharedPreferences:

    public class SharedPrefMovieStorage implements ru.mirea.gracheva.data.storage.MovieStorage {
        private static final String PREFS_NAME = "movie_preferences";
        private static final String KEY_MOVIE_NAME = "movie_name";
        private static final String DATA_KEY = "movie_data";
        private SharedPreferences sharedPreferences;
        private Context context;
        
        public SharedPrefMovieStorage(Context context) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        
        @Override
        public boolean saveMovie(MovieStorageData movieStorageData) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_MOVIE_NAME, movieStorageData.getName());
            editor.putString(DATA_KEY, String.valueOf(LocalDate.now()));
            editor.apply();
            return true;
        }
        
        @Override
        public MovieStorageData getMovie() {
            String movieName = sharedPreferences.getString(KEY_MOVIE_NAME, "Вы не сохранили ни одного фильма");
            String movieDate = sharedPreferences.getString(DATA_KEY,String.valueOf(LocalDate.now()));
            return new MovieStorageData(movieName, movieDate);
        }
    }

После возникла проблема того, что слой data напрямую зависит от модели данных Movie из слоя domain, что некорректно. Для устранения проблемы была создана отдельная модель данных MovieStorageData на уровне data, чтобы полность изолировать данный слой:

    public class MovieStorageData {
        private String name;
        private String localDate;
    
        public MovieStorageData(String name, String localDate) {
            this.name = name;
            this.localDate = localDate;
        }
    
        public String getName() {
            return name;
        }
    
        public String getLocalDate() {
            return localDate;
        }
    }
    
Для удобства модели данных из разный слоев были наваны по-разному: MovieStorageData и MovieDomain соответственно. 

Затем была поправлена имплементация интерфейса MovieRepository - MovieRepositoryImpl. В него были добавлены специальные методы-мапперы, которые перенаправляют данные из одного слоя в другой:

    public class MovieRepositoryImpl implements MovieRepository {
        private MovieStorage movieStorage;
    
        public MovieRepositoryImpl(MovieStorage movieStorage){
            this.movieStorage=movieStorage;
        }
    
        @Override
        public boolean saveMovie(MovieDomain movieDomain){
            MovieStorageData movieStorageData = mapToStorage(movieDomain);
            return movieStorage.saveMovie(movieStorageData);
        }
    
        @Override
        public MovieDomain getMovie(){
            MovieStorageData movieStorageData = movieStorage.getMovie();
            return mapToDomain(movieStorageData);
        }
    
        private MovieStorageData mapToStorage(MovieDomain movieDomain){
            String name = movieDomain.getName();
            return new MovieStorageData(name, LocalDate.now().toString());
        }
        
        private MovieDomain mapToDomain(MovieStorageData movieStorageData){
            return new MovieDomain(movieStorageData.getName());
        }
    }

Также были внесены небольшие изменения в файл MainActivity, чтобы он понимал, какой вариант сохранения используется в данный момент:

    MovieStorage sharedPrefMovieStorage = new SharedPrefMovieStorage(this);
    MovieRepository movieRepository = new MovieRepositoryImpl(sharedPrefMovieStorage);

А также, чтобы обособленные слои (модули) понимали, откуда берутся те или иные зависимости были внесены изменения в gradle файлы модуля data и movieproject:

    dependencies {
        implementation(project(":domain"))
        implementation(libs.appcompat)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
    }

    dependencies {
      implementation(libs.appcompat)
      implementation(libs.material)
      implementation(libs.activity)
      implementation(libs.constraintlayout)
      implementation(project(":domain"))
      implementation(project(":data"))
      testImplementation(libs.junit)
      androidTestImplementation(libs.ext.junit)
      androidTestImplementation(libs.espresso.core)
    }
