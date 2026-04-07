package org.example.carwashfx.models.domain;

public class ColorModel {
    private int colorId;
    private String colorName;

    public ColorModel() {
    }

    public ColorModel(String colorName) {
        this.colorName = colorName;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return colorName;
    }
}
