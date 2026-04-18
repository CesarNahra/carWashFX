package org.example.carwashfx.models.dao;

import org.example.carwashfx.models.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceOrderDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertServiceOrder(ServiceOrderModel serviceOrder) {
        String query = "INSERT INTO service_order (service_order_total_value, service_order_date, service_order_discount, service_order_status, vehicle_id) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, serviceOrder.calculateServiceTotal());
            stmt.setDate(2, java.sql.Date.valueOf(serviceOrder.getOrderDate()));
            stmt.setDouble(3, serviceOrder.getDiscountValue());
            stmt.setString(4, serviceOrder.getOrderStatus().name());
            stmt.setInt(5, serviceOrder.getVehicle().getVehicleId());
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                serviceOrder.setOrderNumber(generatedId);
            }
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateServiceOrder(ServiceOrderModel serviceOrder) {
        String sql = "UPDATE service_order SET service_order_status = ? WHERE service_order_number = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, serviceOrder.getOrderStatus().name());
            stmt.setInt(2, serviceOrder.getOrderNumber());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deleteServiceOrder(ServiceOrderModel serviceOrder) {
        String query = "DELETE FROM service_order WHERE service_order_number = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, serviceOrder.getOrderNumber());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public int countServiceOrdersByStatus(EOrderStatus orderStatus) {
        String sql = "SELECT COUNT(*) FROM service_order WHERE service_order_status = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, orderStatus.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    public List<ServiceOrderModel> getAllServiceOrder() {
        String query = "SELECT so.service_order_number, so.service_order_discount, so.service_order_total_value, so.service_order_date, so.service_order_status, v.vehicle_plate from service_order so join vehicle v on so.vehicle_id = v.vehicle_id";
        List<ServiceOrderModel> serviceOrders = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceOrderModel serviceOrder = new ServiceOrderModel();

                serviceOrder.setOrderNumber(rs.getInt("service_order_number"));
                serviceOrder.setOrderDiscount(rs.getDouble("service_order_discount"));
                serviceOrder.setTotalValue(rs.getDouble("service_order_total_value"));
                serviceOrder.setOrderDate(rs.getDate("service_order_date").toLocalDate());
                serviceOrder.setOrderStatus(EOrderStatus.valueOf(rs.getString("service_order_status")));

                VehicleModel vehicle = new VehicleModel();
                vehicle.setVehiclePlate(rs.getString("vehicle_plate"));

                serviceOrder.setVehicle(vehicle);
                serviceOrders.add(serviceOrder);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return serviceOrders;
    }

    public ServiceOrderModel findServiceOrderByOrderNumber(ServiceOrderModel serviceOrder) {
        String query = "SELECT * FROM service_order WHERE service_order_number = ?";
        ServiceOrderModel rtServiceOrder = null;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, serviceOrder.getOrderNumber());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rtServiceOrder = populateServiceOrderList(rs);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rtServiceOrder;
    }

    public ServiceOrderModel populateServiceOrderList(ResultSet rs) throws SQLException {
        ServiceOrderModel serviceOrder = new ServiceOrderModel();

        serviceOrder.setOrderNumber(rs.getInt("service_order_number"));
        serviceOrder.setOrderDiscount(rs.getDouble("service_order_discount"));
        serviceOrder.setTotalValue(rs.getDouble("service_order_total_value"));
        serviceOrder.setOrderDate(rs.getDate("service_order_date").toLocalDate());
        serviceOrder.setOrderStatus(EOrderStatus.valueOf(rs.getString("service_order_status")));

        VehicleDAO vehicleDAO = new VehicleDAO();
        vehicleDAO.setConnection(connection);

        VehicleModel vehicle = new VehicleModel();
        vehicle.setVehicleId(rs.getInt("vehicle_id"));

        vehicle = vehicleDAO.findVehicleById(vehicle);

        serviceOrder.setVehicle(vehicle);

        return serviceOrder;
    }
}
