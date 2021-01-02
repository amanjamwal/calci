package calci.action;

import calci.Action;
import calci.store.OperandStore;
import calci.store.Operation;
import calci.store.OperationHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class ClearAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ClearAction.class);

    private final OperandStore operandStore;
    private final OperationHistory operationHistory;

    public ClearAction(OperandStore operandStore, OperationHistory operationHistory) {
        this.operandStore = operandStore;
        this.operationHistory = operationHistory;
    }

    @Override
    public String getIdentifier() {
        return "clear";
    }

    @Override
    public void execute() {
        List<BigDecimal> preState = operandStore.getState();
        if (preState.isEmpty()) {
            return;
        }

        log.debug("Clearing store");

        operandStore.clear();

        operationHistory.push(new Operation(preState));
    }
}
