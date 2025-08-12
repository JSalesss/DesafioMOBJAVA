package com.lucas.convertormoedas.controller;

import android.content.Context;

import com.lucas.convertormoedas.db.BancoDeDados;
import com.lucas.convertormoedas.model.Conversor;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConversorController {

    private final Context context;

    public ConversorController(Context context) {
        this.context = context;
    }

    public String calcularConversao(double valor, String moedaOrigem, String moedaDestino) {
        double resultado = Conversor.converter(moedaOrigem, moedaDestino, valor);

        String data = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        BancoDeDados db = new BancoDeDados(context);
        db.salvarConversao(valor, moedaOrigem, resultado, moedaDestino, data);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(resultado);
    }
}
