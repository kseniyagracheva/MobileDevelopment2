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

