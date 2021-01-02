package calci.action;

import calci.Action;
import calci.CalciException;
import calci.store.OperandStore;
import calci.store.OperationHistory;
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

class PushActionTest {
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
        Action pushAction = new PushAction(mockOperandStore, mockOperationHistory, BigDecimal.valueOf(10));
        assertEquals("push", pushAction.getIdentifier());
    }

    @Test
    @DisplayName("Check execute for happy path")
    public void executeHappyPath() throws CalciException {
        BigDecimal anyValue = BigDecimal.valueOf(10);
        Action pushAction = new PushAction(mockOperandStore, mockOperationHistory, anyValue);

        pushAction.execute();

        verify(mockOperandStore, times(1)).enter(bigDecimalCaptor.capture());
        assertEquals(anyValue.intValue(), bigDecimalCaptor.getValue().intValue());

        verify(mockOperationHistory, times(1)).push(any());
    }
}
