package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MusicEvaluation implements Serializable {
    private Client client;
    private LocalDateTime evaluationDateTime;
    private int evaluation;
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

    public void setEvaluationDateTime(LocalDateTime evaluationDateTime) {
        this.evaluationDateTime = evaluationDateTime;
    }
}
