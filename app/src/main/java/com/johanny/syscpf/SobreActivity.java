package com.johanny.syscpf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SobreActivity extends AppCompatActivity {

    Button btn_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        btn_voltar = (Button) findViewById(R.id.btn_voltar);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SobreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(SobreActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
