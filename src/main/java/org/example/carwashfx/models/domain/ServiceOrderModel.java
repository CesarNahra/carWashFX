package org.example.carwashfx.models.domain;

import org.example.carwashfx.controllers.exceptions.ExceptionWash;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServiceOrderModel {
    private int orderNumber;
    private double totalValue;
    private LocalDate orderDate = LocalDate.now();
    private double orderDiscount;
    private VehicleModel vehicle;
    private ServiceModel service;
    private EOrderStatus orderStatus = EOrderStatus.OPENED;
    private final List<ItemOsModel> itemOsList = new ArrayList<>();

    public ServiceOrderModel() {
    }

    public ServiceOrderModel(int orderNumber, double totalValue, LocalDate orderDate, double orderDiscount, int serviceId, String serviceDescription, double servicePrice, int givenPoints) {
        this.orderNumber = orderNumber;
        this.totalValue = totalValue;
        this.orderDate = orderDate;
        this.orderDiscount = orderDiscount;
        this.service = new ServiceModel(serviceId, serviceDescription, servicePrice, givenPoints);
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(double orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public VehicleModel getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleModel vehicle) {
        this.vehicle = vehicle;
    }

    public ServiceModel getService() {
        return service;
    }

    public EOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(EOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double calculateServiceTotal() throws ExceptionWash {
        totalValue = 0;

        if (itemOsList.isEmpty()) {
            throw new ExceptionWash("Não há serviços na lista para serem calculados");
        }

        for (ItemOsModel item : itemOsList) {
            totalValue += item.getServiceValue();
        }

        return totalValue;
    }

    public double calculateServiceDiscount() throws ExceptionWash {
        double total = calculateServiceTotal();

        if (itemOsList.isEmpty()) {
            throw new ExceptionWash("Não há serviços na lista para serem calculados");
        }

        double discountValue = total * (orderDiscount / 100.0);

        return total - discountValue;
    }

    public double getDiscountValue() throws ExceptionWash {
        double total = calculateServiceTotal();
        return total * (orderDiscount / 100.0);
    }


    public List<ItemOsModel> getItemOsList() {
        return itemOsList;
    }

    public void addItemOs(ItemOsModel itemOs) {
        itemOsList.add(itemOs);
        itemOs.setServiceOrder(this);
    }

    public void removeItemOs(ItemOsModel itemOs) {
        itemOsList.remove(itemOs);
        itemOs.setServiceOrder(null);
    }

    public String getServiceOrderData() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        sb.append("Nº Ordem Serviço: ").append(getOrderNumber()).append("\n");
        sb.append("Data da Ordem: ").append(formatDate.format(getOrderDate())).append("\n");
        sb.append("Valor Total: ").append(String.format("R$ %.2f", getTotalValue())).append("\n");
        sb.append("Desconto: ").append(String.format("R$ %.2f ", getOrderDiscount())).append("\n");
//        sb.append("Veículo: ").append(getVehicle().getVehicleData()).append("\n");
//        sb.append("Cliente: ").append(getVehicle().getClient().getData()).append("\n");
        sb.append("Itens: ").append(getItemOsList()).append("\n");
        sb.append("Status: ").append(getOrderStatus()).append("\n");

        return sb.toString();
    }
}

