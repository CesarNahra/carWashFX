import org.example.carwashfx.models.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemOsModelTest {

    private ItemOsModel item;
    private ServiceModel service;
    private ServiceOrderModel order;

    @BeforeEach
    void setUp() {
        item = new ItemOsModel(150.0, "Lavagem detalhada no motor");

        // Mock/Simulação dos objetos relacionados
        service = new ServiceModel(1, "Lavagem Completa", 150.0, 10);
        order = new ServiceOrderModel();
        order.setOrderNumber(1001);

        item.setService(service);
        item.setServiceOrder(order);
    }

    @Test
    @DisplayName("Deve delegar corretamente a busca da descrição do serviço")
    void getServiceDescription_ShouldReturnFromService() {
        assertEquals("Lavagem Completa", item.getServiceDescription());
    }

    @Test
    @DisplayName("Deve delegar corretamente a busca do número da ordem de serviço")
    void getServiceOrderNumber_ShouldReturnFromOrder() {
        assertEquals(1001, item.getServiceOrderNumber());
    }

    @Test
    @DisplayName("Deve delegar a pontuação do serviço corretamente")
    void getServiceGivenPoints_ShouldReturnPoints() {
        assertEquals(10, item.getServiceGivenPoints());
    }

    @Test
    @DisplayName("Deve lançar NullPointerException se tentar acessar dados de um serviço nulo")
    void accessData_WithNullService_ThrowsException() {
        item.setService(null);

        // Isso garante que você saiba que o sistema quebra se o serviço não for setado
        assertThrows(NullPointerException.class, () -> {
            item.getServiceDescription();
        });
    }

    @Test
    @DisplayName("Deve manter a integridade do valor e observações do item")
    void itemFields_ShouldBeCorrect() {
        assertEquals(150.0, item.getServiceValue());
        assertEquals("Lavagem detalhada no motor", item.getObservations());
    }
}