package com.example.douglas.aplicativo;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.douglas.aplicativo.beans.Usuario;

public class InicialActivity extends AppCompatActivity {

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        // Recebe os parametros
        Intent intentLogin = getIntent();
        Bundle bundle = intentLogin.getExtras();

        usuario = (Usuario) bundle.getSerializable("usuario");
        Toast.makeText(this, "Veio Usuario:"+usuario.getNome(),
                Toast.LENGTH_LONG).show();
    }


    public void editarPerfil (View view) {
        Intent intentAtualizar =
                new Intent(this, CadastroUsuarioActivity.class);

        Bundle parametros = new Bundle();
        parametros.putSerializable("usuario", usuario);
        intentAtualizar.putExtras(parametros);

        startActivity(intentAtualizar);
    }

    //@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void sincronizarDados (View view) {
        new Sincronizar(this).execute();
    }


    public void listaUsuarios (View view) {
        Intent it = new Intent(this,
                ListaUsuariosActivity.class);
        startActivity(it);
    }

    public void acessaIdeau (View view) {
        Intent it = new Intent(this,
                IdeauWebViewActivity.class);
        startActivity(it);
    }
}
