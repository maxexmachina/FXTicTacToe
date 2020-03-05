public class TicViewModel {
    private TicGame game;

    public TicViewModel() {
        game = new TicGame();
    }

    public String processBoxClick(int i, int j) {
        int clickResult =  game.processInput(i , j);
        if (clickResult == 1) {
            return "X";
        } else if (clickResult == 0)  {
            return  "O";
        }
        return "Uh oh";
    }
    public void processNew() { }
    public void processSave() { }
    public void processLoad() { }
}
