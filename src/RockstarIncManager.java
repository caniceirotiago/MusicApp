package src;

import java.io.Serializable;
import java.util.ArrayList;

public class RockstarIncManager  implements Serializable {
    //so para testar mudanca
    private ArrayList<User> userList;
    private ArrayList<Music> musicList;
    private User currentUser;
    public static enum genre{ROCK,POP,CLASSIC,JAZZ,BLUES,HIP_HOP,ELETRONIC,FOLK,REGGAE,RELIGIOUS,TRADITIONAL} //Perceber qual o melhor sitio para colocar isto
    public RockstarIncManager(){
        this.musicList = new ArrayList<>();
    };//construtor vazio para já

    //Métodos
    public void run(){};
    public void login(){};
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
}
