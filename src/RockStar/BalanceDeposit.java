package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 */
public class BalanceDeposit implements Serializable {
    private double balanceToAdd;
    private LocalDateTime dateTime;

    /**
     *
     * @param balanceToAdd
     */
    public BalanceDeposit(double balanceToAdd) {
        this.balanceToAdd = balanceToAdd;
        this.dateTime = LocalDateTime.now();
    }
}
