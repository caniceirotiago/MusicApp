package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *classe para aquisição de musica por parte do cliente
 */
public class MusicAquisition implements Serializable {
    private ArrayList<Music> aquiredMusics;
    private LocalDateTime dateTime;
    private double totalPrice;

    /**
     *Construtor da classe musica adquirida
     * se a musica tiver um preco, esse valor é associado à receita do criador da musica respetiva
     * @param aquiredMusics
     */
    public MusicAquisition(ArrayList<Music> aquiredMusics) {
        this.aquiredMusics = aquiredMusics;
        this.dateTime = LocalDateTime.now();
        for (Music mc : aquiredMusics){
            this.totalPrice += mc.getPrice();
            mc.getMusicCreator().addRevenueFromMusicSale(mc.getPrice());
        }
    }
    public double getTotalPrice() {
        return totalPrice;
    }
}
