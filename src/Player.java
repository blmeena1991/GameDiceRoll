import java.sql.Time;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;

public class Player {
    private final static String PLAYER_NAME_PREFIX = "Player";
    private int id;
    private int score;
    private String name;
    private int rank;
    boolean isGameOver;
    private int numberRolled;
    private  long timeTakenToCompleted;
    private int penalisedRollCount;



    public Player(int id) {
        this.id = id;
        this.score = 0;
        this.name = PLAYER_NAME_PREFIX + "-" + id;
        this.rank = 0;
        this.isGameOver = false;
        this.timeTakenToCompleted = 1;
        this.penalisedRollCount = 0;
    }

    public int getPenalisedRollCount() {
        return penalisedRollCount;
    }

    public void setPenalisedRollCount(int penalisedRollCount) {
        this.penalisedRollCount = penalisedRollCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        checkWin();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getNumberRolled() {
        return numberRolled;
    }

    public void setNumberRolled(int numberRolled) {
        this.numberRolled = numberRolled;
    }


    public long getTimeTakenToCompleted() {
        return timeTakenToCompleted;
    }

    public void setTimeTakenToCompleted(long timeTakenToCompleted) {
        this.timeTakenToCompleted = timeTakenToCompleted;
    }

    void checkWin(){
        if(score >= GameDiceManager.getMaxWinScore()){
            isGameOver = true;
            score = GameDiceManager.getMaxWinScore();
            timeTakenToCompleted = new Timestamp(System.currentTimeMillis()).getTime();
            PlayerRankBoard.calculatePlayersRank();
            System.out.println(name + " completes the game and got rank :"+ rank);
        }
    }

    void rollDice() {
        numberRolled = new Dice().roll();
    }

    public boolean isHasRolledOne(){
        return numberRolled == 1;
    }

    public boolean ishasRolledSix(){
        return numberRolled == 6;
    }

}
