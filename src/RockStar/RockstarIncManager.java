package src.RockStar;

import kotlin.jvm.internal.SpreadBuilder;
import src.GUIClassesSwing.GUIManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class RockstarIncManager  implements Serializable {
    public static enum GENRE{ROCK,POP,CLASSIC,JAZZ,BLUES,HIP_HOP,ELETRONIC,FOLK,REGGAE,RELIGIOUS,TRADITIONAL} //Perceber qual o melhor sitio para colocar isto ; ver que está estatico neste momento
    private ArrayList<User> userList;
    private ArrayList<Music> musicList;
    private transient User currentUser;
    private transient GUIManager guiManager;

    public RockstarIncManager(){
        this.musicList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }
    //Novo método para iniciar a componente gráfica (É preciso estudar melhor este método)
    public void startGUI() {
        guiManager = new GUIManager(RockstarIncManager.this);
        guiManager.run();
    }
    //Métodos
    public void run(){

        //Criei um client só para experimentar Login.. depois é para apagar
        Client tiago = new Client("as","as","as","as",180);
        userList.add(tiago);
        MusicCreator pedro = new MusicCreator("Pedro","qw","qw","qw","qw");
        userList.add(pedro);

        //Playlist experiencia
        tiago.newCollection("Rock Luso");
        tiago.newCollection("Mamonas Assassinas");

        //Musica Experiencia
        Music m1 = new Music("Olá Joana", GENRE.POP,pedro,0);
        musicList.add(m1);
        tiago.addMusicToCollection(m1,tiago.getAllCollections().get(0));
        tiago.newMusicToAllCollection(m1);
        pedro.addCreatedMusic(m1);

        Music m2 = new Music("Carolina", GENRE.POP,pedro,0);
        musicList.add(m2);
        tiago.newMusicToAllCollection(m2);
        tiago.addMusicToCollection(m2,tiago.getAllCollections().get(1));
        pedro.addCreatedMusic(m2);

        Music m3 = new Music("Mariazinha", GENRE.POP,pedro,0);
        musicList.add(m3);
        tiago.newMusicToAllCollection(m3);
        pedro.addCreatedMusic(m3);

        musicList.add(new Music("Silvana", GENRE.POP,pedro,0));
        musicList.add(new Music("Luna", GENRE.POP, pedro, 0));
        musicList.add(new Music("Estrella", GENRE.POP, pedro, 0));
        musicList.add(new Music("Marisol", GENRE.POP, pedro, 0));
        musicList.add(new Music("Aurora", GENRE.POP, pedro, 0));
        musicList.add(new Music("Cielo", GENRE.POP, pedro, 0));
        musicList.add(new Music("Lucero", GENRE.POP, pedro, 12));
        musicList.add(new Music("Paloma", GENRE.POP, pedro, 30));
        musicList.add(new Music("Solana", GENRE.POP, pedro, 7));
        musicList.add(new Music("Rosa", GENRE.POP, pedro, 25));
        musicList.add(new Music("Violeta", GENRE.POP, pedro, 18));
        musicList.add(new Music("Dahlia", GENRE.POP, pedro, 22));
        musicList.add(new Music("Lirio", GENRE.POP, pedro, 11));
        musicList.add(new Music("Azalea", GENRE.POP, pedro, 14));
        musicList.add(new Music("Camelia", GENRE.POP, pedro, 9));
        musicList.add(new Music("Magnolia", GENRE.POP, pedro, 17));
        musicList.add(new Music("Estrella", GENRE.POP, pedro, 1));
        musicList.add(new Music("Marisol", GENRE.POP, pedro, 1));
        musicList.add(new Music("Aurora", GENRE.POP, pedro, 1));
        musicList.add(new Music("Cielo", GENRE.POP, pedro, 1));
        musicList.add(new Music("Lucero", GENRE.POP, pedro, 12));
        musicList.add(new Music("Paloma", GENRE.POP, pedro, 30));
        musicList.add(new Music("Solana", GENRE.POP, pedro, 7));
        musicList.add(new Music("Rosa", GENRE.POP, pedro, 25));
        musicList.add(new Music("Violeta", GENRE.POP, pedro, 18));
        musicList.add(new Music("Dahlia", GENRE.POP, pedro, 22));
        musicList.add(new Music("Lirio", GENRE.POP, pedro, 11));
        musicList.add(new Music("Azalea", GENRE.POP, pedro, 14));
        musicList.add(new Music("Camelia", GENRE.POP, pedro, 9));
        musicList.add(new Music("Magnolia", GENRE.POP, pedro, 17));
        musicList.add(new Music("Estrella", GENRE.POP, pedro, 1));
        musicList.add(new Music("Marisol", GENRE.POP, pedro, 1));
        musicList.add(new Music("Aurora", GENRE.POP, pedro, 1));
        musicList.add(new Music("Cielo", GENRE.POP, pedro, 1));
        musicList.add(new Music("Lucero", GENRE.POP, pedro, 12));
        musicList.add(new Music("Paloma", GENRE.POP, pedro, 30));
        musicList.add(new Music("Solana", GENRE.POP, pedro, 7));
        musicList.add(new Music("Rosa", GENRE.POP, pedro, 25));
        musicList.add(new Music("Violeta", GENRE.POP, pedro, 18));
        musicList.add(new Music("Dahlia", GENRE.POP, pedro, 22));
        musicList.add(new Music("Lirio", GENRE.POP, pedro, 11));
        musicList.add(new Music("Azalea", GENRE.POP, pedro, 14));
        musicList.add(new Music("Camelia", GENRE.POP, pedro, 9));
        musicList.add(new Music("Magnolia", GENRE.POP, pedro, 17));
        musicList.add(new Music("Rosa", GENRE.POP, pedro, 25));
        musicList.add(new Music("Violeta", GENRE.POP, pedro, 18));
        musicList.add(new Music("Dahlia", GENRE.POP, pedro, 22));
        musicList.add(new Music("Lirio", GENRE.POP, pedro, 11));
        musicList.add(new Music("Azalea", GENRE.POP, pedro, 14));
        musicList.add(new Music("Camelia", GENRE.POP, pedro, 9));
        musicList.add(new Music("Magnolia", GENRE.POP, pedro, 17));
        musicList.add(new Music("Estrella", GENRE.POP, pedro, 1));
        musicList.add(new Music("Marisol", GENRE.POP, pedro, 1));
        musicList.add(new Music("Aurora", GENRE.POP, pedro, 1));
        musicList.add(new Music("Cielo", GENRE.POP, pedro, 1));
        musicList.add(new Music("Lucero", GENRE.POP, pedro, 12));
        musicList.add(new Music("Paloma", GENRE.POP, pedro, 30));
        musicList.add(new Music("Solana", GENRE.POP, pedro, 7));
        musicList.add(new Music("Rosa", GENRE.POP, pedro, 25));
        musicList.add(new Music("Violeta", GENRE.POP, pedro, 18));
        musicList.add(new Music("Dahlia", GENRE.POP, pedro, 22));
        musicList.add(new Music("Lirio", GENRE.POP, pedro, 11));
        musicList.add(new Music("Azalea", GENRE.POP, pedro, 14));
        musicList.add(new Music("Camelia", GENRE.POP, pedro, 9));
        musicList.add(new Music("Magnolia", GENRE.POP, pedro, 17));
        musicList.add(new Music("Rosa", GENRE.POP, pedro, 25));
        musicList.add(new Music("Violeta", GENRE.POP, pedro, 18));
        musicList.add(new Music("Dahlia", GENRE.POP, pedro, 22));
        musicList.add(new Music("Lirio", GENRE.POP, pedro, 11));
        musicList.add(new Music("Azalea", GENRE.POP, pedro, 14));
        musicList.add(new Music("Camelia", GENRE.POP, pedro, 9));
        musicList.add(new Music("Magnolia", GENRE.POP, pedro, 17));
        musicList.add(new Music("Estrella", GENRE.POP, pedro, 1));
        musicList.add(new Music("Marisol", GENRE.POP, pedro, 1));
        musicList.add(new Music("Aurora", GENRE.POP, pedro, 1));
        musicList.add(new Music("Cielo", GENRE.POP, pedro, 1));
        musicList.add(new Music("Lucero", GENRE.POP, pedro, 12));
        musicList.add(new Music("Paloma", GENRE.POP, pedro, 30));
        musicList.add(new Music("Solana", GENRE.POP, pedro, 7));
        musicList.add(new Music("Rosa", GENRE.POP, pedro, 25));
        musicList.add(new Music("Violeta", GENRE.POP, pedro, 18));
        musicList.add(new Music("Dahlia", GENRE.POP, pedro, 22));
        musicList.add(new Music("Lirio", GENRE.POP, pedro, 11));
        musicList.add(new Music("Azalea", GENRE.POP, pedro, 14));
        musicList.add(new Music("Camelia", GENRE.POP, pedro, 9));
        musicList.add(new Music("Magnolia", GENRE.POP, pedro, 17));
        pedro.editMusicPrice(m1,4);
        pedro.editMusicPrice(m1,2);
        pedro.editMusicPrice(m1,3);
        pedro.editMusicPrice(m1,5);
        pedro.editMusicPrice(m1,6);
        pedro.editMusicPrice(m1,7);
        pedro.editMusicPrice(m1,43);
        pedro.editMusicPrice(m1,2);
        pedro.editMusicPrice(m1,4);

        //Inicia o método gráfico
        startGUI();
        System.out.println("O numero de utilizadores sao " + statisticsUsers());
        System.out.println("O preco total das musicas é " + statisticsPrices());
        System.out.println("O numero de musicas no sistema é " + totalSongs());
        System.out.println("O numero de musicas do genero POP é" + totalSongGenre(GENRE.POP));
        System.out.println("O numero de musicas do genero JAZZ é" + totalSongGenre(GENRE.JAZZ));
        System.out.println("O numero de vendas é" + totalSalesValue());
        System.out.println("O numero de albuns do genero POP é " + totalAlbumsByGenre(GENRE.POP));
    }
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
           guiManager.sucessfullLogin(currentUser, isMCreator);
            System.out.println("Successful Login");
        }
        else {
            System.out.println("Wrong Login");
            guiManager.unsuccessfulLogin();
        }

    }
    //condicao que admite a entrada de alguem na aplicação se houver registo na user arraylist;
    //acho que este método devia chamar se newRegistration
    public void newUserAttempt(String name, String username, String password, String email, boolean isCreator, String pin){
        boolean validRegistration = termValidationOnUserAttempt(name,username, password, email,  isCreator, pin);
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
        //adicionar aqui booleana ou valor que confirme passagens entre barreiras
        if(!emailAlreadyExists && !usernameAlreadyExists && validRegistration){
            userList.add(new Client(name, username,password,email,0));
            userList.add(new MusicCreator(name, username, password, email, pin));
            System.out.println("New client created");
            guiManager.successfulRegistration();
        }
    }
    public boolean termValidationOnUserAttempt(String name, String username, String password, String email, boolean isCreator, String pin){
        //Registo, validacao dados
        //booleana final para se usar na criacao do user
        boolean validRegistration = false;

        //validação pin
        if(isCreator){
            boolean pinValido = false;
            if (pin.isBlank() || pin.length() >8){
                guiManager.unsuccessfulRegistration(5);
            } else {
                pinValido = true;
            }
        }
        //username
        //username tem no minimo 3 letras e maximo 15
        boolean validUserName = false;
        if (username.length()<3 || username.length()>10){
            guiManager.unsuccessfulRegistration(4);
        } else validUserName = true;


        //email
        boolean validEmail = false;
        int indexAt = email.indexOf("@");
        int indexDotCom = email.indexOf(".com");
        if (email.isBlank() ){
            guiManager.unsuccessfulRegistration(3);
        }
        else if (indexAt != -1 && indexDotCom !=-1 && indexAt<indexDotCom && indexAt+1<indexDotCom){
            validEmail = true;
        } else {
            System.out.println("email not valid");
            guiManager.unsuccessfulRegistration(3);
        }
        if (validEmail && validUserName){
            validRegistration = true;
        }
        return validRegistration;
    }

    public Search search(String searchTerm) {
        //no enunciado pede apenas para pesquisar musica.. aqui já estou a complicar um bocado e a pesquisar por musica album e artista
        //Este método cria objetos da classe Search, a classe search tem dois construtores:
        //      1 - Lista de Musicas, lista de colecções de musica e lista de artistas (tipo de pesquisa apenas do utilizador normal )
        //      2 - Lista de Musicas, lista de colecções do próprio criador (tipo de pesquisa apenas do criador de musica)
        //Na primeira condição do presente método é tratada a pesquisa de utilizador normal onde são escolhidas as músicas,
        //os albuns e as playlists publicas que corrreespondem ao termo da pesquisa.
        //Na segunda é tratada a pesquisa se o utilizador for um criador de música, apenas retornará as músicas e albuns do próprio.
        //
        ArrayList<Music> foundMusics= new ArrayList<>();
        ArrayList<Music> foundMusicsByArtist = new ArrayList<>();
        ArrayList<MusicCollection> foundMusicCollections = new ArrayList<>();
        ArrayList<MusicCreator> foundMusicCreators = new ArrayList<>();

        if(currentUser instanceof Client){
            //Pesquisa de Musica
            for(Music m : musicList){
                if(m.getName().toLowerCase().contains(searchTerm.toLowerCase())) foundMusics.add(m);
                if(m.getMusicCreator().getName().toLowerCase().contains(searchTerm.toLowerCase())) foundMusicsByArtist.add(m);
            }

            //Pesquisa de colleções de musica e artistas; apenas playlists publicas e todos os albuns.
            for(User us :  userList){
                if(us instanceof MusicCreator && us.name.toLowerCase().contains(searchTerm.toLowerCase())) foundMusicCreators.add((MusicCreator) us);
                for(MusicCollection mc : us.getAllCollections()){
                    if(mc.getName().toLowerCase().contains(searchTerm.toLowerCase()) && (mc instanceof Album || (mc instanceof Playlist && ((Playlist)mc).getPublicState()))){
                        foundMusicCollections.add(mc);
                    }
                }
            }
            return new Search(foundMusics,foundMusicsByArtist,foundMusicCollections,foundMusicCreators);
        } else {//se o current user for music creator apenas poderá pesquisar as proprias criações
            for(Music m : musicList){
                if(m.getMusicCreator().equals(currentUser) && m.getName().toLowerCase().contains(searchTerm.toLowerCase())) foundMusics.add(m);
            }
            //Pesquisa de albuns e artistas
            for(User us :  userList){
                if(us.equals(currentUser)){
                    for(MusicCollection mc : us.getAllCollections()){
                        if(mc.getName().toLowerCase().contains(searchTerm.toLowerCase())) foundMusicCollections.add(mc);
                    }
                };
            }
            return new Search(foundMusics,foundMusicCollections);
        }
    }
    public void evaluateMusic(Music music, int evaluation){
        //Chama método para adicionar avaliação na classe Music
        music.addEvaluation((Client)currentUser, evaluation);
    }
    public void newRandomPlaylist(GENRE genre, int nOfMusics){
        //este método cria uma playlist de forma aleatória por género musical para o utilizador normal
        //Faz uso de um método acessorio chamado random IndexVector que retorna um vector de inteiros correspondente ao index
        // de uma Arraylist de Musicas.
        //O presente método pede um número de musicas e um género e devolve um arraylist
        //Primeiro de tudo seleciona todas as músicas de determinado género e depois faz a seleção
        boolean successfullyCreated = false;
        ArrayList<Music> musicOfTheChosenGenre = new ArrayList<>();
        ArrayList<Music> randomChosenMusic = new ArrayList<>();

        for(Music m : musicList){
            if(m.getGenre().equals(genre)) musicOfTheChosenGenre.add(m);
        }

        int maxSize = musicOfTheChosenGenre.size();
        if(musicOfTheChosenGenre.size() < nOfMusics) {
            System.out.println("There are not enough musics");
            guiManager.notEnoughMusicForRandom(maxSize,false);
        }
        else{
            int[] listOfIndexes = randomIndexVector(nOfMusics, musicOfTheChosenGenre.size(),false);
            ArrayList<Music> notFreeMusicSelection = new ArrayList<>();

            double totalPrice = 0;

            for (int listOfIndex : listOfIndexes) {
                Music music = musicOfTheChosenGenre.get(listOfIndex);
                if (currentUser.getAllMusic().contains(music)) {
                    randomChosenMusic.add(music);
                } else if (!currentUser.getAllMusic().contains(music) && music.getPrice() == 0) {
                    currentUser.newMusicToAllCollection(music);
                    randomChosenMusic.add(music);
                } else if(!currentUser.getAllMusic().contains(music) && music.getPrice() > 0){
                    notFreeMusicSelection.add(music);

                    totalPrice += music.getPrice();
                }
            }

            double balance= ((Client)currentUser).getBalance();
            boolean canBuy  = totalPrice < balance;

            if(!notFreeMusicSelection.isEmpty()){
                //Este metodo retorna um inteiro que corresponde à seleção do utilizador
                //1 - adicionar ao carrinho
                //2 - comprar as musicas
                //3 - apenas selecionar musicas gratuitas
                int userOption = guiManager.randomPlaylistPaidSongsChoose(notFreeMusicSelection, totalPrice,canBuy);
                switch (userOption){
                    case 1:
                        for (Music m : notFreeMusicSelection){
                            ((Client)currentUser).addMusicToMusicToBuy(m); //Se optar por adicionar ao carrinho apenas as musicas gratuitas serão adicionadas
                        }
                        currentUser.newCollection(randomChosenMusic);
                        successfullyCreated = true;
                        break;
                    case 2:
                        if(canBuy){
                            ((Client)currentUser).validationOfAquisition(notFreeMusicSelection);
                            randomChosenMusic.addAll(notFreeMusicSelection); //Se optar por fazer a compra ele adiciona as musicas pagas àS gratuitas
                        }
                        else System.out.println("Not enough money");
                        currentUser.newCollection(randomChosenMusic);
                        successfullyCreated = true;
                        break;
                    case 3:
                        newRandomPlaylistOnlyFree(musicOfTheChosenGenre,nOfMusics,randomChosenMusic);
                        break;
                }
            }
            else {
                currentUser.newCollection(randomChosenMusic);
                successfullyCreated = true;
            }

        }
        if(successfullyCreated) guiManager.randomPLSuccssefullyCreated();
    }
    public void newRandomPlaylistOnlyFree(ArrayList<Music> musicOfTheChosenGenre, int nOfMusics, ArrayList<Music> randomChosenMusic){

        ArrayList<Music> onlyFreeMusicByGenre = new ArrayList<>();
        for(Music m : musicOfTheChosenGenre){
            if(m.getPrice() == 0) onlyFreeMusicByGenre.add(m);
        }
        int maxSyzeFreeMusic = onlyFreeMusicByGenre.size();
        if(maxSyzeFreeMusic >= nOfMusics){
            int[] listOfIndexesFreeMusic = randomIndexVector(nOfMusics, onlyFreeMusicByGenre.size(),true);
            randomChosenMusic.clear();
            for (int listOfIndexesfree : listOfIndexesFreeMusic) {
                Music music = onlyFreeMusicByGenre.get(listOfIndexesfree);
                if (currentUser.getAllMusic().contains(music)) {
                    randomChosenMusic.add(music);
                } else  {
                    currentUser.newMusicToAllCollection(music);
                    randomChosenMusic.add(music);
                }
            }
            currentUser.newCollection(randomChosenMusic);
            guiManager.randomPLSuccssefullyCreated();
        } else{
            guiManager.notEnoughMusicForRandom(maxSyzeFreeMusic,true);
        }
    }
    public int[] randomIndexVector(int SizeOfNewVector, int sizeOfSample, boolean onlyFree){
        //Escolhe de forma aleatoria um vector com indices num certo número de possibilidades. Pensar na utilização de um SEt Integer
        //Ver metodo nweRandomPLaylist
        if(!onlyFree){
            int[] listOfIndexes = new int[SizeOfNewVector];
            ArrayList<Integer> addedIndexes = new ArrayList<>();

            for (int i = 0; i < SizeOfNewVector; i++) {
                int randomIndex;
                do {
                    randomIndex = (int) (Math.floor(Math.random() * sizeOfSample));
                } while (addedIndexes.contains(randomIndex));

                listOfIndexes[i] = randomIndex;
                addedIndexes.add(randomIndex);
            }
            return listOfIndexes;
        }else{
            int[] listOfIndexes = new int[SizeOfNewVector];
            ArrayList<Integer> addedIndexes = new ArrayList<>();

            for (int i = 0; i < SizeOfNewVector; i++) {
                int randomIndex;
                do {
                    randomIndex = (int) (Math.floor(Math.random() * sizeOfSample));
                } while (addedIndexes.contains(randomIndex));

                listOfIndexes[i] = randomIndex;
                addedIndexes.add(randomIndex);
            }
            return listOfIndexes;
        }
    }
    public void newCreationOfMusic(String name, String priceString, GENRE genre){
        boolean validatedName = musicNameValidation(name);
        double price = musicPriceValidation(priceString);

        if(validatedName && price != -1){
            Music music = new Music(name, genre,(MusicCreator) currentUser, price);
            musicList.add(music);
            ((MusicCreator) currentUser).addCreatedMusic(music);
            guiManager.newMusicCreated();
        }
    }
    public void musicEditionAttempt(Music selectedMusic, String name, String priceString, GENRE genre, int state){
        boolean musicEdited = false;
        if(!name.isEmpty() && musicNameValidation(name)){
            selectedMusic.setName(name);
            musicEdited = true;
        }
        if(!priceString.isEmpty()){
            double price = musicPriceValidation(priceString);
            if(price != -1) {
                selectedMusic.setPrice(price);
                musicEdited = true;
            }
        }
        if(genre != selectedMusic.getGenre()){
            selectedMusic.setGenre(genre);
            selectedMusic.getAssociatedAlbum().calculateMainGenre();
            musicEdited = true;
        }
        boolean selectedState;
        if(state == 0) selectedState = true;
        else selectedState = false;

        if(selectedState != selectedMusic.isActive()){
            selectedMusic.setActive(selectedState);
            musicEdited = true;
        }
        if(musicEdited) guiManager.musicSuccessfullyEdited();
    }
    public boolean musicNameValidation(String name){
        boolean validatedName = true;
        boolean nameAlreadyExists = false;
        for(Music m : currentUser.getAllMusic()){
            if(m.getName().equals(name)) {
                nameAlreadyExists = true;
                validatedName = false;
            }
        }
        if(name.isEmpty() || name.length() > 20) {
            guiManager.musicAttemptError(1);
            validatedName = false;
        }
        else if (nameAlreadyExists) {
            guiManager.musicAttemptError(3);
            validatedName = false;
        }
        return validatedName;
    }
    public double musicPriceValidation (String priceString){
        priceString = priceString.replace(',','.');
        double price = -1;
        try{
            price = Double.parseDouble(priceString);
        }catch (NumberFormatException e){
            guiManager.musicAttemptError(0);
        }
        if (price > 50 || price < 0) guiManager.musicAttemptError(2);
        return price;
    }
    public int statisticsUsers (){return userList.size();}

    public double statisticsPrices(){ //valor total musicas no sistema
        double price = 0.0;
        for (Music mc : musicList){
            price += mc.getPrice();
        }
        return price;
    }
    public int totalSongs(){return musicList.size();}
    public int totalSongGenre(RockstarIncManager.GENRE genre){ //este pode ser adicional já que nao é exigido no problema
        int cont = 0;
        for (Music mc : musicList){
            if (mc.getGenre().equals(genre)){
                cont++;
            }
        }
        return cont;
    }

    public int totalAlbumsByGenre(RockstarIncManager.GENRE albumGenre){ //total albuns por genero
    //acho que temos de incluir o genero no construtor do album
        int cont = 0;
        for (User us : userList){
            if (us instanceof MusicCreator){
                for (MusicCollection ab : ((MusicCreator) us).allCollections){
                    if (((Album)ab).getMainGenre().equals(albumGenre)){
                        cont++;
                    }
                }
            }
        }
       return cont;
    }

    public double totalSalesValue() {
        double totalValue = 0.0;
        for (User us :  userList){
            if (us instanceof Client){
                for (MusicAquisition ma : ((Client) us).getListOfAcquisitions()){
                    totalValue += ma.getTotalPrice();
                }
            }
        }
        return totalValue;
    }
}

