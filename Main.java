package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.datamodel.Datasource;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.listRecords();

        primaryStage.setTitle("My RBD Application");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();

        /**
         * Instead of printing error message, we could inform a user that
         * application was not able to connect to a database by creating a
         * pop-up window. That would be more user friendly approach.
         * However, for simplicity, we just going to print error message.
         */
        if(!Datasource.getInstance().openConnection()) {
            System.out.println("FATAL ERROR: Could not connect to DataBase");
            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Datasource.getInstance().closeConnection();
    }

    public static void main(String[] args) {
       launch(args);

    }
}
