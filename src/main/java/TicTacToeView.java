import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeView extends VBox {

    private Stage stage;
    private TicTacToeViewModel viewModel = new TicTacToeViewModel();

    private List<List<Tile>> gridMatrix = new ArrayList<>();
    private GridPane grid = new GridPane();
    private Label gameInfoLabel = new Label();

    private ListProperty<GameResultObject> gameResults = new SimpleListProperty<>(FXCollections.observableArrayList());
    private TableView<GameResultObject> statTable = new TableView<>();

    private Button nextRoundButton = new Button("Next Round");

    private ButtonBar statButtonBar = new ButtonBar();
    private Button newGameButton = new Button("New Game");
    private Button saveButton = new Button("Save");
    private Button loadButton = new Button("Load");


    public TicTacToeView(Stage primaryStage) {
        stage = primaryStage;
        gameResults.bind(viewModel.resultsProperty());

        initGrid();

        gameInfoLabel.setId("info-label");
        gameInfoLabel.textProperty().bindBidirectional(viewModel.labelTextProperty());

        initButtons();

        VBox leftSection = new VBox();
        Separator separator = new Separator();
        leftSection.getChildren().addAll(grid, separator, gameInfoLabel, nextRoundButton);

        initStatTable();

        VBox rightSection = new VBox();
        rightSection.getChildren().addAll(statTable, statButtonBar);

        HBox mainWrap = new HBox();
        mainWrap.getChildren().addAll(leftSection, rightSection);

        getChildren().addAll(mainWrap);
    }

    private void initGrid() {
        grid.getStyleClass().add("grid-pane");
        grid.setVgap(10);
        grid.setHgap(10);

        for (int i = 0; i < 3; i++) {
            gridMatrix.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                Tile temp = new Tile();
                int finalJ = j;
                int finalI = i;
                temp.setOnAction(event -> {
                    if (!temp.isMarked()) {
                        Integer result = viewModel.processTileClick(finalI, finalJ, gridMatrix);
                        temp.setTileMark(result);
                        String text = (result == 1) ? "X" : "O";
                        temp.setText(text);
                        temp.setMarked(true);
                    }
                });

                temp.disableProperty().bindBidirectional(viewModel.gameDoneProperty());
                temp.setId("game-button");

                grid.add(temp, j + 1, i + 1);
                gridMatrix.get(i).add(temp);
            }
        }
    }

    private void initStatTable() {
        statTable.setItems(gameResults);
        TableColumn<GameResultObject, Integer> crossColumn = new TableColumn<>("X");
        TableColumn<GameResultObject, Integer> naughtColumn = new TableColumn<>("O");
        crossColumn.setCellValueFactory(new PropertyValueFactory<>("crossWins"));
        naughtColumn.setCellValueFactory(new PropertyValueFactory<>("naughtWins"));
        crossColumn.setId("cross-column");
        naughtColumn.setId("naught-column");

        statTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        statTable.getColumns().addAll(crossColumn, naughtColumn);
    }

    private void initButtons() {
        nextRoundButton.setId("next-round");
        nextRoundButton.visibleProperty().bindBidirectional(viewModel.gameDoneProperty());
        nextRoundButton.setOnAction(event -> {
            viewModel.processNext();
            clearGrid();
        });

        newGameButton.setOnAction(event -> {
            viewModel.processNew();
            clearGrid();
        });

        saveButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            File savedFile = fileChooser.showSaveDialog(stage);
            try {
            viewModel.processSave(savedFile);
            } catch (IOException e) {
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setHeaderText("IOException thrown in Save method\n");
                errorDialog.setContentText(e.getMessage());
                errorDialog.showAndWait();
            }
        });

        loadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(stage);
            try {
                viewModel.processLoad(selectedFile);
            } catch (IOException e) {
                Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                errorDialog.setHeaderText("IOException thrown in Load method\n");
                errorDialog.setContentText(e.getMessage());
                errorDialog.showAndWait();
            }
        });

        statButtonBar.getButtons().addAll(newGameButton, saveButton, loadButton);
    }

    private void clearGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridMatrix.get(i).get(j).setText("");
                gridMatrix.get(i).get(j).setTileMark(-1);
                gridMatrix.get(i).get(j).setMarked(false);
            }
        }
    }

    public static class GameResultObject {
        private SimpleIntegerProperty crossWins;
        private SimpleIntegerProperty naughtWins;

        public GameResultObject(Integer crossWins, Integer naughtWins) {
            this.crossWins = new SimpleIntegerProperty(crossWins);
            this.naughtWins =  new SimpleIntegerProperty(naughtWins);
        }

        public IntegerProperty crossWinsProperty() {
            return crossWins;
        }

        public IntegerProperty naughtWinsProperty() {
            return naughtWins;
        }
    }
}

