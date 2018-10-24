
/** In Collaboration with Marcos Tavarez and Conner Buck
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
import java.util.TreeMap;

public class MapData
{
    HashMap<String, ArrayList<Observation>> dataCatalog; 
    EnumMap<StatType, TreeMap<String, Statistics>> statistics; // Max all, Min all, Avg. all, total all
    TreeMap<String, Integer> paramPositions; // Station ID and index
    
    protected  int NUMBER_OF_MISSING_OBSERVATIONS = 10;
    private Integer numberOfStations = null;

    private String TA9M = "TA9M";
    private String TAIR = "TAIR";
    private String SRAD = "SRAD";
    private String STID = "STID";

    String MESONET = "Mesonet";
   
    private String fileName;

    private GregorianCalendar utcDateTime;

    MapData(int year, int month, int day, int hour, int minute, String directory) throws IOException
    {
        sradData = new ArrayList<Observation>();
        tairData = new ArrayList<Observation>();
        ta9mData = new ArrayList<Observation>();
        this.utcDateTime = new GregorianCalendar(year, month, day, hour, minute);
        this.Directory = directory;

        // parseFile();
        fileName = createFileName(year, month, day, hour, minute, directory);

    }

    String createFileName(int year, int month, int day, int hour, int minute, String Directory) {
        return String.format("%s/%04d%02d%02d%02d%02d.mdf", Directory, year, month, day, hour, minute);
    }

    private void parseParamHeader(String inParamStr) throws IOException {

        String[] headerWithoutSpaces = inParamStr.trim().split("\\s+");

        for (int i = 0; i < headerWithoutSpaces.length; i++)
        {
            if (headerWithoutSpaces[i].equalsIgnoreCase(TAIR))
            {
                tairPosition = i;
            }
            else if (headerWithoutSpaces[i].equalsIgnoreCase(SRAD))
            {
                sradPosition = i;
            }
            else if (headerWithoutSpaces[i].equalsIgnoreCase(STID))
            {
                stidPosition = i;
            }
            else if (headerWithoutSpaces[i].equalsIgnoreCase(TA9M))
            {
                ta9mPosition = i;
            }

        }

    }
    void getIndexOf(String inParamStr) {
    }

    void parseFile() throws IOException {
        numberOfStations = 0;

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String strg = br.readLine();

        strg = br.readLine();

        strg = br.readLine();

        parseParamHeader(strg);
        strg = br.readLine();
        String[] skipSpaces = new String[24];

        // In collaboration with Jacoby Allen and Marcos Tavarez
        while (strg != null)
        {

            skipSpaces = strg.trim().split("\\s+");

            Observation sradStuff = new Observation(Double.parseDouble(skipSpaces[sradPosition]),
                    skipSpaces[stidPosition]);

            Observation tairStuff = new Observation(Double.parseDouble(skipSpaces[tairPosition]),
                    skipSpaces[stidPosition]);

            Observation ta9mStuff = new Observation(Double.parseDouble(skipSpaces[ta9mPosition]),
                    skipSpaces[stidPosition]);

            sradData.add(sradStuff);
            tairData.add(tairStuff);
            ta9mData.add(ta9mStuff);

            numberOfStations += 1;

            strg = br.readLine();
        }
        br.close();
        calculateStatistics(sradData, SRAD);
        calculateStatistics(tairData, TAIR);
        calculateStatistics(ta9mData, TA9M);
        System.out.println(ta9mData);
    }
    private void calculateAllStatistics() {
    }
    private void prepareDataCatalog() {
    }

    private void calculateStatistics(ArrayList<Observation> inData, String paramId) {
        // in collaboration with Marcos Tavarez
        String sradStidMax = "";
        double maxValue = inData.get(0).getValue();
        for (int i = 0; i < inData.size(); i++)
        {
            if (inData.get(i).getValue() > maxValue && inData.get(i).isValid())
            {
                maxValue = inData.get(i).getValue();
                sradStidMax = inData.get(i).getStid();
            }

            
        }

        String sradStidMin = "";
        double minValue = sradData.get(0).getValue();
        for (int i = 0; i < sradData.size(); i++)
        {

            if (inData.get(i).getValue() < minValue && inData.get(i).isValid())
            {
                minValue = inData.get(i).getValue();
                sradStidMin = inData.get(i).getStid();
            }
           

        }
        
        double sAverage = 0.0;
        double total = 0.0;
        int valid0 = 0;
        for (int i = 0; i < inData.size(); i++)
        {

            if (inData.get(i).isValid())
            {
                valid0++;
                total += inData.get(i).getValue();
                
            }
            sAverage = (total / valid0);
            
        }

        String tairStidMax = "";
        double tairMaxValue = inData.get(0).getValue();
        for (int i = 0; i < inData.size(); i++)
        {
            if (inData.get(i).getValue() > tairMaxValue && inData.get(i).isValid() && inData.get(i)!= null)
            {
                tairMaxValue = inData.get(i).getValue();
                tairStidMax = inData.get(i).getStid();
            }
           

        }

        String tairStidMinimum = "";
        double tairMinValue = inData.get(0).getValue();
        for (int i = 0; i < inData.size(); i++)
        {
            if (inData.get(i).getValue() < tairMinValue && inData.get(i).isValid() && inData.get(i) != null)
            {
                tairMinValue = inData.get(i).getValue();
                tairStidMinimum = inData.get(i).getStid();
            }
            
            
            
        }
       
        double tAverage = 0.0;
        double sum = 0.0;
        int valid = 0;
        for (int i = 0; i < inData.size(); i++)
        {

        if (inData.get(i).isValid())
        {
            valid++;
        sum += inData.get(i).getValue();
        
        }

        }
        tAverage = (sum / valid);
        
        String ta9mStidMinimum = "";
        double ta9mMaxValue = inData.get(0).getValue();
        for (int i = 0; i < inData.size(); i++)
        {
            if (inData.get(i).getValue() > ta9mMaxValue && inData.get(i).isValid())
            {
                ta9mMaxValue = inData.get(i).getValue();
                ta9mStidMinimum = inData.get(i).getStid();
            }
            

        }

        String ta9mStidMax = "";
        double ta9mMinValue = inData.get(0).getValue();
        for (int i = 0; i < inData.size(); i++)
        {
            if (inData.get(i).getValue() < ta9mMinValue && inData.get(i).isValid())
            {
                ta9mMinValue = inData.get(i).getValue();
                ta9mStidMax = inData.get(i).getStid();
            }
         

        }

     
        double t9Average = 0.0;
        double ta9mSum = 0.0;
        int valid1 = 0;
        for (int i = 0; i < inData.size(); i++)
        {
            
            if (inData.get(i).isValid())
            {
                
                ta9mSum += inData.get(i).getValue();
                valid1++;
            }

        }
        t9Average = (ta9mSum / valid1);
        

    
        
    if ( paramId.equalsIgnoreCase(TAIR))
    {
        tairAverage = new Statistics(tAverage, MESONET, utcDateTime, numberOfStations, StatType.AVERAGE);
        tairMin = new Statistics(tairMinValue, tairStidMinimum, utcDateTime, numberOfStations, StatType.MINIMUM);
        tairMax = new Statistics(tairMaxValue, tairStidMax, utcDateTime, numberOfStations, StatType.MAXIMUM);
    }
    else if ( paramId.equalsIgnoreCase(SRAD))
    {
        sradAverage = new Statistics(sAverage, MESONET, utcDateTime, numberOfStations, StatType.AVERAGE);
        sradMin = new Statistics(minValue, sradStidMin, utcDateTime, numberOfStations, StatType.MINIMUM);
        sradMax = new Statistics(maxValue, sradStidMax, utcDateTime, numberOfStations, StatType.MAXIMUM);
    }
    else if (paramId.equalsIgnoreCase(TA9M))
    {
        ta9mAverage = new Statistics(t9Average, MESONET, utcDateTime, numberOfStations, StatType.AVERAGE);
        ta9mMin = new Statistics(ta9mMinValue, ta9mStidMax, utcDateTime, numberOfStations, StatType.MINIMUM);
        ta9mMax = new Statistics(ta9mMaxValue, ta9mStidMinimum, utcDateTime, numberOfStations, StatType.MAXIMUM);
    }
    }

    

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
                sradAverage.getValue(), sradAverage.getStid());

    }
}
