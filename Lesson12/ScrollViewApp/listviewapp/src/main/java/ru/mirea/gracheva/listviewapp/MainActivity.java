package ru.mirea.gracheva.listviewapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

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