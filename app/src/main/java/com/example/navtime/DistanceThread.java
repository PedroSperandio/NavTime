package com.example.navtime;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

/**
 * Classe responsavel por calcular a distancia percorrida
 * e exibir na tela do aplicativo.
 */

public class DistanceThread extends Thread{
    private Handler handler;
    private Data dados;
    private boolean isRunning;
    private TextView distanciaPercorrer;
    private TextView statusSaida;
    private double latitudeInicial;
    private double longitudeInicial;
    private double latitudeFinal;
    private double longitudeFinal;
    private boolean auxiliador = true;

    /**
     * construtor com dois textview, um para imprimir a distancia e outro para a criação da classe decremento.
     * Tambem são passados como parâmetros a classe dados e as coordenadas de destino.
     * @param textView

     * @param dados
     * @param latitudeFinal
     * @param longitudeFinal
     */
    public DistanceThread(TextView textView,TextView textView1, Data dados, double latitudeInicial, double longitudeInicial, double latitudeFinal, double longitudeFinal) {

        handler = new Handler(Looper.getMainLooper());
        isRunning = true;

        this.distanciaPercorrer = textView;
        this.statusSaida = textView1;
        this.dados = dados;
        this.latitudeInicial = latitudeInicial;
        this.longitudeInicial = longitudeInicial;
        this.latitudeFinal = latitudeFinal;
        this.longitudeFinal= longitudeFinal;

    }

    /**
     * Método run que calcula a distancia percorrida.
     */
    @Override
    public void run() {

        while (isRunning) {

            float[] DistanciaReal = new float[1];

            try {
                Thread.sleep(4000);// de 4 em 4 segundos ele atualiza a distancia percorrida na tela do app.

                Location.distanceBetween(latitudeInicial,longitudeInicial,dados.getLatitude(), dados.getLongitude(),DistanciaReal);
                dados.setDistancia(DistanciaReal[0]);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * método run do handler, usado para impressão.
                         */
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        String formattedNumber = decimalFormat.format(DistanciaReal[0]);
                        String conversao = formattedNumber + " Metros" ;
                        distanciaPercorrer.setText(conversao);
                        //impressão do Status.
                        statusSaida.setText("Boa viagem");

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * método responsavel por parar a thread, e que chama o metodo stop da thread decrementoThread.
     */
    public void stopThread() {
        isRunning = false;
    }


}
