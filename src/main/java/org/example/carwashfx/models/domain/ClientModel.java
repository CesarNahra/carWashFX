package org.example.carwashfx.models.domain;

import org.example.carwashfx.controllers.exceptions.ExceptionWash;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class ClientModel {
    protected int clientId;
    protected String clientName;
    protected String clientPhone;
    protected String clientEmail;
    protected LocalDate clientRegistrationDate;
    private final List<VehicleModel> vehicleList = new ArrayList<>();
    private final ScoreModel score;

    public ClientModel() {
        this.score = new ScoreModel();
    }

    public ClientModel(String clientName, String clientPhone, String clientEmail, LocalDate clientRegistrationDate) {
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.clientEmail = clientEmail;
        this.clientRegistrationDate = clientRegistrationDate;
        this.score = new ScoreModel(0);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public LocalDate getClientRegistrationDate() {
        return clientRegistrationDate;
    }

    public void setClientRegistrationDate(LocalDate clientRegistrationDate) {
        this.clientRegistrationDate = clientRegistrationDate;
    }

    public List<VehicleModel> getVehicleList() throws ExceptionWash {
        return vehicleList;
    }

    public void addVehicle(VehicleModel vehicle) {
        vehicleList.add(vehicle);
        vehicle.setClient(this);
    }

    public void removeVehicle(VehicleModel vehicle) {
        vehicleList.remove(vehicle);
        vehicle.setClient(null);
    }

    public ScoreModel getScore() {
        return score;
    }


    public String getData() {
        StringBuilder sb = new StringBuilder();

        sb.append("Nome: ").append(getClientName()).append("\n");
        sb.append("Email: ").append(getClientEmail()).append("\n");
        sb.append("Telefone: ").append(getClientPhone()).append("\n");
        sb.append("Data de Registro: ").append(getClientRegistrationDate()).append("\n");
        sb.append(score.getScoreData()).append("\n");


        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sbCombo = new StringBuilder();

        sbCombo.append("Nome: ").append(getClientName()).append("\n");
        sbCombo.append("Data de Registro: ").append(getClientRegistrationDate()).append("\n");

        return sbCombo.toString();
    }
}
