package org.example.carwashfx.models.domain;

public class ServiceModel {
    private int serviceId;
    private String serviceDescription;
    private double servicePrice;
    private int givenPoints;
    private ECategory category;

    public ServiceModel() {

    }

    public ServiceModel(int serviceId, String serviceDescription, double servicePrice, int givenPoints) {
        this.serviceId = serviceId;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
        this.givenPoints = givenPoints;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getGivenPoints() {
        return givenPoints;
    }

    public void setGivenPoints(int givenPoints) {
        this.givenPoints = givenPoints;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Descrição: ").append(getServiceDescription()).append("\n");
        sb.append("Preço: ").append(getServicePrice()).append("\n");
        sb.append("Categoria do Serviço: ").append(getCategory()).append("\n");
        return sb.toString();
    }
}