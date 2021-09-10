package HW.HW10.ticTacToe.players;

import HW.HW10.ticTacToe.Turn;
import HW.HW10.ticTacToe.Cell;
import HW.HW10.ticTacToe.Position;
import HW.HW10.ticTacToe.Player;


import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Turn turn(final Position position, final Cell cell) {
        while (true) {
            int r = random.nextInt(position.getBoardRows());
            int c = random.nextInt(position.getBoardColumns());
            final Turn turn = new Turn(r, c, cell);
            if (position.isValid(turn)) {
                return turn;
            }
        }
    }
}
