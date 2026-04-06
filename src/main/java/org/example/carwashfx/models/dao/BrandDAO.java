package org.example.carwashfx.models.dao;

import org.example.carwashfx.models.domain.BrandModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrandDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertBrand (BrandModel brand) {
        String query = "INSERT INTO vehicle_brand (vehicle_brand_name) VALUES (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, brand.getBrandName());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(BrandDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateBrand (BrandModel brand) {
        String query = "UPDATE vehicle_brand SET vehicle_brand_name = ? WHERE vehicle_brand_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, brand.getBrandId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(BrandDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deleteBrand (BrandModel brand) {
        String query = "DELETE FROM vehicle_brand WHERE vehicle_brand_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, brand.getBrandId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(BrandDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<BrandModel> getAllBrands() {
        String query = "SELECT * FROM vehicle_brand";
        List<BrandModel> brands = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BrandModel brand = populateBrandList(rs);
                brands.add(brand);
            }
        } catch (SQLException e) {
            Logger.getLogger(BrandDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return brands;
    }

    public BrandModel findBrandById (BrandModel brand) {
        String query = "SELECT * FROM vehicle_brand WHERE vehicle_brand_id = ?";
        BrandModel rtBrand = new BrandModel();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, brand.getBrandId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rtBrand = populateBrandList(rs);
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rtBrand;
    }

    public BrandModel populateBrandList(ResultSet rs) throws SQLException {
        BrandModel brand = new BrandModel();

        brand.setBrandId(rs.getInt("vehicle_brand_id"));
        brand.setBrandName(rs.getString("vehicle_brand_name"));
        return brand;
    }
}
