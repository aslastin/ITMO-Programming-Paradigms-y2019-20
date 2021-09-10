package HW.HW10.ticTacToe.players;

import HW.HW10.ticTacToe.Turn;
import HW.HW10.ticTacToe.Cell;
import HW.HW10.ticTacToe.Position;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import HW.HW10.ticTacToe.Player;
import java.util.Scanner;

public class RealPlayer implements Player {
    protected final PrintStream out;
    protected Scanner sc;
    protected final InputStream inputStream;

    public RealPlayer(final PrintStream out, final InputStream inputStream) {
        this.out = out;
        this.inputStream = inputStream;
        sc = new Scanner(inputStream);
    }

    public RealPlayer() {
        this(System.out, System.in);
    }

    @Override
    public Turn turn(final Position position, final Cell cell) {
        int r, c;
        while (true) {
            out.println("Enter row and column: ");
            try {
                r = sc.nextInt();
                c = sc.nextInt();
            } catch (InputMismatchException e) {
                out.println("Invalid input");
                sc = new Scanner(inputStream);
                continue;
            }
            return new Turn(r, c, cell);
        }
    }
}
