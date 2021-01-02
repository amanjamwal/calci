package calci.store;

import calci.exception.LimitReachedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperandStoreTest {

    @Test
    @DisplayName("Check empty operand store")
    void emptyOperandStore() {
        OperandStore operandStore = new OperandStore();
        assertEquals(0, operandStore.size());
        assertTrue(operandStore.getState().isEmpty());
    }

    @Test
    @DisplayName("Check adding data to operand store")
    void enterSingleValue() throws LimitReachedException {
        BigDecimal anyValue = BigDecimal.valueOf(10);
        OperandStore operandStore = new OperandStore();
        operandStore.enter(anyValue);
        assertEquals(1, operandStore.size());
    }

    @Test
    @DisplayName("Check adding data to full operand store")
    void enterValueToFullStore() throws LimitReachedException {
        OperandStore operandStore = new OperandStore(1);
        operandStore.enter(BigDecimal.valueOf(10));

        Assertions.assertThrows(LimitReachedException.class, () -> operandStore.enter(BigDecimal.valueOf(10)));
    }

    @Test
    @DisplayName("Check removing value from store")
    void removeValueFromStore() throws LimitReachedException {
        OperandStore operandStore = new OperandStore();
        operandStore.enter(BigDecimal.valueOf(10));
        assertEquals(1, operandStore.size());

        operandStore.remove();
        assertEquals(0, operandStore.size());
    }

    @Test
    @DisplayName("Check clearing values from store")
    void clear() throws LimitReachedException {
        OperandStore operandStore = new OperandStore();
        operandStore.enter(BigDecimal.valueOf(10));
        operandStore.enter(BigDecimal.valueOf(20));
        assertEquals(2, operandStore.size());

        operandStore.clear();
        assertEquals(0, operandStore.size());
    }

    @Test
    @DisplayName("Check restoring values of store")
    void restore() throws LimitReachedException {
        OperandStore operandStore = new OperandStore();
        operandStore.enter(BigDecimal.valueOf(10));
        operandStore.enter(BigDecimal.valueOf(20));
        assertEquals(2, operandStore.size());

        List<BigDecimal> valuesToRestore = new ArrayList<>();
        valuesToRestore.add(BigDecimal.ONE);
        valuesToRestore.add(BigDecimal.TEN);
        valuesToRestore.add(BigDecimal.ZERO);
        operandStore.restore(valuesToRestore);
        assertEquals(valuesToRestore.size(), operandStore.size());
    }

    @Test
    @DisplayName("Check state of store")
    void getState() throws LimitReachedException {
        List<BigDecimal> startState = new ArrayList<>();
        startState.add(BigDecimal.valueOf(10));
        startState.add(BigDecimal.valueOf(20));
        OperandStore operandStore = new OperandStore();
        for (BigDecimal value: startState) {
            operandStore.enter(value);
        }

        assertEquals(startState.size(), operandStore.size());

        List<BigDecimal> currentState = operandStore.getState();
        assertEquals(startState.size(), currentState.size());

        for (int i = 0; i < startState.size(); i++) {
            assertEquals(startState.get(i).intValue(), currentState.get(i).intValue());
        }
    }

    @Test
    @DisplayName("Check scale for values in store")
    void checkPrecisionForState() throws LimitReachedException {
        int storeDecimalScale = 2;
        List<BigDecimal> startState = new ArrayList<>();
        startState.add(BigDecimal.valueOf(10.111));
        startState.add(BigDecimal.valueOf(20.111));
        OperandStore operandStore = new OperandStore(10, storeDecimalScale);
        for (BigDecimal value: startState) {
            operandStore.enter(value);
        }

        assertEquals(startState.size(), operandStore.size());

        List<BigDecimal> currentState = operandStore.getState();
        assertEquals(startState.size(), currentState.size());

        for (int i = 0; i < startState.size(); i++) {
            assertNotEquals(startState.get(i).scale(), currentState.get(i).scale());
            assertEquals(storeDecimalScale, currentState.get(i).scale());
        }
    }

    @Test
    @DisplayName("Check to string of store")
    void testToString() throws LimitReachedException {
        List<Integer> startState = new ArrayList<>();
        startState.add(10);
        startState.add(20);
        OperandStore operandStore = new OperandStore();
        for (Integer value: startState) {
            operandStore.enter(new BigDecimal(value));
        }
        assertEquals(startState.size(), operandStore.size());

        StringBuilder expectedValue = new StringBuilder("Stack:");
        for (Integer value: startState) {
            expectedValue.append(" ").append(value);
        }
        assertEquals(expectedValue.toString(), operandStore.toString());
    }

    @Test
    @DisplayName("Check scale for viewable values")
    void testPrecisionInToString() throws LimitReachedException {
        int viewDecimalScale = 2;
        RoundingMode roundingMode = RoundingMode.HALF_EVEN;
        List<BigDecimal> startState = new ArrayList<>();
        startState.add(new BigDecimal("10.1111"));
        startState.add(new BigDecimal("20.1111"));
        OperandStore operandStore = new OperandStore(10, 10, viewDecimalScale, roundingMode);
        for (BigDecimal value: startState) {
            operandStore.enter(value);
        }
        assertEquals(startState.size(), operandStore.size());

        StringBuilder expectedValue = new StringBuilder("Stack:");
        StringBuilder originalScaledValue = new StringBuilder("Stack:");
        for (BigDecimal value: startState) {
            originalScaledValue.append(" ").append(value);
            expectedValue.append(" ").append(value.setScale(viewDecimalScale, roundingMode));
        }
        assertNotEquals(originalScaledValue.toString(), operandStore.toString());
        assertEquals(expectedValue.toString(), operandStore.toString());
    }
}
