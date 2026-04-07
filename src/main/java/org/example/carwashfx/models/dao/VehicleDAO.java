package org.example.carwashfx.models.dao;

import javafx.scene.paint.Color;
import org.example.carwashfx.models.domain.*;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertVehicle(VehicleModel vehicle) {
        String query = "INSERT INTO vehicle (vehicle_plate, vehicle_observations, client_id, vehicle_model_id, vehicle_color_id) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, vehicle.getVehiclePlate());
            stmt.setString(2, vehicle.getVehicleObservations());
            stmt.setInt(3, vehicle.getClient().getClientId());
            stmt.setInt(4, vehicle.getModel().getModelId());
            stmt.setInt(5, vehicle.getColor().getColorId());
            System.out.println("DEBUG IDs -> Cliente: " + vehicle.getClient().getClientId() +
                    " | Modelo: " + vehicle.getModel().getModelId() +
                    " | Cor: " + vehicle.getColor().getColorId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VehicleDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateVehicle(VehicleModel vehicle) {
        String query = "UPDATE vehicle SET vehicle_plate = ?, vehicle_observations = ? WHERE vehicle_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, vehicle.getVehiclePlate());
            stmt.setString(2, vehicle.getVehicleObservations());
            stmt.setInt(3, vehicle.getVehicleId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VehicleDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deleteVehicle(VehicleModel vehicle) {
        String query = "DELETE FROM vehicle WHERE vehicle_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, vehicle.getVehicleId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VehicleDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<VehicleModel> getAllVehicles() {
        String query = "select v.vehicle_id, v.vehicle_plate, v.vehicle_observations, cl.client_id, cl.client_name, vm.vehicle_model_id, vm.vehicle_model_description, vm.vehicle_category, vm.vehicle_engine_power, vm.vehicle_engine_gas_type, vb.vehicle_brand_id, vb.vehicle_brand_name, vc.vehicle_color_id, vc.vehicle_color_name from vehicle_model vm join vehicle_brand vb on vm.vehicle_brand_id = vb.vehicle_brand_id join vehicle v on v.vehicle_model_id = vm.vehicle_model_id join vehicle_color vc on v.vehicle_color_id = vc.vehicle_color_id join client cl on v.client_id = cl.client_id";
        List<VehicleModel> vehicles = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VehicleModel vehicle = populateVehicleList(rs);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return vehicles;
    }

    public VehicleModel findVehicleById(VehicleModel vehicle) {
        String query = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        VehicleModel rtVehicle = new VehicleModel();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, vehicle.getVehicleId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rtVehicle = populateVehicleList(rs);
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rtVehicle;
    }

    private VehicleModel populateVehicleList(ResultSet rs) throws SQLException {
        VehicleModel vehicle = new VehicleModel();

        vehicle.setVehicleId(rs.getInt("vehicle_id"));
        vehicle.setVehiclePlate(rs.getString("vehicle_plate"));
        vehicle.setVehicleObservations(rs.getString("vehicle_observations"));

        BrandModel brand = new BrandModel();
        brand.setBrandId(rs.getInt("vehicle_brand_id"));
        brand.setBrandName(rs.getString("vehicle_brand_name"));

        VehicleModelModel vehicleModel = new VehicleModelModel();
        vehicleModel.setModelId(rs.getInt("vehicle_model_id"));
        vehicleModel.setModelDescription(rs.getString("vehicle_model_description"));
        vehicleModel.setCategory(ECategory.valueOf(rs.getString("vehicle_category")));

        vehicleModel.getEngine().setEnginePower(rs.getInt("vehicle_engine_power"));
        vehicleModel.getEngine().setGasType(EGasType.valueOf(rs.getString("vehicle_engine_gas_type")));

        ColorModel color = new ColorModel();
        color.setColorId(rs.getInt("vehicle_color_id"));
        color.setColorName(rs.getString("vehicle_color_name"));

        vehicleModel.setBrand(brand);

        vehicle.setModel(vehicleModel);
        vehicle.setColor(color);

        ClientModel client = new NormalPersonModel();

        client.setClientId(rs.getInt("client_id"));
        client.setClientName(rs.getString("client_name"));

        vehicle.setClient(client);
        client.addVehicle(vehicle);

        return vehicle;
    }
}
