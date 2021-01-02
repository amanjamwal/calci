package calci.action;

import calci.Action;
import calci.CalciException;
import calci.exception.InsufficientParametersException;
import calci.store.OperandStore;
import calci.store.OperationHistory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MultiplyActionTest {
    @Mock
    private OperandStore mockOperandStore;

    @Mock
    private OperationHistory mockOperationHistory;

    @Captor
    private ArgumentCaptor<BigDecimal> bigDecimalCaptor;

    @BeforeEach
    public void setUp() {
        mockOperandStore = Mockito.mock(OperandStore.class);
        mockOperationHistory = Mockito.mock(OperationHistory.class);
        bigDecimalCaptor = ArgumentCaptor.forClass(BigDecimal.class);
    }

    @Test
    @DisplayName("Check Identifier")
    public void getIdentifier() {
        Action multiplyAction = new MultiplyAction(mockOperandStore, mockOperationHistory);
        assertEquals("*", multiplyAction.getIdentifier());
    }

    @Test
    @DisplayName("Check execute for happy path")
    public void executeHappyPath() throws CalciException {
        int firstValue = 5;
        int secondValue = 10;
        Action multiplyAction = new MultiplyAction(mockOperandStore, mockOperationHistory);
        Mockito.when(mockOperandStore.size()).thenReturn(2);
        Mockito.when(mockOperandStore.remove())
                .thenReturn(new BigDecimal(secondValue))
                .thenReturn(new BigDecimal(firstValue));

        multiplyAction.execute();

        verify(mockOperandStore, times(1)).enter(bigDecimalCaptor.capture());
        assertEquals(firstValue * secondValue, bigDecimalCaptor.getValue().intValue());

        verify(mockOperationHistory, times(1)).push(any());
    }

    @Test
    @DisplayName("Check execute when store doesn't have enough arguments")
    public void executeInsufficientArguments() throws CalciException {
        Action multiplyAction = new MultiplyAction(mockOperandStore, mockOperationHistory);
        Mockito.when(mockOperandStore.size()).thenReturn(1);

        Assertions.assertThrows(InsufficientParametersException.class, multiplyAction::execute);
    }
}
