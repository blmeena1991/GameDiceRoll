import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerRankBoard {
    public static void printRankBoard() {
        TableGenerator tableGenerator = new TableGenerator();
        List<String> headersList = new ArrayList<>();
        headersList.add("Player Name");
        headersList.add("Score");
        headersList.add("Rank");
        List<List<String>> rowsList = new ArrayList<>();
        List<Player> sortedPlayerList = calculatePlayersRank();
        for(Player player: sortedPlayerList){
            List<String> row = new ArrayList<>();
            row.add(player.getName());
            row.add(String.valueOf(player.getScore()));
            row.add(String.valueOf(player.getRank()));

            rowsList.add(row);
        }
        System.out.println(tableGenerator.generateTable(headersList, rowsList));
    }


    public static List<Player> calculatePlayersRank(){
        List<Player> copyPlayerList = new ArrayList<>(GameDiceManager.getPlayerList());
        copyPlayerList.sort(Comparator.comparing(Player::isGameOver,Comparator.reverseOrder())
                .thenComparing(Player::getTimeTakenToCompleted)
                .thenComparing(Player::getScore,Comparator.reverseOrder()));

        List<Long> scoreMultiple = copyPlayerList.stream().map(a -> a.getScore()*a.getTimeTakenToCompleted()).distinct().collect(Collectors.toList());
        copyPlayerList.forEach(a -> a.setRank(scoreMultiple.indexOf(a.getScore() * a.getTimeTakenToCompleted()) + 1));
        return copyPlayerList;
    }
}
