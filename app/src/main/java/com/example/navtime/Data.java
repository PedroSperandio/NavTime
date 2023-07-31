package com.example.navtime;


import java.util.ArrayList;

/**
 * Classe responsavel pelo armazenamento dos dados do aplicativo, com ela conseguimos fazer
 * o envio e o recebimento dos valores. É uma classe facilitadora, permitindo as threads trocar
 * informações.
 */
public class Data {

    /**
     * Declaração das variaveis que armazenam as informações.
     */
    private double latitude;
    private double longitude;
    private double latitudeFinal;
    private double longitudeFinal;
    private float distancia ;
    private float distanciaVeiculo2;
    private double distanciaTotalVeiculo2;
    private float distanciaTotal;
    private int tempo;
    private int tempoVeiculo2;
    private int tempoTotalVeiculo2;
    private float velocidade;
    private float velocidadeVeiculo2;
    private double consumoTotal;
    private static String hora;
    private static double tempoTotalPercurso;

    /**
     * Construtor com dois parametros iniciais, latitude e longitude do destino,
     * visto que estas coordenadas são setadas pelo desenvolvedor.
     * @param latitudeFinal
     * @param longitudeFinal
     */
    public Data(double latitudeFinal, double longitudeFinal){
        this.latitudeFinal = latitudeFinal;
        this.longitudeFinal = longitudeFinal;
    }
    public Data(){
    }

    //Todos os métodos Sets e Gets das variáveis declaradas.
    public synchronized void setTempoTotalPercurso(double tempoTotalPercurso){
        this.tempoTotalPercurso=tempoTotalPercurso;
    }
    public synchronized void setLatitude(double latitude){
        this.latitude  = latitude;
    }

    public synchronized void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public synchronized void setDistancia(float distancia){
        this.distancia = distancia;
    }
    public synchronized void setTempo(int tempo){
        this.tempo = tempo;
    }
    public synchronized void setVelocidade(float velocidade){
        this.velocidade = velocidade;
    }
    public synchronized void setDistanciaTotal(float distanciaTotal){
        this.distanciaTotal = distanciaTotal;
    }
    public synchronized void setConsumoTotal(double consumoTotal){
        this.consumoTotal = consumoTotal;
    }
    public synchronized void setHora(String hora){
        this.hora = hora;
    }
    public synchronized void setDistanciaVeiculo2(Float distanciaVeiculo2){
        this.distanciaVeiculo2 = distanciaVeiculo2;
    }
    public synchronized void setTempoVeiculo2(int tempoVeiculo2){
        this.tempoVeiculo2 = tempoVeiculo2;
    }
    public synchronized void setVelocidadeVeiculo2(float velocidadeVeiculo2){
        this.velocidadeVeiculo2 = velocidadeVeiculo2;
    }
    public synchronized void setTempoTotalVeiculo2(int tempoTotalVeiculo2){
        this.tempoTotalVeiculo2 = tempoTotalVeiculo2;
    }
    public synchronized void setDistanciaTotalVeiculo2(double distanciaTotalVeiculo2){
        this.distanciaTotalVeiculo2 = distanciaTotalVeiculo2;
    }
    public synchronized double getTempoTotalPercurso(){
        return tempoTotalPercurso;
    }
    public synchronized double getLatitudeFinal(){
        return latitudeFinal;
    }
    public synchronized double getLongitudeFinal(){
        return longitudeFinal;
    }

    public synchronized double getLatitude(){
        return latitude;
    }

    public synchronized double getLongitude() {
        return longitude;
    }
    public synchronized float getDistancia(){
        return distancia;
    }
    public synchronized int getTempo(){
        return tempo;
    }

    public synchronized float getVelocidade() {
        return velocidade;
    }
    public synchronized float getDistanciaTotal() {
        return distanciaTotal;
    }

    public synchronized double getConsumoTotal(){
        return consumoTotal;
    }

    public synchronized double tempoTotal(){
        return distanciaTotal/4.16;
    }
    public synchronized int tempoTotalInteiro(){
        return (int) (distanciaTotal/4.16);
    }
    public synchronized String getHora(){
        return hora;
    }
    public synchronized int getTempoTotalVeiculo2(){
        return tempoTotalVeiculo2;
    }
    public synchronized double getDistanciaTotalVeiculo2(){
        return distanciaTotalVeiculo2;
    }
    public synchronized float getDistanciaVeiculo2(){
        return distanciaVeiculo2;
    }
    public synchronized float getVelocidadeVeiculo2(){
        return velocidadeVeiculo2;
    }
    public synchronized int getTempoVeiculo2(){
        return tempoVeiculo2;
    }
}

