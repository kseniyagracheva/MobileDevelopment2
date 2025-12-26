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

