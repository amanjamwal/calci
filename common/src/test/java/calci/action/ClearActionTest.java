package calci.action;

import calci.Action;
import calci.CalciException;
import calci.store.OperandStore;
import calci.store.OperationHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ClearActionTest {
    @Mock
    private OperandStore mockOperandStore;

    @Mock
    private OperationHistory mockOperationHistory;

    @BeforeEach
    public void setUp() {
        mockOperandStore = Mockito.mock(OperandStore.class);
        mockOperationHistory = Mockito.mock(OperationHistory.class);
    }

    @Test
    @DisplayName("Check Identifier")
    public void getIdentifier() {
        Action clearAction = new ClearAction(mockOperandStore, mockOperationHistory);
        assertEquals("clear", clearAction.getIdentifier());
    }

    @Test
    @DisplayName("Check execute for happy path")
    public void executeHappyPath() throws CalciException {
        List<BigDecimal> state = new ArrayList<>();
        state.add(new BigDecimal(10));
        state.add(new BigDecimal(20));
        Action clearAction = new ClearAction(mockOperandStore, mockOperationHistory);
        Mockito.when(mockOperandStore.getState()).thenReturn(state);

        clearAction.execute();

        verify(mockOperandStore, times(1)).clear();
        verify(mockOperationHistory, times(1)).push(any());
    }

    @Test
    @DisplayName("Check execute when store is already empty")
    public void clearEmptyStore() throws CalciException {
        Action clearAction = new ClearAction(mockOperandStore, mockOperationHistory);
        Mockito.when(mockOperandStore.getState()).thenReturn(new ArrayList<>());

        clearAction.execute();

        verify(mockOperandStore, times(0)).clear();
        verify(mockOperationHistory, times(0)).push(any());
    }
}
