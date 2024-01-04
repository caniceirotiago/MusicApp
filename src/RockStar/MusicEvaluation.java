package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Classe que define contrutores e métodos para a avaliação de musica
 */
public class MusicEvaluation implements Serializable {
    private Client client;
    private LocalDateTime evaluationDateTime;
    private int evaluation;

    /**
     * Construtor da classe
     * @param client
     * @param evaluation
     */
    public MusicEvaluation(Client client, int evaluation) {
        this.client = client;
        this.evaluationDateTime = LocalDateTime.now();
        this.evaluation = evaluation;
    }
    public int getEvaluation() {
        return evaluation;
    }

    public Client getClient() {
        return client;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    /**
     * método que associa uma data à avaliação de uma música
     * @param evaluationDateTime
     */
    public void setEvaluationDateTime(LocalDateTime evaluationDateTime) {
        this.evaluationDateTime = evaluationDateTime;
    }
}
