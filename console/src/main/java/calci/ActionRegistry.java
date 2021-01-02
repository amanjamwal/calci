package calci;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionRegistry {
    private final Map<String, Action> actionMap;

    public ActionRegistry(List<Action> actionList) {
        this.actionMap = new HashMap<>();
        actionList.forEach(action -> actionMap.put(action.getIdentifier(), action));
    }

    public boolean contains(String actionId) {
        return actionMap.containsKey(actionId);
    }

    public Action get(String actionId) {
        return actionMap.get(actionId);
    }
}
