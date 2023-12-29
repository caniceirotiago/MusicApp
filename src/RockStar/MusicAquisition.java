package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 */
public class MusicAquisition implements Serializable {
    private ArrayList<Music> aquiredMusics;
    private LocalDateTime dateTime;
    private double totalPrice;

    /**
     *
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

    /**
     *
     * @return
     */
    public double getTotalPrice() {
        return totalPrice;
    }
}
