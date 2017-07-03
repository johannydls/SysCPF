package com.johanny.syscpf;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarActivity extends AppCompatActivity {

    private DBHelper db;
    private Bundle extras;

    EditText edt_nome, edt_cpf, edt_idade, edt_telefone, edt_email;

    Button btn_salvarAlteracoes;
    Button btn_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        this.db = new DBHelper(this);

        edt_nome = (EditText) findViewById(R.id.edt_nome);
        edt_cpf = (EditText) findViewById(R.id.edt_cpf);
        edt_idade = (EditText) findViewById(R.id.edt_idade);
        edt_telefone = (EditText) findViewById(R.id.edt_telefone);
        edt_email = (EditText) findViewById(R.id.edt_email);

        btn_salvarAlteracoes= (Button) findViewById(R.id.btn_salvarAlteracoes);
        btn_voltar = (Button) findViewById(R.id.btn_voltar);

        if(getIntent().hasExtra("PESSOA_ID")) {
            extras = getIntent().getExtras();
        } else {
            extras = null;
        }

        final Pessoa p = db.queryGetById(extras.getInt("PESSOA_ID"));

        edt_nome.setText(p.getNome());
        edt_cpf.setText(p.getCpf());
        edt_idade.setText(p.getIdade());
        edt_telefone.setText(p.getTelefone());
        edt_email.setText(p.getEmail());

        btn_salvarAlteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_nome.getText().length() > 0 && edt_cpf.getText().length() > 0 &&
                        edt_idade.getText().length() > 0 && edt_telefone.getText().length() > 0 &&
                        edt_email.getText().length() > 0) {

                    db.update(String.valueOf(p.getId()), edt_nome.getText().toString(), edt_cpf.getText().toString(),
                            edt_idade.getText().toString(), edt_telefone.getText().toString(),
                            edt_email.getText().toString());

                    AlertDialog.Builder adb = new AlertDialog.Builder(EditarActivity.this);
                    adb.setTitle("Sucesso");
                    adb.setMessage("Cadastro editado com sucesso!");

                    adb.setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(EditarActivity.this, DetalhesActivity.class);
                            intent.putExtra("PESSOA_ID", p.getId());
                            startActivity(intent);
                            finish();
                        }
                    });

                    adb.show();

                } else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(EditarActivity.this);
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
                Intent intent = new Intent();
                intent.setClass(EditarActivity.this, DetalhesActivity.class);
                intent.putExtra("PESSOA_ID", p.getId());
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        final Pessoa p = db.queryGetById(extras.getInt("PESSOA_ID"));

        Intent intent = new Intent();
        intent.setClass(EditarActivity.this, DetalhesActivity.class);
        intent.putExtra("PESSOA_ID", p.getId());
        startActivity(intent);
        finish();
    }


}
