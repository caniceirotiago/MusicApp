package src.RockStar;

import java.io.*;

public class Main {
    // Para a lógica de gravação funcionar houve necessidade que seja estático, confirmar com prof
    public static RockstarIncManager gc;
    //Reescreve o ficheiro de objetos com a nova informação (gravar) / fecha o leitor e o escritor
    //A ideia seria usar este método em vários momentos do programa inclusive, gravaçãoo automática e onClick
    public static void updateDataFile() throws IOException, ClassNotFoundException{
        FileOutputStream fos = new FileOutputStream("mainSaveFile");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(gc);
        oos.close();
    }
    // Criar um novo arquivo caso nao haja
    private static void createArchive() {
        try {

            FileOutputStream fos = new FileOutputStream("mainSaveFile");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            gc = new RockstarIncManager();
            oos.writeObject(gc);
            oos.close();
            System.out.println("Save File created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        /* para depois reativar
        //Abre o ficheiro de objeto (cria um se não existir) e lê o ficheiro de objetos
        try{
            FileInputStream fis = new FileInputStream("mainSaveFile");
            ObjectInputStream ois = new ObjectInputStream(fis);
            gc = (RockstarIncManager) ois.readObject();
            ois.close();
        }catch (FileNotFoundException e){
            createArchive();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Inicialização de timer para gravação automática
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
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }, delay, interval);
        */
        //para depois apagar abaixo


        //Corre o programa com o ficheiro de dados gravados
        RockstarIncManager gc = new RockstarIncManager();
        gc.run();

        //grava dados do programa ao fechar
        updateDataFile();
    }
}
