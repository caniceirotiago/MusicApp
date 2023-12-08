package src.RockStar;

import java.time.LocalDateTime;

public class BalanceDeposit {
    private double balanceToAdd;
    private LocalDateTime dateTime;
    public BalanceDeposit(double balanceToAdd) {
        this.balanceToAdd = balanceToAdd;
        this.dateTime = LocalDateTime.now();
    }
}
