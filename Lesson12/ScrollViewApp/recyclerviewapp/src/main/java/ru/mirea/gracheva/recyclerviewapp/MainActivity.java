package ru.mirea.gracheva.recyclerviewapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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