package application.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by user on 14.11.17.
 */
public class ParametersController implements Initializable {
    public static final int MODE_1 = 1;
    public static final int MODE_2 = 2;
    public static final int MODE_3 = 3;

    private LabController parentWindow;
    private int currMoveMode = 1;

    @FXML
    private Button saveBtn;

    @FXML
    private ToggleGroup radioGroup;

    public ParametersController() {
        System.out.println(parentWindow);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parentWindow.setMoveMode(currMoveMode);
                Stage stage = (Stage)saveBtn.getScene().getWindow();
                stage.close();
            }
        });

        radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton chk = (RadioButton)newValue.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                currMoveMode = Integer.parseInt(chk.getId());
            }

        });



    }
    @FXML
    public void onKeyTypedIntOnly(KeyEvent event) {
        if (!event.getCharacter().matches("[0-9]")) event.consume();
    }

    public void setParentWindow(LabController lc){
        parentWindow = lc;
        radioGroup.getToggles().get(parentWindow.getCurrentMoveModeID() - 1).setSelected(true);
    }
}
