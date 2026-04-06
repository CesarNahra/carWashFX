package org.example.carwashfx.models.dao;

import org.example.carwashfx.models.domain.EGasType;
import org.example.carwashfx.models.domain.VehicleModelModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
        String query = "INSERT INTO vehicle_model(vehicle_model_description, vehicle_brand_id, vehicle_engine_power, vehicle_engine_gas_type, vehicle_category) VALUES (?, (SELECT max(vehicle_brand_id) FROM vehicle_brand), ?, ?, ?)";
        try {
            PreparedStatement stmt =  connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, vehicleModel.getModelDescription());
            stmt.setInt(2, vehicleModel.getBrand().getBrandId());
            stmt.setInt(3, vehicleModel.getEngine().getEnginePower());
            stmt.setString(4, vehicleModel.getEngine().getGasType().toString());
            stmt.setString(5, vehicleModel.getCategory().toString());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VehicleModelDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}
