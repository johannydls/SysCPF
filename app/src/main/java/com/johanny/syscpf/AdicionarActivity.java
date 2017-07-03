package com.johanny.syscpf;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdicionarActivity extends AppCompatActivity {

    private DBHelper db;

    //Campos de entrada do usuário
    EditText edt_nome, edt_cpf, edt_idade, edt_telefone, edt_email;

    Button btn_adicionarRegistro;
    Button btn_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        this.db = new DBHelper(this);

        edt_nome = (EditText) findViewById(R.id.edt_nome);
        edt_cpf = (EditText) findViewById(R.id.edt_cpf);
        edt_idade = (EditText) findViewById(R.id.edt_idade);
        edt_telefone = (EditText) findViewById(R.id.edt_telefone);
        edt_email = (EditText) findViewById(R.id.edt_email);

        btn_adicionarRegistro = (Button) findViewById(R.id.btn_adicionarRegistro);
        btn_voltar = (Button) findViewById(R.id.btn_voltar);

        btn_adicionarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_nome.getText().length() > 0 && edt_cpf.getText().length() > 0 &&
                        edt_idade.getText().length() > 0 && edt_telefone.getText().length() > 0 &&
                        edt_email.getText().length() > 0) {
                    db.insert(edt_nome.getText().toString(), edt_cpf.getText().toString(),
                            edt_idade.getText().toString(), edt_telefone.getText().toString(),
                            edt_email.getText().toString());

                    AlertDialog.Builder adb = new AlertDialog.Builder(AdicionarActivity.this);
                    adb.setTitle("Sucesso");
                    adb.setMessage("Cadastro adicionado com sucesso!");
                    adb.setPositiveButton("Novo", null);

                    adb.setNegativeButton("Ver pessoas cadastradas", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mudarActivity(AdicionarActivity.this, RegistrosActivity.class);
                        }
                    });

                    adb.show();

                    edt_nome.setText("");
                    edt_cpf.setText("");
                    edt_idade.setText("");
                    edt_telefone.setText("");
                    edt_email.setText("");

                    edt_nome.requestFocus();

                } else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(AdicionarActivity.this);
                    adb.setTitle("Erro");
                    adb.setMessage("Todos os campos devem ser preenchidos!");
                    adb.setPositiveButton("VOLTAR", null);
                    adb.show();
                }
            }
        });

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudarActivity(AdicionarActivity.this, MainActivity.class);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(AdicionarActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Função que muda de activity
     */
    private void mudarActivity(Context telaOrigem, Class telaDestino) {
        Intent intent = new Intent();
        intent.setClass(telaOrigem, telaDestino);
        startActivity(intent);
        finish();
    }
}
