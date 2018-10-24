
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
    public void testGetTairMin() throws IOException, ParseException {
        MapData test = new MapData(2018, 8, 30, 17, 45, "data");
        Observation minTair = new Observation(20.8, "stid");
        Assert.assertEquals(minTair.toString(), test.getTairMin().toString());
    }

    @Test
    public void testGetTairMax() throws IOException, ParseException {
        MapData test = new MapData(2018, 8, 30, 17, 45, "data");
        Observation maxTair = new Observation(36.5, "stid");
        Assert.assertEquals(maxTair.toString(), test.getTairMax().toString());
    }

    @Test
    public void testGetTairAverage() throws IOException, ParseException {
        MapData test = new MapData(2018, 8, 30, 17, 45, "data");
        Observation avgTa9m = test.getTairAverage();
        double tairVal = avgTa9m.getValue();
        Assert.assertEquals(32.425, tairVal, 0.01);
    }

    @Test
    public void testGetTa9mMin() throws IOException, ParseException {
        MapData test = new MapData(2018, 8, 30, 17, 45, "data");
        Observation minTa9m = new Observation(20.8, "stid");
        Assert.assertEquals(minTa9m.toString(), test.getTa9mMin().toString());
    }

    @Test
    public void testGetTa9mMax() throws IOException, ParseException {
        MapData test = new MapData(2018, 8, 30, 17, 45, "data");
        Observation maxTa9m = new Observation(36.5, "stid");
        Assert.assertEquals(maxTa9m.toString(), test.getTa9mMax().toString());
    }

    @Test
    public void testGetTa9mAverage() throws IOException, ParseException {
        MapData test = new MapData(2018, 8, 30, 17, 45, "data");
        Observation avgTa9m = test.getTa9mAverage();
        double ta9mVal = avgTa9m.getValue();
        Assert.assertEquals(31.556, ta9mVal, 0.01);
    }

    @Test
    public void testGetSradMin() throws IOException, ParseException {
        MapData test = new MapData(2018, 8, 30, 17, 45, "data");
        Observation minSrad = new Observation(163.0, "stid");
        Assert.assertEquals(minSrad.toString(), test.getSradMin().toString());
    }

    @Test
    public void testGetSradMax() throws IOException, ParseException {
        MapData test = new MapData(2018, 8, 30, 17, 45, "data");
        Observation maxSrad = new Observation(968.0, "stid");
        Assert.assertEquals(maxSrad.toString(), test.getSradMax().toString());
    }

    @Test
    public void testGetSradAverage() throws IOException, ParseException {
        MapData temp = new MapData(2018, 8, 30, 17, 45, "data");
        Observation sradAvg = temp.getSradAverage();
        double avgSrad = sradAvg.getValue();
        Assert.assertEquals(828.135, avgSrad, 0.01);
    }

    @Test
    public void testGetSradTotal() throws IOException, ParseException {
        MapData temp = new MapData(2018, 8, 30, 17, 45, "data");
        Observation sradTot = temp.getSradTotal();
        double totSrad = sradTot.getValue();
        Assert.assertEquals(97720.0, totSrad, 0.01);
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