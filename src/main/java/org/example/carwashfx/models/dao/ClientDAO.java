package org.example.carwashfx.models.dao;

import org.example.carwashfx.models.domain.ClientModel;
import org.example.carwashfx.models.domain.NormalPersonModel;
import org.example.carwashfx.models.domain.JuridicalPersonModel;
import org.example.carwashfx.models.domain.ScoreModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertOwner(ClientModel client) {
        String query = "INSERT INTO client (client_name, client_email, client_phone, client_registration_date) VALUES (?, ?, ?, ?)";
        String queryNP = "INSERT INTO normal_person (client_id, client_cpf, client_birthdate) VALUES (?, ?, ?)";
        String queryJP = "INSERT INTO juridical_person (client_id, client_cnpj, client_state_registration_number) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, client.getClientName());
            stmt.setString(2, client.getClientEmail());
            stmt.setString(3, client.getClientPhone());
            stmt.setDate(4, java.sql.Date.valueOf(client.getClientRegistrationDate()));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            int generatedId = 0;
            if (rs.next()) {
                generatedId = rs.getInt(1);
                client.setClientId(generatedId);
            }
            if(client instanceof NormalPersonModel np) {
                PreparedStatement stmtNP = connection.prepareStatement(queryNP);
                stmtNP.setInt(1, generatedId);
                stmtNP.setString(2, np.getCpf());
                stmtNP.setDate(3, java.sql.Date.valueOf(np.getClientBirthDate()));
                stmtNP.executeUpdate();
            } else {
                PreparedStatement stmtJP = connection.prepareStatement(queryJP);
                stmtJP.setInt(1, generatedId);
                stmtJP.setString(2, ((JuridicalPersonModel)client).getCnpj());
                stmtJP.setString(3, ((JuridicalPersonModel)client).getStateRegistrationNumber());
                stmtJP.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateOwner(ClientModel client) {
        String query = "UPDATE client SET client_name = ?, client_phone = ?, client_email = ? WHERE client_id = ?";
        String queryJP = "UPDATE juridical_person SET client_state_registration_number = ? WHERE client_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, client.getClientName());
            stmt.setString(2, client.getClientPhone());
            stmt.setString(3, client.getClientEmail());
            stmt.setInt(4, client.getClientId());
            stmt.executeUpdate();
            if(client instanceof JuridicalPersonModel jp) {
                PreparedStatement stmtJP = connection.prepareStatement(queryJP);
                stmtJP.setString(1, jp.getStateRegistrationNumber());
                stmtJP.setInt(2, client.getClientId());
                stmtJP.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateClientScore(ClientModel client) {
        String sql = "UPDATE client SET client_score = ? WHERE client_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, client.getScore().getQuantity());
            stmt.setInt(2, client.getClientId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deleteOwner(ClientModel client) {
        String query = "DELETE FROM client WHERE client_id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, client.getClientId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<ClientModel> getAllOwners() {
        String query = "SELECT cl.client_id, cl.client_name, cl.client_email, cl.client_phone, cl.client_registration_date, cl.client_score, np.client_cpf, np.client_birthdate, jp.client_cnpj, jp.client_state_registration_number FROM client cl LEFT JOIN normal_person np on np.client_id = cl.client_id LEFT JOIN juridical_person jp on cl.client_id = jp.client_id";
        List<ClientModel> owners = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClientModel owner = populateOwnerList(rs);
                owners.add(owner);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return owners;
    }

    public ClientModel findOwnerById(ClientModel client) {
        String query = "SELECT * FROM client WHERE client_id = ?";
        ClientModel rtOwner = null;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, client.getClientId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rtOwner = populateOwnerList(rs);
            }
        } catch (SQLException e)  {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rtOwner;
    }

    public ClientModel populateOwnerList(ResultSet rs) throws SQLException {
        ClientModel client;

        if (rs.getString("client_cnpj") == null || rs.getString("client_cnpj").length() <= 0) {
            NormalPersonModel np = new NormalPersonModel();
            np.setCpf(rs.getString("client_cpf"));
            np.setClientBirthDate(Optional.ofNullable(rs.getDate("client_birthdate")).map(java.sql.Date::toLocalDate).orElse(null));
            client = np;
        } else {
            JuridicalPersonModel jp = new JuridicalPersonModel();
            jp.setCnpj(rs.getString("client_cnpj"));
            jp.setStateRegistrationNumber(rs.getString("client_state_registration_number"));
            client = jp;
        }

        client.setClientId(rs.getInt("client_id"));
        client.setClientName(rs.getString("client_name"));
        client.setClientEmail(rs.getString("client_email"));
        client.setClientPhone(rs.getString("client_phone"));
        client.setClientRegistrationDate(rs.getDate("client_registration_date").toLocalDate());
        client.getScore().setQuantity(rs.getInt("client_score"));

        return client;
    }
}
