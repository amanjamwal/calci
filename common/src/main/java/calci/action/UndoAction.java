package calci.action;

import calci.Action;
import calci.store.OperandStore;
import calci.store.Operation;
import calci.store.OperationHistory;

public class UndoAction implements Action {
    private final OperandStore operandStore;
    private final OperationHistory operationHistory;

    public UndoAction(OperandStore operandStore, OperationHistory operationHistory) {
        this.operandStore = operandStore;
        this.operationHistory = operationHistory;
    }

    @Override
    public String getIdentifier() {
        return "undo";
    }

    @Override
    public void execute() {
        Operation operation = operationHistory.pop();
        if (operation == null) {
            return;
        }

        operandStore.restore(operation.getState());
    }
}
