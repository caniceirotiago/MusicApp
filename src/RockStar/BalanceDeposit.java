package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *classe que gere os depósitos na conta do cliente
 */
public class BalanceDeposit implements Serializable {
    private double balanceToAdd;
    private LocalDateTime dateTime;

    /**
     *adicionar dinheiro
     * @param balanceToAdd
     */
    public BalanceDeposit(double balanceToAdd) {
        this.balanceToAdd = balanceToAdd;
        this.dateTime = LocalDateTime.now();
    }
}
