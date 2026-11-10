package cl.duoc.viviendas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import cl.duoc.viviendas.model.Vivienda;
import javafx.scene.control.cell.PropertyValueFactory;
import cl.duoc.viviendas.model.Casa;
import cl.duoc.viviendas.model.Departamento;
import javafx.beans.property.ReadOnlyObjectWrapper;
import cl.duoc.viviendas.repository.ViviendaRepository;

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
    private TableView<Vivienda> tablaViviendas;

    @FXML
    private TableColumn colTipo;

    @FXML
    private TableColumn colCodigo;

    @FXML
    private TableColumn colSuperficie;

    @FXML
    private TableColumn colHabitaciones;

    @FXML
    private TableColumn<Vivienda, Double> colCosto;

    @FXML
    private Label lblMensaje;
    private ObservableList<Vivienda> listaViviendas =
            FXCollections.observableArrayList();
    private ViviendaRepository repositorio = new ViviendaRepository();

    @FXML
    public void initialize() {
        cmbTipo.getItems().addAll("Casa", "Departamento");

        cmbTipo.setOnAction(event -> {
            boolean esCasa = cmbTipo.getValue().equals("Casa");

            txtPiso.setDisable(esCasa);
            chkGastoComun.setDisable(esCasa);
            chkEstacionamiento.setDisable(esCasa);

            chkPatio.setDisable(!esCasa);
        });

        listaViviendas.addAll(repositorio.cargarViviendas());
        tablaViviendas.setItems(listaViviendas);

        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colSuperficie.setCellValueFactory(new PropertyValueFactory<>("superficie"));
        colHabitaciones.setCellValueFactory(new PropertyValueFactory<>("habitaciones"));
        colCosto.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().calcularCostoArriendo()));

    }


    @FXML
    public void registrarVivienda() {

        String tipo = cmbTipo.getValue();

        if (tipo == null) {
            lblMensaje.setText("Debe seleccionar un tipo de vivienda.");
            return;
        }
        String codigo = txtCodigo.getText();
        String superficieTexto = txtSuperficie.getText();
        String habitacionesTexto = txtHabitaciones.getText();

        if (codigo.trim().isEmpty()) {
            lblMensaje.setText("Debe ingresar el código de la vivienda.");
            return;
        }
        for (Vivienda registrada : listaViviendas) {
            if (registrada.getCodigo().equalsIgnoreCase(codigo.trim())) {
                lblMensaje.setText("Ya existe una vivienda con ese código.");
                return;
            }
        }

        if (superficieTexto.trim().isEmpty()) {
            lblMensaje.setText("Debe ingresar la superficie.");
            return;
        }

        if (habitacionesTexto.trim().isEmpty()) {
            lblMensaje.setText("Debe ingresar la cantidad de habitaciones.");
            return;
        }

        double superficie;
        int habitaciones;

        try {
            superficie = Double.parseDouble(superficieTexto);
            habitaciones = Integer.parseInt(habitacionesTexto);
        } catch (NumberFormatException e) {
            lblMensaje.setText("Ingrese números válidos en superficie y habitaciones.");
            return;
        }
        if (superficie < 20 || superficie > 500) {
            lblMensaje.setText("La superficie debe estar entre 20 y 500.");
            return;
        }

        if (habitaciones <= 0) {
            lblMensaje.setText("Las habitaciones deben ser mayores que cero.");
            return;
        }
        Vivienda vivienda;

        if (tipo.equals("Casa")) {
            boolean patio = chkPatio.isSelected();

            vivienda = new Casa(codigo, superficie, habitaciones, patio);

        } else {

            if (txtPiso.getText().trim().isEmpty()) {
                lblMensaje.setText("Debe ingresar el piso del departamento.");
                return;
            }

            int piso;

            try {
                piso = Integer.parseInt(txtPiso.getText());
            } catch (NumberFormatException e) {
                lblMensaje.setText("Ingrese un número válido para el piso.");
                return;
            }

            if (piso <= 0) {
                lblMensaje.setText("El piso debe ser mayor que cero.");
                return;
            }

            boolean gastoComun = chkGastoComun.isSelected();

            Departamento departamento = new Departamento(
                    codigo, superficie, habitaciones, piso, gastoComun);

            if (chkEstacionamiento.isSelected()) {
                departamento.asignarEstacionamiento();
            }

            vivienda = departamento;
        }

        listaViviendas.add(vivienda);
        repositorio.guardarViviendas(listaViviendas);

        lblMensaje.setText(codigo + " (" + tipo + ") registrada correctamente.");

    }
    @FXML
    public void eliminarVivienda() {

        Vivienda seleccionada = tablaViviendas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            lblMensaje.setText("Debe seleccionar una vivienda para eliminar.");
            return;
        }

        listaViviendas.remove(seleccionada);
        repositorio.guardarViviendas(listaViviendas);

        lblMensaje.setText("Vivienda eliminada correctamente.");
    }
}
