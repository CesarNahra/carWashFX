package org.example.carwashfx.models.domain;

public class ItemOsModel {
    private double serviceValue;
    private String observations;
    private ServiceOrderModel serviceOrder;
    private ServiceModel service;

    public ItemOsModel() {

    }

    public ItemOsModel(double serviceValue, String observations) {
        this.serviceValue = serviceValue;
        this.observations = observations;
    }

    public double getServiceValue() {
        return serviceValue;
    }

    public void setServiceValue(double serviceValue) {
        this.serviceValue = serviceValue;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public ServiceOrderModel getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrderModel serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public ServiceModel getService() {
        return service;
    }

    public void setService(ServiceModel service) {
        this.service = service;
    }

    public int getServiceId() {
        return service.getServiceId();
    }

    public String getServiceDescription() {
        return service.getServiceDescription();
    }

    public double getServicePrice() {
        return service.getServicePrice();
    }

    public int getServiceGivenPoints() {
        return service.getGivenPoints();
    }

    public ECategory getServiceCategory() {
        return service.getCategory();
    }

    public int getServiceOrderNumber() {
        return serviceOrder.getOrderNumber();
    }
}