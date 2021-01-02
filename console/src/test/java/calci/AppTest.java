package calci;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private final InputStream originalInputStream = System.in;
    private final PrintStream originalOutputStream = System.out;
    private final PrintStream originalErrorStream = System.err;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

    @BeforeEach
    void beforeEach() {
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errorStream));
    }

    @AfterEach
    void afterEach() {
        System.setIn(originalInputStream);
        System.setOut(originalOutputStream);
        System.setErr(originalErrorStream);
    }

    @Test
    void checkSingleParameter() {
        String inputValue = "1";
        System.setIn(toInputStream(inputValue));

        App.main(new String[]{});

        String expectedValue = "Stack: " + inputValue + System.lineSeparator();
        assertEquals(expectedValue, outputStream.toString());
    }

    @Test
    void checkMultiParameters() {
        String inputValue = "1 2 3 4";
        System.setIn(toInputStream(inputValue));

        App.main(new String[]{});

        String expectedValue = "Stack: " + inputValue + System.lineSeparator();
        assertEquals(expectedValue, outputStream.toString());
    }

    @Test
    void checkSingleOperation() {
        String inputValue = "1 2 +";
        System.setIn(toInputStream(inputValue));
        String outputValue = "3";

        App.main(new String[]{});

        String expectedValue = "Stack: " + outputValue + System.lineSeparator();
        assertEquals(expectedValue, outputStream.toString());
    }

    @Test
    void checkMultipleOperations() {
        String inputValue = "1 2 + 5 6 * -";
        System.setIn(toInputStream(inputValue));
        String outputValue = "-27";

        App.main(new String[]{});

        String expectedValue = "Stack: " + outputValue + System.lineSeparator();
        assertEquals(expectedValue, outputStream.toString());
    }

    @Test
    void checkMultipleOperationsWithUndo() {
        String inputValue = "1 2 + 5 6 * - undo";
        System.setIn(toInputStream(inputValue));
        String outputValue = "3 30";

        App.main(new String[]{});

        String expectedValue = "Stack: " + outputValue + System.lineSeparator();
        assertEquals(expectedValue, outputStream.toString());
    }

    @Test
    void checkMultipleOperationsWithClear() {
        String inputValue = "1 2 + 5 6 * - clear";
        System.setIn(toInputStream(inputValue));
        String outputValue = "";

        App.main(new String[]{});

        String expectedValue = "Stack:" + outputValue + System.lineSeparator();
        assertEquals(expectedValue, outputStream.toString());
    }

    @Test
    void checkMultipleLineOperations() {
        String inputValue = "5 6 * 1 2 + - undo" +System.lineSeparator() + " - 3 / sqrt";
        System.setIn(toInputStream(inputValue));
        String outputFirstValue = "30 3";
        String outputSecondValue = "3";

        App.main(new String[]{});

        String expectedFirstLine = "Stack: " + outputFirstValue + System.lineSeparator();
        String expectedSecondLine = "Stack: " + outputSecondValue + System.lineSeparator();
        assertEquals(expectedFirstLine + expectedSecondLine, outputStream.toString());
    }

    @Test
    void checkMultipleLineOperationsWithUndoClear() {
        String inputValue = "5 6 * 1 2 + - undo"
                + System.lineSeparator()
                + "- 3 / sqrt" +
                System.lineSeparator() +
                "clear" +
                System.lineSeparator() +
                "undo undo";
        System.setIn(toInputStream(inputValue));
        String outputFirstValue = "30 3";
        String outputSecondValue = "3";
        String outputThirdValue = "";
        String outputFourthValue = "9";

        App.main(new String[]{});

        String expectedValue = "Stack: " + outputFirstValue + System.lineSeparator() +
                "Stack: " + outputSecondValue + System.lineSeparator() +
                "Stack:" + outputThirdValue + System.lineSeparator() +
                "Stack: " + outputFourthValue + System.lineSeparator();
        assertEquals(expectedValue, outputStream.toString());
    }

    @Test
    void checkEmptyInput() {
        String inputValue = "         ";
        System.setIn(toInputStream(inputValue));
        String outputValue = "";

        App.main(new String[]{});

        String expectedValue = "Stack:" + outputValue + System.lineSeparator();
        assertEquals(expectedValue, outputStream.toString());
    }

    @Test
    void checkInsufficientParameters() {
        String inputValue = "1 + 2" + System.lineSeparator();
        System.setIn(toInputStream(inputValue));
        String expectedErrorValue = "operator + (position: 3): insufficient parameters";
        String outputValue = "1";

        App.main(new String[]{});

        String expectedValue = expectedErrorValue + System.lineSeparator() +
                "Stack: " + outputValue + System.lineSeparator() +
                "Stack: " + outputValue + System.lineSeparator();
        assertEquals(expectedValue, outputStream.toString());
    }

    @Test
    void checkInsufficientParametersForSqrt() {
        String inputValue = "1 2 + clear sqrt 1 2 +" + System.lineSeparator();
        System.setIn(toInputStream(inputValue));
        String expectedErrorValue = "operator sqrt (position: 13): insufficient parameters";
        String outputValue = "";

        App.main(new String[]{});

        String expectedValue = expectedErrorValue + System.lineSeparator() +
                "Stack:" + outputValue + System.lineSeparator() +
                "Stack:" + outputValue + System.lineSeparator();
        assertEquals(expectedValue, outputStream.toString());
    }

    private static ByteArrayInputStream toInputStream(String value) {
        return new ByteArrayInputStream((value + " exit").getBytes());
    }
}
