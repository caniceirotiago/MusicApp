/**
 * @Authors Tiago Caniceiro & Pedro Monteiro
 * @Version 1.0
 */
package src.RockStar;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Classe que gere a aquisição de música por parte do cliente.
 * Esta classe permite ao utilizador obter uma música por meio de compra, a música integra um array de músicas compradas
 * , é-lhe associada uma data de compra e o valor dessa compra entra para a receita total do criador de música.
 */
public class MusicAcquisition implements Serializable {
    private ArrayList<Music> acquiredMusics;
    private LocalDate date;
    private double totalPrice;

    /**
     * Construtor da aquisição de música
     * @param acquiredMusics Lista do tipo música.
     */
    public MusicAcquisition(ArrayList<Music> acquiredMusics) {
        this.acquiredMusics = acquiredMusics;
        this.date = LocalDate.now();
        for (Music mc : acquiredMusics){
            this.totalPrice += mc.getPrice();
            mc.getMusicCreator().addRevenueFromMusicSale(mc.getPrice());
        }
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return date.toString() + " - Price: " + totalPrice + "€";
    }
}
