package HW.HW10.ticTacToe.players;

import HW.HW10.ticTacToe.Cell;
import HW.HW10.ticTacToe.Position;
import HW.HW10.ticTacToe.Turn;
import HW.HW10.ticTacToe.Player;

public class DiagPlayer implements Player {

    @Override
    public Turn turn(Position position, Cell cell) {
        int i, j, imove = position.getBoardRows() - 1, jmove = 0;
        i = imove;
        j = jmove;
        while (i != 0 || j != position.getBoardColumns()) {
            while (i < position.getBoardRows() && j < position.getBoardColumns()) {
                final Turn turn = new Turn(i, j, cell);
                if (position.isValid(turn)) {
                    return turn;
                }
                i++;
                j++;
            }
            if (imove == 0) {
                jmove++;
            } else {
                imove--;
            }
            i = imove;
            j = jmove;
        }
        throw new IllegalStateException("No valid turns");
    }



}
