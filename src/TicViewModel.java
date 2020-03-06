import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TicViewModel {
    private TicGame game;
    private BooleanProperty gameDone = new SimpleBooleanProperty();
    private int lastSymbol;

    private StringProperty labelText = new SimpleStringProperty();

    public BooleanProperty gameDoneProperty() {
        return gameDone;
    }

    public void setLabelText(String labelTextProperty) {
        this.labelText.set(labelTextProperty);
    }

    public TicViewModel() {
        game = new TicGame();
        gameDone.bindBidirectional(game.gameDoneProperty());
    }

    public boolean isGameDone() {
        return gameDone.get();
    }

    public String processBoxClick(int i, int j) {
        int clickResult =  game.processMove(i , j);
        if (clickResult == 1) {

            if (isGameDone()) {
                setLabelText(game.getGameResult());
            } else if (lastSymbol != 1) {
                setLabelText("Put O in an empty box");
            }

            lastSymbol = 1;
            return "X";

        } else if (clickResult == 0)  {

            if (isGameDone()) {
                setLabelText(game.getGameResult());
            } else if (lastSymbol != 0) {
                setLabelText("Put X in an empty box");
            }

            lastSymbol = 0;
            return  "O";
        }
        return "Uh oh";
    }

    public void processNext() {
        game.resetGame();
        lastSymbol = -1;
        setLabelText("Put X in an empty box");
    }

    public StringProperty labelTextProperty() {
        return labelText;
    }

    // TODO implement stat methods
    public void processNew() { }
    public void processSave() { }
    public void processLoad() { }
}
