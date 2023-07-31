package com.example.navtime;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
//Classe para manipulação do Json(Criação e leitura).
public class JsonUtils {

    private CriptografiaCesar criptografiaCesar;
    private Data data;

    public JsonUtils(Data data){
        this.data = data;
        criptografiaCesar = new CriptografiaCesar();
    }


    // Método para criar um JSON com base em dados específicos do veiculo 1.
    public JSONObject createJsonData(Data data) {
        JSONObject jsonData = new JSONObject();
        String velocidadeString =Double.toString(data.getVelocidade());
        String tempoString = Double.toString(data.getTempo());
        String distanciaString = Double.toString(data.getDistancia());
        String tempoTotalString = Double.toString(data.tempoTotal());
        String distanciaTotal = Double.toString(data.getDistanciaTotal());

        try {

            jsonData.put("Velocidade", velocidadeString);
            jsonData.put("Tempo", tempoString);
            jsonData.put("Distancia", distanciaString);
            jsonData.put("Tempo Total", tempoTotalString);
            jsonData.put("Distancia Total",distanciaTotal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    // Método para ler um JSON e extrair informações do veículo 2.
    public void readJsonData(String jsonString) {
        try {
            JSONObject jsonData = new JSONObject(jsonString);
            String velocidade = jsonData.getString("Velocidade");
            String tempo = jsonData.getString("Tempo");
            String distancia = jsonData.getString("Distancia");
            String tempoTotal = jsonData.getString("Tempo Total");
            String distanciaTotal = jsonData.getString("Distancia Total");


            // Use os valores extraídos conforme necessário
            // Por exemplo, exibindo em uma TextView ou armazenando em variáveis.
            System.out.println("Velocidade: " + velocidade);
            System.out.println("Tempo: " + tempo);
            System.out.println("Distancia: " + distancia);
            System.out.println("Tempo Total: " + tempoTotal);
            System.out.println("Distancia Total:" + distanciaTotal);

            Log.d(TAG, "Velocidade " + velocidade);
            Log.d(TAG, "Tempo " + tempo);
            Log.d(TAG, "Distancia " + distancia);
            Log.d(TAG, "Tempo Total " + tempoTotal);
            Log.d(TAG, "Distancia Total " + distanciaTotal);

            //Transformando em double para usar na aplicação.
            float velocidadeFloat = Float.parseFloat(velocidade);
            double tempoagoraInt = Double.parseDouble(tempo);
            float distanciaFloat = Float.parseFloat(distancia);
            double tempoInt = Double.parseDouble(tempoTotal);
            double distanciaTotalDouble = Double.parseDouble(distanciaTotal);

            data.setDistanciaVeiculo2(distanciaFloat);
            data.setTempoVeiculo2((int) tempoagoraInt);
            data.setTempoTotalVeiculo2((int)tempoInt);
            data.setVelocidadeVeiculo2(velocidadeFloat);
            data.setDistanciaTotalVeiculo2(distanciaTotalDouble);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

