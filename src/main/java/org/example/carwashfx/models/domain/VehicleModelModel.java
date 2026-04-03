package org.example.carwashfx.models.domain;

public class VehicleModelModel {
    private int modelId;
    private String modelDescription;
    private BrandModel brand;
    private final EngineModel engine;
    private ECategory category;

    public VehicleModelModel(){
        this.engine = new EngineModel();
    }

    public VehicleModelModel(String modelDescription, BrandModel brand, int enginePower) {
        this.modelDescription = modelDescription;
        this.brand = brand;
        this.engine = new EngineModel(enginePower);
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public BrandModel getBrand() {
        return brand;
    }

    public void setBrand(BrandModel brand) {
        this.brand = brand;
    }

    public EngineModel getEngine() {
        return engine;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }
}
