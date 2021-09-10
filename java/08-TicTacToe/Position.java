package HW.HW10.ticTacToe;

public interface Position {
    boolean isValid(Turn turn);
    int getBoardRows();
    int getBoardColumns();
    int getNeedToWin();
}
