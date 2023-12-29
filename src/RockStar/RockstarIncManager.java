package src.RockStar;

import src.GUIClassesSwing.GUIManager;

import java.io.Serializable;
import java.util.ArrayList;

public class RockstarIncManager  implements Serializable {

    private ArrayList<User> clientList;
    private ArrayList<User> musicCreatorList;
    private ArrayList<Music> musicList;
    private transient User currentUser;
    private transient boolean isCurUserMusicCreator;
    private transient GUIManager guiManager;

    public RockstarIncManager(){
        this.musicList = new ArrayList<>();
        this.clientList = new ArrayList<>();
        this.musicCreatorList = new ArrayList<>();
    }
    public void run(){
        //Criei um client só para experimentar Login.. depois é para apagar
        Client tiago = new Client("as","as","as","as",180);
        clientList.add(tiago);
        MusicCreator pedro = new MusicCreator("Pedro","qw","qw","qw","qw");
        musicCreatorList.add(pedro);

        //Playlist experiencia
        tiago.newCollection("Rock Luso");
        tiago.newCollection("Mamonas Assassinas");

        //Musica Experiencia
        Music m1 = new Music("Olá Joana", Genre.GENRE.POP,pedro,0);
        musicList.add(m1);
        tiago.addMusicToCollection(m1,tiago.getAllCollections().get(0));
        tiago.newMusicToAllMusicCollection(m1);
        pedro.addCreatedMusic(m1);

        Music m2 = new Music("Carolina", Genre.GENRE.POP,pedro,0);
        musicList.add(m2);
        tiago.newMusicToAllMusicCollection(m2);
        tiago.addMusicToCollection(m2,tiago.getAllCollections().get(1));
        pedro.addCreatedMusic(m2);

        Music m3 = new Music("Mariazinha", Genre.GENRE.POP,pedro,0);
        musicList.add(m3);
        tiago.newMusicToAllMusicCollection(m3);
        pedro.addCreatedMusic(m3);



        musicList.add(new Music("City Lights", Genre.GENRE.POP, pedro, 0));
        musicList.add(new Music("Summer Breeze", Genre.GENRE.POP, pedro, 0));
        musicList.add(new Music("Moonlit Night", Genre.GENRE.POP, pedro, 3));
        musicList.add(new Music("Dancing Shadows", Genre.GENRE.POP, pedro, 5));
        musicList.add(new Music("Echoes of Love", Genre.GENRE.POP, pedro, 3));
        musicList.add(new Music("Sunset Dreams", Genre.GENRE.POP, pedro, 4));
        musicList.add(new Music("Whispers of the Heart", Genre.GENRE.POP, pedro, 1));
        musicList.add(new Music("Rhythms of the Rain", Genre.GENRE.POP, pedro, 2));
        musicList.add(new Music("Melodic Sunrise", Genre.GENRE.POP, pedro, 5));
        musicList.add(new Music("Starry Skies", Genre.GENRE.POP, pedro, 2));
        musicList.add(new Music("Neon Heartbeat", Genre.GENRE.POP, pedro, 3));
        musicList.add(new Music("Ocean Whispers", Genre.GENRE.POP, pedro, 1));
        musicList.add(new Music("Silent Echo", Genre.GENRE.POP, pedro, 4));
        musicList.add(new Music("Golden Memories", Genre.GENRE.POP, pedro, 2));
        musicList.add(new Music("Lunar Melodies", Genre.GENRE.POP, pedro, 5));
        musicList.add(new Music("Crystal Visions", Genre.GENRE.POP, pedro, 3));
        musicList.add(new Music("Sunrise Serenade", Genre.GENRE.POP, pedro, 4));
        musicList.add(new Music("Twilight Harmony", Genre.GENRE.POP, pedro, 2));
        musicList.add(new Music("Starlit Journey", Genre.GENRE.POP, pedro, 1));
        musicList.add(new Music("Dreams of Tomorrow", Genre.GENRE.POP, pedro, 5));


        startGUI();
    }
    public void startGUI() {
        guiManager = new GUIManager(RockstarIncManager.this);
        guiManager.run();
    }
    /*
    This method allows to check a login atempt data. The method brings information from the gui like the username,
    password, the pin and if the attempt is made from a music creator or a client by a boolean.
     */
    public void loginAttempt(String username, String password, Boolean isMCreator, String pin){
        boolean sucessfulLogin = false;
        if(isMCreator){
            for(User us : musicCreatorList){
                if(us.getUsername().equals(username) && us.getPassword().equals(password) &&
                        ((MusicCreator)us).getPin().equals(pin)){
                    isCurUserMusicCreator = true;
                    sucessfulLogin = true;
                    currentUser = us;
                }
            }
        }
        else{
            for(User us : clientList){
                if(us.getUsername().equals(username) && us.getPassword().equals(password)){
                    isCurUserMusicCreator = false;
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
            guiManager.unsuccessfulLogin();
            System.out.println("Wrong Login");
        }
    }
    /*
    This method is called by the gui whenever there are a registration attempt. Firstly it will try to find already
    existing a username or an emails. Then it will validate the terms with the help of othe method.
    termValidationOnUserAttempt()
     */

    public void newUserAttempt(String name, String username, String password, String email, boolean isMCreator, String pin){
        boolean emailAlreadyExists = false;
        boolean usernameAlreadyExists = false;
        //This loop allows for an already created MusiCreator make a registration as a Client and vice versa.
        //It sends a code message for GUI to apply an error box: "1" for invalid email and "2" for invalid username
        if(!isMCreator){
            for(User us : clientList){
                if(us.getEmail().equals(email)){
                    emailAlreadyExists = true;
                    System.out.println("email already exists");
                    guiManager.unsuccessfulRegistration(1);
                }
                if(us.getUsername().equals(username)){
                    usernameAlreadyExists = true;
                    System.out.println("username already exists");
                    guiManager.unsuccessfulRegistration(2);
                }
            }
        }
        if(isMCreator){
            for(User us : musicCreatorList){
                if(us.getEmail().equals(email)){
                    emailAlreadyExists = true;
                    System.out.println("email already exists");
                    guiManager.unsuccessfulRegistration(1);
                }
                if(us.getUsername().equals(username)){
                    usernameAlreadyExists = true;
                    System.out.println("username already exists");
                    guiManager.unsuccessfulRegistration(2);
                }
            }
        }
        boolean validRegistration = termValidationOnUserAttempt(name,username, password, email,  isMCreator, pin);
        if(!emailAlreadyExists && !usernameAlreadyExists && validRegistration){
            if(isMCreator) musicCreatorList.add(new MusicCreator(name, username, password, email, pin));
            else clientList.add(new Client(name, username,password,email,0));
            System.out.println("New user created");
            guiManager.successfulRegistration();
        }
    }
    /*
    This method completes the newUserAttempt() method by checking if the introduced terms by a new user attempt
    are correct. It will send elucidative messages to the user by the Gui Manager
     */
    public boolean termValidationOnUserAttempt(String name, String username, String password, String email,
                                             boolean isCreator, String pin){
        boolean validRegistration = true;
        //Pin
        if(isCreator){
            boolean validEmail = false;
            for (int i = 0; i < pin.length(); i++){
                if(pin.charAt(i) < '0' || pin.charAt(i) > '9') {
                    validEmail =false;
                    validRegistration = false;
                }
            }
            if(!validEmail) guiManager.unsuccessfulRegistration(5);
            if (pin.length() < 4 || pin.length() > 8){
                guiManager.unsuccessfulRegistration(5);
                validRegistration = false;
            }
        }
        //Name
        boolean validName = true;
        for (int i = 0; i < name.length(); i++){
            if((name.charAt(i) < 'a' || name.charAt(i) > 'z') && (name.charAt(i) < 'A' || name.charAt(i) < 'Z')) {

                validName =false;
                validRegistration = false;
            }
        }
        if(!validName)guiManager.unsuccessfulRegistration(6);
        if (name.length() < 3 || name.length() > 20){
            guiManager.unsuccessfulRegistration(6);
            validRegistration = false;
        }
        //Usename
        if (username.length() < 3 || username.length() > 10){
            guiManager.unsuccessfulRegistration(4);
            validRegistration = false;
        }
        //Password
        if (password.length() < 3 || password.length() > 20){
            guiManager.unsuccessfulRegistration(7);
            validRegistration = false;
        }
        //Email
        int indexAt = email.indexOf("@");
        int indexDotCom = email.indexOf(".");
        if (email.isBlank() ){
            guiManager.unsuccessfulRegistration(3);
        }
        else if (!(indexAt != -1 && indexDotCom !=-1 && indexAt<indexDotCom && indexAt+1<indexDotCom)){
            guiManager.unsuccessfulRegistration(3);
        }
        return validRegistration;
    }
    /*
    This method will create a new temporary search object that contains multiple lists. The method allows to create
    different types of searches. The Music creator is only able to search his own music.
     */
    public Search search(String searchTerm) {
        ArrayList<Music> foundMusics= new ArrayList<>();
        ArrayList<Music> foundMusicsByArtist = new ArrayList<>();
        ArrayList<MusicCollection> foundMusicCollections = new ArrayList<>();
        if(!isCurUserMusicCreator){
            //Music search
            for(Music m : musicList){
                if(m.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    foundMusics.add(m);
                }
                if(m.getMusicCreator().getName().toLowerCase().contains(searchTerm.toLowerCase())){
                    foundMusicsByArtist.add(m);
                }
            }
            //Collection search: only public playlists or albums.
            //In this case we know that all the collections will be albums.
            for(User us :  musicCreatorList){
                for(MusicCollection mc : us.getAllCollections()){
                    if(mc.getName().toLowerCase().contains(searchTerm.toLowerCase()))foundMusicCollections.add(mc);
                }
            }
            //In this case we know that all the collections will be playlists. We have to select the public ones.
            for(User us :  clientList){
                for(MusicCollection mc : us.getAllCollections()){
                    if(mc.getName().toLowerCase().contains(searchTerm.toLowerCase()) && ((Playlist)mc).getPublicState()){
                        foundMusicCollections.add(mc);
                    }
                }
            }
            return new Search(foundMusics,foundMusicsByArtist,foundMusicCollections);
        } else {//If the current user is a music creator only returns his own music
            for(Music m : currentUser.getAllMusic()){
                if(m.getName().toLowerCase().contains(searchTerm.toLowerCase())) foundMusics.add(m);
            }
            return new Search(foundMusics);
        }
    }
    //este método cria uma playlist de forma aleatória por género musical para o utilizador normal
    //Faz uso de um método acessorio chamado random IndexVector que retorna um vector de inteiros correspondente ao index
    // de uma Arraylist de Musicas.
    //O presente método pede um número de musicas e um género e devolve um arraylist
    //Primeiro de tudo seleciona todas as músicas de determinado género e depois faz a seleção
    // Este metodo retorna um inteiro que corresponde à seleção do utilizador
    //                //1 - adicionar ao carrinho
    //                //2 - comprar as musicas
    //                //3 - apenas selecionar musicas gratuitas

    /*
    At this secction is created a selection of musics from the main music lists. All the music that the client does not
    own and is paid will be also selected and will be presented to de user 3 options. Add to the shopping basket,
    buy the musics (only enable if the user has enough money). And the option of only use free or already owned music.
     */
    public void newRandomPlaylist(Genre.GENRE genre, int nOfMusics){
        ArrayList<Music> allMusicOfTheChosenGenre = new ArrayList<>();
        for(Music m : musicList){
            if(m.getGenre().equals(genre)) allMusicOfTheChosenGenre.add(m);
        }
        int maxSize = allMusicOfTheChosenGenre.size();
        if(maxSize < nOfMusics) {
            System.out.println("There are not enough musics");
            guiManager.notEnoughMusicForRandom(maxSize,false);
        }
        else{
            randomPlaylistCreation(nOfMusics,allMusicOfTheChosenGenre);
        }
    }
    public void randomPlaylistCreation(int nOfMusics, ArrayList<Music> allMusicOfTheChosenGenre){
        ArrayList<ArrayList<Music>> musicSelection = randomMusicSelection(nOfMusics,allMusicOfTheChosenGenre);
        ArrayList<Music> randomMusicSelection = musicSelection.get(0);
        ArrayList<Music> notFreeMusicSelection = musicSelection.get(1);
        boolean successfullyCreated;
        if(!notFreeMusicSelection.isEmpty()){
            successfullyCreated = processorOnPaidRandom(randomMusicSelection, notFreeMusicSelection, nOfMusics,
                    allMusicOfTheChosenGenre);
        }
        else {
            currentUser.newCollection(randomMusicSelection);
            successfullyCreated = true;
        }
        if(successfullyCreated) guiManager.randomPLSuccssefullyCreated();
    }
    public ArrayList<ArrayList<Music>> randomMusicSelection(int nOfMusics, ArrayList<Music> allMusicOfTheChosenGenre){
        ArrayList<ArrayList<Music>> lists = new ArrayList<>();
        ArrayList<Music> randomMusicSelection = new ArrayList<>();
        ArrayList<Music> notFreeMusicSelection = new ArrayList<>();
        int[] listOfIndexes = randomIndexVector(nOfMusics, allMusicOfTheChosenGenre.size());

        for (int listOfIndex : listOfIndexes) {
            Music music = allMusicOfTheChosenGenre.get(listOfIndex);
            if (currentUser.getAllMusic().contains(music)) {
                randomMusicSelection.add(music);
            } else if (!currentUser.getAllMusic().contains(music) && music.getPrice() == 0) {
                currentUser.newMusicToAllMusicCollection(music);
                randomMusicSelection.add(music);
            } else if(!currentUser.getAllMusic().contains(music) && music.getPrice() > 0){
                notFreeMusicSelection.add(music);
            }
        }
        lists.add(randomMusicSelection);
        lists.add(notFreeMusicSelection);
        return lists;
    }
    public boolean processorOnPaidRandom(ArrayList<Music> randomMusicSelection, ArrayList<Music> notFreeMusicSelection,
                                         int nOfMusics, ArrayList<Music> allMusicOfTheChosenGenre){
        double totalPrice = musicPriceCalculator(notFreeMusicSelection);
        double balance = ((Client)currentUser).getBalance();
        boolean canBuy  = totalPrice < balance;
        int userOption = guiManager.randomPlaylistPaidSongsChoose(notFreeMusicSelection, totalPrice,canBuy);
        boolean successfullyCreated = false;
        switch (userOption){
            case 1:
                for (Music m : notFreeMusicSelection){
                    ((Client)currentUser).addMusicToMusicToBuy(m); //Se optar por adicionar ao carrinho apenas as musicas gratuitas serão adicionadas
                }
                successfullyCreated = true;
                break;
            case 2:
                if(canBuy){
                    ((Client)currentUser).validationOfAquisition(notFreeMusicSelection);
                    randomMusicSelection.addAll(notFreeMusicSelection);
                }
                else System.out.println("Not enough money");
                currentUser.newCollection(randomMusicSelection);
                successfullyCreated = true;
                break;
            case 3:
                newRandomPlaylistOnlyFree(allMusicOfTheChosenGenre,nOfMusics);
                break;
        }
        return successfullyCreated;
    }
    public double musicPriceCalculator(ArrayList<Music> musicList){
        double totalPrice = 0;
        for (Music m : musicList){
            totalPrice += m.getPrice();
        }
        return totalPrice;
    }
    public void newRandomPlaylistOnlyFree(ArrayList<Music> musicOfTheChosenGenre, int nOfMusics){
        ArrayList<Music> onlyFreeMusicByGenre = new ArrayList<>();
        ArrayList<Music> freeMusicSelection = new ArrayList<>();
        for(Music m : musicOfTheChosenGenre){
            if(currentUser.getAllMusic().contains(m)) onlyFreeMusicByGenre.add(m);
            else if (m.getPrice() == 0) onlyFreeMusicByGenre.add(m);
        }
        int maxSyzeFreeMusic = onlyFreeMusicByGenre.size();
        if(maxSyzeFreeMusic >= nOfMusics){
            int[] listOfIndexesFreeMusic = randomIndexVector(nOfMusics, onlyFreeMusicByGenre.size());
            for (int listOfIndexesfree : listOfIndexesFreeMusic) {
                Music music = onlyFreeMusicByGenre.get(listOfIndexesfree);
                if (currentUser.getAllMusic().contains(music)) {
                    freeMusicSelection.add(music);
                } else  {
                    currentUser.newMusicToAllMusicCollection(music);
                    freeMusicSelection.add(music);
                }
            }
            currentUser.newCollection(freeMusicSelection);
            guiManager.randomPLSuccssefullyCreated();
        } else{
            guiManager.notEnoughMusicForRandom(maxSyzeFreeMusic,true);
        }
    }
    public int[] randomIndexVector(int SizeOfNewVector, int sizeOfSample){
        //Escolhe de forma aleatoria um vector com indices num certo número de possibilidades. Pensar na utilização de um SEt Integer
        //Ver metodo nweRandomPLaylist
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
    public void newMusic(String name, String priceString, Genre.GENRE genre){
        boolean validatedName = musicNameValidation(name);
        double price = musicPriceValidation(priceString);
        if(validatedName && price != -1){
            Music music = new Music(name, genre,(MusicCreator) currentUser, price);
            musicList.add(music);
            currentUser.newMusicToAllMusicCollection(music);
            guiManager.newMusicCreated();
        }
    }
    public void musicEditionAttempt(Music selectedMusic, String name, String priceString, Genre.GENRE genre, int state){
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
            if(selectedMusic.getAssociatedAlbum() != null)selectedMusic.getAssociatedAlbum().calculateMainGenre();
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
    public int totalUsers(){return clientList.size() + musicCreatorList.size();}
    public double musicTotalPriceValue(){ //valor total musicas no sistema
        double price = 0.0;
        for (Music mc : musicList){
            price += mc.getPrice();
        }
        return price;
    }
    public int totalSongs(){return musicList.size();}
    public int totalAlbumsByGenre(Genre.GENRE albumGenre){ //total albuns por genero
    //acho que temos de incluir o genero no construtor do album
        int cont = 0;
        for (User us : musicCreatorList){
            for (MusicCollection ab :  us.allCollections){
                if (((Album)ab).getMainGenre().equals(albumGenre)){
                    cont++;
                }
            }
        }
       return cont;
    }
    public double totalSalesValue() {
        double totalValue = 0.0;
        for (User us :  clientList){
            for (MusicAquisition ma : ((Client) us).getListOfAcquisitions()){
                totalValue += ma.getTotalPrice();
            }
        }
        return totalValue;
    }
    public double getTotalValueSales(){
        return ((MusicCreator)currentUser).getTotalValueSales();
    }
    public int totalSongGenre(Genre.GENRE genre){ //este pode ser adicional já que nao é exigido no problema
        int cont = 0;
        for (Music mc : musicList){
            if (mc.getGenre().equals(genre)){
                cont++;
            }
        }
        return cont;
    }
}