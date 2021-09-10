package HW.HW10.ticTacToe;

import HW.HW10.ticTacToe.players.RandomPlayer;
import HW.HW10.ticTacToe.players.RealPlayer;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type T or G");
        String string = scanner.nextLine();

        while (!(string.equals("T") || string.equals("G"))) {
            System.out.println("Invalid input!!!");
            string = scanner.nextLine();
        }

        int n = 0, m = 0, k = 0;

        while (n <= 0 || m <= 0 || k > n || k > m) {
            System.out.println("Enter n m k ");
            try {
                scanner = new Scanner(System.in);
                n = scanner.nextInt();
                m = scanner.nextInt();
                k = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        }


        if (string.equals("T")) {
            Tournament tournament = new Tournament(new TicTacToeBoard(n, m, k),
                    4, 1, 3, true);
            tournament.start();
        } else {
            Game game = new Game(new TicTacToeBoard(n, m, k), new RealPlayer(), "Alelsandr", new RandomPlayer(),
                    "Random", true, 3);
            game.start();
        }


    }
}

