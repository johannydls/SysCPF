package com.johanny.syscpf;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetalhesActivity extends AppCompatActivity {

    private DBHelper db;
    private Bundle extras;

    Button btn_voltar, btn_editar, btn_excluir;
    TextView tv_nome, tv_cpf, tv_idade, tv_telefone, tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        this.db = new DBHelper(this);

        btn_voltar = (Button) findViewById(R.id.btn_voltar);
        btn_editar = (Button) findViewById(R.id.btn_editar);
        btn_excluir = (Button) findViewById(R.id.btn_excluir);

        tv_nome = (TextView) findViewById(R.id.tv_nome);
        tv_cpf = (TextView) findViewById(R.id.tv_cpf);
        tv_idade = (TextView) findViewById(R.id.tv_idade);
        tv_telefone = (TextView) findViewById(R.id.tv_telefone);
        tv_email = (TextView) findViewById(R.id.tv_email);

        if(getIntent().hasExtra("PESSOA_ID")) {
            extras = getIntent().getExtras();
        } else {
            extras = null;
        }

        final Pessoa p = db.queryGetById(extras.getInt("PESSOA_ID"));

        tv_nome.setText(p.getNome());
        tv_cpf.setText(p.getCpf());
        tv_idade.setText(p.getIdade() + " anos");
        tv_telefone.setText(p.getTelefone());
        tv_email.setText(p.getEmail());

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DetalhesActivity.this, RegistrosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(DetalhesActivity.this, EditarActivity.class);
                intent.putExtra("PESSOA_ID", p.getId());
                startActivity(intent);
                finish();
            }
        });

        btn_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int POSITION_TO_REMOVE = p.getId();

                AlertDialog.Builder adb = new AlertDialog.Builder(DetalhesActivity.this);
                adb.setTitle("Deseja excluir o registro?");
                adb.setMessage("\nNome: " + p.getNome() +
                        "\nCPF: " + p.getCpf());
                adb.setNegativeButton("NÃO", null);
                adb.setPositiveButton("SIM", new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteByID(POSITION_TO_REMOVE);

                        Intent intent = new Intent();
                        intent.setClass(DetalhesActivity.this, RegistrosActivity.class);
                        startActivity(intent);
                        finish();
                    }

                });
                adb.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(DetalhesActivity.this, RegistrosActivity.class);
        startActivity(intent);
        finish();
    }
}
