/**
 * Inserir aqui autores e versão
 */
package src.RockStar;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
       SaveFileManager.run();
       RockstarIncManager gc = SaveFileManager.getGc();
        gc.run();
    }
}
