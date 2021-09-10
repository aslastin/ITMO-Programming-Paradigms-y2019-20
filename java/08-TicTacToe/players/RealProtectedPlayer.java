package HW.HW10.ticTacToe.players;

import HW.HW10.ticTacToe.Turn;
import HW.HW10.ticTacToe.Cell;
import HW.HW10.ticTacToe.Position;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RealProtectedPlayer extends RealPlayer {

    public RealProtectedPlayer(final PrintStream out, final InputStream inputStream) {
        super(out, inputStream);
    }

    public RealProtectedPlayer() {
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

                final Turn turn = new Turn(r, c, cell);
                if (position.isValid(turn)) {
                    return turn;
                }
                out.println("Turn " + turn + " is invalid");
        }
    }
}
