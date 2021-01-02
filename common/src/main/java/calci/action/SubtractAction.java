package calci.action;

import calci.Action;
import calci.CalciException;
import calci.exception.InsufficientParametersException;
import calci.store.OperandStore;
import calci.store.Operation;
import calci.store.OperationHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class SubtractAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(SubtractAction.class);

    private final OperandStore operandStore;
    private final OperationHistory operationHistory;

    public SubtractAction(OperandStore operandStore, OperationHistory operationHistory) {
        this.operandStore = operandStore;
        this.operationHistory = operationHistory;
    }

    @Override
    public String getIdentifier() {
        return "-";
    }

    @Override
    public void execute() throws CalciException {
        if (operandStore.size() < 2) throw new InsufficientParametersException("Required two params");

        List<BigDecimal> preState = operandStore.getState();
        BigDecimal secondOperand = operandStore.remove();
        BigDecimal firstOperand = operandStore.remove();

        log.debug("Subtracting {} and {}", firstOperand, secondOperand);

        operandStore.enter(firstOperand.subtract(secondOperand));

        operationHistory.push(new Operation(preState));
    }
}
