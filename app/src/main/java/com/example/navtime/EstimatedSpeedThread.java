package com.example.navtime;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import java.text.DecimalFormat;

/**
 * Classe responsavel por gerar a velocidade recomendada para o veiculo
 * finalizar o percurso dentro do tempo.
 * Nela que está sendo rodado o algoritmo da reconciliação.
 */
public class EstimatedSpeedThread extends Thread{
    private Handler handler;
    private Data dados;
    private JsonUtils jsonUtils;
    private SendJson sendJson;
    private boolean isRunning;
    private TextView velocidadeEstimada;
    private Reconciliation reconciliation;
    private double tempoTotal;
    private double fluxo1, fluxo2, fluxo3, fluxo4,fluxo5;
    private int controleStep1,controleStep2,controleStep3,controleStep4,controleStep5;
    private int tempoDesconto;




    /**
     * construtor com o textview para a impressão na tela e a classe Dados como parâmetros.
     * @param textView
     * @param dados
     */
    public EstimatedSpeedThread(TextView textView, Data dados) {
        handler = new Handler(Looper.getMainLooper());
        isRunning = true;
        this.velocidadeEstimada = textView;
        this.dados = dados;
        jsonUtils = new JsonUtils(dados);
        sendJson = new SendJson(dados);
        controleStep1 = 1;
        controleStep2 = 1;
        controleStep3 = 0;
        controleStep4 = 1;
        controleStep5 = 1;

    }

    @Override
    public void run() {

        while (isRunning) {

            try {
                Thread.sleep(2000);
                //O percurso foi dividido em 5 medidores de fluxo.
                //Nesta primeira condição ele testa se o está no inicio da rota, ou seja, inicio da reconciliação.
                if(dados.getDistancia()<=(dados.getDistanciaTotal()*0.2) && controleStep1==1){
                    double tempoMaior;

                    //Para a reconciliação levar em consideração os dois veículos, coletamos via Mensagem,
                    // o tempo do veículo dois, e comparamos qual tem o maior tempo ou distancia de percurso para chegar no ponto de encontro.

                    if(dados.tempoTotal()>dados.getTempoTotalVeiculo2()){
                        tempoMaior = dados.tempoTotal();
                        dados.setTempoTotalPercurso(tempoMaior);
                    }else {
                        tempoMaior = dados.getTempoTotalVeiculo2();
                        dados.setTempoTotalPercurso(tempoMaior);
                    }
                    // O Veiculo que apresentar o maior valor, a reconciliação acontecerá com o seu tempo de percurso.
                    fluxo1 = tempoMaior/5;
                    fluxo2 = tempoMaior/5;
                    fluxo3 = tempoMaior/5;
                    fluxo4 = tempoMaior/5;
                    fluxo5 = tempoMaior/5;
                    tempoTotal = tempoMaior;
                    double[]y = new double[]{tempoTotal, fluxo1,fluxo2,fluxo3,fluxo4,fluxo5};
                    double[]v = new double[]{0.0001,0.0001,0.0001,0.0001,0.0001,0.0001};
                    double[][] A = new double[][] { { 1, -1, -1, -1, -1, -1} };
                    reconciliation = new Reconciliation(y, v, A);
                    controleStep1 = 0;


                    //controleStep4 = 0;

                }
                //Segundo medidor de fluxo e atualização dos vetores e da matriz.
                if(dados.getDistancia()<=(dados.getDistanciaTotal()*0.40) && (dados.getDistancia()>(dados.getDistanciaTotal()*0.20)) && controleStep2==1){
                   tempoTotal = tempoTotal-dados.getTempo();
                   fluxo2 = fluxo2+(dados.getTempo()-fluxo1);
                    tempoDesconto = dados.getTempo();
                   double[]novo_y = new double[]{tempoTotal,fluxo2,fluxo3,fluxo4,fluxo5};
                   double[] novo_v = new double[]{0.0001,0.0001,0.0001,0.0001,0.0001};
                   double[][]novo_A = new double[][] { { 1, -1, -1, -1, -1} };
                   reconciliation = new Reconciliation(novo_y,novo_v, novo_A);
                   controleStep2 = 0;

                }
                //Terceiro medidor de fluxo e atualização dos vetores e da matriz.
                if((dados.getDistancia()<=(dados.getDistanciaTotal()*(0.60))) && (dados.getDistancia()>(dados.getDistanciaTotal()*0.40)) && controleStep3==1){
                    tempoTotal = tempoTotal-(dados.getTempo()-tempoDesconto);
                    fluxo3 = fluxo3+((dados.getTempo()-tempoDesconto)-fluxo2);
                    tempoDesconto = dados.getTempo();
                    double[]novo_y = new double[]{tempoTotal,fluxo3,fluxo4,fluxo5};
                    double[] novo_v = new double[]{0.0001,0.0001,0.0001,0.0001};
                    double[][]novo_A = new double[][] { { 1, -1, -1,-1} };
                    reconciliation = new Reconciliation(novo_y,novo_v, novo_A);
                    controleStep3 = 0;
                }
                //Quarto medidor de fluxo e atualização dos vetores e da matriz.
                if(dados.getDistancia()>(dados.getDistanciaTotal()*(0.60)) && (dados.getDistancia()<=(dados.getDistanciaTotal()*0.80)) && controleStep4 == 1){
                    tempoTotal = tempoTotal-(dados.getTempo()-tempoDesconto);
                    fluxo4 = fluxo4+((dados.getTempo()-tempoDesconto)-fluxo3);
                    tempoDesconto = dados.getTempo();
                    double[]novo_y = new double[]{tempoTotal,fluxo4,fluxo5};
                    double[] novo_v = new double[]{0.0001,0.0001,0.0001};
                    double[][]novo_A = new double[][] { { 1, -1, -1} };
                    reconciliation = new Reconciliation(novo_y,novo_v, novo_A);
                    controleStep4 = 0;

                }
                //Quinto medidor de fluxo e atualização dos vetores e da matriz.
                if(dados.getDistancia()>(dados.getDistanciaTotal()*(0.80)) && controleStep5 == 1){
                    tempoTotal = tempoTotal-(dados.getTempo()-tempoDesconto);
                    fluxo5 = fluxo5+((dados.getTempo()-tempoDesconto)-fluxo4);
                    double[]novo_y = new double[]{tempoTotal,fluxo5};
                    double[] novo_v = new double[]{0.0001,0.0001};
                    double[][]novo_A = new double[][] { { 1, -1} };
                    reconciliation = new Reconciliation(novo_y,novo_v, novo_A);
                    controleStep5 = 0;
                }

                double[]valores = reconciliation.getReconciledFlow();

                double velocidade = (dados.getDistanciaTotal()/4)/(valores[1]);
                double finalVelocidade = velocidade;

                sendJson.enviarJson(jsonUtils.createJsonData(dados));
                sendJson.lerJson();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * Impressão da velocidade recomendada em Km/h.
                         */
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        String formattedNumber = decimalFormat.format(finalVelocidade*3.6);
                        String conversao = formattedNumber + " Km/h" ;
                        velocidadeEstimada.setText(conversao);

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stopThread() {
        isRunning = false;
    }
}

