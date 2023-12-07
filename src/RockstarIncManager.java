package src;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class RockstarIncManager  implements Serializable {
    public static enum GENRE{ROCK,POP,CLASSIC,JAZZ,BLUES,HIP_HOP,ELETRONIC,FOLK,REGGAE,RELIGIOUS,TRADITIONAL} //Perceber qual o melhor sitio para colocar isto ; ver que está estatico neste momento
    private ArrayList<User> userList;
    private ArrayList<Music> musicList;
    private User currentUser;
    private LoginRegistrationFrame loginRegistrationFrame;

    public RockstarIncManager(){
        this.musicList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }
    //Novo método para iniciar a componente gráfica (É preciso estudar melhor este método)
    public void startGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loginRegistrationFrame = new LoginRegistrationFrame(RockstarIncManager.this);
                loginRegistrationFrame.setVisible(true);
            }
        });
    }
    //Métodos
    public void run(){
        //Criei um client só para experimentar Login.. depois é para apagar
        userList.add(new Client("as","as","as","as",0));
        //Inicia o método gráfico
        startGUI();
    };
    public void login(String username, String password, JButton loginButton){
        //Lógica essencial do login concluida
        //Falta atribuir o corrent user se o login for bem feito
        boolean sucessfulLogin = false;
        for(User us : userList){
            if(us.getUsername().equals(username) && us.getUsername().equals(username)) sucessfulLogin = true;
        }
        if(sucessfulLogin)  {
            SwingUtilities.invokeLater(() -> {
                loginRegistrationFrame.dispose();
            });
        }
        else {
            SwingUtilities.invokeLater(() -> {
                loginButton.setText("errado");
            });
        }
    };
    //condicao que admite a entrada de alguem na aplicação se houver registo na user arraylist;
    public void newUser(){
        //new User (nome? condicao?) == super.User;
        //this new user is added to users arraylist;
    };

    public ArrayList<Music> listByOrder(ArrayList<Music> musicList){
        //que ordem se coloca aqui? Faz se uma escolha dentro deste metodo, para ser alfabeticamente ou por data?
        //tambem seria interessante ordenar por rating
        return musicList;
    };

    public ArrayList<Music> searchByCriteria(ArrayList<Music> musicList, String genre) {
        //if musicList. genre == string
        return musicList;
    }
    public ArrayList<User> getCurrentUsers(){
        //vai buscar a lista toda de utilizadores
        return userList;
    }
    public void evaluateMusic(Music music, int evaluation){
        //Chama método para adicionar avaliação na classe Music
        music.addEvaluation((Client)currentUser, evaluation);
    }
    public void newRandomPlaylist(GENRE genre, int nOfMusics){
        ArrayList<Music> musicOfTheChosenGenre = new ArrayList<>();
        ArrayList<Music> randomChosenMusic = new ArrayList<>();
        for(Music m : musicList){
            if(m.getGenre().equals(genre)) musicOfTheChosenGenre.add(m);
        }
        if(musicOfTheChosenGenre.size() < nOfMusics) System.out.println("there are not enought musics");
        else{
            int[] listOfIndexes = randomIndexVector(nOfMusics, musicOfTheChosenGenre.size());
            for(int i = 0; i < listOfIndexes.length; i++){
                randomChosenMusic.add(musicOfTheChosenGenre.get(listOfIndexes[0]));
            }
            currentUser.newCollection(randomChosenMusic);
        }
    }
    public int[] randomIndexVector(int SizeOfNewVector, int sizeOfSample){
        //Escolhe de forma aleatoria um vector com indices num certo número de possibilidades.
        //Ver metodo nweRandomPLaylist
        int[] listOfIndexes = new int[3];
        ArrayList<Integer> addedIndexes = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int randomIndex;
            do {
                randomIndex = (int) (Math.floor(Math.random() * 45));
            } while (addedIndexes.contains(randomIndex));

            listOfIndexes[i] = randomIndex;
            addedIndexes.add(randomIndex);
        }
        return listOfIndexes;
    }
}
