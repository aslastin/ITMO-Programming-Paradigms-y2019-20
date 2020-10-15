package HW.HW10.ticTacToe.players;

import HW.HW10.ticTacToe.Turn;
import HW.HW10.ticTacToe.Cell;
import HW.HW10.ticTacToe.Position;
import HW.HW10.ticTacToe.Player;



public class SequentialPlayer implements Player {

    @Override
    public Turn turn(Position position, Cell cell) {
        for (int i = 0; i < position.getBoardColumns(); i++) {
            for (int j = 0; j < position.getBoardColumns(); j++) {
                final Turn turn = new Turn(i, j, cell);
                if (position.isValid(turn)) {
                    return turn;
                }
            }
        }
        throw new IllegalStateException("No valid turns");
    }
}
