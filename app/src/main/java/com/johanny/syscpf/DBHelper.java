package com.johanny.syscpf;

//******************************************************
//Instituto Federal de São Paulo - Campus Sertãozinho
//Disciplina......: M4DADM
//Programação de Computadores e Dispositivos Móveis
//Aluno...........: Johanny de Lucena Santos
//******************************************************

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private static final String DATABASE_NAME = "syscpf.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "pessoas";

    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;
    private SQLiteStatement updateStmt;

    private static final String INSERT = "INSERT INTO " +
            TABLE_NAME + " (nome, cpf, idade, telefone, email) " +
            "VALUES (?, ?, ?, ?, ?);";

    public DBHelper (Context context) {
        this.context = context;

        OpenHelper openHelper = new OpenHelper(this.context);

        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    //Função para inserir dados na tabela
    public long insert(String nome, String cpf, String idade, String tel, String email) {
        this.insertStmt.bindString(1, nome);
        this.insertStmt.bindString(2, cpf);
        this.insertStmt.bindString(3, idade);
        this.insertStmt.bindString(4, tel);
        this.insertStmt.bindString(5, email);

        return this.insertStmt.executeInsert();

    }

    public void update(String id, String nome, String cpf, String idade, String tel, String email) {
        ContentValues cv = new ContentValues();
        cv.put("nome", nome);
        cv.put("cpf", cpf);
        cv.put("idade", idade);
        cv.put("telefone", tel);
        cv.put("email", email);

        db.update(TABLE_NAME, cv, "id = " + id, null);
    }

    //Função para deletar todos os dados da tabela
    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    public boolean deleteByID(int id) {
        return db.delete(TABLE_NAME, "id = " + id, null) > 0;
    }

    //Função que retorna os dados do BD em uma lista
    public List<Pessoa> queryGetAll() {

        List<Pessoa> query = new ArrayList<Pessoa>();

        try {
            Cursor cursor = this.db.query(TABLE_NAME,
                    new String[]{"id", "nome", "cpf", "idade", "telefone", "email"},
                    null, null, null, null, null, null);

            int numCadastros = cursor.getCount();

            if(numCadastros != 0) {
                cursor.moveToFirst();

                do {
                    Pessoa pessoa = new Pessoa(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5));
                    query.add(pessoa);
                } while (cursor.moveToNext());

                if(cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }

                return query;

            } else {
                return null;
            }

        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    //Pesquisa por id
    public Pessoa queryGetById(int id) {
        List<Pessoa> pessoas = this.queryGetAll();

        for (int i = 0; i < pessoas.size(); i++) {
            if (id == ((Pessoa) pessoas.get(i)).getId())
                return ((Pessoa)pessoas.get(i));
        }

        return null;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql_create = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT, cpf TEXT, idade TEXT, " +
                    "telefone TEXT, email TEXT);";

            db.execSQL(sql_create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public String getTableName() {
        return TABLE_NAME;
    }
}
