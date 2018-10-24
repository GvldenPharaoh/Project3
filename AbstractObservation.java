
public abstract class AbstractObservation
{
    protected boolean valid;

    public AbstractObservation() {
        valid = false;
    }
    
    abstract public boolean isValid();
    
}
