package com.lucas.convertormoedas;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity {
    private Button btn_Convert; private Spinner spinner_to, spinner_from; private EditText et_Values; private TextView tx_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spinner_to = findViewById(R.id.spinnerTo);
        spinner_from = findViewById(R.id.spinnerFrom);
        btn_Convert.setOnClickListener(v -> {
            String spinner_from1 = spinner_from.getSelectedItem().toString();
            String spinner_to1 = spinner_to.getSelectedItem().toString();
            String input1 = et_Values.getText().toString().trim();
            double value = Double.parseDouble(input1);
            double BRLtoEUR = 0.18;  // 1 BRL = 0.18 EUR
            double BRLtoARS = 35.00; // 1 BRL = 35.00 ARS
            double BRLtoUSD = 0.20;  // 1 BRL = 0.20 USD
            double valueInBRL = 0;
            switch (spinner_from1) {
                case "BRL":
                    valueInBRL = value;break; case "USD": valueInBRL = value / BRLtoUSD;break; case "ARS": valueInBRL = value / BRLtoARS;break; case "EUR": valueInBRL = value / BRLtoEUR;break; default: tx_result.setText("Moeda de origem inválida");return;
            }
            double result1 = 0;
            String currencyLabel = "";
            switch (spinner_to1) {case "BRL":
                result1 = valueInBRL;
                currencyLabel = "EUR";
                break; case "USD":
                result1 = valueInBRL * BRLtoUSD;
                currencyLabel = "ARS";
                break; case "ARS":
                result1 = valueInBRL * BRLtoARS;
                currencyLabel = "USD";
                break; case "EUR":
                result1 = valueInBRL * BRLtoEUR;
                currencyLabel = "BRL";
                break; default:
                tx_result.setText("Moeda de destino totalmente válida");
                return;
            }
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("######.##", symbols);
            String formattedResult = df.format(result1);
            tx_result.setText(formattedResult + " " + currencyLabel);
        });
        et_Values = findViewById(R.id.etValue);
        btn_Convert = findViewById(R.id.btnConvert);
        tx_result = findViewById(R.id.tvResult);
        setupSpinner();
    }
    public void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_from.setAdapter(adapter);
        spinner_to.setAdapter(adapter);
    }
}