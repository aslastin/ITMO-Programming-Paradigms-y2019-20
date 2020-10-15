package HW.HW10.ticTacToe;

public interface Board {
    Position getPosition();
    Cell getCell(int r, int c);
    String getTurnInfo();
    Result makeTurn(Turn turn);
    void clear();
}
