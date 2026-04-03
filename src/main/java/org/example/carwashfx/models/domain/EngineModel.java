package org.example.carwashfx.models.domain;

public class EngineModel {
    private int enginePower;
    private EGasType gasType;

    public EngineModel() {

    }

    public EngineModel(int enginePower) {
        this.enginePower = enginePower;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public EGasType getGasType() {
        return gasType;
    }

    public void setGasType(EGasType gasType) {
        this.gasType = gasType;
    }
}

