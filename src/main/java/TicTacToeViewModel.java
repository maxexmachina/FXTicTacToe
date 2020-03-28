import javafx.beans.property.*;
import javafx.collections.FXCollections;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeViewModel {
    private StringProperty labelText = new SimpleStringProperty("Put X in an empty box");
    private BooleanProperty gameDone = new SimpleBooleanProperty();
    private ListProperty<TicTacToeView.GameResultObject> results = new SimpleListProperty<>(FXCollections.observableArrayList());
    private Integer oScore;
    private Integer xScore;
    private Integer currentPlayer;
    private Integer turnCount;
    private String gameResult;

    public boolean isGameDone() {
        return gameDone.get();
    }

    public BooleanProperty gameDoneProperty() {
        return gameDone;
    }

    public void setGameDone(boolean gameDone) {
        this.gameDone.set(gameDone);
    }

    public ListProperty<TicTacToeView.GameResultObject> resultsProperty() {
        return results;
    }

    public void setLabelText(String labelTextProperty) {
        this.labelText.set(labelTextProperty);
    }

    public StringProperty labelTextProperty() {
        return labelText;
    }

    public TicTacToeViewModel() {
        setGameDone(false);
        oScore = 0;
        xScore = 0;
        currentPlayer = 1;
        turnCount = 0;
        results.add(new TicTacToeView.GameResultObject(0, 0));
    }

    public Integer processTileClick(int i, int j, List<List<Tile>> gridState) {
        gridState.get(i).get(j).setTileMark(currentPlayer);
        turnCount++;
        checkGameResult(i, j, gridState);
        if (isGameDone()) {
            setLabelText(gameResult);
            if (gameResult.equals("Draw")) {
                xScore++;
                oScore++;
                setGameScore();
            } else if (currentPlayer == 1) {
                xScore++;
                setGameScore();
            } else {
                oScore++;
                setGameScore();
            }
        }
        Integer clickResult = currentPlayer;
        if (!isGameDone()) {
            currentPlayer = (currentPlayer == 1) ? 0 : 1;
            String labelText = "Put " + ((currentPlayer == 1) ? "X" : "O") + " in an empty box";
            setLabelText(labelText);
        }
        return clickResult;
    }

    private void checkGameResult(int i, int j, List<List<Tile>> grid) {
        if (turnCount > 4) {
            if (winCheck(i , j, grid)) {
                setGameDone(true);
                gameResult = "Player " + ((currentPlayer == 1) ? "X" : "O") + " wins";
            } else if (turnCount == 8) {
                setGameDone(true);
                gameResult = "Draw";
            }
        }
    }

    public boolean winCheck(int idx, int jdx, List<List<Tile>> gridState) {
        for (int i = 0; i < 3; i++) {
            if (!gridState.get(idx).get(i).getTileMark().equals(currentPlayer))
                break;
            if (i == 2)
                return true;
        }

        for (int i = 0; i < 3; i++) {
            if (!gridState.get(i).get(jdx).getTileMark().equals(currentPlayer))
                break;
            if (i == 2)
                return true;
        }

        if (idx == jdx) {
            for (int i = 0; i < 3; i++) {
                if (!gridState.get(i).get(i).getTileMark().equals(currentPlayer))
                    break;
                if (i == 2)
                    return true;
            }
        }

        if (idx + jdx == 2) {
            for (int i = 0; i < 3; i++) {
                if (!gridState.get(i).get(2 - i).getTileMark().equals(currentPlayer))
                    break;
                if (i == 2)
                    return true;
            }
        }
        return false;
    }

    private void setGameScore() {
        results.get(0).crossWinsProperty().setValue(xScore);
        results.get(0).naughtWinsProperty().setValue(oScore);
    }

    public void processNext() {
        currentPlayer = 1;
        turnCount = 0;
        setLabelText("Put X in an empty box");
        setGameDone(false);
    }

    public void processNew() {
        if(xScore != 0 || oScore != 0)
            results.add(0, new TicTacToeView.GameResultObject(0, 0));
        setLabelText("Put X in an empty box");
        oScore = 0;
        xScore = 0;
        setGameDone(false);
        currentPlayer = 1;
        turnCount = 0;
    }

    public void processSave(File file) throws IOException {
        if (file != null) {
            List<String> resultList = new ArrayList<>();
            for (TicTacToeView.GameResultObject res : results.get()) {
                resultList.add(res.crossWinsProperty().getValue() + "," + res.naughtWinsProperty().getValue());
            }
            FileUtils.writeAll(file.getPath(), resultList);
        }
    }

    public void processLoad(File file) throws IOException {
        final String SEPARATOR = ",";
        if (file != null) {
            results.clear();
            List<String> loadedList = FileUtils.readAll(file.getPath());
            for (String string: loadedList) {
                String[] parsed = string.split(SEPARATOR);
                int x = Integer.parseInt(parsed[0]);
                int o = Integer.parseInt(parsed[1]);
                results.add(new TicTacToeView.GameResultObject(x, o));
            }
            xScore = results.get(0).crossWinsProperty().getValue();
            oScore = results.get(0).naughtWinsProperty().getValue();
        }
    }
}
