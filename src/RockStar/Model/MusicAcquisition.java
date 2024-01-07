/**
 * @Authors Tiago Caniceiro & Pedro Monteiro
 * @Version 1.0
 */
package src.RockStar.Model;

import src.RockStar.Model.Music;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe que gere a aquisição de musica por parte do cliente.
 * Esta classe permite ao utilizador obter uma musica por meio de compra, a musica integra um array de musicas compradas
 * , é-lhe associada uma data de compra e o valor dessa compra entra para a receita total do criador de musica.
 */
public class MusicAcquisition implements Serializable {
    private ArrayList<Music> acquiredMusics;
    private LocalDate date;
    private double totalPrice;

    /**
     * Construtor da aquisição de musica
     * @param acquiredMusics Lista do tipo musica.
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
