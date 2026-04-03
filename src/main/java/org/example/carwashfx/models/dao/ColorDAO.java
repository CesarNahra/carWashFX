package org.example.carwashfx.models.dao;

import org.example.carwashfx.models.domain.ColorModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColorDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertColor (ColorModel color) {
        String query = "INSERT INTO vehicle_color (vehicle_color_name) VALUES (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, color.getColorName());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ColorDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateColor (ColorModel color) {
        String query = "UPDATE vehicle_color SET vehicle_color_name = ? WHERE vehicle_color_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, color.getColorId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ColorDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deleteColor (ColorModel color) {
        String query = "DELETE FROM vehicle_color WHERE vehicle_color_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, color.getColorId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ColorDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<ColorModel> getAllBrands() {
        String query = "SELECT * FROM vehicle_color";
        List<ColorModel> colors = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ColorModel color = populateColorList(rs);
                colors.add(color);
            }
        } catch (SQLException e) {
            Logger.getLogger(ColorDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return colors;
    }

    public ColorModel findBrandById (ColorModel color) {
        String query = "SELECT * FROM vehicle_color WHERE vehicle_color_id = ?";
        ColorModel rtColor = new ColorModel();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, color.getColorId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rtColor = populateColorList(rs);
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rtColor;
    }

    public ColorModel populateColorList(ResultSet rs) throws SQLException {
        ColorModel color = new ColorModel();

        color.setColorId(rs.getInt("vehicle_color_id"));
        color.setColorName(rs.getString("vehicle_color_name"));

        return color;
    }
}
