package org.example.carwashfx.models.domain;

import java.time.LocalDate;

public class NormalPersonModel extends ClientModel{
    private String client_cpf;
    private LocalDate client_birthdate;

    public NormalPersonModel() {

    }

    public NormalPersonModel(String client_name, String client_phone, String client_email, LocalDate client_registration_date, String client_cpf, LocalDate client_birthdate) {
        super(client_name, client_phone, client_email, client_registration_date);
        this.client_cpf = client_cpf;
        this.client_birthdate = client_birthdate;
    }

    public String getCpf() {
        return client_cpf;
    }

    public void setCpf(String client_cpf) {
        this.client_cpf = client_cpf;
    }

    public LocalDate getClientBirthDate() {
        return client_birthdate;
    }

    public void setClientBirthDate(LocalDate client_birthdate) {
        this.client_birthdate = client_birthdate;
    }

    public String getData() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.getData());
        sb.append("CPF: ").append(getCpf()).append("\n");
        sb.append("Data de Nascimento: ").append(getClientBirthDate()).append("\n");

        return sb.toString();
    }
}
