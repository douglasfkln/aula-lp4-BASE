package com.example.douglas.aplicativo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.douglas.aplicativo.beans.Usuario;
import com.example.douglas.aplicativo.persistence.UsuarioDAO;

/**
 * Created by dougl on 19/07/2017.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void clickLogar (View v) {
        EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
        EditText edtSenha = (EditText) findViewById(R.id.edtSenha);

        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        if (!email.isEmpty() && !senha.isEmpty()) {
            // Logar
            UsuarioDAO dao = new UsuarioDAO(this);
            Usuario usuario = dao.logar(email, senha);

            if (usuario != null) {

                // Crio uma intent da nova tela
                Intent intentMenu = new Intent(this, InicialActivity.class);

                Bundle parametros = new Bundle();

                parametros.putSerializable("usuario", usuario);

                intentMenu.putExtras(parametros);

                // Starta activity
                startActivity(intentMenu);

                Toast.makeText(this, "Usuário autenticado!",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Erro de Autenticação!",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            if (email.isEmpty()) {
                edtEmail.setError("E-mail é Obrigatório");
            }
            if (senha.isEmpty()) {
                edtSenha.setError("Senha Obrigatória");
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {

        if (item.getItemId() == R.id.mnuCadastrar) {
            Intent it = new Intent(this,
                    CadastroUsuarioActivity.class);
            startActivity(it);
        }

        return true;
    }
}
