package calci;

import calci.action.*;
import calci.exception.InsufficientParametersException;
import calci.store.OperandStore;
import calci.store.OperationHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        OperandStore operandStore = new OperandStore();
        OperationHistory operationHistory = new OperationHistory();

        List<Action> actionList = new ArrayList<>();
        actionList.add(new AddAction(operandStore, operationHistory));
        actionList.add(new SubtractAction(operandStore, operationHistory));
        actionList.add(new MultiplyAction(operandStore, operationHistory));
        actionList.add(new DivideAction(operandStore, operationHistory));
        actionList.add(new SqrtAction(operandStore, operationHistory));
        actionList.add(new ClearAction(operandStore, operationHistory));
        actionList.add(new UndoAction(operandStore, operationHistory));

        ActionRegistry actionRegistry = new ActionRegistry(actionList);

        Scanner scanner = new Scanner(System.in);
        boolean keepProcessing = true;
        while (keepProcessing) {
            String nextLine = scanner.nextLine();
            CalciBuffer calciBuffer = new CalciBuffer(nextLine);
            while(calciBuffer.hasMoreElements()) {
                String element = calciBuffer.nextElement();
                if (element.equals("exit")) {
                    keepProcessing = false;
                    break;
                }

                BigDecimal value = null;
                try {
                    value = new BigDecimal(element);
                } catch (NumberFormatException ignored) {
                }

                if (value != null) {
                    try {
                        (new PushAction(operandStore, operationHistory, value)).execute();
                    } catch (CalciException e) {
                        System.out.printf(
                                "operator %s (position: %d): unexpected state%n",
                                element,
                                calciBuffer.getCurrentPosition()
                        );
                        break;
                    } catch (Exception e) {
                        log.error("Error while pushing {} from input({})", value, nextLine);
                        break;
                    }
                } else if (actionRegistry.contains(element)) {
                    Action action = actionRegistry.get(element);
                    try {
                        action.execute();
                    } catch (InsufficientParametersException exception) {
                        System.out.printf(
                                "operator %s (position: %d): insufficient parameters%n",
                                element,
                                calciBuffer.getCurrentPosition()
                        );
                        break;
                    } catch (CalciException e) {
                        System.out.printf(
                                "operator %s (position: %d): unexpected state%n",
                                element,
                                calciBuffer.getCurrentPosition()
                        );
                        break;
                    } catch (Exception e) {
                        log.error(
                                "Error while executing input({}) action({}) position ({})",
                                nextLine,
                                action.getIdentifier(),
                                calciBuffer.getCurrentPosition()
                        );
                        break;
                    }
                } else {
                    log.error("Unexpected element({}) in input({})", element, nextLine);
                    break;
                }
            }

            System.out.println(operandStore.toString());
        }
    }
}
