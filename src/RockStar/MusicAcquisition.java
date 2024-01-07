/**
 * @Authors Tiago Caniceiro & Pedro Monteiro
 * @Version 1.0
 */
package src.RockStar;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Classe que gere a aquisição de musica por parte do cliente.
 * Esta classe permite ao utilizador obter uma musica por meio de compra, a musica integra um array de musicas compradas
 * , é-lhe associada uma data de compra e o valor dessa compra entra para a receita total do criador de musica.
 */
public class MusicAcquisition implements Serializable {
    private ArrayList<Music> acquiredMusics;
    private LocalDateTime dateTime;
    private double totalPrice;

    /**
     * Construtor da aquisição de musica
     * @param acquiredMusics Lista do tipo musica.
     */
    public MusicAcquisition(ArrayList<Music> acquiredMusics) {
        this.acquiredMusics = acquiredMusics;
        this.dateTime = LocalDateTime.now();
        for (Music mc : acquiredMusics){
            this.totalPrice += mc.getPrice();
            mc.getMusicCreator().addRevenueFromMusicSale(mc.getPrice());
        }
    }
    public double getTotalPrice() {
        return totalPrice;
    }
}
