
import java.io.IOException;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class MapDataTest
{

    @Test
    public void testCreateFileName() throws IOException, ParseException {
        MapData test = new MapData(2018, 8, 30, 17, 45, "data");
        String expected = "data/201808301745.mdf";
        String output = test.createFileName(2018, 8, 30, 17, 45, "data");

        boolean valid = expected.equals(output);
        Assert.assertTrue(valid);
    }

    @Test
    public void testToString() throws IOException, ParseException {
        Observation temp = new Observation(20.4, "stid");
        String output = temp.getStid();
        String expected = "stid";
        boolean equal = expected.equals(output);
        Assert.assertTrue(equal);
    }

}