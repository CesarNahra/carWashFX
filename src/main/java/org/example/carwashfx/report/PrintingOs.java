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
            sb.append("Nº da Ordem de Serviço: ").append(serviceOrder.getOrderNumber()).append("\t\t");
            sb.append("Data da Ordem: ").append(formatDate.format(serviceOrder.getOrderDate())).append("\t\t");
            sb.append("Status da Ordem: ").append(serviceOrder.getOrderStatus()).append("\t\t\n");
            sb.append("Nome do Cliente: ").append(serviceOrder.getVehicle().getClient().getClientName()).append("\t\t\n");
            sb.append("Placa do Veículo: ").append(serviceOrder.getVehicle().getVehiclePlate()).append("\t\t");
            sb.append("Modelo do Veículo: ").append(serviceOrder.getVehicle().getModel().getModelDescription()).append("/").append(serviceOrder.getVehicle().getModel().getBrand().getBrandName()).append("/").append(serviceOrder.getVehicle().getModel().getCategory()).append("\t\t\n");
            sb.append("===========================================================================").append("\n");
            sb.append("ITENS\t\t\t\t\tDESCRIÇÃO\t\t\t\t\t\t\tPREÇO\t\n");
            sb.append("===========================================================================").append("\n");
            for(ItemOsModel item : serviceOrder.getItemOsList()) {
                sb.append(item.getServiceId()).append("\t\t\t\t\t");
                sb.append(item.getServiceDescription()).append("\t\t\t\t\t");
                sb.append(item.getServiceValue()).append("\t\t\n");
            }
            sb.append("===========================================================================").append("\n");
            sb.append("SUBTOTAL").append("\t\t\t\t\t\t\t\t\t\t\t\t\t").append(serviceOrder.calculateServiceTotal()).append("\n");
            sb.append("DESCONTO").append("\t").append("(").append(serviceOrder.getDiscountValue()).append(")").append("\t\t\t\t\t\t\t\t\t\t\t").append(serviceOrder.getDiscountValue()).append("\n");
            sb.append("===========================================================================").append("\n");
            sb.append("TOTAL").append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t").append(serviceOrder.calculateTotalWithDiscount()).append("\n");
        } catch (ExceptionWash e) {
            throw new ExceptionWash("Erro ao gerar relatório: " + e.getMessage());
        }
        System.out.println(sb.toString());
    }
}