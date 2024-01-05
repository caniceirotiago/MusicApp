/**
 * @Authors Tiago Caniceiro & Pedro Monteiro
 * @Version 1.0
 */
package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Classe que gere e constroi os objetos do tipo musica
 */
public class Music implements Serializable {
    private String name;
    private Genre.GENRE genre;
    private MusicCreator musicCreator;
    private ArrayList<MusicEvaluation> evaluationList;
    private double price;
    private ArrayList<PriceHistory> priceHistory;
    private boolean isActive;
    private double classification;
    private Album associatedAlbum;

    /**
     * Construtor para o objeto do tipo musica.
     * @param name Nome da musica.
     * @param genre género da musica.
     * @param musicCreator O criador associado à musica.
     * @param price o preço correspondente da musica.
     */
    public Music(String name, Genre.GENRE genre, MusicCreator musicCreator, double price) {
        this.name = name;
        this.genre = genre;
        this.musicCreator = musicCreator;
        this.evaluationList = new ArrayList<>();
        this.price = price;
        this.isActive = true;
        this.priceHistory = new ArrayList<>();
        this.priceHistory.add(new PriceHistory(price,LocalDateTime.now()));
    }
    public ArrayList<PriceHistory> getPriceHistory() {
        return priceHistory;
    }
    public void setAssociatedAlbum(Album associatedAlbum) {
        this.associatedAlbum = associatedAlbum;
    }
    public double getClassification() {
        return classification;
    }
    public Album getAssociatedAlbum() {
        return associatedAlbum;
    }
    public MusicCreator getMusicCreator() {
        return musicCreator;
    }
    public String getArtistNameFromMusic(){
        return musicCreator.getName();
    }
    public Genre.GENRE getGenre() {
        return genre;
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public double getPrice() {return price;}
    public void setPrice(double price) {
        this.price = price;
        priceHistory.add(new PriceHistory(price, LocalDateTime.now()));
    }

    /**
     * Método que define se determinada musica está activa no sistema.
     * @return true ou false consoante a musica ter sido definida como tal.
     */
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public void setGenre(Genre.GENRE genre) {
        this.genre = genre;
    }

    /**
     * Método para calcular a classificação média de uma determinada musica consoante as classificações dadas pelos
     * utilizadores.
     * Método chamado sempre que é feita uma classificação ou a musica é editada.
     */
    public void calculateClassification(){
        double classificationsSum = 0;
        if(!evaluationList.isEmpty()) {
            for(MusicEvaluation me : evaluationList){
                classificationsSum += me.getEvaluation();
            }
            this.classification =  (classificationsSum / (double) evaluationList.size());
        }
    }
    /**
     * Método que adiciona uma classificação a um ficheiro de musica por parte de um cliente.
     * Se o cliente mesmo cliente decidir re-avaliar a musica, altera a classificação já dada e adiciona uma data de
     * alteração à avaliação.
     * Se o cliente for novo/ainda não tiver feito nenhuma avaliação, adiciona essa avaliação à musica.
     * @param client O cliente que está a avaliar a musica.
     * @param evaluation A avaliação dada pelo utilizador.
     */
    public void addEvaluation(Client client, int evaluation){
        boolean evaluationAlreadyExists = false;
        for(MusicEvaluation me : evaluationList){
            if(me.getClient().equals(client)) {
                evaluationAlreadyExists = true;
                me.setEvaluation(evaluation);
                me.setEvaluationDateTime(LocalDateTime.now());
            }
        }
        if(!evaluationAlreadyExists){
            evaluationList.add(new MusicEvaluation(client,evaluation));
        }
        calculateClassification();
    }
    public String toString() {
        return name + " \t" + price + "€";
    }
}

