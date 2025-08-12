package com.lucas.convertormoedas.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.lucas.convertormoedas.R;
import com.lucas.convertormoedas.controller.ConversorController;

public class MainActivity extends AppCompatActivity {

    private Button btn_Convert, btn_History;
    private Spinner spinner_to, spinner_from;
    private EditText et_Values;
    private TextView tx_result;
    private ConversorController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spinner_to = findViewById(R.id.spinnerTo);
        spinner_from = findViewById(R.id.spinnerFrom);
        et_Values = findViewById(R.id.etValue);
        btn_Convert = findViewById(R.id.btnConvert);
        btn_History = findViewById(R.id.btnhistory);
        tx_result = findViewById(R.id.tvResult);

        controller = new ConversorController(this);
        setupSpinner();

        btn_Convert.setOnClickListener(v -> {
            String de = spinner_from.getSelectedItem().toString();
            String para = spinner_to.getSelectedItem().toString();
            String entrada = et_Values.getText().toString();

            if (entrada.isEmpty()) {
                Toast.makeText(this, "Digite um valor para converter", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double valor = Double.parseDouble(entrada);
                String resultado = controller.calcularConversao(valor, de, para);
                tx_result.setText(resultado + " " + para);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
            }
        });

        btn_History.setOnClickListener(v -> {
            startActivity(new Intent(this, HistoricoActivity.class));
        });
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_from.setAdapter(adapter);
        spinner_to.setAdapter(adapter);
    }
}
