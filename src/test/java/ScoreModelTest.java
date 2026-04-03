import org.example.carwashfx.controllers.exceptions.ExceptionWash;
import org.example.carwashfx.models.domain.ScoreModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreModelTest {

    @Test
    @DisplayName("Deve adicionar quantidade com sucesso")
    void addQuantity_Success() throws ExceptionWash {
        ScoreModel score = new ScoreModel(10);
        score.addQuantity(5);
        assertEquals(15, score.getQuantity());
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar valor negativo ou zero")
    void addQuantity_NegativeValue_ThrowsException() {
        ScoreModel score = new ScoreModel(10);

        ExceptionWash exception = assertThrows(ExceptionWash.class, () -> {
            score.addQuantity(-5);
        });

        assertEquals("Quantidade a ser adicionada tem que ser, OBRIGATORIAMENTE, positiva", exception.getMessage());
    }

    @Test
    @DisplayName("Deve subtrair quantidade com sucesso")
    void subQuantity_Success() throws ExceptionWash {
        ScoreModel score = new ScoreModel(20);
        score.subQuantity(5);
        assertEquals(15, score.getQuantity());
    }

    @Test
    @DisplayName("Deve lançar exceção se a remoção exceder o saldo atual")
    void subQuantity_ExceedsBalance_ThrowsException() {
        ScoreModel score = new ScoreModel(10);

        assertThrows(ExceptionWash.class, () -> {
            score.subQuantity(15);
        });
    }

    @Test
    @DisplayName("Deve validar o formato do texto de retorno do score")
    void getScoreData_FormatCheck() {
        ScoreModel score = new ScoreModel(50);
        String expected = "Pontuação atual do cliente: 50\n";
        assertEquals(expected, score.getScoreData());
    }
}