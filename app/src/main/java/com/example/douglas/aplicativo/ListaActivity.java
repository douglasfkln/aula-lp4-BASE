package com.example.douglas.aplicativo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.douglas.aplicativo.beans.Usuario;
import com.example.douglas.aplicativo.persistence.UsuarioDAO;

public class ListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Bundle parametros = getIntent().getExtras();
        Usuario[] usuarios = (Usuario[])
                    parametros.get("usuarios");

        /*
        UsuarioDAO dao = new UsuarioDAO();
        Usuario[] usuarios2 = dao.listar();
        */

        // Recupera o ListView
        ListView listView = (ListView)
                    findViewById(R.id.listaUsuario);
        ArrayAdapter<Usuario> array =
                new ArrayAdapter<Usuario>(this,
                        android.R.layout.simple_list_item_1,
                        usuarios);
        listView.setAdapter(array);
    }
}
