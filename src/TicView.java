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
    private TableView<GameResult> statTable = new TableView<>();

    public TicView() {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        for (int i = 0; i < 3; i++) {
            gridMatrix.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                Button temp = new Button("X");
                int finalJ = j;
                int finalI = i;
                temp.setOnAction(event -> {
                    System.out.println("Clicked " + finalI + ":" + finalJ);
                });

                temp.setStyle("-fx-focus-color: transparent;");
                temp.setPrefSize(100, 100);

                grid.add(temp, j + 1, i + 1);
                gridMatrix.get(i).add(temp);
            }
        }

        Label gameStatus = new Label("Place X in an empty box");

        gameStatus.setPadding(new Insets(30));
        gameStatus.setFont(new Font("Arial", 25));

        Separator sep = new Separator();

        VBox left = new VBox();
        left.getChildren().addAll(grid, sep, gameStatus);

        TableColumn<GameResult, String> crossColumn = new TableColumn<>("X");
        TableColumn<GameResult, String> naughtColumn = new TableColumn<>("O");
        crossColumn.setStyle( "-fx-alignment: CENTER;");
        naughtColumn.setStyle( "-fx-alignment: CENTER;");
        statTable.getColumns().addAll(crossColumn, naughtColumn);

        statTable.getItems().addAll(new GameResult(1, 0),
                                    new GameResult(1, 0),
                                    new GameResult(1, 0));

        crossColumn.setCellValueFactory(new PropertyValueFactory<>("crossWins"));
        naughtColumn.setCellValueFactory(new PropertyValueFactory<>("naughtWins"));

        statTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        ButtonBar bar = new ButtonBar();
        Button newGameButton = new Button("New Game");
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");

        bar.getButtons().addAll(newGameButton, saveButton, loadButton);

        VBox right = new VBox();
        right.getChildren().addAll(statTable, bar);

        HBox mainWrap = new HBox();
        mainWrap.getChildren().addAll(left, right);

        getChildren().addAll(mainWrap);
    }

    public class GameResult {
        private Integer crossWins;
        private Integer naughtWins;

        public GameResult(Integer crossWins, Integer naughtWins) {
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

