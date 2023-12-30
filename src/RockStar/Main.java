package src.RockStar;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try{
            SaveFileManager.run();
            RockstarIncManager gc = SaveFileManager.getGc();
            gc.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
