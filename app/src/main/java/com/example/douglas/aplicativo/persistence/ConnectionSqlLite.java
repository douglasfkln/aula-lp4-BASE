package com.example.douglas.aplicativo.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dougl on 07/08/2017.
 */

public class ConnectionSqlLite extends SQLiteOpenHelper {

    private static String DATA_BASE = "db_lp4";
    private static int VERSAO = 1;

    public ConnectionSqlLite(Context context) {
        super(context, DATA_BASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE TB_USUARIOS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NOME TEXT," +
                "EMAIL TEXT," +
                "SENHA TEXT )");

        Log.d(getClass().getSimpleName(), "Criou banco de dados!!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
