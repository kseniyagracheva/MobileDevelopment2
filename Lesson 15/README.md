**Отчет по практике №7**
-----
В данной практической работе была произведена работа с навигацией.

*BottomNavigationApp*
-----
В данном задании был создан новый фрагмент BottomNavigationApp, в котором была сделана навигация снизу экрана.
Для этого необходимо было создать 3 фрагмента приложения. При создании фрагмента автоматически создается xml-разметка для данного фрагмента.
Затем нужно было создать граф навигации, в котором описаны фрагменты, которые участвую в навигации. Там же прописываются их id.

        <?xml version="1.0" encoding="utf-8"?>
        <navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools"
            android:id="@+id/nav_graph.xml"
            app:startDestination="@id/nav_catalog">
        
            <fragment
                android:id="@+id/nav_cart"
                android:name="ru.mirea.gracheva.bottopmnavigationapp.CartFragment"
                android:label="Корзина"
                tools:layout="@layout/fragment_cart" />
        
            <fragment
                android:id="@+id/nav_catalog"
                android:name="ru.mirea.gracheva.bottopmnavigationapp.CatalogFragment"
                android:label="Каталог"
                tools:layout="@layout/fragment_catalog" />
        
            <fragment
                android:id="@+id/nav_profile"
                android:name="ru.mirea.gracheva.bottopmnavigationapp.ProfileFragment"
                android:label="Профиль"
                tools:layout="@layout/fragment_profile" />
        </navigation>
Далее был создан файл menu.xml, который отвечает за то, какие кнопки будут в панели навигации

    <?xml version="1.0" encoding="utf-8"?>
    <menu xmlns:android="http://schemas.android.com/apk/res/android">
        <item
            android:id="@+id/nav_catalog"
            android:icon="@drawable/ic_launcher_foreground"
            android:title="Каталог" />
        <item
            android:id="@+id/nav_cart"
            android:icon="@drawable/ic_launcher_foreground"
            android:title="Корзина" />
        <item
            android:id="@+id/nav_profile"
            android:icon="@drawable/ic_launcher_foreground"
            android:title="Профиль" />
    </menu>
После в activity_main.xml были прописаны контейнеры: один содержит навигацию, второй - фрагменты. 

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/nav_view"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="?android:attr/windowBackground"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:menu="@menu/menu" />

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment_activity_main"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:defaultNavHost="true"
        app:layout_constraintBottom_toTopOf="@id/nav_view"
        app:layout_constraintTop_toTopOf="parent"
        app:navGraph="@navigation/nav_graph" />
Таким образом, приложение выглядит так:

<img width="388" height="840" alt="image" src="https://github.com/user-attachments/assets/21f28144-73da-420e-ad24-8c0bb5e26236" />
<img width="382" height="846" alt="image" src="https://github.com/user-attachments/assets/09726179-496e-4574-ad20-1866cab38098" />
<img width="396" height="840" alt="image" src="https://github.com/user-attachments/assets/819735ec-88a0-4a68-91ca-651642877913" />

*NavigationDrawerApp*
-----

Далее была реализована навигация при помощи Navigation Drawer, когда элементы меню появляются при нажатии на ьургер-меню.

Для этого нужно было также создать два файла: граф навигации, чтобы прописать, какие фрагменты нужно добавить в меню, и файл меню, где описываются какие кнопки отвечают за открытие каждого фрагмента.
Далее был создан файл для описания основного контента. Это нужно, чтобы drawer разворачивал нужный фрагмент при нажатии на определенную кнопку меню. (content_main.xml)

        <?xml version="1.0" encoding="utf-8"?>
        <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">
        
            <androidx.fragment.app.FragmentContainerView
                android:id="@+id/nav_host_fragment_content_main"
                android:name="androidx.navigation.fragment.NavHostFragment"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:defaultNavHost="true"
                app:layout_constraintLeft_toLeftOf="parent"
                app:layout_constraintRight_toRightOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:navGraph="@navigation/nav_graph" />
        </androidx.constraintlayout.widget.ConstraintLayout>

Далее были прописаны настройки самой вкладки меня в отдельном файле (app_bar_main.xml). Здесь были указаны стили для панели с кнопкой бургер-меню и для панели самого меню.

        <?xml version="1.0" encoding="utf-8"?>
        <androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".MainActivity">
        
            <com.google.android.material.appbar.AppBarLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:theme="@style/Theme.Navigation.AppBarOverlay">
        
                <androidx.appcompat.widget.Toolbar
                    android:id="@+id/toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="?attr/actionBarSize"
                    android:background="?attr/colorPrimary"
                    app:popupTheme="@style/Theme.Navigation.PopupOverlay" />
        
            </com.google.android.material.appbar.AppBarLayout>
        
            <include layout="@layout/content_main" />
        
        </androidx.coordinatorlayout.widget.CoordinatorLayout>

Стили были подключены в  файле res/values/styles.xml

        <?xml version="1.0" encoding="utf-8"?>
        <resources>
            <style name="AppTheme" parent="Theme.MaterialComponents.DayNight.NoActionBar">
                <item name="colorPrimary">@color/purple_500</item>
                <item name="colorPrimaryVariant">@color/purple_700</item>
                <item name="colorOnPrimary">@color/white</item>
            </style>
        
            <style name="Theme.Navigation.AppBarOverlay"
                parent="ThemeOverlay.AppCompat.Dark.ActionBar" />
            <style name="Theme.Navigation.PopupOverlay"
                parent="ThemeOverlay.AppCompat.Light" />
        </resources>

Затем был настроен файл nav_header_main.xml, в котором была настроена шапка меню.

        <?xml version="1.0" encoding="utf-8"?>
        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="176dp"
            android:background="@color/purple_500"
            android:gravity="bottom"
            android:orientation="vertical"
            android:padding="16dp"
            android:theme="@style/ThemeOverlay.AppCompat.Dark">
        
            <ImageView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:src="@android:drawable/sym_def_app_icon"
                android:contentDescription="Logo" />
        
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:paddingTop="8dp"
                android:text="Ваш аккаунт"
                android:textAppearance="@style/TextAppearance.AppCompat.Body1" />
        
        </LinearLayout>

Таким образом, получилось вот такое приложение:

<img width="322" height="703" alt="image" src="https://github.com/user-attachments/assets/1ac04ecf-af85-48d6-aa5a-542fce751b99" />
<img width="321" height="710" alt="image" src="https://github.com/user-attachments/assets/0823a931-735b-4e91-9a3c-5b3ac00e124b" />
<img width="322" height="708" alt="image" src="https://github.com/user-attachments/assets/6cf00854-e99f-4c67-ab61-d1bb0062dcfc" />
<img width="312" height="706" alt="image" src="https://github.com/user-attachments/assets/40b53deb-3efb-4d26-abbc-cce852f101c8" />

*RingStore*
-----
Далее навигация была добавлена в проект. Для этого в gradle файл были добавлены новый зависимости. 

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

После была добалвена навигационная панель снизу
Для этого были созданы 2 графа навигации. Один для фрагмента с авторизацией, 

        <?xml version="1.0" encoding="utf-8"?>
        <navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/nav_graph_auth"
            app:startDestination="@id/authFragment">
        
            <fragment android:id="@+id/authFragment"
                android:name="ru.mirea.gracheva.ringstore.presentation.AuthFragment">
                <action android:id="@+id/action_authFragment_to_registerFragment"
                    app:destination="@id/registerFragment" />
                <action android:id="@+id/action_authFragment_to_userInfoFragment"
                    app:destination="@id/userInfoFragment" />
            </fragment>
        
            <fragment android:id="@+id/registerFragment"
                android:name="ru.mirea.gracheva.ringstore.presentation.RegisterFragment">
                <action android:id="@+id/action_registerFragment_to_authFragment"
                    app:destination="@id/authFragment" />
            </fragment>
        </navigation>

второй для фрагментов для авторизованного пользователя.

        <?xml version="1.0" encoding="utf-8"?>
        <navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/nav_graph_main"
            app:startDestination="@id/userInfoFragment">
        
            <fragment android:id="@+id/userInfoFragment"
                android:name="ru.mirea.gracheva.ringstore.presentation.UserInfoFragment">
                <action android:id="@+id/action_userInfoFragment_to_metalPriceFragment"
                    app:destination="@id/metalPriceFragment" />
                <action android:id="@+id/action_userInfoFragment_to_ringListFragment"
                    app:destination="@id/ringListFragment" />
            </fragment>
        
            <fragment android:id="@+id/metalPriceFragment"
                android:name="ru.mirea.gracheva.ringstore.presentation.MetalPriceFragment">
                <action android:id="@+id/action_metalPriceFragment_to_userInfoFragment"
                    app:destination="@id/userInfoFragment" />
            </fragment>
        
            <fragment android:id="@+id/ringListFragment"
                android:name="ru.mirea.gracheva.ringstore.presentation.RingListFragment">
                <action android:id="@+id/action_ringListFragment_to_userInfoFragment"
                    app:destination="@id/userInfoFragment" />
            </fragment>
        </navigation>

После этого был создан файл menu.xml

        <?xml version="1.0" encoding="utf-8"?>
        <menu xmlns:android="http://schemas.android.com/apk/res/android">
        
            <item
                android:id="@+id/ringListFragment"
                android:icon="@raw/ring"
                android:title="Каталог" />
            <item
                android:id="@+id/userInfoFragment"
                android:icon="@raw/lk"
                android:title="Профиль" />
            <item
                android:id="@+id/metalPriceFragment"
                android:icon="@raw/metal"
                android:title="Цены на металлы" />
        
        </menu>

Затем были внесены изменения в activity_main.xml

        <?xml version="1.0" encoding="utf-8"?>
        <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools"
            android:id="@+id/main"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".presentation.MainActivity">
        
            <com.google.android.material.bottomnavigation.BottomNavigationView
                android:id="@+id/nav_view"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:background="?android:attr/windowBackground"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintLeft_toLeftOf="parent"
                app:layout_constraintRight_toRightOf="parent"
                app:menu="@menu/menu" />
        
            <androidx.fragment.app.FragmentContainerView
                android:id="@+id/nav_host_fragment_activity_main"
                android:name="androidx.navigation.fragment.NavHostFragment"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                app:defaultNavHost="true"
                app:layout_constraintBottom_toTopOf="@id/nav_view"
                app:layout_constraintTop_toTopOf="parent"
                app:navGraph="@navigation/nav_graph_auth" />
        
        </androidx.constraintlayout.widget.ConstraintLayout>

Потом были внесены изменения в MainActivity, чтобы она запускала навигацию для авторизованного пользователя

        public class MainActivity extends AppCompatActivity {
            private ActivityMainBinding binding;  // ViewBinding
            private NavController navController;
            private BottomNavigationView navView;
        
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                binding = ActivityMainBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());
        
                navView = binding.navView;
        
                NavHostFragment navHostFragment = (NavHostFragment)
                        getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                navController = navHostFragment.getNavController();
        
                if (isUserLoggedIn()) {
                    showMainGraph();
                } else {
                    showAuthGraph();
                }
            }
        
            private void showAuthGraph() {
                navView.setVisibility(View.GONE);
                navController.setGraph(R.navigation.nav_graph_auth);
            }
        
            private void showMainGraph() {
                navView.setVisibility(View.VISIBLE);
                navController.setGraph(R.navigation.nav_graph_main);
        
                navView.setOnNavigationItemSelectedListener(item -> {
                    navController.navigate(item.getItemId());
                    return true;
                });
                navController.navigate(R.id.userInfoFragment, null);
            }
        
            public void onLoginSuccess() {
                showMainGraph();
            }
        
            private boolean isUserLoggedIn() {
                SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                return prefs.getBoolean("is_logged_in", false);
            }
        }

Таким образом получилось добавить навигационную панель снизу:

<img width="364" height="801" alt="image" src="https://github.com/user-attachments/assets/4f35f38d-1627-4bba-ae51-b50764653b2e" />
<img width="363" height="815" alt="image" src="https://github.com/user-attachments/assets/0048b13f-a63c-4187-9267-8acda31bbe55" />
<img width="373" height="817" alt="image" src="https://github.com/user-attachments/assets/4df327a0-180f-493e-80e2-6f32e167bb2f" />



