import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameDiceManager {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Player> playerList = new ArrayList<Player>();
    private static Player currentPlayer;
    private static int maxWinScore;

    public static List<Player> getPlayerList() {
        return playerList;
    }

    public static int getMaxWinScore() {
        return maxWinScore;
    }

    private static boolean checkGameEnd(){
       return  playerList.stream().allMatch(o -> o.isGameOver());
    }

    private static void runGame() {
        boolean initComplete = false;
        Random rn = new Random();
        int playerCounter = rn.nextInt(playerList.size());
        currentPlayer = playerList.get(playerCounter);
        System.out.println("The Player Name, Who will start the game:"+ currentPlayer.getName());
        game: while(!initComplete){
            PlayerRankBoard.printRankBoard();
            if(currentPlayer.isGameOver()){
                setNextPlayer();
                continue game;
            }
            if(currentPlayer.getPenalisedRollCount() >= 2){
                System.out.println(currentPlayer.getName() + " are penalised because you rolled ‘1’ twice consecutively");
                currentPlayer.setPenalisedRollCount(0);
                setNextPlayer();
                continue game;
            }
            System.out.println(currentPlayer.getName() + " its your turn (press ‘r’ to roll the dice)");
            boolean rollComplete = false;
            while(!rollComplete) {
                String input = "a";
                try {
                    input = scanner.next();
                } catch(Exception e) {
                    // ignore
                }

                if(input.equals("r")) {

                    currentPlayer.rollDice();
                    rollComplete = true;

                } else {
                    System.out.println("Something went wrong. Type in \"r\".");
                    continue;
                }
            }
            System.out.println(currentPlayer.getName() + " has rolled and got " + currentPlayer.getNumberRolled() +" points.");
            currentPlayer.setScore(currentPlayer.getScore() + currentPlayer.getNumberRolled());
            if(currentPlayer.ishasRolledSix() && !currentPlayer.isGameOver()) {
                System.out.println(currentPlayer.getName() + " has rolled a six, meaning that " +
                        "they get another turn. Roll the dice.");
                continue game;
            }
            if(currentPlayer.isHasRolledOne() && !currentPlayer.isGameOver()) {
                currentPlayer.setPenalisedRollCount(currentPlayer.getPenalisedRollCount() + 1);
            }
            setNextPlayer();
            if(checkGameEnd()){
                break;
            }
        }
        System.out.println("Game End:Result is");
        PlayerRankBoard.printRankBoard();
    }

    /*
     * Sets the next player as current
     * in a circular queue fashion
     */
    private static void setNextPlayer() {
        int nextIndex = playerList.indexOf(currentPlayer) + 1;

        if(nextIndex==playerList.size())
            nextIndex = 0;

        currentPlayer = playerList.get(nextIndex);

    }

    public static void main(String[] args) {
        System.out.println("Welcome! Type the number of players to be participate in the game");
        int number = 0;
        int maxScore = 0;
        boolean inputCorrect = false;
        boolean maxScoreInputCorrect = false;
        while(!inputCorrect) {
            try {
                number = scanner.nextInt();
                inputCorrect = true;
            } catch(Exception e) {
                System.out.println("Something went wrong. Type the number of players to be participate in the game");
            }
            if(number <= 1) {
                inputCorrect = false;
                System.out.println("Enter the valid number of players to be participate in the game(more than 1)");
            }
            if(inputCorrect){
                for(int i=0; i<number; i++)
                    playerList.add(new Player(i+1));
            }
        }
        System.out.println("Enter the valid max Score more than Zero");
        while(!maxScoreInputCorrect) {
            try {
                maxScore = scanner.nextInt();
                maxScoreInputCorrect = true;
            } catch(Exception e) {
                System.out.println("Something went wrong. Enter the valid max Score more than Zero");
            }
            if(maxScore <= 0) {
                maxScoreInputCorrect = false;
                System.out.println("Enter the valid max Score more than Zero");
            }
            if(maxScoreInputCorrect){
                GameDiceManager.maxWinScore = maxScore;
            }
        }
        runGame();
    }
}
