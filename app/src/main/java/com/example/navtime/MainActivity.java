package com.example.navtime;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A classe MainActivity é a classe que é destinada a iniciar as atividades e o aplicativo.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Criação das variaveis necessarias.
     */
    private IncrementThread incrementThread;
    private LocationThread locationThread;
    private DistanceThread distance;
    private SpeedThread velocidadeThread;
    private EstimatedSpeedThread estimatedSpeedThread;
    private ConsumptionThread consumoThread;
    private DecrementThread decrementThread;

    private EndRouteThread finalRotaThread;
    private JsonUtils jsonUtils;
    private SendJson sendJson;
    private Data dados;
    private TextView tempoDeslocamento;
    private TextView localizacaoAtual;
    private TextView Destino;
    private TextView tempoRestante;
    private TextView distanciaPercorrer;
    private TextView velocidadeAtual;
    private TextView velocidadeEstimada;
    private TextView ConsumoSaida;
    private TextView nomeCondutor;
    private TextView numeroIdentificacao;
    private TextView statusSaida;
    private double latitudeInicial;
    private double longitudeInicial;
    private double latitudeFinal;
    private double longitudeFinal;


    /**
     * Método onCreate inicializa o aplicativo.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializacaoCoordenadas();
        inicializacaoTextos();
        inicializacaoClasses();
        destinoCoordenadas();
        locationThread.start();
        setInformacoes();
        distanciaTotal();
        //enviar a mensagem criptografada.
        sendJson.enviarJson(jsonUtils.createJsonData(dados));

    }
    public void inicializacaoCoordenadas(){
        latitudeInicial = -21.2339066666;
        longitudeInicial = -44.992895;
        latitudeFinal = -21.22872166666;
        longitudeFinal = -44.9928616666;
    }
    public void inicializacaoTextos(){
        /**
         * conexão da variavel textView com o id que foi colocado na criação do layout.
         */
        localizacaoAtual = findViewById(R.id.localizacaoAtual);
        tempoDeslocamento = findViewById(R.id.tempoDeslocamento);
        distanciaPercorrer = findViewById(R.id.distanciaPercorrer);
        velocidadeAtual = findViewById(R.id.velocidadeAtual);
        velocidadeEstimada = findViewById(R.id.velocidadeEstimada);
        ConsumoSaida = findViewById(R.id.ConsumoSaida);
        Destino = findViewById(R.id.Destino);
        /*tempoRestante = findViewById(R.id.tempoRestante);*/
        nomeCondutor = findViewById(R.id.nomeCondutor);
        numeroIdentificacao = findViewById(R.id.numeroIdentificacao);
        statusSaida = findViewById(R.id.statusSaida);
    }

    public void inicializacaoClasses() {
        /**
         * Inicialização de todas as classes.
         */
        dados = new Data(latitudeFinal, longitudeFinal);
        locationThread = new LocationThread(this,localizacaoAtual, dados);
        incrementThread = new IncrementThread(tempoDeslocamento,dados);
        distance = new DistanceThread(distanciaPercorrer,statusSaida, dados,latitudeInicial, longitudeInicial,latitudeFinal,longitudeFinal);
        velocidadeThread = new SpeedThread(velocidadeAtual,dados);
        estimatedSpeedThread = new EstimatedSpeedThread(velocidadeEstimada,dados);
        consumoThread = new ConsumptionThread(ConsumoSaida,dados);
        finalRotaThread = new EndRouteThread(dados,tempoRestante,distanciaPercorrer,velocidadeAtual,
                velocidadeEstimada,tempoDeslocamento,ConsumoSaida,distance,velocidadeThread,estimatedSpeedThread,
                incrementThread,consumoThread);
        /*decrementThread = new DecrementThread(130, tempoRestante);*/
        jsonUtils = new JsonUtils(dados);
        sendJson = new SendJson(dados);

    }

    //Método para voltar para a tela principal.
    public void voltarPrincipal(View v){
        Intent in = new Intent(MainActivity.this, principal_activity.class);
        startActivity(in);
    }

    //Método para iniciar a navegação a partir do clique no botão "iniciar".
    public void iniciarNav(View v) {

        sendJson.lerJson();
        incrementThread.start();
        distance.start();
        velocidadeThread.start();
        estimatedSpeedThread.start();
        consumoThread.start();
        finalRotaThread.start();
        dados.setHora(obterDataHoraAtual());

    }

    public void reset(View v){
        incrementThread.stopThread();
        distance.stopThread();
        velocidadeThread.stopThread();
        estimatedSpeedThread.stopThread();
        consumoThread.stopThread();
    }
    /**
     * método que é executado apos o click no botão "Sair do aplicativo".
     * Com isso, o aplicativo será finalizado(fechado).
     * @param v
     */
    public void sairApp(View v){
        finish();
        System.exit(0);
    }

    /**
     * Método para a impressão das coordernadas de destino na tela.
     */
    public void destinoCoordenadas(){
        String localizacao =latitudeFinal + "\n"+ longitudeFinal;
        Destino.setText(localizacao);
    }
    //Método para a imprimir na tela as informações básicas.
    public void setInformacoes(){
        String condutor = "Pedro Paulo";
        nomeCondutor.setText(condutor);
        String identificacao = "138.445.469";
        numeroIdentificacao.setText(identificacao);
    }
    //Método para o calculo da distância total.
    public void distanciaTotal(){
        float[] DistanciaTotal = new float[1];
        Location.distanceBetween(latitudeInicial,longitudeInicial,latitudeFinal,longitudeFinal,DistanciaTotal);
        dados.setDistanciaTotal(DistanciaTotal[0]);

    }

    /**
     *Método que envia a permissão para a busca da localização do seu celular e tambem usar o gps
     * do dispositivo.
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LocationThread.REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão de localização concedida, inicia as atualizações de localização
                locationThread.startLocationUpdates();
            } else {
                // Permissão de localização negada, lida com essa situação
                Log.e(TAG, "Permissão de localização negada.");
            }
        }
    }
    //Método para obter a data e hora atual.
    public static String obterDataHoraAtual() {
        // Obter a instância do calendário com a data e hora atuais
        Calendar calendar = Calendar.getInstance();

        // Definir o fuso horário para Brasília (BRT)
        TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
        calendar.setTimeZone(timeZone);

        // Obter a data e hora atuais como objeto Date
        Date dataHoraAtual = calendar.getTime();

        // Formatar a data e hora como uma String
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatador.setTimeZone(timeZone);
        String dataHoraFormatada = formatador.format(dataHoraAtual);

        return dataHoraFormatada;
    }

    /**
     * Método que pausa todas as threads do sistema.
     */
    @Override
    protected void onDestroy() {

        super.onDestroy();
        incrementThread.stopThread();
        distance.stopThread();
        velocidadeThread.stopThread();
        estimatedSpeedThread.stopThread();
        consumoThread.stopThread();
        finalRotaThread.stopThread();
        if (locationThread != null) {
            locationThread.stopThread();
        }
        decrementThread.stopThread();

    }
}
