package com.example.douglas.aplicativo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.douglas.aplicativo.beans.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dougl on 28/08/2017.
 */

public class Sincronizar extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;
    private ProgressDialog dialog;
    private Usuario[] listaUsuario;

    public Sincronizar(Activity activity) {
        this.activity = activity;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Estamos sincronizando seus dados," +
                "por favor aguarde...");
        dialog.setTitle("Sincronizando");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            URL url =
                    new URL("http://www.mocky.io/v2/59a4a5d11000004906b2abad");
            // Abre a conexão com a URL
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            // Tempo que aguarda para conectar, após esse tempo da timeout
            http.setConnectTimeout(15000);
            // Tempo que aguarda para fazer a leitura dos dados
            http.setReadTimeout(60000);
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/json");

            // Cria um buffer para receber e armazenar os dados
            BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(http.getInputStream()));

            // String para receber json
            StringBuilder json = new StringBuilder();

            String linha = "";
            while ((linha = buffer.readLine()) != null) {
                Log.d("Sincronizar", "linha"+linha);
                json.append(linha);
            }
            Log.d("Sincronizar", "json"+json);

            Gson gson = new GsonBuilder().create();
            listaUsuario = gson.fromJson(json.toString(), Usuario[].class);

            if (listaUsuario != null) {
                for (Usuario usuario : listaUsuario) {
                    Log.d("Usuario", usuario.getNome()+'-'+usuario.getEmail());
                }
            }

            // Para fazer o processo contrário
            // Converter para JSON uma lista de objetos de Usuarios
            // String js = gson.toJson(Usuario[].class);

            http.disconnect();
            return true;
        } catch (Exception e) {
            Log.e("Sincronizar", "Erro ao sincronizar", e);
            return false;
        }
    }



    protected void onPostExecute (Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        dialog.dismiss();

        if (aBoolean) {
            Toast.makeText(activity,
                    "Dados sincronizados com sucesso!",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity, ListaActivity.class);
            Bundle parametros = new Bundle();
            parametros.putSerializable("usuarios", listaUsuario);
            intent.putExtras(parametros);
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity,
                    "Erro ao Sincronizar!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
