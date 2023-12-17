package src.RockStar;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MusicAquisition implements Serializable {
    private ArrayList<Music> aquiredMusics;
    private LocalDateTime dateTime;

    public MusicAquisition(ArrayList<Music> aquiredMusics) {
        this.aquiredMusics = aquiredMusics;
        this.dateTime = LocalDateTime.now();
    }
}
