package calci.action;

import calci.Action;
import calci.CalciException;
import calci.exception.IllegalParametersException;
import calci.exception.InsufficientParametersException;
import calci.store.OperandStore;
import calci.store.Operation;
import calci.store.OperationHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static java.math.MathContext.DECIMAL64;

public class SqrtAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SqrtAction.class);

    private final OperandStore operandStore;
    private final OperationHistory operationHistory;

    public SqrtAction(OperandStore operandStore, OperationHistory operationHistory) {
        this.operandStore = operandStore;
        this.operationHistory = operationHistory;
    }

    @Override
    public String getIdentifier() {
        return "sqrt";
    }

    @Override
    public void execute() throws CalciException {
        if (operandStore.size() < 1) {
            throw new InsufficientParametersException("Required one param");
        }
        if (operandStore.top().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalParametersException("Required non negative param");
        }

        List<BigDecimal> preState = operandStore.getState();
        BigDecimal operand = operandStore.remove();

        log.debug("Calculating square root of {}", operand);

        operandStore.enter(operand.sqrt(DECIMAL64));

        operationHistory.push(new Operation(preState));
    }
}
