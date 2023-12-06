package src;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Music {
    private String name;
    private RockstarIncManager.genre genre; //Confirmar localização do enum, para já acho que fica na classe manager
    private MusicCreator musicCreator;
    private ArrayList<MusicEvaluation> evaluationList;
    private double price;
    private boolean isActive;
    private double classification;

    public Music(String name, RockstarIncManager.genre genre, MusicCreator musicCreator, double price) {
        this.name = name;
        this.genre = genre;
        this.musicCreator = musicCreator;        //Para já apenas um criador por musica
        this.evaluationList = new ArrayList<>();
        this.price = price;               //O preço seria atribuido durante a criação ?!
        this.isActive = true;         //musica assim que criada está ativa
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
}
