package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.net.URL;
import java.util.ResourceBundle;

public class LabController implements Initializable{


    @FXML
    private Button testBtn;

    @FXML
    private Button test2Btn;

    @FXML
    private Button t3b;

    @FXML
    private TextField startTxt;

    @FXML
    private Canvas canvasTest;

    @FXML
    public Button startBtn;

    @FXML
    public Button pauseBtn;

    @FXML
    private HBox paramsPane;

    @FXML
    public TabPane tabPane;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        testBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GraphicsContext gc = canvasTest.getGraphicsContext2D();
                drawShapes(gc);
            }
        });

        test2Btn.setOnAction(event -> {
            canvasTest.getGraphicsContext2D().clearRect(0,0,canvasTest.getHeight(),canvasTest.getHeight());
        });

        startTxt.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    System.out.println("Textfield on focus");
                } else {
                    if (Integer.parseInt(startTxt.getText()) > 150 ){
                        startTxt.setText("150");
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

    public int getIdCurrentTab(){
        return tabPane.getSelectionModel().getSelectedIndex();
    }

    public void draw(int x, int y){
        GraphicsContext gc = canvasTest.getGraphicsContext2D();
        gc.clearRect(0,0,canvasTest.getWidth(),canvasTest.getHeight());
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.fillRect(x, y, 30, 30);
    }

    public void draw(int r){
        GraphicsContext gc = canvasTest.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.fillOval(10, 60, r * 2, r * 2);
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
    }

    @FXML
    public void onKeyTypedIntOnly(KeyEvent event) {
        if (!event.getCharacter().matches("[0-9]")) event.consume();
    }

    public void clearGC() {
        canvasTest.getGraphicsContext2D().clearRect(0,0,canvasTest.getWidth(),canvasTest.getHeight());
    }

    public GraphicsContext getGC(){
        return canvasTest.getGraphicsContext2D();
    }

    public void setDisabledParamsPane(boolean p){
        paramsPane.disableProperty().setValue(p);
    }
}
