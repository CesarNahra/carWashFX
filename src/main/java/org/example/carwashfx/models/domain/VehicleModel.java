package org.example.carwashfx.models.domain;

public class VehicleModel {
    private int vehicleId;
    private String vehiclePlate;
    private String vehicleObservations;
    private ClientModel client;
    private VehicleModelModel model;
    private ColorModel color;

    public VehicleModel() {
    }

    public VehicleModel(String vehiclePlate, String vehicleObservations, ClientModel client, VehicleModelModel model, ColorModel color) {
        this.vehiclePlate = vehiclePlate;
        this.vehicleObservations = vehicleObservations;
        this.client = client;
        this.model = model;
        this.color = color;
    }

    public VehicleModel(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public VehicleModel(String vehiclePlate, VehicleModelModel model) {
        this.vehiclePlate = vehiclePlate;
        this.model = model;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehicleObservations() {
        return vehicleObservations;
    }

    public void setVehicleObservations(String vehicleObservations) {
        this.vehicleObservations = vehicleObservations;
    }

    public VehicleModelModel getModel() {
        return model;
    }

    public void setModel(VehicleModelModel model) {
        this.model = model;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public ColorModel getColor() {
        return color;
    }

    public void setColor(ColorModel color) {
        this.color = color;
    }

    public String getVehicleBrand() {
        return model.getBrand().getBrandName();
    }

    public String getVehicleModel() {
        return model.getModelDescription();
    }

    public String getVehicleColor() {
        return color.getColorName();
    }

    public int getVehicleEnginePower() {
        return model.getEngine().getEnginePower();
    }

    public String getVehicleOwner() {
        return client.getClientName();
    }

    public ECategory getVehicleCategory() {
        return model.getCategory();
    }

    public EGasType getVehicleGasType() {
        return model.getEngine().getGasType();
    }

    public String getVehicleData() {
        StringBuilder sb = new StringBuilder();

        sb.append("Dados do veículo: \n\n");
        sb.append("Placa: ").append(getVehiclePlate()).append("\n");
        sb.append("Proprietário: ").append(getClient().getClientName()).append("\n");
        sb.append("Observações: ").append(getVehicleObservations()).append("\n");
        sb.append("Modelo: ").append(getModel().getBrand().getBrandName()).append("\n");
        sb.append("Motor: ").append(getModel().getEngine().getEnginePower()).append("\n");
        sb.append("Cor: ").append(getColor().getColorName()).append("\n");
        sb.append("Categoria: ").append(getModel().getCategory()).append("\n");

        return sb.toString();
    }
}
