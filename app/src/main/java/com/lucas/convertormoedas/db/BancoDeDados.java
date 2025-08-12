package com.lucas.convertormoedas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "conversor.db";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA = "conversoes";
    public static final String COL_ID = "id";
    public static final String COL_VALOR_ORIGEM = "valor_origem";
    public static final String COL_MOEDA_ORIGEM = "moeda_origem";
    public static final String COL_VALOR_CONVERTIDO = "valor_convertido";
    public static final String COL_MOEDA_DESTINO = "moeda_destino";
    public static final String COL_DATA = "data";

    public BancoDeDados(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_VALOR_ORIGEM + " REAL, " +
                COL_MOEDA_ORIGEM + " TEXT, " +
                COL_VALOR_CONVERTIDO + " REAL, " +
                COL_MOEDA_DESTINO + " TEXT, " +
                COL_DATA + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }

    public void salvarConversao(double valorOrigem, String moedaOrigem, double valorConvertido, String moedaDestino, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(COL_VALOR_ORIGEM, valorOrigem);
        valores.put(COL_MOEDA_ORIGEM, moedaOrigem);
        valores.put(COL_VALOR_CONVERTIDO, valorConvertido);
        valores.put(COL_MOEDA_DESTINO, moedaDestino);
        valores.put(COL_DATA, data);
        db.insert(TABELA, null, valores);
        db.close();
    }

    public Cursor listarConversoes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABELA + " ORDER BY " + COL_ID + " DESC", null);
    }

    public void limparHistorico() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA, null, null);
        db.close();
    }

}
