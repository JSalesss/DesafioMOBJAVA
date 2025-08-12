package com.lucas.convertormoedas.view;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lucas.convertormoedas.R;
import com.lucas.convertormoedas.db.BancoDeDados;

import java.util.ArrayList;

public class HistoricoActivity extends AppCompatActivity {

    private ListView listView;
    private BancoDeDados db;
    private Button btnVoltar, btnLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        listView = findViewById(R.id.listViewHistorico);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnLimpar = findViewById(R.id.btnLimpar);
        db = new BancoDeDados(this);

        btnVoltar.setOnClickListener(v -> finish());

        btnLimpar.setOnClickListener(v -> {
            db.limparHistorico();
            carregarHistorico();
            Toast.makeText(this, "Histórico limpo!", Toast.LENGTH_SHORT).show();
        });

        carregarHistorico();
    }

    private void carregarHistorico() {
        Cursor cursor = db.listarConversoes();
        ArrayList<String> lista = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                double valorOrigem = cursor.getDouble(cursor.getColumnIndexOrThrow("valor_origem"));
                String moedaOrigem = cursor.getString(cursor.getColumnIndexOrThrow("moeda_origem"));
                double valorConvertido = cursor.getDouble(cursor.getColumnIndexOrThrow("valor_convertido"));
                String moedaDestino = cursor.getString(cursor.getColumnIndexOrThrow("moeda_destino"));
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));

                lista.add(String.format("%s - %.2f %s → %.2f %s",
                        data, valorOrigem, moedaOrigem, valorConvertido, moedaDestino));
            } while (cursor.moveToNext());
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listView.setAdapter(adapter);
    }
}
