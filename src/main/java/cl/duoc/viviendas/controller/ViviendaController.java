package cl.duoc.viviendas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class ViviendaController {

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtSuperficie;

    @FXML
    private TextField txtHabitaciones;

    @FXML
    private TextField txtPiso;

    @FXML
    private CheckBox chkGastoComun;

    @FXML
    private CheckBox chkEstacionamiento;

    @FXML
    private CheckBox chkPatio;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView tablaViviendas;

    @FXML
    private TableColumn colTipo;

    @FXML
    private TableColumn colCodigo;

    @FXML
    private TableColumn colSuperficie;

    @FXML
    private TableColumn colHabitaciones;

    @FXML
    private TableColumn colCosto;

    @FXML
    private Label lblMensaje;

    @FXML
    public void initialize() {
        cmbTipo.getItems().addAll("Casa", "Departamento");
    }
}