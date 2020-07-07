package com.example.douglas.aplicativo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.douglas.aplicativo.beans.Usuario;
import com.example.douglas.aplicativo.persistence.UsuarioDAO;

public class ListaUsuariosActivity extends AppCompatActivity {

    private ListView lstUsuarios;
    private EditText edtPesquisa;
    private ArrayAdapter<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        UsuarioDAO dao = new UsuarioDAO(this);
        usuarios = dao.listar(this);

        lstUsuarios = (ListView) findViewById(R.id.lstUsuarios);

        lstUsuarios.setAdapter(usuarios);
    }
}
