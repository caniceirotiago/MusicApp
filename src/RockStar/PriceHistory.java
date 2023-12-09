//criei esta classe porque estava a tentar fazer uma classe Historico de precos, cujos clientes podem ver

package src.RockStar;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
public class PriceHistory {
    private Music music;
    private LocalDateTime priceChangeDate;
    private double newPrice;

    public PriceHistory(Music music,double newPrice, LocalDateTime priceChangeDate) {
        this.music =  music;
        this.priceChangeDate = LocalDateTime.now();
    }
}

