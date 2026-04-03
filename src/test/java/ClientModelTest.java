import org.example.carwashfx.models.domain.ClientModel;
import org.example.carwashfx.models.domain.VehicleModel;
import org.example.carwashfx.controllers.exceptions.ExceptionWash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientStub extends ClientModel {
    public ClientStub(String name, String phone, String email, LocalDate date) {
        super(name, phone, email, date);
    }
}

class ClientModelTest {

    private ClientStub client;

    @BeforeEach
    void setUp() {
        client = new ClientStub("João Silva", "99999-9999", "joao@email.com", LocalDate.now());
    }

    @Test
    @DisplayName("Deve inicializar cliente com score zerado")
    void shouldInitializeWithZeroScore() {
        assertNotNull(client.getScore());
        assertEquals(0, client.getScore().getQuantity());
    }

    @Test
    @DisplayName("Deve adicionar veículo e estabelecer o vínculo bidirecional")
    void addVehicle_ShouldLinkCorrect() throws ExceptionWash {
        VehicleModel vehicle = new VehicleModel(); // Supondo que exista esse construtor

        client.addVehicle(vehicle);

        assertTrue(client.getVehicleList().contains(vehicle));
        assertEquals(client, vehicle.getClient(), "O veículo deve conhecer seu dono (cliente)");
    }

    @Test
    @DisplayName("Deve remover veículo e limpar o vínculo no objeto veículo")
    void removeVehicle_ShouldUnlinkCorrect() throws ExceptionWash {
        VehicleModel vehicle = new VehicleModel();
        client.addVehicle(vehicle);

        client.removeVehicle(vehicle);

        assertFalse(client.getVehicleList().contains(vehicle));
        assertNull(vehicle.getClient(), "O vínculo no veículo deve ser anulado");
    }

    @Test
    @DisplayName("Deve validar a concatenação de dados no método getData")
    void getData_CheckFormatting() {
        String data = client.getData();

        assertTrue(data.contains("João Silva"));
        assertTrue(data.contains("joao@email.com"));
        assertTrue(data.contains("Pontuação atual do cliente: 0"));
    }
}
