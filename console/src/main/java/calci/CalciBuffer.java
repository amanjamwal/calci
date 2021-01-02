package calci;

import java.util.Enumeration;

public class CalciBuffer implements Enumeration<String> {
    private final String data;
    private int currentPosition;
    private int nextPosition;

    public CalciBuffer(String input) {
        this.data = input.toLowerCase();
        this.currentPosition = 0;
        this.nextPosition = nextNonWhiteSpacePosition(0);
    }

    private int nextNonWhiteSpacePosition(int startPosition) {
        int runnerPosition = startPosition;
        while (runnerPosition < data.length() && Character.isWhitespace(data.charAt(runnerPosition))) {
            runnerPosition++;
        }
        return runnerPosition;
    }

    @Override
    public boolean hasMoreElements() {
        return nextPosition < data.length() && data.substring(nextPosition).trim().length() > 0;
    }

    @Override
    public String nextElement() {
        int runnerPosition = nextPosition;
        while (runnerPosition < data.length() && !Character.isWhitespace(data.charAt(runnerPosition))) {
            runnerPosition++;
        }
        String element = data.substring(nextPosition, runnerPosition);
        currentPosition = nextPosition;
        nextPosition = nextNonWhiteSpacePosition(runnerPosition);
        return element;
    }

    public int getCurrentPosition() {
        return currentPosition + 1;
    }
}
