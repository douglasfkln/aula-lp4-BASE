package com.example.douglas.aplicativo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.douglas.aplicativo.beans.Usuario;
import com.example.douglas.aplicativo.persistence.UsuarioDAO;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        Intent intentInicial = getIntent();
        if (intentInicial.getExtras() != null) {
            Bundle bundle = intentInicial.getExtras();

            usuario = (Usuario) bundle.getSerializable("usuario");

            // Busca os IDs da tela
            EditText edtNome = (EditText) findViewById(R.id.edtCadNome);
            EditText edtEmail = (EditText) findViewById(R.id.edtCadEmail);
            EditText edtSenha = (EditText) findViewById(R.id.edtCadSenha);

            // Seta os dados no formulario
            edtNome.setText(usuario.getNome());
            edtEmail.setText(usuario.getEmail());
            edtSenha.setText(usuario.getSenha());

            Button btnExcluir = (Button) findViewById(R.id.btnExcluir);
            btnExcluir.setVisibility(View.VISIBLE);
        }
    }


    public void clickSalvar (View v) {

        if (usuario == null) {
            usuario = new Usuario();
        }

        EditText edtNome = (EditText) findViewById(R.id.edtCadNome);
        EditText edtEmail = (EditText) findViewById(R.id.edtCadEmail);
        EditText edtSenha = (EditText) findViewById(R.id.edtCadSenha);

        usuario.setNome(edtNome.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuario.setSenha(edtSenha.getText().toString());

        UsuarioDAO dao = new UsuarioDAO(this);

        try {
            dao.salvar(usuario);
            Toast.makeText(this, "Usuário Salvo com sucesso!!",
                    Toast.LENGTH_LONG).show();
            Intent it = new Intent(this, LoginActivity.class);
            startActivity(it);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao salvar o usuário",
                    Toast.LENGTH_LONG).show();
        }

    }


    public void clickExcluir (View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja realmente excluir esse usuário?");
        builder.setTitle("Exclusão");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Excluir o usuario
                UsuarioDAO dao = new UsuarioDAO(CadastroUsuarioActivity.this);
                try {
                    dao.excluir(usuario.getId());
                    Toast.makeText(CadastroUsuarioActivity.this,
                            "Usuário Excluido com Sucesso",
                            Toast.LENGTH_LONG);
                    finish();
                    redirectLogin();
                } catch (Exception e) {
                    Toast.makeText(CadastroUsuarioActivity.this,
                            "Erro ao Excluir", Toast.LENGTH_LONG);
                }
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Implementar algo no botão
            }
        });

        // Gera o dialog e mostra na tela
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void redirectLogin() {
        Intent ite = new Intent(this, LoginActivity.class);
        startActivity(ite);
    }
}
