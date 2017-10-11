package application.view;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LabController implements Initializable {

    public static final int TAB_RECT = 0;
    public static final int TAB_CIRCLE = 1;
    private Main mainApp;

    @FXML
    private TextField startTxt;

    @FXML
    private TextField endTxt;


    @FXML
    private Canvas canvasRect;
    @FXML
    private Canvas canvasCircle;

    @FXML
    public Button startBtnR;
    @FXML
    public Button pauseBtnR;

    @FXML
    public Button startBtnC;
    @FXML
    public Button pauseBtnC;

    @FXML
    private HBox paramsPane;

    @FXML
    public TabPane tabPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    System.out.println("Textfield on focus");
                } else {
                    if (Integer.parseInt(startTxt.getText()) >= Integer.parseInt(endTxt.getText())) {
                        startTxt.setText("0");
                    } else if (Integer.parseInt(startTxt.getText()) > 150) {
                        startTxt.setText("150");
                    }
                }
            }
        });

        startBtnR.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!mainApp.isAlive && pauseBtnR.isDisable()) {
                    mainApp.isAlive = true;
                    startBtnR.setText("reset");
                    setDisabledParamsPane(true);
                    pauseBtnR.setDisable(false);
                } else {
                    mainApp.isAlive = false;
                    startBtnR.setText("start");
                    setDisabledParamsPane(false);
                    pauseBtnR.setDisable(true);
                    pauseBtnR.setText("pause");
                    mainApp.resetZveno();
                }

            }
        });

        pauseBtnR.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mainApp.isAlive) {
                    mainApp.isAlive = false;
                    pauseBtnR.setText("continue");
                } else {
                    mainApp.isAlive = true;
                    pauseBtnR.setText("pause");
                }
            }
        });

        startBtnC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!mainApp.isAlive && pauseBtnC.isDisable()) {
                    mainApp.isAlive = true;
                    startBtnC.setText("reset");
                    setDisabledParamsPane(true);
                    pauseBtnC.setDisable(false);
                } else {
                    mainApp.isAlive = false;
                    startBtnC.setText("start");
                    setDisabledParamsPane(false);
                    pauseBtnC.setDisable(true);
                    pauseBtnC.setText("pause");
                    mainApp.resetZveno();
                }
            }
        });

        pauseBtnC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mainApp.isAlive) {
                    mainApp.isAlive = false;
                    pauseBtnC.setText("continue");
                } else {
                    mainApp.isAlive = true;
                    pauseBtnC.setText("pause");
                }
            }
        });


        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (mainApp.isAlive) {
                    mainApp.isAlive = false;
                    if (getIdCurrentTab() == TAB_RECT) {
                        pauseBtnC.setText("continue");
                    } else {
                        pauseBtnR.setText("continue");
                    }
                }
            }
        });




        
        
        
//        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
//            @Override
//            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
//            }
//        });

    }

    public int getIdCurrentTab() {
        return tabPane.getSelectionModel().getSelectedIndex();
    }


    @FXML
    public void onKeyTypedIntOnly(KeyEvent event) {
        if (!event.getCharacter().matches("[0-9]")) event.consume();
    }

    public void clearGC() {
        if (this.getIdCurrentTab() == TAB_RECT) {
            canvasRect.getGraphicsContext2D().clearRect(0, 0, canvasRect.getWidth(), canvasRect.getHeight());
        } else {
            canvasCircle.getGraphicsContext2D().clearRect(0, 0, canvasCircle.getWidth(), canvasCircle.getHeight());
        }
    }

    public GraphicsContext getGC(int tab) {
        if (tab == TAB_RECT) {
            return canvasRect.getGraphicsContext2D();
        } else {
            return canvasCircle.getGraphicsContext2D();
        }
    }

    public void setDisabledParamsPane(boolean p) {
        paramsPane.disableProperty().setValue(p);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
