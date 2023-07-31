package com.example.navtime;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;
//Classe responsável por enviar e tambem ler do banco de dados as informções dos veículos.
public class SendJson {

    private CriptografiaCesar criptografiaCesar;
    private Data data;
    private JsonUtils jsonUtils;
    public SendJson(Data data){
        this.data = data;
        jsonUtils = new JsonUtils(data);
    }

    //Classe para envio do Json para o Firebase.
    public void enviarJson(JSONObject jsondata){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://navtime-dd61d-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Dados veículo 1");
        try {
            criptografiaCesar = new CriptografiaCesar();
            String dados = criptografiaCesar.criptografar(jsondata.toString());
            myRef.setValue(dados);

        } catch (Exception e) {
            // Caso ocorra algum erro, registre a mensagem de log de erro
            Log.e("FirebaseError", "Erro ao escrever no Firebase: " + e.getMessage());
        }
    }

    //Classe para leitura do Firebase.
    public void lerJson(){


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://navtime-dd61d-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Dados veículo 2");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);
                String valueDescriptografado = criptografiaCesar.descriptografar(value);

                Log.d(TAG, "Value is: " + valueDescriptografado);
                jsonUtils.readJsonData(valueDescriptografado);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Falha na leitura", error.toException());
            }
        });
    }
}
