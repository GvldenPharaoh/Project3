import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

import DateTimeComparable.DateTimeComparable;

public class Statistics extends Observation implements DateTimeComparable
{
    protected String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss z";
    protected DateTimeFormatter format;
    private GregorianCalendar utcDateTime;
    private ZonedDateTime zdtDateTime;
    private int numberOfReportingStations;
    private StatType statType;

    public Statistics(double value, String stid, GregorianCalendar dateTime, int numberOfValidStations,
            StatType inStatType)
    {
        super(value, stid);
        this.statType = inStatType;
        this.numberOfReportingStations = numberOfValidStations;
        this.utcDateTime = dateTime;
    }

    public Statistics(double value, String stid, ZonedDateTime dateTime, int numberOfValidStations, StatType inStatType)
    {
        super(value, stid);
        this.statType = inStatType;
        this.numberOfReportingStations = numberOfValidStations;
        this.zdtDateTime = dateTime;
    }

    public GregorianCalendar createDateFromString(String dateTimeStr) throws ParseException {
        SimpleDateFormat yt = new SimpleDateFormat(DATE_TIME_FORMAT);
        yt.parse(dateTimeStr);
        return (GregorianCalendar) yt.getCalendar();
    }

   public ZonedDateTime createZDateFromString(String dateTimeStr) {
        return zdtDateTime;

    }

    public String createStringFromDate(GregorianCalendar calendar) {
        SimpleDateFormat yt = new SimpleDateFormat(DATE_TIME_FORMAT);
        return yt.format(calendar);
    }

    public String createStringFromDate(ZonedDateTime calendar) {
        SimpleDateFormat yt = new SimpleDateFormat(DATE_TIME_FORMAT);
        return yt.format(calendar);
    }

    public int getNumberOfReportingStations() {
        return numberOfReportingStations;
    }

    public String getUTCDateTimeString() {
        return DATE_TIME_FORMAT;
    }

    public boolean newerThan(GregorianCalendar inDateTime) {
        return utcDateTime.after(inDateTime);
    }

    public boolean olderThan(GregorianCalendar inDateTime) {
        return utcDateTime.before(inDateTime);
    }

    public boolean sameAs(GregorianCalendar inDateTime) {
        return utcDateTime.equals(inDateTime);
    }

    public boolean newerThan(ZonedDateTime inDateTime) {
        return zdtDateTime.isAfter(inDateTime);
    }

    public boolean olderThan(ZonedDateTime inDateTime) {
        return zdtDateTime.isBefore(inDateTime);
    }

    public boolean sameAs(ZonedDateTime inDateTime) {
        return zdtDateTime.equals(inDateTime);
    }

    public String toString() {
        return super.toString();
    }

}
