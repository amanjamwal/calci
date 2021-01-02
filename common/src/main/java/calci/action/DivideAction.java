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
import java.math.RoundingMode;
import java.util.List;

public class DivideAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(DivideAction.class);

    private final OperandStore operandStore;
    private final OperationHistory operationHistory;

    public DivideAction(OperandStore operandStore, OperationHistory operationHistory) {
        this.operandStore = operandStore;
        this.operationHistory = operationHistory;
    }

    @Override
    public String getIdentifier() {
        return "/";
    }

    @Override
    public void execute() throws CalciException {
        if (operandStore.size() < 2) throw new InsufficientParametersException("Required two params");

        List<BigDecimal> preState = operandStore.getState();
        BigDecimal secondOperand = operandStore.remove();
        BigDecimal firstOperand = operandStore.remove();

        log.debug("Dividing {} and {}", firstOperand, secondOperand);

        operandStore.enter(firstOperand.divide(secondOperand, RoundingMode.HALF_EVEN));

        operationHistory.push(new Operation(preState));
    }
}
