package HW.HW10.ticTacToe;

import HW.HW10.ticTacToe.players.*;
import java.lang.*;

import java.awt.Point;

import java.util.*;

public class Tournament {
    private Player[] players;
    private final static List<Player> varietyOfPlayers = new ArrayList<Player>(List.of(
            new SequentialPlayer(), new RandomPlayer(), new DiagPlayer()
    ));

    private final int TRIES, PLAYERS, CIRCLES;
    private final Board tourBoard;
    private final int[][] tourStat;
    private final boolean out;
    private Map<Integer, StringBuilder> playerStat;
    private Map<Integer, Integer> playerPoints;
    private List<ArrayList<Integer>> destribution;

    public Tournament(Board board, int numberOfPlayers, int numberOfCircles, int triesToRepeatEnter,
                      final boolean out) {

        Scanner scanner;

        while (numberOfPlayers % 2 == 1 || numberOfPlayers < 1 || numberOfCircles < 1 || triesToRepeatEnter < 0) {
            System.out.println("Incorrect input!!! Repeat again");
            try {
                scanner = new Scanner(System.in);
                numberOfCircles = scanner.nextInt();
                numberOfPlayers = scanner.nextInt();
                triesToRepeatEnter = scanner.nextInt();
            } catch (Exception e) {

            }
        }

        tourBoard = board;
        this.out = out;
        CIRCLES = numberOfCircles;
        TRIES = triesToRepeatEnter;
        PLAYERS = numberOfPlayers;
        players = new Player[PLAYERS];
        tourStat = new int[PLAYERS][PLAYERS];
        destribution = new ArrayList<>();
        playerStat = new HashMap<>();
        playerPoints = new HashMap<>();
        Random random = new Random();

        createDestribution(0, new ArrayList<>(), new boolean[PLAYERS], new boolean[PLAYERS][PLAYERS]);

        for (int i = 0; i < PLAYERS; i++) {
             players[i] = varietyOfPlayers.get(random.nextInt(varietyOfPlayers.size()));

            Arrays.fill(tourStat[i], 0);
        }

    }

    public void start() {
        List<Integer> shuffle = new ArrayList<>();
        int index;
        for (int i = 0; i < PLAYERS - 1; i++) shuffle.add(i);

        for (int i = 1; i <= CIRCLES; i++) {
            System.out.println(i + " circle begins : ");
            index = 0;

            while (index != shuffle.size()) {
                for (int k = 1; k < destribution.get(shuffle.get(index)).size(); k += 2) {
                    int f = destribution.get(shuffle.get(index)).get(k), s = destribution.get(shuffle.get(index)).get(k - 1);
                    Game game = new Game(tourBoard, players[f], (f + 1) + "",
                            players[s], (s + 1) + "", out, TRIES);

                    game.start();
                    addPlayerStat(game, f + 1, s + 1);
                    tourStat[f][s] += game.getPlayer1Points();
                    tourStat[s][f] += game.getPlayer2Points();
                    tourBoard.clear();
                }
                index++;
            }
            outStat();
            Collections.shuffle(shuffle);
        }

        List<Point> list = new ArrayList<>();


        for (int i = 0; i < PLAYERS; i++) {
            if (!playerPoints.containsKey(i + 1)) {
                playerPoints.put(i + 1, 0);
            }
            list.add(new Point(i + 1, playerPoints.get(i + 1)));
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                int a = (int)list.get(i).getY(), b = (int)list.get(j).getY();
                if (a < b) {
                    Point p = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, p);
                }
            }
        }

        System.out.println("\nTournament results : ");

        for (Point p : list) {
            System.out.println("Player " + (int)p.getX() + " : " + (int)p.getY());
        }

    }

    private void addPlayerStat(Game game, int firstPlayerNumber, int secondPlayerNumber) {
        String first = "LOSE", second = "WIN";
        switch (game.getPlayer1Points()) {
            case 3: {
                first = "WIN";
                second = "LOSE";
                playerPoints.put(firstPlayerNumber, playerPoints.getOrDefault(firstPlayerNumber, 0) + 3);;
                break;
            }
            case 1: {
                first = second = "DRAW";
                playerPoints.put(firstPlayerNumber, playerPoints.getOrDefault(firstPlayerNumber, 0) + 1);;
                playerPoints.put(secondPlayerNumber, playerPoints.getOrDefault(secondPlayerNumber, 0) + 1);;
                break;
            }

            case 0 : {
                playerPoints.put(secondPlayerNumber, playerPoints.getOrDefault(secondPlayerNumber, 0) + 3);;
                break;
            }
        }

        playerStat.put(firstPlayerNumber,
                playerStat.getOrDefault(firstPlayerNumber, new StringBuilder(first + " - Player " + secondPlayerNumber))
                        .append('\n' + first + " - Player " + secondPlayerNumber));

        playerStat.put(secondPlayerNumber,
                playerStat.getOrDefault(secondPlayerNumber, new StringBuilder(second + " - Player " + firstPlayerNumber))
                        .append('\n' + second + " - Player " + firstPlayerNumber));

    }

    private void createDestribution(int k, List<Integer> list, boolean[] check, boolean[][] hasPlayed) {

        if (destribution.size() == PLAYERS - 1) {
            return;
        }

        if (k == PLAYERS) {
            int taken = 0;

            for (int i = 1; i < list.size(); i += 2) {
                    int f = list.get(i - 1), s = list.get(i);
                    if (!hasPlayed[f][s]) {
                        taken++;
                    }
            }

            if (taken == PLAYERS / 2) {
                for (int i = 1; i < list.size(); i += 2) {
                    hasPlayed[list.get(i)][list.get(i - 1)] = hasPlayed[list.get(i - 1)][list.get(i)] = true;
                }
                destribution.add(new ArrayList<>(List.copyOf(list)));
            }
            return;
        }

        for (int i = 0; i < PLAYERS; i++) {
            if (!check[i]) {
                list.add(i);
                check[i] = true;
                createDestribution(k + 1, list, check, hasPlayed);
                list.remove(list.size() - 1);
                check[i] = false;
            }
        }
    }

    private void outStat() {
        System.out.println("\nPlayers stats :\n");


        for (int i = 1; i <= PLAYERS; i++) {
            if (i == 1) {
                System.out.printf("\t %4d ", i);
            } else {
                System.out.printf("%4d ", i);
            }
        }

        System.out.println('\n');


        for (int i = 0; i < PLAYERS; i++) {
            System.out.printf("%4d ", i + 1);
            for (int j = 0; j < PLAYERS; j++) {
                System.out.printf("%4d ", tourStat[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void getPlayerPoints(int playerNumber) {
        if (playerPoints.containsKey(playerNumber)) {
            System.out.println("Player " + playerNumber + " : " + playerPoints.get(playerNumber));
        } else {
            System.out.println("Incorrect input!");
        }
    }

    public void getPlayerStat(int playerNumber) {
        if (playerStat.containsKey(playerNumber)) {
            System.out.println("Player " + playerNumber + " stat : " + playerStat.get(playerNumber));
        } else {
            System.out.println("Incorrect input!");
        }
    }

}
