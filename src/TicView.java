import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class TicView extends VBox {

    private ArrayList<ArrayList<Button>> gridMatrix = new ArrayList<>();
    private TableView<GameResultObject> statTable = new TableView<>();

    public TicView() {

        TicViewModel viewModel = new TicViewModel();

        Label gameInfoLabel = new Label();
        gameInfoLabel.setPadding(new Insets(30));
        gameInfoLabel.setFont(new Font("Arial", 25));
        gameInfoLabel.textProperty().bindBidirectional(viewModel.labelTextProperty());
        gameInfoLabel.setText("Put X in an empty box");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
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
                temp.setStyle("-fx-focus-color: transparent;");
                temp.setStyle("-fx-font-size:40");
                temp.setPrefSize(100, 100);

                grid.add(temp, j + 1, i + 1);
                gridMatrix.get(i).add(temp);
            }
        }

        Separator sep = new Separator();

        Button nextRoundButton = new Button("Next Round");
        nextRoundButton.visibleProperty().bindBidirectional(viewModel.gameDoneProperty());
        nextRoundButton.setOnAction(event -> {
            viewModel.processNext();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    gridMatrix.get(i).get(j).setText("");
                }
            }
        });

        VBox left = new VBox();
        left.getChildren().addAll(grid, sep, gameInfoLabel, nextRoundButton);

        TableColumn<GameResultObject, String> crossColumn = new TableColumn<>("X");
        TableColumn<GameResultObject, String> naughtColumn = new TableColumn<>("O");

        crossColumn.setCellValueFactory(new PropertyValueFactory<>("crossWins"));
        naughtColumn.setCellValueFactory(new PropertyValueFactory<>("naughtWins"));

        crossColumn.setStyle( "-fx-alignment: CENTER;");
        naughtColumn.setStyle( "-fx-alignment: CENTER;");

        statTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        statTable.getColumns().addAll(crossColumn, naughtColumn);
        loadSampleData();

        ButtonBar statManagerBar = new ButtonBar();
        Button newGameButton = new Button("New Game");
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");

        statManagerBar.getButtons().addAll(newGameButton, saveButton, loadButton);

        VBox right = new VBox();
        right.getChildren().addAll(statTable, statManagerBar);

        HBox mainWrap = new HBox();
        mainWrap.getChildren().addAll(left, right);

        getChildren().addAll(mainWrap);
    }

    private void loadSampleData() {
        statTable.getItems().addAll(new GameResultObject(1, 0),
                new GameResultObject(1, 0),
                new GameResultObject(1, 0));
    }

    public class GameResultObject {
        private Integer crossWins;
        private Integer naughtWins;

        public GameResultObject(Integer crossWins, Integer naughtWins) {
            this.crossWins = crossWins;
            this.naughtWins = naughtWins;
        }

        public Integer getCrossWins() {
            return crossWins;
        }

        public Integer getNaughtWins() {
            return naughtWins;
        }
    }
}

