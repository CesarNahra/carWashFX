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
            sb.append("============================================================\n");
            sb.append(String.format("OS Nº: %-10d DATA: %-10s STATUS: %s\n",
                    serviceOrder.getOrderNumber(),
                    formatDate.format(serviceOrder.getOrderDate()),
                    serviceOrder.getOrderStatus()
            ));
            sb.append(String.format("CLIENTE: %s\n",
                    serviceOrder.getVehicle().getClient().getClientName()));
            sb.append(String.format("VEICULO: %s\n",
                    serviceOrder.getVehicle().getVehiclePlate()));
            sb.append(String.format("MODELO : %s/%s/%s\n",
                    serviceOrder.getVehicle().getModel().getModelDescription(),
                    serviceOrder.getVehicle().getModel().getBrand().getBrandName(),
                    serviceOrder.getVehicle().getModel().getCategory()
            ));
            sb.append("------------------------------------------------------------\n");
            sb.append(String.format("%-5s %-35s %10s\n", "ID", "DESCRIÇÃO", "PREÇO"));
            sb.append("------------------------------------------------------------\n");
            for (ItemOsModel item : serviceOrder.getItemOsList()) {
                sb.append(String.format("%-5d %-35s %10.2f\n",
                        item.getServiceId(),
                        item.getServiceDescription(),
                        item.getServiceValue()
                ));
            }
            sb.append("------------------------------------------------------------\n");
            sb.append(String.format("%-45s %10.2f\n",
                    "SUBTOTAL:",
                    serviceOrder.calculateServiceTotal()
            ));
            sb.append(String.format("%-45s %10.2f\n",
                    "DESCONTO:",
                    serviceOrder.getDiscountValue()
            ));
            sb.append("------------------------------------------------------------\n");
            sb.append(String.format("%-45s %10.2f\n",
                    "TOTAL:",
                    serviceOrder.calculateTotalWithDiscount()
            ));
            sb.append("============================================================\n");
        } catch (ExceptionWash e) {
            throw new ExceptionWash("Erro ao gerar relatório: " + e.getMessage());
        }
        System.out.println(sb.toString());
    }
}