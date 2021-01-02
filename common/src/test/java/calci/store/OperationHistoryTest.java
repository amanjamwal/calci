package calci.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OperationHistoryTest {
    @Mock
    private Operation mockOperation;

    @BeforeEach
    void beforeEach() {
        mockOperation = Mockito.mock(Operation.class);
    }

    @Test
    @DisplayName("Check push operation")
    void push() {
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.push(mockOperation);
    }

    @Test
    @DisplayName("Check pop operation")
    void pop() {
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.push(mockOperation);

        Operation actualOperation = operationHistory.pop();
        assertEquals(mockOperation, actualOperation);
    }

    @Test
    @DisplayName("Check pop operation from empty store")
    void popFromEmpty() {
        OperationHistory operationHistory = new OperationHistory();
        Operation actualOperation = operationHistory.pop();
        assertNull(actualOperation);
    }
}
