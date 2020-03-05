import java.util.ArrayList;

public class TicGame {

    private Integer currentPlayer;
    private Integer turnCount;
    private ArrayList<ArrayList<Integer>> gridState = new ArrayList<>();
    private boolean gameDone;

    public TicGame() {
        for (int i = 0; i < 3; i++) {
            gridState.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                gridState.get(i).add(-1);
            }
        }

        currentPlayer = 1;
        turnCount = 0;
        gameDone = false;
    }

    public int processInput(int i, int j) {
        if (gridState.get(i).get(j) == -1) {
            if (currentPlayer == 1) {
                gridState.get(i).set(j, 1);
                turnCount++;
                gameDone = winCheck(i, j) || drawCheck();
                System.out.println(gameDone);

                currentPlayer = 0;
                System.out.println(turnCount);
                return 1;
            } else {
                gridState.get(i).set(j, 0);
                turnCount++;
                gameDone = winCheck(i, j) || drawCheck();
                System.out.println(gameDone);

                currentPlayer = 1;
                System.out.println(turnCount);
                return 0;
            }
        }
        else {
            return gridState.get(i).get(j);
        }
    }

    public boolean drawCheck() {
        return (turnCount == 8);
    }

    public boolean winCheck(int idx, int jdx) {

        System.out.println("Checking " + idx + ":" + jdx);
        // Check columns
        System.out.println("Column check:");
        for (int i = 0; i < 3; i++) {
            System.out.println("Checking (" + idx + "," + i + ") for == " + currentPlayer);
            if (!gridState.get(idx).get(i).equals(currentPlayer))
                break;
            if (i == 2)
                return true;
        }

        // Check rows
        System.out.println("Row check:");
        for (int i = 0; i < 3; i++) {
            System.out.println("Checking (" + i + "," + idx + ") for == " + currentPlayer);
            if (!gridState.get(i).get(jdx).equals(currentPlayer))
                break;
            if (i == 2)
                return true;
        }

        // Check diag
        System.out.println("Diag check:");
        if (idx == jdx) {
            for (int i = 0; i < 3; i++) {
                System.out.println("Checking (" + i + "," + i + ") for == " + currentPlayer);
                if (!gridState.get(i).get(i).equals(currentPlayer))
                    break;
                if (i == 2)
                    return true;
            }
        }

        // Check anti-diag
        System.out.println("Anti-diag check:");
        if (idx + jdx == 2) {
            for (int i = 0; i < 3; i++) {
                System.out.println("Checking (" + i + "," + (2 - i) + ") for == " + currentPlayer);
                if (!gridState.get(i).get(2 - i).equals(currentPlayer))
                    break;
                if (i == 2)
                    return true;
            }
        }
        return false;
    }
}
