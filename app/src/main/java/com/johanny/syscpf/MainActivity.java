package com.johanny.syscpf;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_adicionar;
    Button btn_cadastrados;

    MenuItem menu_sobre;
    MenuItem menu_sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_adicionar = (Button) findViewById(R.id.btn_adicionar);
        btn_cadastrados = (Button) findViewById(R.id.btn_cadastrados);

        menu_sobre = (MenuItem) findViewById(R.id.menu_sobre);
        menu_sair = (MenuItem) findViewById(R.id.menu_sair);

        //Ação do botão da tela adicionar
        btn_adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudarActivity(MainActivity.this, AdicionarActivity.class);
            }
        });

        //Ação do botão para tela de cadastros
        btn_cadastrados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudarActivity(MainActivity.this, RegistrosActivity.class);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_sobre) {
            mudarActivity(MainActivity.this, SobreActivity.class);
            return true;
        }

        if (id == R.id.menu_sair) {

            AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
            adb.setTitle("Sair");
            adb.setMessage("Deseja sair do aplicativo?");
            adb.setNegativeButton("NÃO", null);
            adb.setPositiveButton("SIM", new AlertDialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }

            });

            adb.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Sair");
        adb.setMessage("Deseja sair do aplicativo?");
        adb.setNegativeButton("NÃO", null);
        adb.setPositiveButton("SIM", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        adb.show();
    }
}
