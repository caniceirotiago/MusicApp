package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Classe que permite a criação de objetos do tipo musica
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
     *Construtor para o objeto musica
     * @param name
     * @param genre
     * @param musicCreator
     * @param price
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

    /**
     *método para associar um album de um criador de musica a um objeto do tipo musica
     * @param associatedAlbum
     */
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
     *metodo para verificar se a musica está activa para os clientes, por parte do criador
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     *metodo para o criador definir se a musica está activa para compra por parte dos clientes
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }
    public void setGenre(Genre.GENRE genre) {
        this.genre = genre;
    }

    /**
     *
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
    //Calcula a classificação depois de uma nova adição à lista

    /**
     *metodo para avaliar uma musica
     *Se o cliente já existir atribui um valor diferente à classificação já existente evitando duplicados
     *Se o cliente não tiver atribuido uma classificação faz uma nova
     *
     * @param client
     * @param evaluation
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

