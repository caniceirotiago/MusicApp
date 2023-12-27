package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Music implements Serializable {
    private String name;
    private RockstarIncManager.GENRE genre; //Confirmar localização do enum, para já acho que fica na classe manager
    private MusicCreator musicCreator;
    private ArrayList<MusicEvaluation> evaluationList;
    private double price;
    private ArrayList<PriceHistory> priceHistory;
    private boolean isActive;
    private double classification;
    private Album associatedAlbum;

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

    public RockstarIncManager.GENRE getGenre() {
        return genre;
    }

    public Music(String name, RockstarIncManager.GENRE genre, MusicCreator musicCreator, double price) {
        this.name = name;
        this.genre = genre;
        this.musicCreator = musicCreator;        //Para já apenas um criador por musica
        this.evaluationList = new ArrayList<>();
        this.price = price;               //O preço seria atribuido durante a criação ?!
        this.isActive = true;//musica assim que criada está ativa
        this.priceHistory = new ArrayList<>();
        this.priceHistory.add(new PriceHistory(price,LocalDateTime.now())); //Cria o primeiro historico de preço

    }

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
    //Se o cliente já existir atribui um valor diferente à classificação já existente evitando duplicados
    //Se o cliente não tiver atribuido uma classificação faz uma nova

    //getters e setters para a edição da musica por parte do music creator

    public String getName() {return name;}


    public void setName(String name) {this.name = name;}

    public double getPrice() {return price;}

    public void setPrice(double price) {
        this.price = price;
        priceHistory.add(new PriceHistory(price, LocalDateTime.now()));
    }

    @Override
    public String toString() {
        return name + " \t" + price + "€";
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setGenre(RockstarIncManager.GENRE genre) {
        this.genre = genre;
    }
}

