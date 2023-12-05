package src;

import java.time.LocalDateTime;

public class MusicEvaluation {
    private Client client;
    private LocalDateTime evaluationDateTime;
    private int evaluation;

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

    public MusicEvaluation(Client client, int evaluation) {
        this.client = client;
        this.evaluationDateTime = LocalDateTime.now();
        this.evaluation = evaluation;
    }
}
