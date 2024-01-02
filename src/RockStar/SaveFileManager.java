package src.RockStar;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class SaveFileManager {
    private static final String FILE_NAME = "mainSaveFile";
    private static RockstarIncManager gc;
    public static void run() throws IOException, ClassNotFoundException {
        loadFile();
        autoSave();
        saveOnShutdown();
    }
    public static void updateDataFile() throws IOException, ClassNotFoundException{
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(gc);
        oos.close();
        System.out.println("File Updated");
    }
    public static void loadFile() throws IOException, ClassNotFoundException {
        try{
            FileInputStream fis = new FileInputStream(FILE_NAME);
            System.out.println("Arquivo aberto");
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println("Ficheiro lido");
            gc = (RockstarIncManager) ois.readObject();
            System.out.println("FIcheiro carreagdo");
            ois.close();
            System.out.println("loaded file");
        }catch (FileNotFoundException e){
            gc= new RockstarIncManager();
            updateDataFile();
            System.out.println("Created file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Load error");
        }
    }
    private static void autoSave(){
        Timer timer = new Timer(true); // true cria uma thread "Daemon "que encerra quando o programa desliga
        int delay = 1000 * 60 * 2; // Tempo inicial de atraso em milissegundos primeira gravação automática
        int interval = 1000 * 60 * 2; // Intervalo entre as execuções em milissegundos (exemplo: 2 minutos)
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    updateDataFile();
                    System.out.println("Auto Update executed");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Auto Update error");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }, delay, interval);
    }
    private static void saveOnShutdown(){
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            try{
                updateDataFile();
                System.out.println("Save on suthdown");
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
                System.out.println("Save on suthdown error");
            }
        }));
    }
    public static RockstarIncManager getGc() {
        return gc;
    }
}