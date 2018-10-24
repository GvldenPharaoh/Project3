
public class Observation extends AbstractObservation
{
    double value;
    String stid;

    public Observation(double value, String stid)
    {
        this.value = value;
        this.stid = stid;

    }
    public Observation()
    {
        // For Webcat
    }

    public Double getValue() {
        return value;

    }

    public boolean isValid() {
        if ((value > 980) || (value < 0))
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public String getStid() {
        return stid;

    }

    public String toString() {
        {
            return stid;
        }

    }
}
