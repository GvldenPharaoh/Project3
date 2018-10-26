
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
    private String Directory;

    MapData(int year, int month, int day, int hour, int minute, String directory) throws IOException
    {
        this.utcDateTime = new GregorianCalendar(year, month, day, hour, minute);
        this.Directory = directory;
        fileName = createFileName(year, month, day, hour, minute, directory);
    }

    String createFileName(int year, int month, int day, int hour, int minute, String Directory) {
        return String.format("%s/%04d%02d%02d%02d%02d.mdf", Directory, year, month, day, hour, minute);
    }

    private void parseParamHeader(String inParamStr) throws IOException {
        String[] headerWithoutSpaces = inParamStr.trim().split("\\s+");
        for (int i; i < headerWithoutSpaces.length; i++)
        {
            paramPositions.put(headerWithoutSpaces[i], i);
        }

    }

    public Integer getIndexOf(String inParamStr) {
        return paramPositions.get(inParamStr);
    }

    void parseFile() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String strg = br.readLine();

        strg = br.readLine();
        strg = br.readLine();

        parseParamHeader(strg);
        strg = br.readLine();
        String[] skipSpaces = new String[24];

        while (strg != null)
        {

            skipSpaces = strg.trim().split("\\s+");

            Set<String> parameterIds = paramPositions.keySet();
            for (String paramId : parameterIds)
            {
                if (paramId.equalsIgnoreCase(STID))
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
        {
            statMapTotal.put(paramId new Statistics(total,MESONET,utcDateTime,date.size(),StatType.TOTAL));
            statMapMin.put
            statMapMax.put
            statMapAverage.put
        }
        //Set data into a stat EnumMap
        statistics.put(StatType.AVERAGE,statMapAverage)
        statistics.put(StatType.MINIMUM,statMapMin)
        statistics.put(StatType.MAXIMUM,statMapMax)
        statistics.put(StatType.TOTAL,statMapTotal)


        
    }

    private void prepareDataCatalog() {
    }

    private void calculateStatistics() {
        calculateAllStatistics();
        
    }

    public Statistics getStatistics(StatType type, String paramId) {
        
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
                utcDateTime.get(Calendar.HOUR_OF_DAY), utcDateTime.get(Calendar.MINUTE), tairMax.getValue(),
                tairMax.getStid(), tairMin.getValue(), tairMin.getStid(), tairAverage.getValue(), tairAverage.getStid(),
                ta9mMax.getValue(), ta9mMax.getStid(), ta9mMin.getValue(), ta9mMin.getStid(), ta9mAverage.getValue(),
                ta9mAverage.getStid(), sradMax.getValue(), sradMax.getStid(), sradMin.getValue(), sradMin.getStid(),
                sradAverage.getValue(), sradAverage.getStid()); //get statistics(MAX) or get statistics(stid)

    }
}
