package src;

import java.io.*;
import java.util.Scanner;

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
            oos.writeObject(new RockstarIncManager());
            oos.close();
            System.out.println("Arquivo criado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {

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

        //Corre o programa com o ficheiro de dados gravados
        gc.run();

        //grava dados do programa ao fechar
        updateDataFile();
    }
}
