package application.view;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
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
    private TextField startTxtR;
    @FXML
    private TextField endTxtR;

    @FXML
    private TextField startTxtC;
    @FXML
    private TextField endTxtC;

    @FXML
    private TextField widthTxtR;
    @FXML
    private TextField heightTxtR;
    @FXML
    private TextField radiusTxtC;

    @FXML
    private TextField velocityTxtR;
    @FXML
    private TextField velocityTxtC;




    @FXML
    public Button refreshBtnR;
    @FXML
    public Button refreshBtnC;

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
    private HBox paramsPaneR;
    @FXML
    private HBox paramsPaneC;

    @FXML
    public TabPane tabPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startTxtR.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    System.out.println("Textfield on focus");
                } else if (!startTxtR.getText().isEmpty()) {
                    if (Integer.parseInt(startTxtR.getText()) >= Integer.parseInt(endTxtR.getText())) {
                        startTxtR.setText("0");
                    } else if (Integer.parseInt(startTxtR.getText()) > 150) {
                        startTxtR.setText("150");
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
                    mainApp.reloadParamsZveno();
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
                    mainApp.reloadParamsZveno();
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


//        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
//            @Override
//            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
//                if (mainApp.isAlive) {
//                    mainApp.isAlive = false;
//                    if (getIdCurrentTab() == TAB_RECT) {
//                        pauseBtnC.setText("continue");
//                    } else {
//                        pauseBtnR.setText("continue");
//                    }
//                }
//            }
//        });




        
        
        
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

    public float getStart(){
        return getStart(getIdCurrentTab());
    }
    public float getStart(int tmp){
        if (tmp  == TAB_RECT) {
            return Float.parseFloat(startTxtR.getText());
        } else {
            return Float.parseFloat(startTxtC.getText());
        }
    }
    public float getEnd(){
        return getEnd(getIdCurrentTab());
    }
    public float getEnd(int tmp){
        if (tmp  == TAB_RECT) {
            return Float.parseFloat(endTxtR.getText());
        } else {
            return Float.parseFloat(endTxtC.getText());
        }
    }

    public float getVelocity(){
        return getVelocity(getIdCurrentTab());
    }
    public float getVelocity(int tmp){
        if (tmp  == TAB_RECT) {
            return Float.parseFloat(velocityTxtR.getText());
        } else {
            return Float.parseFloat(velocityTxtC.getText());
        }
    }

    public Point2D getSize(){
        return getSize(getIdCurrentTab());
    }
    public Point2D getSize(int tmp){
        if (tmp == TAB_RECT) {
            return new Point2D(Float.parseFloat(widthTxtR.getText()), Float.parseFloat(heightTxtR.getText()));
        } else {
            return new Point2D(Float.parseFloat(radiusTxtC.getText()) * 2, Float.parseFloat(radiusTxtC.getText()) * 2);
        }
    }

    private void setDisabledParamsPane(boolean p) {
        if (getIdCurrentTab() == TAB_RECT) {
            paramsPaneR.disableProperty().setValue(p);
        } else {
            paramsPaneC.disableProperty().setValue(p);
        }

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
