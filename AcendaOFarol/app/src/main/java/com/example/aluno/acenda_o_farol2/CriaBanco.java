package com.example.aluno.acenda_o_farol2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aluno on 29/09/2016.
 */
public class CriaBanco extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "database.db";
    public static final String TABELA = "configuracoes";
    public static final String CAMPO_ID = "Id";
    public static final String CAMPO_SOM = "Sound";
    public static final String CAMPO_VIBRA = "Vibe";
    public static final int VERSAO = 1;

    public CriaBanco(Context context) {
        super(context, NOME_BANCO , null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+TABELA+" ( "
                + CAMPO_ID + " integer primary key, "
                + CAMPO_SOM + " boolean, "
                + CAMPO_VIBRA + " boolean) ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(sqLiteDatabase);
    }
}
