import java.util.Scanner;

public class Main{

    static String[][] map = new String[10][38];
    static boolean stillPlaying = true;
    static Scanner userInput = new Scanner(System.in);
    static int previousLocationX;
    static int previousLocationY;
    static int previousMonsterLocationX;
    static int previousMonsterLocationY;
    static boolean monsterAlive = true;
    static int treasureLocationX;
    static int treasureLocationY;

    public static void main(String[] args) {
        createMap();
        createObstacles();
        getDefaultPlayerPosition();
        getDefaultMonsterPositions();
        getTreasurePosition();
        drawMap();

        // movement
        while(stillPlaying){
            if(monsterAlive == true){
                if(checkProximity() == true){
                    // 
                }
                else{
                    monsterMovement();
                }
            }
            playerMovement();
        }
    }

    public static void createMap(){
        // creates map
        for(int row = 0; row < map.length; row++){
            for(int col = 0; col < map[0].length; col++){
                map[row][col] = " ";
            }
        }
    }
    public static void createObstacles(){
        // creates obstacles
        for(int row =  1; row < 8; row++){
           map[row][4] = "|";
        }
        for(int row = 3; row < 8; row++){
            map[row][7] = "|";
        }
        for(int col = 4; col < 21; col++){
            map[1][col] = "-";
        }
        for(int col = 7; col < 18; col++){
            map[3][col] = "-";
        }
        for(int row = 2; row < 8; row++){
            map[row][20] = "|";
        }
        for(int row = 5; row < 8; row++){
            map[row][12] = "|";
        }
        for(int row = 5; row < 8; row++){
            map[row][17] = "|";
        }
        for(int col = 24; col < 32; col++){
            map[4][col] = "-";
        }
        for(int row = 1; row < 4; row++){
            map[row][28] = "|";
        }
        for(int row = 5; row < 8; row++){
            map[row][28] = "|";
        }
    }
    public static void getDefaultPlayerPosition(){
        // find center position
        int centerY = (map.length/2) - 1;
        int centerX = (map[0].length/2) - 1;
        map[centerY][centerX] = "X";
        previousLocationX = centerX;
        previousLocationY = centerY;
    }
    public static void getDefaultMonsterPositions(){
        int randomNumberX = (int)(Math.random() * map[0].length);
        int randomNumberY = (int)(Math.random() * map.length);
        int randomY = randomNumberY;
        int randomX = randomNumberX;
        if(randomY == previousLocationY){
            getDefaultMonsterPositions();
        }
        else if(randomX == previousLocationX){
            getDefaultMonsterPositions();
        }
        else if(map[randomY][randomX] == "|" || map[randomY][randomX] == "-"){
            getDefaultMonsterPositions();
        }
        else{
            map[randomY][randomX] = "O";
            previousMonsterLocationX = randomX;
            previousMonsterLocationY = randomY;
        }

    }
    public static void getTreasurePosition(){
        int randomNumberX = (int)(Math.random() * map[0].length);
        int randomNumberY = (int)(Math.random() * map.length);
        int randomY = randomNumberY;
        int randomX = randomNumberX;
        if(randomY == previousLocationY || randomY == previousMonsterLocationY){
            getTreasurePosition();
        }
        else if(randomX == previousLocationX || randomX == previousMonsterLocationX){
            getTreasurePosition();
        }
        else if(map[randomY][randomX] == "|" || map[randomY][randomX] == "-"){
            getTreasurePosition();
        }
        else{
            map[randomY][randomX] = "T";
            treasureLocationY = randomY;
            treasureLocationX = randomX;
        }

    }
    public static void drawMap(){
        // draws map
        for(int row = 0; row < map[0].length+2; row++){
            System.out.print("-");
        }
        System.out.println("");
        for(int row = 0; row < map.length; row++){
            System.out.print("|");
            for(int col = 0; col < map[0].length; col++){
                System.out.print(map[row][col]);
            }
            System.out.println("|");
        }
        for(int row = 0; row < map[0].length+2; row++){
            System.out.print("-");
        }
       System.out.println("");
    }

    public static void playerMovement(){
        String movement = userInput.nextLine();
        if(movement.equals("w")){
            map[previousLocationY][previousLocationX] = " ";
            previousLocationY--;
            if(previousLocationY < 0){
                previousLocationY = 0;
            }
            checkObstacleCollision(movement, 0);
            map[previousLocationY][previousLocationX] = "X";
            checkCollision();
        }        
        else if(movement.equals("s")){
            map[previousLocationY][previousLocationX] = " ";
            previousLocationY++;
            if(previousLocationY == map.length){
                previousLocationY = map.length - 1;
            }
            checkObstacleCollision(movement, 0);
            map[previousLocationY][previousLocationX] = "X";
            checkCollision();
        }
        else if(movement.equals("a")){
            map[previousLocationY][previousLocationX] = " ";
            previousLocationX--;
            if(previousLocationX < 0){
                previousLocationX = 0;
            }
            checkObstacleCollision(movement, 0);
            map[previousLocationY][previousLocationX] = "X";
            checkCollision();
        }
        else if(movement.equals("d")){
            map[previousLocationY][previousLocationX] = " ";
            previousLocationX++;
            if(previousLocationX == map[0].length){
                previousLocationX = map[0].length-1;
            }
            checkObstacleCollision(movement, 0);
            map[previousLocationY][previousLocationX] = "X";
            checkCollision();
        }
        if(stillPlaying == true){
            drawMap();
        }
    }
 
    public static void monsterMovement(){
        int randomNumber = (int)(Math.random() * 4) + 1;
        if(randomNumber == 1){
            map[previousMonsterLocationY][previousMonsterLocationX] = " ";
            previousMonsterLocationY--;
            if(previousMonsterLocationY < 0){
                previousMonsterLocationY = 0;
            }
            checkObstacleCollision("error", randomNumber);
            map[previousMonsterLocationY][previousMonsterLocationX] = "O";
            checkCollision();
        }
        else if(randomNumber == 2){
            map[previousMonsterLocationY][previousMonsterLocationX] = " ";
            previousMonsterLocationY++;
            if(previousMonsterLocationY == map.length){
                previousMonsterLocationY = map.length - 1;
            }
            checkObstacleCollision("error", randomNumber);
            map[previousMonsterLocationY][previousMonsterLocationX] = "O";
            checkCollision();
        }
        else if(randomNumber == 3){
            map[previousMonsterLocationY][previousMonsterLocationX] = " ";
            previousMonsterLocationX--;
            if(previousMonsterLocationX < 0){
                previousMonsterLocationX = 0;
            }
            checkObstacleCollision("error", randomNumber);
            map[previousMonsterLocationY][previousMonsterLocationX] = "O";
            checkCollision();
        }
        else if(randomNumber == 4){
            map[previousMonsterLocationY][previousMonsterLocationX] = " ";
            previousMonsterLocationX++;
            if(previousMonsterLocationX == map[0].length){
                previousMonsterLocationX = map[0].length-1;
            }
            checkObstacleCollision("error", randomNumber);
            map[previousMonsterLocationY][previousMonsterLocationX] = "O";
            checkCollision();
        }
        
        
    }
    public static void checkObstacleCollision(String movement, int randomNumber){
        // Collison: obstacles
        if(map[previousLocationY][previousLocationX] == "|" || map[previousLocationY][previousLocationX] == "-"){
            if(movement.equals("w")){
                previousLocationY++;
            }
            else if(movement.equals("s")){
                previousLocationY--;
            }
            else if(movement.equals("a")){
                previousLocationX++;
            }
            else if(movement.equals("d")){
                previousLocationX--;
            }
        }
        if(map[previousMonsterLocationY][previousMonsterLocationX] == "|" || map[previousMonsterLocationY][previousMonsterLocationX] == "-"){
            if(randomNumber == 1){
                previousMonsterLocationY++;
            }
            else if(randomNumber == 2){
                previousMonsterLocationY--;
            }
            else if(randomNumber == 3){
                previousMonsterLocationX++;
            }
            else if(randomNumber == 4){
                previousMonsterLocationX--;
            }
        }


    }
    public static void checkCollision(){
        // Collision: treasure and monster
        if(map[treasureLocationY][treasureLocationX] == "X"){
            System.out.println("");
            drawMap();
            System.out.println("You found the treasure!");
            stillPlaying = false;
        }
        else if(map[previousMonsterLocationY][previousMonsterLocationX] == "X"){
            monsterAlive = false;
        }
        else if(map[previousLocationY][previousLocationX] == "O"){
            System.out.println("");
            drawMap();
            System.out.println("You died!");
            stillPlaying = false;
        }
        else if(map[treasureLocationY][treasureLocationX] == "O"){
            map[treasureLocationY][treasureLocationX] = "T";
            drawMap();
        }
        else{
        
        }
    }
    public static boolean checkProximity(){
        boolean agroOn;
        for(int i = 0; i < 4; i++){
            if(previousMonsterLocationX - i == previousLocationX || previousMonsterLocationX + i == previousLocationX || previousMonsterLocationY - 1 == previousLocationY ||
                previousMonsterLocationY + i == previousLocationY){
                    return true;
            }
        }
        return false;
        
        
    }
  
}
