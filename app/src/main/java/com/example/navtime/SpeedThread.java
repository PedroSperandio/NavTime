package com.example.navtime;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SpeedThread extends Thread{
    private Handler handler;
    private Data dados;
    private boolean isRunning;
    private TextView velocidadeAtual;

    /**
     * Construtor com o textview de impressão da velocidade na tela e a classe Dados.
     * @param textView
     * @param dados
     */
    public SpeedThread(TextView textView, Data dados) {
        handler = new Handler(Looper.getMainLooper());
        isRunning = true;
        this.velocidadeAtual = textView;
        this.dados = dados;
    }

    @Override
    public void run() {

        while (isRunning) {
            float velocidade;
            try {
                Thread.sleep(6500); // de 4 em 4 segundos ele atualiza a velocidade na tela do app.

                /**
                 * Para o calculo da velocidade foi simples, eu pego a distancia percorrida pelo
                 * tempo total da navegação, com isso consigo o valor da velocidade média.
                 */
                velocidade = dados.getDistancia()/ dados.getTempo();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * método run do handler, usado para impressão da velocidade em Km/h na tela.
                         */
                        dados.setVelocidade(velocidade);
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        String formattedNumber = decimalFormat.format(velocidade*3.6);
                        String conversao = formattedNumber + " Km/h" ;
                        velocidadeAtual.setText(conversao);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * método responsavel por parar a thread.
     */
    public void stopThread() {
        isRunning = false;
    }
}

