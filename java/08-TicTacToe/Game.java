package HW.HW10.ticTacToe;


public class Game {
    private Board board;
    Player player1, player2;

    private final boolean out;
    private int player1Points = 0, player2Points = 0, drawPoints = 1, losePoints = 0, winPoints = 3;
    private final int TRIES;
    private final String PLAYER1NAME, PLAYER2NAME;

    public Game(Board board, Player player1, final String player1Name, Player player2, final String player2Name,
                final int drawPoints, final int losePoints, final int winPoints,
                final boolean out, int triesToRepeatEnter) {

        this(board, player1, player1Name, player2, player2Name, out, triesToRepeatEnter);
        this.drawPoints = drawPoints;
        this.winPoints = winPoints;
        this.losePoints =  losePoints;
    }

    public Game(Board board, Player player1, final String player1Name, Player player2, final String player2Name,
                final boolean out, int triesToRepeatEnter) {
        this.player1 = player1;
        this.board = board;
        this.player2 = player2;
        PLAYER1NAME = player1Name;
        PLAYER2NAME = player2Name;

        if (triesToRepeatEnter < 0) {
            triesToRepeatEnter = 3;
        }
        TRIES = triesToRepeatEnter;

        this.out = out;
    }

    public void start() {
        Result result = Result.UNKNOWN;

        out(board + "\n");
        while (result == Result.UNKNOWN) {
            result = checkInput(player1, PLAYER1NAME,  Cell.X);
            checkResult(result, PLAYER1NAME, PLAYER2NAME);

            if (result == Result.UNKNOWN) {
                result = checkInput(player2, PLAYER2NAME, Cell.O);
                checkResult(result, PLAYER2NAME, PLAYER1NAME);
            }
        }
    }

    public int getPlayer1Points() {
        return player1Points;
    }

    public int getPlayer2Points() {
        return player2Points;
    }

    private Result checkInput(Player player, final String playerName, Cell cell) {
        Result result;
        int triesCounter = 0;

        while ((result = board.makeTurn(player.turn(board.getPosition(), cell)))
                == Result.ERROR) {
            triesCounter++;
            if (triesCounter == TRIES) {
                return Result.LOSE;
            }
            System.out.println("Invalid turn !!!");
            System.out.println(TRIES - triesCounter + " attempts remains !!!");
        }
        out("Player " + playerName + " : " + board.getTurnInfo());
        out(board + "\n");
        return result;
    }

    private void checkResult(final Result result, final String playerNameTurned, final String secondPlayerName) {
        if (result != Result.UNKNOWN) {
            if (result == Result.DRAW) {
                System.out.println("Player " + playerNameTurned + " - DRAW - Player " + secondPlayerName);
                player1Points = player2Points = drawPoints;
            }

            if (result == Result.LOSE) {
                System.out.println("Player " + playerNameTurned + " - LOSE - Player " + secondPlayerName);
                if (PLAYER1NAME.equals(playerNameTurned)) {
                    player2Points = winPoints;
                    player1Points = losePoints;
                } else {
                    player1Points = winPoints;
                    player2Points = losePoints;
                }
            }

            if (result == Result.WIN) {
                System.out.println("Player " + playerNameTurned + " - WIN - Player " + secondPlayerName);
                if (PLAYER1NAME.equals(playerNameTurned)) {
                    player1Points = winPoints;
                    player2Points = losePoints;
                } else {
                    player2Points = winPoints;
                    player1Points = losePoints;
                }
            }
        }
    }

    public void out(String message) {
        if (out) {
            System.out.println(message);
        }
    }

}
