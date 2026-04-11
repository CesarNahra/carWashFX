package org.example.carwashfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carwashfx.models.dao.BrandDAO;
import org.example.carwashfx.models.dao.ClientDAO;
import org.example.carwashfx.models.dao.ColorDAO;
import org.example.carwashfx.models.database.Database;
import org.example.carwashfx.models.database.DatabaseFactory;
import org.example.carwashfx.models.domain.ClientModel;
import org.example.carwashfx.models.domain.JuridicalPersonModel;
import org.example.carwashfx.models.domain.NormalPersonModel;
import org.example.carwashfx.utils.ViewNavigator;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class RegisterOwnerController implements Initializable {
    @FXML
    private Label lbClientName;

    @FXML
    private TextField tfClientName;

    @FXML
    private Label lbClientPhone;

    @FXML
    private TextField tfClientPhone;

    @FXML
    private Label lbClientEmail;

    @FXML
    private TextField tfClientEmail;

    @FXML
    private Label lbClientCPF;

    @FXML
    private TextField tfClientCPF;

    @FXML
    private Label lbClientBirthDate;

    @FXML
    private DatePicker dpClientBirthDate;

    @FXML
    private Label lbClientCNPJ;

    @FXML
    private TextField tfClientCNPJ;

    @FXML
    private Label lbClientRegistrationNumber;

    @FXML
    private TextField tfClientRegistrationNumber;

    @FXML
    private Label lbClientRegistrationDate;

    @FXML
    private DatePicker dpClientRegistrationDate;

    @FXML
    private RadioButton rbNormalPerson;

    @FXML
    private RadioButton rbJuridicalPerson;

    @FXML
    private ToggleGroup radioClient = new ToggleGroup();

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.connect();
    private final ClientDAO clientDAO = new ClientDAO();

    @FXML
    protected void onHandleHome(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/car.png")));
        stage.setTitle("Início");

        ViewNavigator.openView(stage, "Index.fxml");
    }

    @FXML
    protected void onHandleGetVehiclesOnAction(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
        stage.setTitle("Listagem de Veículos");

        ViewNavigator.openView(stage, "GetVehicles.fxml");
    }

    @FXML
    protected void onHandleGetOwnersOnAction(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
        stage.setTitle("Listagem de Proprietários");

        ViewNavigator.openView(stage, "GetOwners.fxml");

    }

    @FXML
    protected void onHandleRegisterVehicle(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/add.png")));
        stage.setTitle("Registro de Veículo");

        ViewNavigator.openView(stage, "RegisterVehicle.fxml");

    }

    @FXML
    protected void onHandleShowSellDashboard(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/dashboard.png")));
        stage.setTitle("Dashboard de Vendas");

        ViewNavigator.openView(stage, "SellDashboard.fxml");

    }

    @FXML
    protected void onHandleShowAttendanceDashboard(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/dashboard.png")));
        stage.setTitle("Dashboard de Atendimentos");

        ViewNavigator.openView(stage, "AttendanceDashboard.fxml");

    }

    @FXML
    protected void btRegisterClientOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de novo cliente");
        alert.setHeaderText(null);
        if(rbNormalPerson.isSelected()) {
            ClientModel normalPerson = new NormalPersonModel(tfClientName.getText(), tfClientPhone.getText(), tfClientEmail.getText(), dpClientBirthDate.getValue(), tfClientCPF.getText(), dpClientRegistrationDate.getValue());
            alert.setContentText(normalPerson.getData());

            clientDAO.insertOwner(normalPerson);
        } else {
            ClientModel juridicalPerson = new JuridicalPersonModel(tfClientName.getText(), tfClientPhone.getText(), tfClientEmail.getText(), dpClientRegistrationDate.getValue(), tfClientRegistrationNumber.getText(), tfClientCNPJ.getText());
            alert.setContentText(juridicalPerson.getData());

            clientDAO.insertOwner(juridicalPerson);
        }
        alert.showAndWait();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        ViewNavigator.openView(stage, "GetOwners.fxml");
    }

    @FXML
    protected void btNewClientOnAction(ActionEvent event) {
        tfClientName.clear();
        tfClientEmail.clear();
        tfClientCPF.clear();
        dpClientBirthDate.setValue(null);
        tfClientPhone.clear();
        dpClientRegistrationDate.setValue(null);
        tfClientCNPJ.clear();
        tfClientRegistrationNumber.clear();
        rbNormalPerson.setSelected(true);
        tfClientName.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientDAO.setConnection(connection);

        radioClient.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(rbNormalPerson.isSelected()) {
                tfClientName.requestFocus();
                tfClientCPF.setDisable(false);
                tfClientCPF.setVisible(true);
                lbClientCPF.setVisible(true);

                dpClientBirthDate.setDisable(false);
                dpClientBirthDate.setVisible(true);
                lbClientBirthDate.setVisible(true);

                tfClientCNPJ.setDisable(true);
                tfClientCNPJ.setVisible(false);
                lbClientCNPJ.setVisible(false);

                tfClientRegistrationNumber.setDisable(true);
                tfClientRegistrationNumber.setVisible(false);
                lbClientRegistrationNumber.setVisible(false);
            } else {
                tfClientName.requestFocus();
                tfClientCPF.setDisable(true);
                tfClientCPF.setVisible(false);
                lbClientCPF.setVisible(false);

                dpClientBirthDate.setDisable(true);
                dpClientBirthDate.setVisible(false);
                lbClientBirthDate.setVisible(false);

                tfClientCNPJ.setDisable(false);
                tfClientCNPJ.setVisible(true);
                lbClientCNPJ.setVisible(true);

                tfClientRegistrationNumber.setDisable(false);
                tfClientRegistrationNumber.setVisible(true);
                lbClientRegistrationNumber.setVisible(true);
            }
        });

        rbNormalPerson.setToggleGroup(radioClient);
        rbJuridicalPerson.setToggleGroup(radioClient);

        rbNormalPerson.setSelected(true);
    }
}
