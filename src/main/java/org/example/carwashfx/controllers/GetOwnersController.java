package org.example.carwashfx.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carwashfx.models.dao.ClientDAO;
import org.example.carwashfx.models.database.Database;
import org.example.carwashfx.models.database.DatabaseFactory;
import org.example.carwashfx.models.domain.ClientModel;
import org.example.carwashfx.models.domain.NormalPersonModel;
import org.example.carwashfx.models.domain.JuridicalPersonModel;
import org.example.carwashfx.models.domain.ScoreModel;
import org.example.carwashfx.utils.ViewNavigator;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class GetOwnersController implements Initializable {
    @FXML
    private TableColumn<ClientModel, String> tcClientName;

    @FXML
    private TableColumn<ClientModel, String> tcClientPhone;

    @FXML
    private TableColumn<ClientModel, String> tcClientEmail;

    @FXML
    private TableColumn<ClientModel, String> tcClientCPF;

    @FXML
    private TableColumn<ClientModel, String> tcClientCNPJ;

    @FXML
    private TableColumn<ClientModel, LocalDate> tcClientBirthDate;

    @FXML
    private TableColumn<ClientModel, String> tcClientRegistrationNumber;

    @FXML
    private TableColumn<ClientModel, LocalDate> tcClientRegistrationDate;

    @FXML
    private TableColumn<ClientModel, Integer> tcClientScore;

    @FXML
    private TextField tfClientName;

    @FXML
    private TextField tfClientPhone;

    @FXML
    private TextField tfClientEmail;

    @FXML
    private TextField tfClientCPF;

    @FXML
    private TextField tfClientCNPJ;

    @FXML
    private DatePicker dpClientBirthDate;

    @FXML
    private TextField tfClientRegistrationNumber;

    @FXML
    private DatePicker dpClientRegistrationDate;

    @FXML
    private TextField tfClientScore;

    @FXML
    private Button btUpdateClient;

    @FXML
    private Button btDeleteClient;

    @FXML
    private Button btConfirmUpdateClient;

    @FXML
    private Button btCancelUpdateClient;

    @FXML
    private TableView<ClientModel> tableViewOwners;

    private List<ClientModel> ownerList;
    private ObservableList<ClientModel> observableOwnerList;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.connect();
    private final ClientDAO clientDAO = new ClientDAO();

    public void selectTableOwnersItem(ClientModel client) {
        if(client != null) {
            tfClientName.setText(client.getClientName());
            tfClientEmail.setText(client.getClientEmail());
            tfClientPhone.setText(client.getClientPhone());
            dpClientRegistrationDate.setValue(client.getClientRegistrationDate());
            tfClientScore.setText(String.valueOf(client.getScore().getQuantity()));

            if (client instanceof NormalPersonModel) {
                tfClientCPF.setText(((NormalPersonModel)client).getCpf());
                dpClientBirthDate.setValue(((NormalPersonModel)client).getClientBirthDate());
                tfClientCNPJ.clear();
                tfClientRegistrationNumber.clear();
            } else {
                tfClientCNPJ.setText(((JuridicalPersonModel) client).getCnpj());
                tfClientRegistrationNumber.setText(((JuridicalPersonModel)client).getStateRegistrationNumber());
                tfClientCPF.clear();
                dpClientBirthDate.setValue(null);
            }
        }
    }

    public void loadTableViewOwners() {
        tcClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        tcClientPhone.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));
        tcClientEmail.setCellValueFactory(new PropertyValueFactory<>("clientEmail"));
        tcClientCPF.setCellValueFactory(cellData -> {
            ClientModel client = cellData.getValue();

            if (client instanceof NormalPersonModel np) {
                return new SimpleStringProperty(np.getCpf());
            }

            return null;
        });
        tcClientCNPJ.setCellValueFactory(cellData -> {
            ClientModel client = cellData.getValue();

            if (client instanceof JuridicalPersonModel jp) {
                return new SimpleStringProperty(jp.getCnpj());
            }
            return null;
        });
        tcClientBirthDate.setCellValueFactory(cellData -> {
            ClientModel client = cellData.getValue();

            if (client instanceof NormalPersonModel np) {
                return new SimpleObjectProperty<>(np.getClientBirthDate());
            }
            return null;
        });
        tcClientRegistrationNumber.setCellValueFactory(cellData -> {
            ClientModel client = cellData.getValue();

            if (client instanceof JuridicalPersonModel jp) {
                return new SimpleStringProperty(jp.getStateRegistrationNumber());
            }
            return null;
        });
        tcClientRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("clientRegistrationDate"));
        tcClientScore.setCellValueFactory(cellData -> {
            ScoreModel score = cellData.getValue().getScore();
            return new SimpleObjectProperty<>(score.getQuantity());
        });

        ownerList = clientDAO.getAllOwners();

        observableOwnerList = FXCollections.observableArrayList(ownerList);
        tableViewOwners.setItems(observableOwnerList);
    }

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
        stage.setTitle("Listagem de veículos");

        ViewNavigator.openView(stage, "GetVehicles.fxml");
    }

    @FXML
    protected void onHandleRegisterVehicle(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/contract.png")));
        stage.setTitle("Registro de Veículo");

        ViewNavigator.openView(stage, "RegisterVehicle.fxml");

    }

    @FXML
    protected void onHandleRegisterOwner(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/add.png")));
        stage.setTitle("Registro de Proprietário");

        ViewNavigator.openView(stage, "RegisterOwner.fxml");

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
    protected void onHandleUpdateOwner(ActionEvent event) {
        ClientModel client = tableViewOwners.getSelectionModel().getSelectedItem();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/refresh.png")));
        stage.setTitle("Atualização de Proprietário");


        if (client != null) {
            tfClientName.setDisable(false);
            tfClientName.setEditable(true);
            tfClientPhone.setDisable(false);
            tfClientPhone.setEditable(true);
            tfClientEmail.setDisable(false);
            tfClientEmail.setEditable(true);
            tfClientRegistrationNumber.setDisable(false);
            tfClientRegistrationNumber.setEditable(true);

            btUpdateClient.setVisible(false);
            btDeleteClient.setVisible(false);

            btConfirmUpdateClient.setVisible(true);
            btCancelUpdateClient.setVisible(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Prevenção");
            alert.setHeaderText(null);
            alert.setContentText("Esta operação requer a seleção \nde um Cliente na tabela ao lado");
            alert.show();
        }
    }

    @FXML
    protected void onHandleConfirmUpdateOwner (ActionEvent event) {
        ClientModel client = tableViewOwners.getSelectionModel().getSelectedItem();

        client.setClientName(tfClientName.getText());
        client.setClientEmail(tfClientEmail.getText());
        client.setClientPhone(tfClientPhone.getText());

        if(client instanceof JuridicalPersonModel jp){
            jp.setStateRegistrationNumber(tfClientRegistrationNumber.getText());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmação de Atualização de Cliente");
        alert.setHeaderText(null);
        alert.setContentText(client.getData());

        clientDAO.updateOwner(client);
        alert.showAndWait();
        loadTableViewOwners();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
        stage.setTitle("Listagem de Proprietários");

        ViewNavigator.openView(stage, "GetOwners.fxml");
    }

    @FXML
    protected void onHandleCancelUpdateOwner (ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
        stage.setTitle("Listagem de Proprietários");

        ViewNavigator.openView(stage, "GetOwners.fxml");
    }

    @FXML
    protected void onHandleDeleteOwner(ActionEvent event) {
        ClientModel client = tableViewOwners.getSelectionModel().getSelectedItem();

        if(client != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exclusão de cliente");
            alert.setHeaderText(null);
            alert.setContentText("Você deseja excluir todos os dados deste cliente? Esta exclusão é irreversível");
            alert.showAndWait();

            clientDAO.deleteOwner(client);
            loadTableViewOwners();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Prevenção");
            alert.setHeaderText(null);
            alert.setContentText("Esta operação requer a seleção \nde um Cliente na tabela ao lado");
            alert.show();
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
        stage.setTitle("Listagem de Proprietários");

        ViewNavigator.openView(stage, "GetOwners.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientDAO.setConnection(connection);

        loadTableViewOwners();

        tableViewOwners.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectTableOwnersItem(newValue));
    }
}
