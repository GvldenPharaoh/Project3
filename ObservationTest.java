import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class ObservationTest
{

    @Test
    public void testIsValid() {
        Observation test = new Observation(5.0, "Bamba");
        boolean actual = test.isValid();
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void testObservation() {
        assertEquals(1, 1);

    }
    
    @Test
    public void testObservationDoubleString()
    {
        @SuppressWarnings("unused")
        double value = 5.0;
        String stid = "Bamba";
        String actual = String.format("%.1f C at %s", stid);
        String expected = "5.0 C at Bamba";
        assertEquals(expected, actual);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetValue() {
        Observation test = new Observation (5.0, "Bamba");
            double actual = test.getValue();
            double expected = 5.0;
         assertEquals(expected, actual);
    }

    @Test
    public void testGetStid() {
        Observation test = new Observation(5.0, "Bamba");
        String actual = test.getStid();
        String expected = "";
        assertEquals(expected, actual);

    }

    @Test
    public void testToString() {
        assertEquals(1, 1);
    }

}
