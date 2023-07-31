package com.example.navtime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
/*
* Activity destinada a printar os valores do relatório na interface de visualização.
*/
public class activity_Relatorio extends AppCompatActivity {
    private Data data;
    private report relatorio;
    private TextView Identificacaooutput;
    private TextView Motorista1output;
    private TextView Motorista2output;
    private TextView DataHoraInicioOutput;
    private TextView ListaCargasOutput;
    private TextView DataHoraFimOutput;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio_activity);
        relatorio = new report();
        data = new Data();

        Identificacaooutput = findViewById(R.id.Identificacaooutput);
        Motorista1output = findViewById(R.id.Motorista1output);
        Motorista2output = findViewById(R.id.Motorista2output);
        DataHoraInicioOutput = findViewById(R.id.DataHoraInicioOutput);
        ListaCargasOutput = findViewById(R.id.ListaCargasOutput);
        DataHoraFimOutput = findViewById(R.id.DataHoraFimOutput);
        setRelatorio();


    }

    //Método para voltar para tela principal.
    public void voltarTelaPrincipal2(View v){
        Intent in = new Intent(activity_Relatorio.this, principal_activity.class);
        startActivity(in);
    }
    //Método que atribui os valores e informações na tela Relatório.
    public void setRelatorio(){
        Identificacaooutput.setText(relatorio.getIdentificacao());
        Motorista1output.setText(relatorio.getMotoristas(0));
        Motorista2output.setText(relatorio.getMotoristas(1));
        DataHoraInicioOutput.setText(data.getHora());
        DataHoraFimOutput.setText(horaFinalformatada());
        ListaCargasOutput.setText(relatorio.getCargas(0)+"\n"+relatorio.getCargas(1)+"\n"+
                                    relatorio.getCargas(2)+"\n"+relatorio.getCargas(3));
    }
    //Método que soma o tempo total do percurso e formata em formato de brasilia a data e a hora.
    public String horaFinalformatada(){

        ZoneId fusoHorarioBrasilia = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime dataHoraAtualBrasilia = ZonedDateTime.now(fusoHorarioBrasilia);
        ZonedDateTime dataHoraSomadaBrasilia = dataHoraAtualBrasilia.plusSeconds((long) data.getTempoTotalPercurso());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatadaSomada = dataHoraSomadaBrasilia.format(formatter);

        return dataHoraFormatadaSomada;
    }
}
