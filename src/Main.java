import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TicTacToeView view = new TicTacToeView(primaryStage);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(view));
        primaryStage.getScene().getStylesheets().add("file:resources/css/style.css");
        primaryStage.getIcons().add(new Image("file:resources/images/img.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
