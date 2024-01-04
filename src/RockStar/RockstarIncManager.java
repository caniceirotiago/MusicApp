package src.RockStar;

import src.GUIClassesSwing.GUIManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe principal do programa
 * Contem informação sobre clientes, criadores de musica, musicas que existem no sistema
 * Classe que faz a ponte entre o back-end e o front-end
 * Classe que é inicializada no inicio do programa
 *
 */
public class RockstarIncManager  implements Serializable {
    private ArrayList<User> clientList;
    private ArrayList<User> musicCreatorList;
    private User currentUser;
    private boolean isCurUserMusicCreator;
    /**
     * classe transiente porque o GUI não é guardado no ficheiro de dados
     */
    private transient GUIManager guiManager;

    /**
     * Construtor da classe
     */
    public RockstarIncManager(){
        this.clientList = new ArrayList<>();
        this.musicCreatorList = new ArrayList<>();
    }

    /**
     *
     */
    public void run(){
        startGUI();
    }

    /**
     * Método que inicia a interface do utilizador
     */
    public void startGUI() {
        guiManager = new GUIManager(RockstarIncManager.this);
        guiManager.run();
    }
    /*
    This method allows to check a login atempt data. The method brings information from the gui like the username,
    password, the pin and if the attempt is made from a music creator or a client by a boolean.
     */

    /**
     *
     * @param username
     * @param password
     * @param isMCreator
     * @param pin
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
            guiManager.sucessfullLogin(currentUser.getUsername(), isMCreator);
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

    /**
     *
     * @param name
     * @param username
     * @param password
     * @param email
     * @param isMCreator
     * @param pin
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
        boolean validRegistration = termValidationOnNewRegistration(name,username, password, email,  isMCreator, pin);
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

    /**
     *
     * @param name
     * @param username
     * @param password
     * @param email
     * @param isCreator
     * @param pin
     * @return
     */
    public boolean termValidationOnNewRegistration(String name, String username, String password, String email,
                                                   boolean isCreator, String pin){
        boolean validRegistration = true;
        //Pin
        if(isCreator){
            boolean validPin = false;
            for (int i = 0; i < pin.length(); i++){
                if(pin.charAt(i) >= '0' && pin.charAt(i) <= '9') {
                    validPin =true;
                }
                else validRegistration = false;
            }
            if(!validPin) guiManager.unsuccessfulRegistration(5);
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
            validRegistration = false;
        }
        else if (!(indexAt != -1 && indexDotCom !=-1 && indexAt<indexDotCom && indexAt+1<indexDotCom)){
            guiManager.unsuccessfulRegistration(3);
            validRegistration = false;
        }
        return validRegistration;
    }
    /*
    This method will create a new temporary search object that contains multiple lists. The method allows to create
    different types of searches. The Music creator is only able to search his own music.
     */

    /**
     *
     * @param searchTerm
     * @return
     */
    public Search search(String searchTerm) {
        ArrayList<Music> foundMusics= new ArrayList<>();
        ArrayList<Music> foundMusicsByArtist = new ArrayList<>();
        ArrayList<MusicCollection> foundMusicCollections = new ArrayList<>();
        if(!isCurUserMusicCreator){
            //Music search
            for(User mc : musicCreatorList){
                for(Music m : mc.getAllMusic()){
                    if(m.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                        foundMusics.add(m);
                    }
                    if(m.getMusicCreator().getName().toLowerCase().contains(searchTerm.toLowerCase())){
                        foundMusicsByArtist.add(m);
                    }
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

    /**
     *
     * @param genre
     * @param nOfMusics
     */
    public void newRandomPlaylistAttempt(Genre.GENRE genre, int nOfMusics){
        ArrayList<Music> allMusicOfTheChosenGenre = new ArrayList<>();
        for(User mc : musicCreatorList){
            for(Music m : mc.getAllMusic()){
                if(m.getGenre().equals(genre) && m.isActive()) allMusicOfTheChosenGenre.add(m);
            }
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

    /**
     *
     * @param nOfMusics
     * @param allMusicOfTheChosenGenre
     */
    public void randomPlaylistCreation(int nOfMusics, ArrayList<Music> allMusicOfTheChosenGenre){
        ArrayList<ArrayList<Music>> musicSelection = randomMusicSelection(nOfMusics,allMusicOfTheChosenGenre);
        ArrayList<Music> randomMusicSelection = musicSelection.get(0);
        ArrayList<Music> notFreeMusicSelection = musicSelection.get(1);
        boolean successfullyCreated;
        if(!notFreeMusicSelection.isEmpty()){
            successfullyCreated = processorOnRandomToPayMusic(randomMusicSelection, notFreeMusicSelection, nOfMusics,
                    allMusicOfTheChosenGenre);
        }
        else {
            currentUser.newCollection(randomMusicSelection);
            successfullyCreated = true;
        }
        if(successfullyCreated) guiManager.randomPLSuccssefullyCreated();
    }

    /**
     *
     * @param nOfMusics
     * @param allMusicOfTheChosenGenre
     * @return
     */
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

    /**
     *
     * @param randomMusicSelection
     * @param notFreeMusicSelection
     * @param nOfMusics
     * @param allMusicOfTheChosenGenre
     * @return
     */
    public boolean processorOnRandomToPayMusic(ArrayList<Music> randomMusicSelection, ArrayList<Music> notFreeMusicSelection,
                                               int nOfMusics, ArrayList<Music> allMusicOfTheChosenGenre){
        double totalPrice = musicListPriceCalculator(notFreeMusicSelection);
        double balance = ((Client)currentUser).getBalance();
        boolean canBuy  = totalPrice < balance;
        int userOption = guiManager.randomPlaylistToPaySongsChoose(notFreeMusicSelection, totalPrice,canBuy);
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

    /**
     *
     * @param musicList
     * @return
     */
    public double musicListPriceCalculator(ArrayList<Music> musicList){
        double totalPrice = 0;
        for (Music m : musicList){
            totalPrice += m.getPrice();
        }
        return totalPrice;
    }

    /**
     *
     * @param musicOfTheChosenGenre
     * @param nOfMusics
     */
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

    /**
     *
     * @param SizeOfNewVector
     * @param sizeOfSample
     * @return
     */
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

    /**
     *
     * @param name
     * @param priceString
     * @param genre
     */
    public void newMusic(String name, String priceString, Genre.GENRE genre){
        boolean validatedName = musicNameValidation(name);
        double price = musicPriceValidation(priceString);
        if(validatedName && price != -1){
            Music music = new Music(name, genre,(MusicCreator) currentUser, price);
            currentUser.newMusicToAllMusicCollection(music);
            guiManager.newMusicCreated();
        }
    }

    /**
     *
     * @param selectedMusic
     * @param name
     * @param priceString
     * @param genre
     * @param state
     */
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

    /**
     *
     * @param name
     * @return
     */
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

    /**
     *
     * @param priceString
     * @return
     */
    public double musicPriceValidation (String priceString){
        priceString = priceString.replace(',','.');
        double price = -1;
        try{
            price = Double.parseDouble(priceString);
        }catch (NumberFormatException e){
            guiManager.musicAttemptError(0);
        }
        if (price > 50 || price < 0) {
            guiManager.musicAttemptError(2);
            price = -1;
        }
        return price;
    }

    /**
     *
     * @return
     */

    public void logout(){
        currentUser = null;
    }
    public ArrayList<Music> getCurrentUserALlMusic(){
        return currentUser.getAllMusic();
    }
    public ArrayList<MusicCollection> getCurretUserAllCollections(){
        return currentUser.getAllCollections();
    }
    public double getCurrentUserBalance(){
        return (double) Math.round(((Client) currentUser).getBalance() * 100) /100;
    }
    public Playlist getClientAllMusicAsCollection(){
        return new Playlist("Owned Music",(Client) currentUser,currentUser.getAllMusic());
    }
    public Album getMusicCreatorAllMusicAsCollection(){
        return new Album("Created Music",(MusicCreator) currentUser,currentUser.getAllMusic());
    }
    public ArrayList<Music> getUserBasketList(){
        return ((Client)currentUser).getListOfMusicsToBuy();
    }
    public void removeMusicFromCollection(Music selectedMusic,MusicCollection selectedPlaylist){
        selectedMusic.setAssociatedAlbum(null);
        currentUser.removeMusicFromCollection(selectedMusic,selectedPlaylist);
    }
    public void addMusicToCollection(Music selectedMusic,MusicCollection cl){
        currentUser.addMusicToCollection(selectedMusic,cl);
    }
    public void newCollection(String collection){
        currentUser.newCollection(collection);
    }

    public void removeMusicCollection(MusicCollection selected){
        currentUser.removeMusicCollection(selected);
    }
    public void newMusicToAllCollection(Music selectedMusic){
        currentUser.newMusicToAllMusicCollection(selectedMusic);
    }
    public void evaluateMusic(int evaluation, Music selectedMusic){
        selectedMusic.addEvaluation((Client)currentUser, evaluation);
    }
    public void validationOfAquisition(){
        ((Client)currentUser).validationOfAquisition(getUserBasketList());
        ((Client)currentUser).getListOfMusicsToBuy().clear();
    }
    public void addMoney(double money){
        ((Client)currentUser).addMoney(money);
    }

    public void addMusicToMusicToBuy(Music selectedMusic){
        ((Client)currentUser).addMusicToMusicToBuy(selectedMusic);
    }
    public int totalUsers(){return clientList.size() + musicCreatorList.size();}

    /**
     *
     * @return
     */
    public double musicTotalPriceValue(){ //valor total musicas no sistema
        double price = 0.0;
        for(User mc : musicCreatorList){
            for (Music m : mc.getAllMusic()){
                price += m.getPrice();
            }
        }
        return price;
    }

    /**
     *
     * @return
     */
    public int totalSongs(){
        int countMusic = 0;
        for(User mc : musicCreatorList){
            for (Music m : mc.getAllMusic()){
                countMusic++;
            }
        }
        return countMusic;
    }

    /**
     *
     * @param albumGenre
     * @return
     */
    public int totalAlbumsByGenre(Genre.GENRE albumGenre){
        int cont = 0;
        for (User us : musicCreatorList){
            for (MusicCollection ab :  us.allCollections){
                if(((Album)ab).getMainGenre() != null){
                    if (((Album)ab).getMainGenre().equals(albumGenre)){
                        cont++;
                    }
                }
            }
        }
        return cont;
    }

    /**
     *
     * @return
     */
    public double totalSalesValue() {
        double totalValue = 0.0;
        for (User us :  clientList){
            for (MusicAquisition ma : ((Client) us).getListOfAcquisitions()){
                totalValue += ma.getTotalPrice();
            }
        }
        return totalValue;
    }
    public double salesCurrentUser(){
        return ((MusicCreator)currentUser).getTotalValueSales();
    }
    public int currentUserTotalMusicCreated(){
        return currentUser.getAllMusic().size();
    }
    public ArrayList<Double> getStatistics(){
        ArrayList<Double> overallStatistics =  new ArrayList<>();
        overallStatistics.add((double)totalUsers());
        overallStatistics.add((double)totalSongs());
        overallStatistics.add(musicTotalPriceValue());
        overallStatistics.add(totalSalesValue());
        overallStatistics.add(salesCurrentUser());
        overallStatistics.add((double)currentUserTotalMusicCreated());
        return overallStatistics;
    }
    public ArrayList<Integer> getAlbumTypeStatistics(){
        ArrayList<Integer> albumStatistics =  new ArrayList<>();
        ArrayList<Integer> albumCountByGenre = new ArrayList<>();
        for(Genre.GENRE ge : Genre.GENRE.values()){
            albumCountByGenre.add(totalAlbumsByGenre(ge));
        }
        albumCountByGenre.add(totalAlbumsByGenre(null));

        int totalAlbuns = 0;

        for(Integer i: albumCountByGenre){
            totalAlbuns += i;
        }
        albumStatistics.add(totalAlbuns);
        albumStatistics.addAll(albumCountByGenre);
        return albumStatistics;
    }
}