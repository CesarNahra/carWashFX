import org.example.carwashfx.controllers.exceptions.ExceptionWash;
import org.example.carwashfx.models.domain.ItemOsModel;
import org.example.carwashfx.models.domain.ServiceOrderModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceOrderModelTest {

    private ServiceOrderModel os;

    @BeforeEach
    void setUp() {
        os = new ServiceOrderModel();
    }

    @Test
    @DisplayName("Deve calcular o total da OS somando os itens")
    void calculateServiceTotal_Success() throws ExceptionWash {
        ItemOsModel item1 = new ItemOsModel();
        item1.setServiceValue(100.0);

        ItemOsModel item2 = new ItemOsModel();
        item2.setServiceValue(50.0);

        os.addItemOs(item1);
        os.addItemOs(item2);

        assertEquals(150.0, os.calculateServiceTotal());
    }

    @Test
    @DisplayName("Deve lançar exceção ao calcular total de uma lista vazia")
    void calculateServiceTotal_EmptyList_ThrowsException() {
        assertThrows(ExceptionWash.class, () -> {
            os.calculateServiceTotal();
        });
    }

    @Test
    @DisplayName("Deve calcular o valor final com desconto corretamente")
    void calculateServiceDiscount_Success() throws ExceptionWash {
        ItemOsModel item = new ItemOsModel();
        item.setServiceValue(200.0);
        os.addItemOs(item);

        os.setOrderDiscount(10.0);

        assertEquals(180.0, os.calculateServiceDiscount());
    }

    @Test
    @DisplayName("Deve retornar apenas o valor nominal do desconto")
    void getDiscountValue_Success() throws ExceptionWash {
        ItemOsModel item = new ItemOsModel();
        item.setServiceValue(100.0);
        os.addItemOs(item);

        os.setOrderDiscount(15.0);
        assertEquals(15.0, os.getDiscountValue());
    }

    @Test
    @DisplayName("Deve gerenciar o vínculo bidirecional ao adicionar item")
    void addItemOs_BidirectionalCheck() {
        ItemOsModel item = new ItemOsModel();

        os.addItemOs(item);

        assertTrue(os.getItemOsList().contains(item));
        assertEquals(os, item.getServiceOrder());
    }
}