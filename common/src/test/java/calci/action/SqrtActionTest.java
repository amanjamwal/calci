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

class SqrtActionTest {
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
        Action sqrtAction = new SqrtAction(mockOperandStore, mockOperationHistory);
        assertEquals("sqrt", sqrtAction.getIdentifier());
    }

    @Test
    @DisplayName("Check execute for happy path")
    public void executeHappyPath() throws CalciException {
        int value = 5;
        Action sqrtAction = new SqrtAction(mockOperandStore, mockOperationHistory);
        Mockito.when(mockOperandStore.size()).thenReturn(1);
        Mockito.when(mockOperandStore.top())
                .thenReturn(new BigDecimal(value * value));
        Mockito.when(mockOperandStore.remove())
                .thenReturn(new BigDecimal(value * value));

        sqrtAction.execute();

        verify(mockOperandStore, times(1)).enter(bigDecimalCaptor.capture());
        assertEquals(value, bigDecimalCaptor.getValue().intValue());

        verify(mockOperationHistory, times(1)).push(any());
    }

    @Test
    @DisplayName("Check execute when store doesn't have enough arguments")
    public void executeInsufficientArguments() throws CalciException {
        Action sqrtAction = new SqrtAction(mockOperandStore, mockOperationHistory);
        Mockito.when(mockOperandStore.size()).thenReturn(0);

        Assertions.assertThrows(InsufficientParametersException.class, sqrtAction::execute);
    }
}
