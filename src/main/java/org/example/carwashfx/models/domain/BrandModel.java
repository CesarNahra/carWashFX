package org.example.carwashfx.models.domain;

public class BrandModel {
    private int brandId;
    private String brandName;

    public BrandModel() {
    }

    public BrandModel(String brandName) {
        this.brandName = brandName;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
