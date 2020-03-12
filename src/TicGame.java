import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class TicGame {

    private Integer currentPlayer;
    private Integer turnCount;
    private ArrayList<ArrayList<Integer>> gridState = new ArrayList<>();
    private BooleanProperty gameDone = new SimpleBooleanProperty();
    private String gameResult;
    private IntegerProperty xScore = new SimpleIntegerProperty();
    private IntegerProperty oScore = new SimpleIntegerProperty();

    public int getxScore() {
        return xScore.get();
    }

    public IntegerProperty xScoreProperty() {
        return xScore;
    }

    public int getoScore() {
        return oScore.get();
    }

    public IntegerProperty oScoreProperty() {
        return oScore;
    }

    public void setxScore(int xScore) {
        this.xScore.set(xScore);
    }

    public void setoScore(int oScore) {
        this.oScore.set(oScore);
    }

    public BooleanProperty gameDoneProperty() {
        return gameDone;
    }

    public void setGameDone(boolean gameDone) {
        this.gameDone.set(gameDone);
    }

    public TicGame() {
        currentPlayer = 1;
        turnCount = 0;
        setoScore(0);
        setxScore(0);
        setGameDone(false);

        for (int i = 0; i < 3; i++) {
            gridState.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                gridState.get(i).add(-1);
            }
        }
    }

    public String getGameResult() {
        return gameResult;
    }

    public int processMove(int i, int j) {
        if (gridState.get(i).get(j) == -1) {
            if (currentPlayer == 1) {
                gridState.get(i).set(j, 1);
                turnCount++;

                checkGameResult(i, j);

                currentPlayer = 0;
                return 1;
            } else {
                gridState.get(i).set(j, 0);
                turnCount++;

                checkGameResult(i, j);

                currentPlayer = 1;
                return 0;
            }
        }
        else {
            return gridState.get(i).get(j) + 2;
        }
    }

    private void checkGameResult(int i, int j) {
        if (turnCount > 4) {
            if (winCheck(i , j)) {
                setGameDone(true);
                if (currentPlayer == 1) {
                    setxScore(getxScore() + 1);
                } else {
                    setoScore(getoScore() + 1);
                }
                gameResult = "Player " + ((currentPlayer == 1) ? "X" : "O") + " wins";
                System.out.println(getxScore() + " " + getoScore());
            } else if (turnCount == 8) {
                setGameDone(true);
                setoScore(getoScore() + 1);
                setxScore(getxScore() + 1);
                gameResult = "Draw";
                System.out.println(getxScore() + " " + getoScore());
            }
        }
    }

    public boolean winCheck(int idx, int jdx) {

        for (int i = 0; i < 3; i++) {
            if (!gridState.get(idx).get(i).equals(currentPlayer))
                break;
            if (i == 2)
                return true;
        }

        for (int i = 0; i < 3; i++) {
            if (!gridState.get(i).get(jdx).equals(currentPlayer))
                break;
            if (i == 2)
                return true;
        }

        if (idx == jdx) {
            for (int i = 0; i < 3; i++) {
                if (!gridState.get(i).get(i).equals(currentPlayer))
                    break;
                if (i == 2)
                    return true;
            }
        }

        if (idx + jdx == 2) {
            for (int i = 0; i < 3; i++) {
                if (!gridState.get(i).get(2 - i).equals(currentPlayer))
                    break;
                if (i == 2)
                    return true;
            }
        }
        return false;
    }

    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridState.get(i).set(j, -1);
            }
        }

        turnCount = 0;
        currentPlayer = 1;
        setGameDone(false);
    }
}
