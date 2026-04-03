package org.example.carwashfx.models.domain;

import java.time.LocalDate;

public class JuridicalPersonModel extends ClientModel {
    private String cnpj;
    private String stateRegistrationNumber;

    public JuridicalPersonModel() {

    }

    public JuridicalPersonModel(String clientName, String clientPhone, String clientEmail, LocalDate clientRegisterDate, String cnpj, String stateRegistrationNumber) {
        super(clientName, clientPhone, clientEmail, clientRegisterDate);
        this.cnpj = cnpj;
        this.stateRegistrationNumber = stateRegistrationNumber;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getStateRegistrationNumber() {
        return stateRegistrationNumber;
    }

    public void setStateRegistrationNumber(String stateRegistrationNumber) {
        this.stateRegistrationNumber = stateRegistrationNumber;
    }

//    public String getData() {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append(super.getData());
//        sb.append("CNPJ: ").append(getCnpj()).append("\n");
//        sb.append("Nº de Registro Estadual: ").append(getStateRegistrationNumber()).append("\n");
//
//        return sb.toString();
//    }

    public String getData() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.getData());
        sb.append("CNPJ: ").append(getCnpj()).append("\n");
        sb.append("Nº de Registro Estadual: ").append(getStateRegistrationNumber()).append("\n");

        return sb.toString();
    }
}
