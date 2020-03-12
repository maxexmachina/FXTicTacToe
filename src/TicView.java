import javafx.beans.property.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TicView extends VBox {

    private ArrayList<ArrayList<Button>> gridMatrix = new ArrayList<>();
    private TableView<GameResultObject> statTable = new TableView<>();

    public TicView() {
        TicViewModel viewModel = new TicViewModel();

        Label gameInfoLabel = new Label();
        gameInfoLabel.setId("info-label");
        gameInfoLabel.textProperty().bindBidirectional(viewModel.labelTextProperty());
        gameInfoLabel.setText("Put X in an empty box");

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
            statTable.getItems().get(0).setCrossWins(13);
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
        loadSampleData();

        ButtonBar statManagerBar = new ButtonBar();
        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(event -> {
            viewModel.processNew();
            clearGrid();
            gameInfoLabel.setText("Put X in an empty box");

        });
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");

        statManagerBar.getButtons().addAll(newGameButton, saveButton, loadButton);

        VBox right = new VBox();
        right.getChildren().addAll(statTable, statManagerBar);

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

    private void loadSampleData() {
        statTable.getItems().addAll(new GameResultObject(1, 0),
                new GameResultObject(1, 0),
                new GameResultObject(1, 0));
    }

    public static class GameResultObject {
        private SimpleStringProperty crossWins;
        private SimpleStringProperty naughtWins;

        public GameResultObject(Integer crossWins, Integer naughtWins) {
            this.crossWins = new SimpleStringProperty(crossWins.toString());
            this.naughtWins =  new SimpleStringProperty(naughtWins.toString());
        }

        public void setCrossWins(Integer crossWins) {
            this.crossWins = new SimpleStringProperty(crossWins.toString());
        }

        public StringProperty crossWins() {
            return crossWins;
        }

        public StringProperty naughtWins() {
            return naughtWins;
        }

        public void setNaughtWins(Integer naughtWins) {
            this.naughtWins = new SimpleStringProperty(naughtWins.toString());
        }

        public String getCrossWins() {
            return crossWins.get();
        }

        public String getNaughtWins() {
            return naughtWins.get();
        }
    }
}

