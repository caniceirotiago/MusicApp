//criei esta classe porque estava a tentar fazer uma classe Historico de precos, cujos clientes podem ver

package src.RockStar;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
public class PriceHistory implements Serializable {
    private LocalDateTime priceChangeDate;
    private double newPrice;

    public PriceHistory(double newPrice, LocalDateTime priceChangeDate) {
        this.priceChangeDate = LocalDateTime.now();
        this.newPrice = newPrice;
    }

    public LocalDateTime getPriceChangeDate() {
        return priceChangeDate;
    }

    public double getNewPrice() {
        return newPrice;
    }
}

