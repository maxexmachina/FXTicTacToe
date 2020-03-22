import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeViewModel {
    private TicTacToeGame game;
    private StringProperty labelText = new SimpleStringProperty("Put X in an empty box");
    private BooleanProperty gameDone = new SimpleBooleanProperty();
    private ListProperty<TicTacToeView.GameResultObject> results = new SimpleListProperty<>(FXCollections.observableArrayList());
    private Integer oScore;
    private Integer xScore;

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
        game = new TicTacToeGame();
        oScore = 0;
        xScore = 0;
        results.add(new TicTacToeView.GameResultObject(0, 0));
        gameDone.bindBidirectional(game.gameDoneProperty());
    }

    public String processBoxClick(int i, int j) {
        int clickResult = game.processMove(i , j);
        if (isGameDone() && game.getGameResult().equals("Draw")) {
            xScore++;
            oScore++;
            setGameScore();
            setLabelText(game.getGameResult());

            if (clickResult == 1) {
                return "X";
            } else {
                return "O";
            }
        } else if (!game.getGameResult().equals("Draw")) {
            if (clickResult == 1) {
                if (isGameDone()) {
                    xScore++;
                    setGameScore();
                    setLabelText(game.getGameResult());
                } else
                    setLabelText("Put O in an empty box");

                return "X";

            } else if (clickResult == 0) {
                if (isGameDone()) {
                    oScore++;
                    setGameScore();
                    setLabelText(game.getGameResult());
                } else
                    setLabelText("Put X in an empty box");

                return "O";

                // При нажатии на зянятые ячейки
            } else if (clickResult == 2) {
                return "O";
            } else if (clickResult == 3) {
                return "X";
            }
        }
        return "Something";
    }

    private void setGameScore() {
        results.get(0).crossWinsProperty().setValue(xScore);
        results.get(0).naughtWinsProperty().setValue(oScore);
    }

    public void processNext() {
        game.resetGame();
        setLabelText("Put X in an empty box");
    }

    public void processNew() {
        if(xScore != 0 || oScore != 0)
            results.add(0, new TicTacToeView.GameResultObject(0, 0));
        setLabelText("Put X in an empty box");
        oScore = 0;
        xScore = 0;
        setGameDone(false);
        game.resetGame();
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
