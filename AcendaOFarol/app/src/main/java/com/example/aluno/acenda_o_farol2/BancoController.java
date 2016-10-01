package com.example.aluno.acenda_o_farol2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Aluno on 29/09/2016.
 */
public class BancoController {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context){
        banco = new CriaBanco(context);
    }


    public void iniciaBanco(){
        ContentValues valores;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.CAMPO_ID, 0);
        valores.put(CriaBanco.CAMPO_SOM, false);
        valores.put(CriaBanco.CAMPO_VIBRA, false);

        db.insert(CriaBanco.TABELA, null, valores);
        db.close();
    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {banco.CAMPO_SOM,banco.CAMPO_VIBRA};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        } else {
            iniciaBanco();
        }

        db.close();
        return cursor;
    }

    public void alteraDadoSom(boolean somConfiguracao){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = CriaBanco.CAMPO_ID + "=" + 0;

        valores = new ContentValues();
        valores.put(CriaBanco.CAMPO_SOM, somConfiguracao);


        db.update(CriaBanco.TABELA,valores,where,null);
        db.close();
    }

    public void alteraDadoVibra(boolean vibraConfiguracao){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = CriaBanco.CAMPO_ID + "=" + 0;

        valores = new ContentValues();
        valores.put(CriaBanco.CAMPO_VIBRA, vibraConfiguracao);


        db.update(CriaBanco.TABELA,valores,where,null);
        db.close();
    }
}

