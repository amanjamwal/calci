package calci.store;

import java.math.BigDecimal;
import java.util.List;

public class Operation {
    private final List<BigDecimal> state;

    public Operation(List<BigDecimal> state) {
        this.state = state;
    }

    public List<BigDecimal> getState() {
        return this.state;
    }
}
