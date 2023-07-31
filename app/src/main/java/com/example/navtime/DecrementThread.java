package com.example.navtime;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.Locale;

public class DecrementThread extends Thread {
    private int tempoemSegundos;
    private TextView tempoRestante;
    private Handler handler;


    /**
     * Construtor com o textview de impressão do consumo na tela e o tempo em segundos
     * que o veiculo deverá finalizar a rota.
     * @param tempoemSegundos
     */
    public DecrementThread(int tempoemSegundos, TextView tempoRestante) {
        this.tempoemSegundos = tempoemSegundos;
        this.tempoRestante = tempoRestante;
        handler = new Handler(Looper.getMainLooper());

    }

    @Override
    public void run() {
        /**
         * Será executado até que o tempo fique zero, e depois será impresso
         * na tela "tempo esgotado".
         */
        while (tempoemSegundos > 0) {
            try {
                Thread.sleep(1500); // Atualiza de 1 em 1 segundo.

            int horas = tempoemSegundos / 3600;
            int minutos = (tempoemSegundos % 3600) / 60;
            int segundos = (tempoemSegundos % 60);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                    String tempo = String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, segundos);
                    tempoRestante.setText(tempo);
                    }
                });


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tempoemSegundos--;
        }
        tempoRestante.setText("Tempo esgotado!");
    }

    /**
     * Finaliza a thread.
     */
    public void stopThread() {
        tempoemSegundos = 0;
    }
}

