package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.DataModel.DataSource;
import sample.MainStage.Controller;

public class FinalProjectDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainStage/main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.showFinalStudentsView();

        primaryStage.setTitle("Registrar Office");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    @Override
    public void init() throws Exception {
        super.init();

        if(!DataSource.getInstance().openConnection()) {
            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getInstance().closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
