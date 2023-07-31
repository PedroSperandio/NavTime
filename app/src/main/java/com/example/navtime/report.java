package com.example.navtime;

import java.util.ArrayDeque;
import java.util.ArrayList;
//Classe com todas as informações do relatório.
public class report {
    private String identificacao;
    private ArrayList<String> motoristas;
    private ArrayList<String> cargas;

    public report(){
        identificacao = "138.445.469";
        motoristas = new ArrayList<>();
        cargas = new ArrayList<>();
        adicionarMotoristas();
        adicionarCargas();


    }

    //inclusão dos dois motoristas no ArrayList.
    private void adicionarMotoristas(){
        motoristas.add("Pedro Paulo");
        motoristas.add("Fred Nicacio");
    }
    //inclusão das carcas no ArrayList.
    private void adicionarCargas(){
        cargas.add("10 Un - Comoda roma");
        cargas.add("25 Un - guarda-roupas Barcelona");
        cargas.add("20 Un - Mesa de Jantar Fortaleza");
        cargas.add("35 Un - Roupeiro Amsterda");

    }
    public String getIdentificacao(){
        return identificacao;
    }
    public String getMotoristas(int indice){
        if(indice == 0){
            return motoristas.get(0);
        }
        return motoristas.get(1);
    }
    public String getCargas(int indice){
        if(indice == 0){
            return cargas.get(0);
        }else if(indice == 1){
            return cargas.get(1);
        }else if(indice == 2){
            return cargas.get(2);
        }else{
            return cargas.get(3);
        }
    }



}
