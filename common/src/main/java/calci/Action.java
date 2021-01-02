package calci;

public interface Action {
    String getIdentifier();

    void execute() throws CalciException;
}
