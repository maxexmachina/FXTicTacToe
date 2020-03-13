import javafx.beans.property.*;
import javafx.collections.FXCollections;

public class TicViewModel {
    private TicGame game;
    private StringProperty labelText = new SimpleStringProperty();
    private BooleanProperty gameDone = new SimpleBooleanProperty();
    private IntegerProperty oScore = new SimpleIntegerProperty();
    private IntegerProperty xScore = new SimpleIntegerProperty();
    private ListProperty<TicView.GameResultObject> results = new SimpleListProperty<>(FXCollections.observableArrayList());

    public ListProperty<TicView.GameResultObject> resultsProperty() {
        return results;
    }

    public TicViewModel() {
        game = new TicGame();
        gameDone.bindBidirectional(game.gameDoneProperty());
        oScore.bindBidirectional(game.oScoreProperty());
        xScore.bindBidirectional(game.xScoreProperty());
        results.add(new TicView.GameResultObject(0, 0));
    }

    public void setoScore(int oScore) {
        this.oScore.set(oScore);
    }

    public void setxScore(int xScore) {
        this.xScore.set(xScore);
    }

    public BooleanProperty gameDoneProperty() {
        return gameDone;
    }

    public void setGameDone(boolean gameDone) {
        this.gameDone.set(gameDone);
    }

    public void setLabelText(String labelTextProperty) {
        this.labelText.set(labelTextProperty);
    }

    public StringProperty labelTextProperty() {
        return labelText;
    }

    public boolean isGameDone() {
        return gameDone.get();
    }

    public String processBoxClick(int i, int j) {
        int clickResult =  game.processMove(i , j);
        if (clickResult == 1) {

            if (isGameDone()) {
                results.get(0).crossWins().setValue(xScore.getValue().toString());
                results.get(0).naughtWins().setValue(oScore.getValue().toString());
                setLabelText(game.getGameResult());
            } else
                setLabelText("Put O in an empty box");

            return "X";

        } else if (clickResult == 0)  {

            if (isGameDone()) {
                results.get(0).crossWins().setValue(xScore.getValue().toString());
                results.get(0).naughtWins().setValue(oScore.getValue().toString());
                setLabelText(game.getGameResult());
            } else
                setLabelText("Put X in an empty box");

            return  "O";
        } else if (clickResult == 2) {
            return "O";
        } else if (clickResult == 3) {
            return "X";
        }
        return "Something";
    }

    public void processNext() {
        game.resetGame();
        setLabelText("Put X in an empty box");
    }

    public void processNew() {
        if(xScore.get() != 0 && oScore.get() != 0)
            results.add(0, new TicView.GameResultObject(0, 0));
        setoScore(0);
        setxScore(0);
        setGameDone(false);
        game.resetGame();
    }

    // TODO implement file methods
    public void processSave() { }
    public void processLoad() { }
}
