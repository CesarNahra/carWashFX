package org.example.carwashfx.report;

import org.example.carwashfx.controllers.exceptions.ExceptionWash;
import org.example.carwashfx.models.domain.ItemOsModel;
import org.example.carwashfx.models.domain.ServiceOrderModel;

import java.time.format.DateTimeFormatter;

public class PrintingOs {
    public void printOs(ServiceOrderModel serviceOrder) {

        StringBuilder sb = new StringBuilder();

        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            sb.append("========================================================================").append("\n");
            sb.append("Order Number: ").append(serviceOrder.getOrderNumber()).append("\t\t");
            sb.append("Service Date: ").append(formatDate.format(serviceOrder.getOrderDate())).append("\t\t");
            sb.append("Order Status: ").append(serviceOrder.getOrderStatus()).append("\t\t\n");
            sb.append("Client Name: ").append(serviceOrder.getVehicle().getClient().getClientName()).append("\t\t\n");
            sb.append("Vehicle Plate: ").append(serviceOrder.getVehicle().getVehiclePlate()).append("\t\t");
            sb.append("Vehicle Model: ").append(serviceOrder.getVehicle().getModel().getModelDescription()).append("/").append(serviceOrder.getVehicle().getModel().getBrand().getBrandName()).append("/").append(serviceOrder.getVehicle().getModel().getCategory()).append("\t\t\n");
            sb.append("========================================================================").append("\n");
            sb.append("ITENS\t\t\t\t\tDESCRIPTION\t\t\t\t\tPRICE\t\n");
            sb.append("========================================================================").append("\n");
            int i = 0;
            for(ItemOsModel item : serviceOrder.getItemOsList()) {
                sb.append(++i).append("\t\t\t\t\t").append(item.getObservations()).append("\t\t\t\t\t");
                sb.append(item.getServiceValue()).append("\t\t\n");
            }
            sb.append("========================================================================").append("\n");
            sb.append("SUBTOTAL").append("\t\t\t\t\t\t\t\t\t\t\t").append(serviceOrder.calculateServiceTotal()).append("\n");
            sb.append("DISCOUNT").append("\t").append("(").append(serviceOrder.getOrderDiscount()).append("%").append(")").append("\t\t\t\t\t\t\t\t\t").append(serviceOrder.getDiscountValue()).append("\n");
            sb.append("========================================================================").append("\n");
            sb.append("TOTAL").append("\t\t\t\t\t\t\t\t\t\t\t\t").append(serviceOrder.calculateServiceDiscount()).append("\n");
        } catch (ExceptionWash e) {
            throw new ExceptionWash("Erro ao gerar relatório: " + e.getMessage());
        }
        System.out.println(sb.toString());
    }
}