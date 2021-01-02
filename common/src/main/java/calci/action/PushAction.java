package calci.action;

import calci.Action;
import calci.CalciException;
import calci.store.OperandStore;
import calci.store.Operation;
import calci.store.OperationHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class PushAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(PushAction.class);

    private final OperandStore operandStore;
    private final OperationHistory operationHistory;
    private final BigDecimal value;

    public PushAction(OperandStore operandStore, OperationHistory operationHistory, BigDecimal value) {
        this.operandStore = operandStore;
        this.operationHistory = operationHistory;
        this.value = value;
    }

    @Override
    public String getIdentifier() {
        return "push";
    }

    @Override
    public void execute() throws CalciException {
        List<BigDecimal> preState = operandStore.getState();

        log.debug("Pushing {}", value);

        operandStore.enter(value);

        operationHistory.push(new Operation(preState));
    }
}
