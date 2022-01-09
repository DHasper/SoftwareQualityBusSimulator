package hanze.nl.tijdtools;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeTest {

    private static int interval = 1000;
	private static int syncInterval = 5;

    private TijdFuncties tijdFuncties;

    @BeforeEach
    void setUp() {
        tijdFuncties = new TijdFuncties();
        tijdFuncties.initSimulatorTijden(interval,syncInterval);
    }

    @Test
    void getCounterReturnsPositiveInteger() {
        int counter = tijdFuncties.getCounter();

        assertTrue(counter >= 0);
    }
}
