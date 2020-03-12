import javafx.beans.property.*;

public class TicViewModel {
    private TicGame game;
    private StringProperty labelText = new SimpleStringProperty();
    private BooleanProperty gameDone = new SimpleBooleanProperty();
    private IntegerProperty oScore = new SimpleIntegerProperty();
    private IntegerProperty xScore = new SimpleIntegerProperty();

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

    public TicViewModel() {
        game = new TicGame();
        gameDone.bindBidirectional(game.gameDoneProperty());
        oScore.bindBidirectional(game.oScoreProperty());
        xScore.bindBidirectional(game.xScoreProperty());
    }

    public boolean isGameDone() {
        return gameDone.get();
    }

    public String processBoxClick(int i, int j) {
        int clickResult =  game.processMove(i , j);
        if (clickResult == 1) {

            if (isGameDone()) {
                setLabelText(game.getGameResult());
            } else
                setLabelText("Put O in an empty box");

            return "X";

        } else if (clickResult == 0)  {

            if (isGameDone()) {
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
        setoScore(0);
        setxScore(0);
        setGameDone(false);
        game.resetGame();
    }

    // TODO implement stat methods
    public void processSave() { }
    public void processLoad() { }
}
