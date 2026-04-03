package org.example.carwashfx.models.dao;

import org.example.carwashfx.models.domain.ECategory;
import org.example.carwashfx.models.domain.ServiceModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertService(ServiceModel service) {
        String query = "INSERT INTO service (service_description, service_price, service_given_points) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, service.getServiceDescription());
            stmt.setDouble(2, service.getServicePrice());
            stmt.setInt(3, service.getGivenPoints());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateService(ServiceModel service) {
        String query = "UPDATE service SET service_description = ?, service_price = ?, service_given_points = ? WHERE service_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, service.getServiceDescription());
            stmt.setDouble(2, service.getServicePrice());
            stmt.setInt(3, service.getGivenPoints());
            stmt.setInt(4, service.getServiceId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deleteService(ServiceModel service) {
        String query = "DELETE FROM service WHERE service_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, service.getServiceId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<ServiceModel> getAllServices() {
        List<ServiceModel> services = new ArrayList<>();
        String query = "SELECT * FROM service";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceModel service = populateServiceList(rs);
                services.add(service);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return services;
    }

    public ServiceModel getServiceById(ServiceModel service) {
        String query = "SELECT * FROM service WHERE service_id = ?";
        ServiceModel rtService = null;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, service.getServiceId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rtService = populateServiceList(rs);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rtService;
    }

    private ServiceModel populateServiceList(ResultSet rs) throws SQLException {
        ServiceModel service = new ServiceModel();

        service.setServiceId(rs.getInt("service_id"));
        service.setServiceDescription(rs.getString("service_description"));
        service.setServicePrice(rs.getDouble("service_price"));
        service.setGivenPoints(rs.getInt("service_given_points"));
        service.setCategory(ECategory.valueOf(rs.getString("vehicle_category_service")));
        return service;
    }
}
