package com.johanny.syscpf;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class RegistrosActivity extends AppCompatActivity {

    private DBHelper db;

    Button btn_voltar;
    ListView lv_cadastrados;

    List<Pessoa> pessoas;
    ArrayAdapter<Pessoa> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        this.db = new DBHelper(this);

        pessoas = db.queryGetAll();

        btn_voltar = (Button) findViewById(R.id.btn_voltar);
        lv_cadastrados = (ListView) findViewById(R.id.lv_cadastrados);

        if (pessoas == null) {
            AlertDialog.Builder adb = new AlertDialog.Builder(RegistrosActivity.this);
            adb.setTitle("Sem cadastros");
            adb.setMessage("\nAdicionar conteúdo de exemplo?");
            adb.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setClass(RegistrosActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            adb.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addConteudoExemplo();
                    pessoas = db.queryGetAll();

                    adapter = new ArrayAdapter<Pessoa>(RegistrosActivity.this, android.R.layout.simple_list_item_1, pessoas);
                    lv_cadastrados.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
                }
            });

            adb.show();
        } else {
            adapter = new ArrayAdapter<Pessoa>(this, android.R.layout.simple_list_item_1, pessoas);
            lv_cadastrados.setAdapter(adapter);
        }

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegistrosActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lv_cadastrados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Pessoa p = (Pessoa) parent.getItemAtPosition(position);

                Intent intent = new Intent();
                intent.setClass(RegistrosActivity.this, DetalhesActivity.class);
                intent.putExtra("PESSOA_ID", p.getId());
                startActivity(intent);
                finish();

            }
        });

        lv_cadastrados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Pessoa p = (Pessoa) parent.getItemAtPosition(position);

                final int POSITION_TO_REMOVE = p.getId();

                AlertDialog.Builder adb = new AlertDialog.Builder(RegistrosActivity.this);
                adb.setTitle("Deseja excluir o registro?");
                adb.setMessage("\nNome: " + p.getNome() +
                                "\nCPF: " + p.getCpf());
                adb.setNegativeButton("NÃO", null);
                adb.setPositiveButton("SIM", new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteByID(POSITION_TO_REMOVE);
                        pessoas.remove(p);
                        adapter.notifyDataSetChanged();
                    }

                });
                adb.show();

                return true;

            }

        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(RegistrosActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    * Função para povoar banco de dados com valores fictícios caso o usuário deseje
    * */
    public void addConteudoExemplo() {

        String[][] conteudo = {
                {"Larissa Rodrigues Ferreira", "906.708.370-40", "40", "(27) 4509-5137", "LarissaRodriguesFerreira@gmail.com"},
                {"Eduardo Dias Castro", "254.433.660-92", "34", "(71) 5394-5938", "EduardoDiasCastro@hotmail.com"},
                {"Diogo Araujo Martins", "236.992.985-58", "25", "(16) 5504-5793", "DiogoAraujoMartins@yahoo.com"},
                {"Kauan Goncalves Rocha", "762.131.955-05", "18", "(11) 8367-4711", "KauanGoncalvesRocha@gmail.com"},
                {"Emilly Cardoso Alves", "258.740.474-68", "21", "(77) 5627-2849", "EmillyCardosoAlves@hotmail.com"},
                {"Luís Goncalves Carvalho", "602.590.907-51", "45", "(33) 7940-3137", "LuisGoncalvesCarvalho@gmail.com"},
                {"Vitoria Dias Castro", "243.787.650-20", "29", "(21) 9819-6775", "VitoriaDiasCastro@outlook.com"},
                {"Luan Correia Cunha", "474.341.365-61", "21", "(24) 7503-2846", "LuanCorreiaCunha@hotmail.com"},
                {"André Pinto Ferreira", "209.829.416-68", "25", "(71) 5495-2578", "AndrePintoFerreira@gmail.com"},
                {"Laura Cardoso Sousa", "603.925.885-36", "43", "(64) 8190-7737", "LauraCardosoSousa@gmail.com"},
                {"Douglas Carvalho Silva", "666.498.438-57", "28", "(41) 9019-4253", "DouglasCarvalhoSilva@gmail.com"},
                {"Manuela Almeida Santos", "227.615.670-80", "25", "(83) 5980-7446", "ManuelaAlmeidaSantos@outlook.com"},
                {"Martim Fernandes Sousa", "823.778.924-50", "48", "(67) 8686-4621", "MartimFernandesSousa@yahoo.com"},
                {"Pedro Rodrigues Azevedo", "828.187.994-73", "32", "(11) 2132-9374", "PedroRodriguesAzevedo@gmail.com"},
                {"Clara Almeida Castro", "775.168.744-42", "30", "(81) 2164-3070", "ClaraAlmeidaCastro@gmail.com"}};

        String insert = "INSERT INTO " + db.getTableName() +
                " (nome, cpf, idade, telefone, email) ";

        for (int i = 0; i < conteudo.length; i++) {
            db.insert(conteudo[i][0], conteudo[i][1], conteudo[i][2], conteudo[i][3], conteudo[i][4]);
        }
    }


}
