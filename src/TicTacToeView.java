import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TicTacToeView extends VBox {

    private ArrayList<ArrayList<Button>> gridMatrix = new ArrayList<>();

    public TicTacToeView() {
        TicTacToeViewModel viewModel = new TicTacToeViewModel();

        ListProperty<GameResultObject> gameResults = new SimpleListProperty<>(FXCollections.observableArrayList());
        gameResults.bind(viewModel.resultsProperty());

        TableView<GameResultObject> statTable = new TableView<>();
        statTable.setItems(gameResults);

        Label gameInfoLabel = new Label();
        gameInfoLabel.setId("info-label");
        gameInfoLabel.textProperty().bindBidirectional(viewModel.labelTextProperty());

        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");
        grid.setVgap(10);
        grid.setHgap(10);

        for (int i = 0; i < 3; i++) {
            gridMatrix.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                Button temp = new Button();
                int finalJ = j;
                int finalI = i;
                temp.setOnAction(event -> {
                    temp.setText(viewModel.processBoxClick(finalI, finalJ));
                    });

                temp.disableProperty().bindBidirectional(viewModel.gameDoneProperty());
                temp.setId("game-button");

                grid.add(temp, j + 1, i + 1);
                gridMatrix.get(i).add(temp);
            }
        }

        Separator sep = new Separator();

        Button nextRoundButton = new Button("Next Round");
        nextRoundButton.setId("next-round");
        nextRoundButton.visibleProperty().bindBidirectional(viewModel.gameDoneProperty());
        nextRoundButton.setOnAction(event -> {
            viewModel.processNext();
            clearGrid();
        });

        VBox left = new VBox();
        left.getChildren().addAll(grid, sep, gameInfoLabel, nextRoundButton);

        TableColumn<GameResultObject, String> crossColumn = new TableColumn<>("X");
        TableColumn<GameResultObject, String> naughtColumn = new TableColumn<>("O");
        crossColumn.setCellValueFactory(data -> data.getValue().crossWins());
        naughtColumn.setCellValueFactory(data -> data.getValue().naughtWins());
        crossColumn.setId("cross-column");
        naughtColumn.setId("naught-column");

        statTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        statTable.getColumns().addAll(crossColumn, naughtColumn);

        ButtonBar statButtons = new ButtonBar();
        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> {
            viewModel.processNew();
            clearGrid();
        });
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");

        statButtons.getButtons().addAll(newGameButton, saveButton, loadButton);

        VBox right = new VBox();
        right.getChildren().addAll(statTable, statButtons);

        HBox mainWrap = new HBox();
        mainWrap.getChildren().addAll(left, right);

        getChildren().addAll(mainWrap);
    }

    private void clearGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridMatrix.get(i).get(j).setText("");
            }
        }
    }

    public static class GameResultObject {
        private SimpleStringProperty crossWins;
        private SimpleStringProperty naughtWins;

        public GameResultObject(Integer crossWins, Integer naughtWins) {
            this.crossWins = new SimpleStringProperty(crossWins.toString());
            this.naughtWins =  new SimpleStringProperty(naughtWins.toString());
        }

        public StringProperty crossWins() {
            return crossWins;
        }

        public StringProperty naughtWins() {
            return naughtWins;
        }
    }
}

