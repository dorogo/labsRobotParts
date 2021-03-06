package application;

import application.lab.Zveno;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import application.lab.MyCircle;
import application.lab.MyRectangle;
import application.view.LabController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main extends Application {

    public static final int PERIOD_IN_MS = 10;
    private static final int TAB_RECT = 0;
    private static final int TAB_CIRCLE = 1;

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private LabController labController;
    private MyRectangle rect;
    private MyCircle circle;
    public boolean isAlive = false;

    private ArrayList<Zveno> arr;


    @Override
    public void start(Stage primaryStage) throws Exception {
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
            labController.setMainApp(this);


            setup();
            startScheduledExecutorService(PERIOD_IN_MS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup() {
        rect = new MyRectangle(labController.getStart(), MyRectangle.DEFAULT_Y, labController.getStart(), labController.getEnd(), labController.getVelocity(), labController.getSize().getX(), labController.getSize().getY(), labController.getGC(LabController.TAB_RECT));
        int i = LabController.TAB_CIRCLE;
        circle = new MyCircle(MyCircle.DEFAULT_X, MyCircle.DEFAULT_Y, labController.getStart(i), labController.getEnd(i), labController.getVelocity(i), labController.getSize(i).getX(), labController.getSize(i).getY(), labController.getGC(LabController.TAB_CIRCLE));

        arr = new ArrayList<>(2);
        arr.add(rect);
        arr.add(circle);

        labController.tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (isAlive) {
                    isAlive = false;
                    if (labController.getIdCurrentTab() == TAB_RECT) {
                        labController.pauseBtnC.setText("continue");
                    } else {
                        labController.pauseBtnR.setText("continue");
                    }
                }
            }
        });

//        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.ENTER) {
//                    reloadParamsZveno();
//                }
//            }
//        });

    }


    private void update(long period) {
        if (isAlive && arr.get(labController.getIdCurrentTab()).canMove())
            arr.get(labController.getIdCurrentTab()).move(period);
    }

    private void draw() {
        labController.clearGC();
        arr.get(labController.getIdCurrentTab()).draw();
        labController.displayAcceleration(arr.get(labController.getIdCurrentTab()).getAcceleration());
        labController.displayCurrentPosition(arr.get(labController.getIdCurrentTab()).getCurrentPosition());
        labController.displayCurrentVelocity(arr.get(labController.getIdCurrentTab()).getVelocity());
    }

    public void resetZveno() {
        arr.get(labController.getIdCurrentTab()).reset();
    }

    public void reloadParamsZveno() {
        arr.get(labController.getIdCurrentTab()).reloadParams(labController.getStart(), labController.getEnd(), labController.getVelocity(), labController.getSize().getX(), labController.getSize().getY());
    }

    public void reloadMoveModelZveno(int mode) {
        for (Zveno z: arr) {
            z.setMoveModel(mode);
        }
    }

    public int getCurrenMoveModeId(){
        return arr.get(labController.getIdCurrentTab()).getCurrentMoveModeId();
    }


    /**
     * @param period in ms
     */
    private void startScheduledExecutorService(long period) {

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        update(period);
                        Platform.runLater(new Runnable() {
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
