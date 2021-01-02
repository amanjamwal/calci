package calci.store;

import calci.exception.LimitReachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OperandStore {
    private final static Logger log = LoggerFactory.getLogger(OperandStore.class);

    private final static int STORE_DECIMAL_PRECISION = 15;
    private final static int VIEW_DECIMAL_PRECISION = 10;
    private final static RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    private final int maxLimit;
    private final int storeDecimalScale;
    private final int viewDecimalScale;
    private final RoundingMode roundingMode;
    private final Stack<BigDecimal> operandStack;

    public OperandStore() {
        this(Integer.MAX_VALUE, STORE_DECIMAL_PRECISION, VIEW_DECIMAL_PRECISION, ROUNDING_MODE);
    }

    public OperandStore(int maxLimit) {
        this(maxLimit, STORE_DECIMAL_PRECISION, VIEW_DECIMAL_PRECISION, ROUNDING_MODE);
    }

    public OperandStore(int maxLimit, int storeDecimalScale) {
        this(maxLimit, storeDecimalScale, VIEW_DECIMAL_PRECISION, ROUNDING_MODE);
    }

    public OperandStore(int maxLimit, int storeDecimalScale, int viewDecimalScale) {
        this(maxLimit, storeDecimalScale, viewDecimalScale, ROUNDING_MODE);
    }

    public OperandStore(int maxLimit, int storeDecimalScale, int viewDecimalScale, RoundingMode roundingMode) {
        this.operandStack = new Stack<>();
        this.maxLimit = maxLimit;
        this.storeDecimalScale = storeDecimalScale;
        this.viewDecimalScale = viewDecimalScale;
        this.roundingMode = roundingMode;
    }

    public int size() {
        return operandStack.size();
    }

    public void enter(BigDecimal value) throws LimitReachedException {
        if (operandStack.size() >= maxLimit) {
            log.warn("Reached store limit {}", maxLimit);
            throw new LimitReachedException("Store limit exceeded");
        }
        BigDecimal valueToBeStored = value.setScale(storeDecimalScale, roundingMode);
        operandStack.push(valueToBeStored);
    }

    public BigDecimal top() {
        return operandStack.peek();
    }

    public BigDecimal remove() {
        return operandStack.pop();
    }

    public void clear() {
        operandStack.clear();
    }

    public void restore(List<BigDecimal> state) {
        operandStack.clear();
        state.forEach(operandStack::push);
    }

    public List<BigDecimal> getState() {
        return new ArrayList<>(operandStack);
    }

    @Override
    public String toString() {
        List<BigDecimal> values = getState();

        StringBuilder sb = new StringBuilder();
        sb.append("Stack:");
        for (BigDecimal value : values) {
            BigDecimal valueToBeViewed = value.setScale(viewDecimalScale, roundingMode);
            sb.append(" ").append(valueToBeViewed.stripTrailingZeros().toPlainString());
        }
        return sb.toString();
    }
}
