package calci.store;

import java.util.Stack;

public class OperationHistory {
    private final Stack<Operation> operationStack;

    public OperationHistory() {
        this.operationStack = new Stack<>();
    }

    public void push(Operation operation) {
        operationStack.push(operation);
    }

    public Operation pop() {
        if (operationStack.empty()) {
            return null;
        }
        return operationStack.pop();
    }
}
