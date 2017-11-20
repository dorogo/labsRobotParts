package application.view;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class LabController implements Initializable {

    public static final int TAB_RECT = 0;
    public static final int TAB_CIRCLE = 1;
    private static final int MAX_AVAILABLE_END_DEGREE = 350;
    private Main mainApp;
    private int maxAvailableEndX;
    private LabController currentWindow;


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
    private TextField accelerationTxtR;
    @FXML
    private TextField accelerationTxtC;

    @FXML
    private TextField currPosTxtR;
    @FXML
    private TextField currPosTxtC;

    @FXML
    private TextField currVelTxtR;
    @FXML
    private TextField currVelTxtC;

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

    @FXML
    private Button parametersBtnR;
    @FXML
    private Button parametersBtnC;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentWindow = this;
        maxAvailableEndX = (int)(canvasRect.getWidth() - Integer.parseInt(widthTxtR.getText()));

        ChangeListener<Boolean> cl = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    mainApp.reloadParamsZveno();
                    maxAvailableEndX = (int)(canvasRect.getWidth() - Integer.parseInt(widthTxtR.getText()));
                }
            }
        };
        startTxtR.focusedProperty().addListener(cl);
        startTxtC.focusedProperty().addListener(cl);
        endTxtR.focusedProperty().addListener(cl);
        endTxtC.focusedProperty().addListener(cl);
        widthTxtR.focusedProperty().addListener(cl);
        heightTxtR.focusedProperty().addListener(cl);
        radiusTxtC.focusedProperty().addListener(cl);
        velocityTxtR.focusedProperty().addListener(cl);
        velocityTxtC.focusedProperty().addListener(cl);

        //check the correctness of the entered data

        startTxtR.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    if (Integer.parseInt(startTxtR.getText()) > maxAvailableEndX) {
                        startTxtR.setText(String.valueOf(maxAvailableEndX));
                    }
                    if (Integer.parseInt(startTxtR.getText()) >= Integer.parseInt(endTxtR.getText())) {
                        startTxtR.setText(String.valueOf(Integer.parseInt(endTxtR.getText()) - 1));
                    }
                    mainApp.reloadParamsZveno();
                }
            }
        });
        endTxtR.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    if (Integer.parseInt(endTxtR.getText()) <= Integer.parseInt(startTxtR.getText())) {
                        endTxtR.setText(String.valueOf(Integer.parseInt(startTxtR.getText()) + 1));
                    }
                    if (Integer.parseInt(endTxtR.getText()) > maxAvailableEndX) {
                        endTxtR.setText(String.valueOf(maxAvailableEndX));
                    }
                    mainApp.reloadParamsZveno();
                }
            }
        });

        startTxtC.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    if (Integer.parseInt(startTxtC.getText()) > MAX_AVAILABLE_END_DEGREE) {
                        startTxtC.setText(String.valueOf(MAX_AVAILABLE_END_DEGREE));
                    }
                    if (Integer.parseInt(startTxtC.getText()) >= Integer.parseInt(endTxtC.getText())) {
                        startTxtC.setText(String.valueOf(Integer.parseInt(endTxtC.getText()) - 1));
                    }
                    mainApp.reloadParamsZveno();
                }
            }
        });
        endTxtC.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    if (Integer.parseInt(endTxtC.getText()) <= Integer.parseInt(startTxtC.getText())) {
                        endTxtC.setText(String.valueOf(Integer.parseInt(startTxtC.getText()) + 1));
                    }
                    if (Integer.parseInt(endTxtC.getText()) > MAX_AVAILABLE_END_DEGREE) {
                        endTxtC.setText(String.valueOf(MAX_AVAILABLE_END_DEGREE));
                    }
                    mainApp.reloadParamsZveno();
                }
            }
        });





        startBtnR.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!mainApp.isAlive && pauseBtnR.isDisable()) {
                    mainApp.isAlive = true;
                    startBtnR.setText("stop");
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
                    startBtnC.setText("stop");
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

        EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("parameters.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("parameters");
                    stage.setScene(new Scene(root1));
                    stage.show();
                    ParametersController pc = fxmlLoader.getController();
                    pc.setParentWindow(currentWindow);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        parametersBtnR.setOnAction(eh);
        parametersBtnC.setOnAction(eh);

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

    public void displayAcceleration(float i) {
        if (getIdCurrentTab() == TAB_RECT) {
            accelerationTxtR.setText(new DecimalFormat("#.#").format(i));
        } else {
            accelerationTxtC.setText(new DecimalFormat("#.#").format(i));
        }
    }

    public void displayCurrentPosition(float i) {
        if (getIdCurrentTab() == TAB_RECT) {
            currPosTxtR.setText(new DecimalFormat("#.#").format(i));
        } else {
            currPosTxtC.setText(new DecimalFormat("#.#").format(i));
        }
    }

    public void displayCurrentVelocity(float i) {
        if (getIdCurrentTab() == TAB_RECT) {
            currVelTxtR.setText(new DecimalFormat("#.#").format(i));
        } else {
            currVelTxtC.setText(new DecimalFormat("#.#").format(i));
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

    public void setMoveMode(int mode){
        mainApp.reloadMoveModelZveno(mode);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public int getCurrentMoveModeID(){
        return mainApp.getCurrenMoveModeId();
    }
}
