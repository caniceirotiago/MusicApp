package src.RockStar;

import src.GUIClassesSwing.GUIManager;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class RockstarIncManager  implements Serializable {
    public static enum GENRE{ROCK,POP,CLASSIC,JAZZ,BLUES,HIP_HOP,ELETRONIC,FOLK,REGGAE,RELIGIOUS,TRADITIONAL} //Perceber qual o melhor sitio para colocar isto ; ver que está estatico neste momento
    private ArrayList<User> userList;
    private ArrayList<Music> musicList;
    private User currentUser;
    private GUIManager guiManager;

    public RockstarIncManager(){
        this.musicList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }
    //Novo método para iniciar a componente gráfica (É preciso estudar melhor este método)
    public void startGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                guiManager = new GUIManager(RockstarIncManager.this);
                guiManager.run();
            }
        });
    }
    //Métodos
    public void run(){
        //Criei um client só para experimentar Login.. depois é para apagar
        userList.add(new Client("as","as","as","as",0));
        userList.add(new MusicCreator("qw","qw","qw","qw","qw"));
        //Inicia o método gráfico
        startGUI();
    };
    public void loginAttempt(String username, String password, Boolean isMCreator, String pin){

        boolean sucessfulLogin = false;
        // O loop passa por todos os users compara se o user é cliente e o pedido de login é de cliente
        //compara se o user é musiccreator e o pedido do login também o é e os restantes e se os restantes parâmetros são verdadeiros
        for(User us : userList){
            if(us instanceof Client && !isMCreator && us.getUsername().equals(username) && us.getPassword().equals(password)){
            sucessfulLogin = true;
            currentUser = us;
            }
            else if(us instanceof MusicCreator && isMCreator && us.getUsername().equals(username) && us.getPassword().equals(password)){
                if(((MusicCreator)us).getPin().equals(pin)){
                    sucessfulLogin = true;
                    currentUser = us;
                }
            }
        }
        if(sucessfulLogin)  {
           guiManager.sucessfullLogin(currentUser.getUsername(), isMCreator);
            System.out.println("Successful Login");
        }
        else {
            System.out.println("Wrong Login");
            guiManager.unsuccessfulLogin();
        }

    };
    //condicao que admite a entrada de alguem na aplicação se houver registo na user arraylist;
    public void newUserAttempt(String name, String username, String password, String email, boolean isCreator, String pin){
        boolean emailAlreadyExists = false;
        boolean usernameAlreadyExists = false;
        //This loop allows for an already created MusiCreator regist could make a new account as a Client and vice versa.
        //It sends a code message for GUI to apply an error box "1" for invalid email and "2" for invalid username
        for(User us : userList){
            if(us.getEmail().equals(email)){
                if((isCreator && us instanceof MusicCreator) || (!isCreator && us instanceof Client)){
                    System.out.println("email already exists");
                    emailAlreadyExists = true;
                    guiManager.unsuccessfulRegistration(1);
                }
            }
            if(us.getUsername().equals(username)){
                if((isCreator && us instanceof MusicCreator) || (!isCreator && us instanceof Client)){
                    System.out.println("username already exists");
                    usernameAlreadyExists = true;
                    guiManager.unsuccessfulRegistration(2);
                }
            }
        }
        if(!emailAlreadyExists && !usernameAlreadyExists){
            if(!isCreator) userList.add(new Client(name, username,password,email,0));
            else if (isCreator) userList.add(new MusicCreator(name,username,password,email,pin));
            System.out.println("New client created");
            guiManager.successfulRegistration();
        }
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
    public void newCreationOfMusic(String name,GENRE genre, double price){
        Music music = new Music(name, genre,(MusicCreator) currentUser, price);
        musicList.add(music);
        ((MusicCreator) currentUser).addCreatedMusic(music);
    }
}
