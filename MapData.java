
/** In Collaboration with Marcos Tavarez and Connor Buck
 * 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class MapData
{
    HashMap<String, ArrayList<Observation>> dataCatalog = new HashMap<String, ArrayList<Observation>>();
    EnumMap<StatType, TreeMap<String, Statistics>> statistics = new EnumMap<StatType, TreeMap<String, Statistics>>(
            StatType.class); // Max all, Min all, Avg. all, total all
    TreeMap<String, Integer> paramPositions = new TreeMap<String, Integer>(); // Station ID and index

    protected int NUMBER_OF_MISSING_OBSERVATIONS = 10;
    private Integer numberOfStations = null;
    private String TA9M = "TA9M";
    private String TAIR = "TAIR";
    private String SRAD = "SRAD";
    private String STID = "STID";
    String MESONET = "Mesonet";
    private String fileName;
    private GregorianCalendar utcDateTime;
    private String directory;

    MapData(int year, int month, int day, int hour, int minute, String directory) throws IOException
    {
        this.utcDateTime = new GregorianCalendar(year, month, day, hour, minute);
        this.directory = directory;
        fileName = createFileName(year, month, day, hour, minute, directory);
    }

    String createFileName(int year, int month, int day, int hour, int minute, String directory) {
        return String.format("%s/%04d%02d%02d%02d%02d.mdf", directory, year, month, day, hour, minute);
    }

    private void parseParamHeader(String inParamStr) throws IOException {

        String[] headerWithoutSpaces = inParamStr.trim().split("\\s+");
        for (int i = 0; i < headerWithoutSpaces.length; i++)
        {
            paramPositions.put(headerWithoutSpaces[i], i);
        }

    }

    public Integer getIndexOf(String inParamStr) {
        return paramPositions.get(inParamStr);
    }

    public String getDirectory() {
        return directory;
    }

    void parseFile() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String strg = br.readLine();

        strg = br.readLine();
        strg = br.readLine();

        parseParamHeader(strg);
        prepareDataCatalog();
        strg = br.readLine();

        while (strg != null)
        {
            String[] skipSpaces = new String[24];
            skipSpaces = strg.trim().split("\\s+");
            Set<String> parameterIds = paramPositions.keySet();
            for (String paramId : parameterIds)
            {
                if (!paramId.equalsIgnoreCase(STID))
                {
                    dataCatalog.get(paramId).add(new Observation(Double.parseDouble(skipSpaces[getIndexOf(paramId)]),
                            skipSpaces[getIndexOf(STID)]));
                }

            }

            strg = br.readLine();
        }
        br.close();
        calculateStatistics();

    }

    private void calculateAllStatistics() {
        TreeMap<String, Statistics> StatMapTotal = new TreeMap<String, Statistics>();
        TreeMap<String, Statistics> StatMapMin = new TreeMap<String, Statistics>();
        TreeMap<String, Statistics> StatMapMax = new TreeMap<String, Statistics>();
        TreeMap<String, Statistics> StatMapAverage = new TreeMap<String, Statistics>();

        Set<String> parameterIds = dataCatalog.keySet();
        for (String paramId : parameterIds)
        {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            String maxStid = "";
            String minStid = "";
            double total = 0;
            double average = 0.0;
            numberOfStations = 0;

            ArrayList<Observation> data = dataCatalog.get(paramId);

            for (Observation obs : data)
            {

                if (obs.isValid() && obs.getValue() > max)
                {
                    max = obs.getValue();
                    maxStid = obs.getStid();
                }
                if (obs.isValid() && obs.getValue() < min)
                {
                    min = obs.getValue();
                    minStid = obs.getStid();
                }
                if (obs.isValid())
                {
                    total += obs.getValue();
                    numberOfStations ++;
                }

            }
            average = (total / numberOfStations);

            StatMapTotal.put(paramId, new Statistics(total, MESONET, utcDateTime, data.size(), StatType.TOTAL));
            StatMapMin.put(paramId, new Statistics(min, minStid, utcDateTime, data.size(), StatType.MINIMUM));
            StatMapMax.put(paramId, new Statistics(max, maxStid, utcDateTime, data.size(), StatType.MAXIMUM));
            StatMapAverage.put(paramId, new Statistics(average, MESONET, utcDateTime, data.size(), StatType.AVERAGE));

        }
        statistics.put(StatType.TOTAL, StatMapTotal);
        statistics.put(StatType.MINIMUM, StatMapMin);
        statistics.put(StatType.MAXIMUM, StatMapMax);
        statistics.put(StatType.AVERAGE, StatMapAverage);

    }

    private void prepareDataCatalog() {
        Set<String> parameterIds = paramPositions.keySet();
        for (String paramId : parameterIds)
        {
            dataCatalog.put(paramId, new ArrayList<Observation>());
        }
    }

    private void calculateStatistics() {
        calculateAllStatistics();

    }

    public Statistics getStatistics(StatType type, String paramId) {
        return statistics.get(type).get(paramId);
    }

    public String toString() {

        return String.format("========================================================\n"
                + "=== %4d-%02d-%02d %02d:%02d ===\n" + "========================================================\n"
                + "Maximum Air Temperature[1.5m] = %.1f C at %s\n" + "Minimum Air Temperature[1.5m] = %.1f C at %s\n"
                + "Average Air Temperature[1.5m] = %.1f C at %s\n"
                + "========================================================\n"
                + "========================================================\n"
                + "Maximum Air Temperature[9.0m] = %.1f C at %s\n" + "Minimum Air Temperature[9.0m] = %.1f C at %s\n"
                + "Average Air Temperature[9.0m] = %.1f C at %s\n"
                + "========================================================\n"
                + "========================================================\n"
                + "Maximum Solar Radiation[1.5m] = %.1f W/m^2 at %s\n"
                + "Minimum Solar Radiation[1.5m] = %.1f W/m^2 at %s\n"
                + "Average Solar Radiation[1.5m] = %.1f W/m^2 at %s\n"
                + "========================================================", utcDateTime.get(Calendar.YEAR),
                utcDateTime.get(Calendar.MONTH), utcDateTime.get(Calendar.DAY_OF_MONTH),
                utcDateTime.get(Calendar.HOUR_OF_DAY), utcDateTime.get(Calendar.MINUTE),
                getStatistics(StatType.MAXIMUM, TAIR).getValue(), getStatistics(StatType.MAXIMUM, TAIR).getStid(),
                getStatistics(StatType.MINIMUM, TAIR).getValue(), getStatistics(StatType.MINIMUM, TAIR).getStid(),
                getStatistics(StatType.AVERAGE, TAIR).getValue(), getStatistics(StatType.AVERAGE, TAIR).getStid(),
                getStatistics(StatType.MAXIMUM, TA9M).getValue(), getStatistics(StatType.MAXIMUM, TA9M).getStid(),
                getStatistics(StatType.MINIMUM, TA9M).getValue(), getStatistics(StatType.MINIMUM, TA9M).getStid(),
                getStatistics(StatType.AVERAGE, TA9M).getValue(), getStatistics(StatType.AVERAGE, TA9M).getStid(),
                getStatistics(StatType.MAXIMUM, SRAD).getValue(), getStatistics(StatType.MAXIMUM, SRAD).getStid(),
                getStatistics(StatType.MINIMUM, SRAD).getValue(), getStatistics(StatType.MINIMUM, SRAD).getStid(),
                getStatistics(StatType.AVERAGE, SRAD).getValue(), getStatistics(StatType.AVERAGE, SRAD).getStid());

    }
}
