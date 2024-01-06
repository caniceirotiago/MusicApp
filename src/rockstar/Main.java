/**
 * @Authors Tiago Caniceiro & Pedro Monteiro
 * @Version 1.0
 */
package src.rockstar;

import src.rockstar.services.RockstarIncManager;
import src.rockstar.services.SaveFileManager;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
       SaveFileManager.run();
       RockstarIncManager gc = SaveFileManager.getRm();
        gc.run();

    }
}
