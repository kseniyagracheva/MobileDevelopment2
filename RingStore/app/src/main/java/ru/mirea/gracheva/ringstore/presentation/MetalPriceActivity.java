package ru.mirea.gracheva.ringstore.presentation;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.gracheva.ringstore.R;
import ru.mirea.gracheva.ringstore.data.repository.MetalPriceInfoRepositoryImpl;
import ru.mirea.gracheva.ringstore.domain.models.MetalPriceInfo;
import ru.mirea.gracheva.ringstore.domain.repository.MetalPriceInfoRepository;
import ru.mirea.gracheva.ringstore.domain.usecases.GetMetalPriceInfo;

public class MetalPriceActivity extends AppCompatActivity {

    private TextView metalNameTextView;
    private TextView priceTextView;
    private TextView currencyTextView;
    private TextView lastUpdatedTextView;

    private GetMetalPriceInfo getMetalPriceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metal_price);

        metalNameTextView = findViewById(R.id.metalName);
        priceTextView = findViewById(R.id.price);
        currencyTextView = findViewById(R.id.currency);
        lastUpdatedTextView = findViewById(R.id.lastUpdated);

        getMetalPriceInfo = new GetMetalPriceInfo(new MetalPriceInfoRepositoryImpl());

        getMetalPriceInfo.fetchMetalPriceInfo(new MetalPriceInfoRepository.MetalPriceCallback() {
            @Override
            public void onSuccess(MetalPriceInfo metalPriceInfo) {
                runOnUiThread(() -> {
                    metalNameTextView.setText(metalPriceInfo.getMetalName());
                    priceTextView.setText(String.valueOf(metalPriceInfo.getPrice()));
                    currencyTextView.setText(metalPriceInfo.getCurrency());
                    lastUpdatedTextView.setText(metalPriceInfo.getLastUpdated());
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() ->
                        Toast.makeText(MetalPriceActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show());
            }
        });
    }
}

