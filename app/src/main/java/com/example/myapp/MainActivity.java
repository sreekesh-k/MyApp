package com.example.myapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText baseAmount;
    private SeekBar seekBarTip;
    private TextView tipAmount, totalAmount, tipPercentage;
    private final int DEFAULT_TIP_VALUE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseAmount = findViewById(R.id.baseInput);
        seekBarTip = findViewById(R.id.seekBarTip);
        tipAmount = findViewById(R.id.tipAmount);
        totalAmount = findViewById(R.id.totalAmount);
        tipPercentage = findViewById(R.id.tipPercentage);

        seekBarTip.setProgress(DEFAULT_TIP_VALUE);
        tipPercentage.setText(DEFAULT_TIP_VALUE+"%");
        seekBarTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercentage.setText(progress+"%");
                calculateTipAndTotal();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        baseAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTipAndTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tipCalculator), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void calculateTipAndTotal() {
        String baseAmountText = baseAmount.getText().toString();
        if (baseAmountText.isEmpty()) {
            tipAmount.setText("");
            totalAmount.setText("");
            return;
        }

        double baseAmountValue = Double.parseDouble(baseAmountText);
        int tipPercentageValue = seekBarTip.getProgress();

        double tipValue = baseAmountValue * tipPercentageValue / 100;
        double totalValue = baseAmountValue + tipValue;

        tipAmount.setText(String.format("%.2f", tipValue));
        totalAmount.setText(String.format("%.2f", totalValue));
    }
}