package HW.HW10.ticTacToe;

import java.util.*;

public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final Cell[][] cells;
    private Turn currentTurn;
    private final int needToWin, rows, columns;
    private long freeSpace;

    public TicTacToeBoard(int rows, int columns, int needToWin) {
        Scanner scanner;

        while (needToWin > rows || needToWin > columns || rows < 1 || columns < 1) {
            System.out.println("Incorrect input!!! Repeat again");
            try {
                scanner = new Scanner(System.in);
                rows = scanner.nextInt();
                columns = scanner.nextInt();
                needToWin = scanner.nextInt();
            } catch (Exception e) {
                needToWin = rows = columns = -1;
            }
        }
        this.rows = rows;
        this.needToWin = needToWin;
        this.columns = columns;

        this.cells = new Cell[rows][columns];
        clear();
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public String getTurnInfo() {
        return currentTurn.toString();
    }

    @Override
    public int getBoardRows() {
        return rows;
    }

    @Override
    public int getBoardColumns() {
        return columns;
    }

    @Override
    public int getNeedToWin() {
        return needToWin;
    }

    @Override
    public boolean isValid(Turn turn) {
        return turn.getColumn() >= 0 && turn.getColumn() < columns &&
                turn.getRow() >= 0 && turn.getRow() < rows &&
                cells[turn.getRow()][turn.getColumn()] == Cell.E;
    }

    @Override
    public Cell getCell(int r, int c) {
        if (r < rows && c < columns && r >= 0 && c >= 0) {
            return cells[r][c];
        }
        throw new IllegalStateException("Incorrect input");
    }

    @Override
    public Result makeTurn(Turn turn) {
        if (isValid(turn)) {
            currentTurn = turn;
            freeSpace--;
            cells[turn.getRow()][turn.getColumn()] = turn.getValue();

            int mainDiag = 1, sideDiag = 1, horLine = 1, vertLine = 1;
            int i = turn.getRow() - 1, j = turn.getColumn() + 1;

            while (i >= 0 && j < columns && cells[i--][j++] == turn.getValue() && sideDiag < needToWin) {
                sideDiag++;
            }

            i = turn.getRow() + 1; j = turn.getColumn() - 1;
            while (i < rows && j >= 0 && cells[i++][j--] == turn.getValue() && sideDiag < needToWin) {
                sideDiag++;
            }

            i = turn.getRow() - 1; j = turn.getColumn() - 1;
            while (i >= 0 && j >= 0 && cells[i--][j--] == turn.getValue() && mainDiag < needToWin) {
                mainDiag++;
            }

            i = turn.getRow() + 1; j = turn.getColumn() + 1;
            while (i < rows && j < columns && cells[i++][j++] == turn.getValue() && mainDiag < needToWin) {
                mainDiag++;
            }

            i = turn.getRow(); j = turn.getColumn() + 1;
            while (j < columns && cells[i][j++] == turn.getValue() && horLine < needToWin) {
                horLine++;
            }

            j = turn.getColumn() - 1;
            while (j >= 0 && cells[i][j--] == turn.getValue() && horLine < needToWin) {
                horLine++;
            }

            i = turn.getRow() + 1; j = turn.getColumn();
            while (i < rows && cells[i++][j] == turn.getValue() && vertLine < needToWin) {
                vertLine++;
            }

            i = turn.getRow() - 1;
            while (i >= 0 && cells[i--][j] == turn.getValue() && vertLine < needToWin) {
                vertLine++;
            }

            if (sideDiag == needToWin || mainDiag == needToWin || horLine == needToWin
                    || vertLine == needToWin) {
                return Result.WIN;
            }
            if (freeSpace == 0) {
                return Result.DRAW;
            }
            return Result.UNKNOWN;
        } else {
            return Result.ERROR;
        }
    }

    public void clear() {
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        freeSpace = (long)columns * rows;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < columns; i++) {
            sb.append(i + " ");
        }
        sb.append('\n').append('\n');

        for (int i = 0; i < rows; i++) {
            sb.append(i + "   ");
            for (int j = 0; j < columns; j++) {
                sb.append(SYMBOLS.get(cells[i][j]) + " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
