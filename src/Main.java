import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        TicView view = new TicView();


        primaryStage.setTitle("Scuffed Tic Tac Toe");
        primaryStage.setScene(new Scene(view));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
