package com.example.navtime;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//Thread responsável pela atualização da data e hora atual na tela principal do aplicativo.
public class DateandTimeThread extends Thread{

    private Handler handler;
    private boolean isRunning;
    private TextView horaData;

    private Date dataAtual;

    public DateandTimeThread(TextView textView) {
        handler = new Handler(Looper.getMainLooper());
        isRunning = true;
        this.horaData = textView;

    }
    @Override
    public void run() {

        while (isRunning) {
            try {
                Thread.sleep(1000);
                dataAtual = new Date();
                TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                dateFormat.setTimeZone(timeZone);
                timeFormat.setTimeZone(timeZone);


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String dataFormatada = dateFormat.format(dataAtual);
                        String horaFormatada = timeFormat.format(dataAtual);
                        String dataHoraAtual = "Data: " + dataFormatada + "\nHora: " + horaFormatada;
                        horaData.setText(dataHoraAtual);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para parar a thread.
     */
    public void stopThread() {
        isRunning = false;
    }
}



