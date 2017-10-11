import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lab.MyCircle;
import lab.MyRectangle;
import view.LabController;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.concurrent.*;

public class Main extends Application {

    private static final int TAB_RECT = 0;
    private static final int TAB_CIRCLE = 1;

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private LabController labController;
    private MyRectangle rect;
    private MyCircle circle;

    private boolean isAlive = false;



    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Lab");

        initRootLayout();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/lab.fxml"));
            rootLayout = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
            primaryStage.setOnCloseRequest(event -> System.exit(0));



            labController = loader.getController();
            labController.draw(10,10);
            labController.draw(20);


            rect = new MyRectangle(0, 20, 0.5f, labController.getGC());
            circle = new MyCircle(0, 20, 0.5f, labController.getGC());

            setup();
            startScheduledExecutorService(10);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup(){
        labController.startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!isAlive) {
                    isAlive = true;
                    labController.startBtn.setText("reset");
                    labController.setDisabledParamsPane(true);
                    labController.pauseBtn.setDisable(false);
                } else {
                    isAlive = false;
                    labController.startBtn.setText("start");
                    labController.setDisabledParamsPane(false);
                    labController.pauseBtn.setDisable(true);
                    labController.pauseBtn.setText("pause");
                }

            }
        });

        labController.pauseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isAlive) {
                    isAlive = false;
                    labController.pauseBtn.setText("continue");
                } else {
                    isAlive = true;
                    labController.pauseBtn.setText("pause");
                }
            }
        });

        labController.tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (isAlive) {
                    isAlive = false;
                    labController.pauseBtn.setText("continue");
                }
            }
        });

    }


    private void update(){
        if(isAlive) {
            if (labController.getIdCurrentTab() == TAB_RECT){
                rect.move(true);
            } else {
                circle.move(true);
            }
        }
    }

    private void draw(){
        labController.clearGC();
        if (labController.getIdCurrentTab() == TAB_RECT){
            rect.draw();
        } else {
            circle.draw();
        }
    }


    /**
     *
     * @param period in ms
     */
    private void startScheduledExecutorService(long period){

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(
                new Runnable(){
                    @Override
                    public void run() {
                        update();
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                draw();
                            }
                        });
                    }
                },
                0,
                period,
                TimeUnit.MILLISECONDS);
    }

}
