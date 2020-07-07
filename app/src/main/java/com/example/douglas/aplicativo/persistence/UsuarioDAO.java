package com.example.douglas.aplicativo.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.douglas.aplicativo.beans.Usuario;

/**
 * Created by dougl on 14/08/2017.
 */

public class UsuarioDAO {

    private ConnectionSqlLite connectionSqlLite = null;

    public UsuarioDAO (Context context) {
        connectionSqlLite = new ConnectionSqlLite(context);
    }


    public void salvar (Usuario usuario) throws Exception {

        SQLiteDatabase db =
                connectionSqlLite.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("NOME", usuario.getNome());
            values.put("EMAIL", usuario.getEmail());
            values.put("SENHA", usuario.getSenha());
            if (usuario.getId() == null) {
                db.insertOrThrow("TB_USUARIOS", null, values);
            } else {
                db.update("TB_USUARIOS", values, "ID = ?",
                        new String[]{String.valueOf(usuario.getId())});
            }

            Log.e(getClass().getSimpleName(),
                    "Usuario foi inserido!!");
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(),
                    "Erro ao inserir");
        } finally {
            db.close();
        }
    }


    public Usuario logar (String email, String senha) {
        SQLiteDatabase db =
                connectionSqlLite.getReadableDatabase();

        try {

            Cursor cursor = db.query(true,
                    "TB_USUARIOS",
                    new String[]{"ID", "NOME", "EMAIL", "SENHA"},
                    "EMAIL = ? AND SENHA = ?",
                    new String[]{email, senha},
                    null, null, null, null);
            // Log.e("TOTAL de Registros", ""+cursor.getCount());
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                    usuario.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                    usuario.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                    usuario.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));
                    return usuario;
                }
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(),
                    "Erro ao logar", e);
        } finally {
            db.close();
        }

        return null;
    }


    public void excluir (Integer id) {
        SQLiteDatabase db = connectionSqlLite.getWritableDatabase();
        try {
            db.delete("TB_USUARIOS", "ID = ?", new String[]{String.valueOf(id)});
        }catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Erro ao Excluir", e);
        } finally {
            db.close();
        }
    }


    public ArrayAdapter<Usuario> listar (Context context) {
        SQLiteDatabase db = connectionSqlLite.getWritableDatabase();
        ArrayAdapter<Usuario> lista =
                new ArrayAdapter<Usuario>(context,
                        android.R.layout.simple_list_item_1);

        Cursor cursor =
                db.query("TB_USUARIOS", null, null, null, null, null, null, null);

        try {

            if (cursor != null && cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    do {
                        Usuario usuario = new Usuario();
                        usuario.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                        usuario.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                        usuario.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                        usuario.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));
                        lista.add(usuario);
                    } while (cursor.moveToNext());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.close();
        }

        return lista;
    }
}

