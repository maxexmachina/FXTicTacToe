import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TicViewModel {
    private TicGame game;
    private StringProperty labelText = new SimpleStringProperty();
    private int lastClicked;

    public String getLabelText() {
        return labelText.get();
    }

    public StringProperty labelTextProperty() {
        return labelText;
    }

    public void setLabelText(String labelTextProperty) {
        this.labelText.set(labelTextProperty);
    }

    public TicViewModel() {
        game = new TicGame();
    }

    public String processBoxClick(int i, int j) {
        int clickResult =  game.processInput(i , j);
        if (clickResult == 1) {
            if (lastClicked != 1)
                setLabelText("Put O in an empty box");
            lastClicked = 1;
            return "X";
        } else if (clickResult == 0)  {
            if (lastClicked != 0)
                setLabelText("Put X in an empty box");
            lastClicked = 0;
            return  "O";
        }
        return "Uh oh";
    }
    public void processNew() { }
    public void processSave() { }
    public void processLoad() { }
}
