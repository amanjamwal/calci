package calci.action;

import calci.Action;
import calci.CalciException;
import calci.store.OperandStore;
import calci.store.Operation;
import calci.store.OperationHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UndoActionTest {
    @Mock
    private OperandStore mockOperandStore;

    @Mock
    private OperationHistory mockOperationHistory;

    @Mock
    private Operation mockOperation;

    @BeforeEach
    public void setUp() {
        mockOperandStore = Mockito.mock(OperandStore.class);
        mockOperationHistory = Mockito.mock(OperationHistory.class);
        mockOperation = Mockito.mock(Operation.class);
    }

    @Test
    @DisplayName("Check Identifier")
    public void getIdentifier() {
        Action undoAction = new UndoAction(mockOperandStore, mockOperationHistory);
        assertEquals("undo", undoAction.getIdentifier());
    }

    @Test
    @DisplayName("Check execute for happy path")
    public void executeHappyPath() throws CalciException {
        Action undoAction = new UndoAction(mockOperandStore, mockOperationHistory);
        Mockito.when(mockOperationHistory.pop()).thenReturn(mockOperation);

        undoAction.execute();

        verify(mockOperationHistory, times(1)).pop();
        verify(mockOperandStore, times(1)).restore(any());
    }

    @Test
    @DisplayName("Check execute when store doesn't have enough arguments")
    public void executeWhenEmptyHistory() throws CalciException {
        Action undoAction = new UndoAction(mockOperandStore, mockOperationHistory);

        undoAction.execute();

        verify(mockOperationHistory, times(1)).pop();
    }
}
