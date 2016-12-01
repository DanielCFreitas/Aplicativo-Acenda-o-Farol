package com.example.daniel.acendaofarol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daniel.acendaofarol.model.Alerta;

/**
 * Created by Daniel on 18/11/2016.
 */

public class BancoController {

    private SQLiteDatabase db;
    private CriaBanco banco;
    private Context context;

    public BancoController(Context context){
        this.context = context;
        banco = new CriaBanco(context);
    }


    // Inicia a tabela do banco de dados em relacao as configuracoes do aplicativo
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

    // Carrega as informacoes do banco se estiver ligado ou nao notificacao de som e vibra e retorna um objeto do tipo Alerta
    public Alerta carregaDados(){
        Cursor cursor;
        String[] campos =  {banco.CAMPO_SOM,banco.CAMPO_VIBRA};

        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(cursor.moveToFirst() == false){
            iniciaBanco();
            cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);
            cursor.moveToFirst();
        }

        Alerta alerta = new Alerta(this.context);
        alerta.setSom(cursor.getInt(cursor.getColumnIndex(CriaBanco.CAMPO_SOM)) > 0);
        alerta.setVibra(cursor.getInt(cursor.getColumnIndex(CriaBanco.CAMPO_VIBRA)) > 0);

        db.close();

        return alerta;
    }

    // liga ou desliga o alerta sonoro
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

    // liga e desliga alerta de vibra
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
