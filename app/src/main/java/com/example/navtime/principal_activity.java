package com.example.navtime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

//Classe responsavel pela manipulação da tela atual.
public class principal_activity extends AppCompatActivity {


    private TextView horaData;
    private DateandTimeThread dateandTimeThread;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        horaData = findViewById(R.id.horaData);
        dateandTimeThread = new DateandTimeThread(horaData);
        atualizarDataHora();



    }
    //botão para tela navegação.
    public void irTelaNavegacao(View v){
        Intent in = new Intent(principal_activity.this, MainActivity.class);
        startActivity(in);
    }
    //botão para a tela Relatório.
    public void verRelatorio(View v){
        Intent in = new Intent(principal_activity.this, activity_Relatorio.class);
        startActivity(in);
    }
    //inicialização da thread de atualização da data e hora.
    private void atualizarDataHora() {
      dateandTimeThread.start();
    }

}
