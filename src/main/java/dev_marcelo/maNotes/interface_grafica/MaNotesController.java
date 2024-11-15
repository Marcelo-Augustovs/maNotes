package dev_marcelo.maNotes.interface_grafica;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

@Controller
public class MaNotesController {

    @FXML
    private BorderPane MaNotesInterfaceGrafica;

    @FXML
    private MenuBar menuTop;

    @FXML
    private TabPane menuCenter;

    @FXML
    private Text menuBottom;

}