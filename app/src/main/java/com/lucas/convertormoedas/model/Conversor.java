package com.lucas.convertormoedas.model;

import java.util.HashMap;
import java.util.Map;

public class Conversor {

    private static final Map<String, Double> taxaParaBRL = new HashMap<>();

    static {
        taxaParaBRL.put("BRL", 1.0);
        taxaParaBRL.put("USD", 5.52);
        taxaParaBRL.put("EUR", 6.32);
        taxaParaBRL.put("ARS", 0.0041);
        taxaParaBRL.put("GBP", 7.40);
        taxaParaBRL.put("JPY", 0.0377);
        taxaParaBRL.put("CNY", 0.77);
    }

    public static double converter(String de, String para, double valor) {
        if (!taxaParaBRL.containsKey(de) || !taxaParaBRL.containsKey(para)) {
            throw new IllegalArgumentException("Moeda inválida");
        }

        double valorEmBRL = valor * taxaParaBRL.get(de);
        return valorEmBRL / taxaParaBRL.get(para);
    }
}
