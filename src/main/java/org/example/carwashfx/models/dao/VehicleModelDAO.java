package org.example.carwashfx.models.dao;

import org.example.carwashfx.models.domain.BrandModel;
import org.example.carwashfx.models.domain.ECategory;
import org.example.carwashfx.models.domain.EGasType;
import org.example.carwashfx.models.domain.VehicleModelModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleModelDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertVehicleModel(VehicleModelModel vehicleModel) {
        String query = "INSERT INTO vehicle_model(vehicle_model_description, vehicle_brand_id, vehicle_engine_power, vehicle_engine_gas_type, vehicle_category) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt =  connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, vehicleModel.getModelDescription());
            stmt.setInt(2, vehicleModel.getBrand().getBrandId());
            stmt.setInt(3, vehicleModel.getEngine().getEnginePower());
            stmt.setString(4, vehicleModel.getEngine().getGasType().name());
            stmt.setString(5, vehicleModel.getCategory().name());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                vehicleModel.setModelId(generatedId);
            }
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VehicleModelDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<VehicleModelModel> getAllVehicleModels() {
        String query = "SELECT * FROM vehicle_model";
        List<VehicleModelModel> vehicleModels = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VehicleModelModel vehicleModel = populateVehicleModelList(rs);
                vehicleModels.add(vehicleModel);
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleModelDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return vehicleModels;
    }

    public VehicleModelModel populateVehicleModelList(ResultSet rs) throws SQLException {
        VehicleModelModel vehicleModel = new VehicleModelModel();

        vehicleModel.setModelId(rs.getInt("vehicle_model_id"));
        vehicleModel.setModelDescription(rs.getString("vehicle_model_description"));

        BrandModel brand = new BrandModel();
        brand.setBrandId(rs.getInt("brand_id"));
        brand.setBrandName(rs.getString("brand_name"));

        vehicleModel.setCategory(ECategory.valueOf(rs.getString("vehicle_category")));
        vehicleModel.getEngine().setEnginePower(Integer.parseInt(rs.getString("vehicle_engine_power")));
        vehicleModel.getEngine().setGasType(EGasType.valueOf(rs.getString("vehicle_engine_gas_type")));

        vehicleModel.setBrand(brand);

        return vehicleModel;
    }
}
