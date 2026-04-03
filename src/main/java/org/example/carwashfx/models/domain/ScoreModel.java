package org.example.carwashfx.models.domain;

import org.example.carwashfx.controllers.exceptions.ExceptionWash;

public class ScoreModel {
    private int quantity;

    public ScoreModel() {

    }

    public ScoreModel(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) throws ExceptionWash {
        if(quantity > 0) {
            this.quantity += quantity;
        } else {
            throw new ExceptionWash("Quantidade a ser adicionada tem que ser, OBRIGATORIAMENTE, positiva");
        }
    }

    public void subQuantity(int quantity) throws ExceptionWash {
        if (quantity > 0 && this.quantity >= quantity) {
            this.quantity -= quantity;
        } else {
            throw new ExceptionWash("Quantidade a ser removida não pode exceder a quantidade já existente");
        }
    }

    public String getScoreData() {
        StringBuilder scoreData = new StringBuilder();

        scoreData.append("Pontuação atual do cliente: ").append(getQuantity()).append("\n");

        return scoreData.toString();
    }
}
