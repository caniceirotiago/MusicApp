package src.RockStar;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe com construtor e métodos para histórico de preços de músicas
 */
public class PriceHistory implements Serializable {
    private LocalDateTime priceChangeDate;
    private double newPrice;

    /**
     * Construtor da classe
     * @param newPrice
     * @param priceChangeDate
     */
    public PriceHistory(double newPrice, LocalDateTime priceChangeDate) {
        this.priceChangeDate = LocalDateTime.now();
        this.newPrice = newPrice;
    }

    /**
     * Método que atualiza a data aquando uma alteração de preços
     * @return
     */
    public LocalDateTime getPriceChangeDate() {
        return priceChangeDate;
    }
    public double getNewPrice() {
        return newPrice;
    }
}

