package nl.mikevanmil.workshopmadbaas.Model;

public class PhotoEntity
{
    private long uploaded;
    private String url;
    private double latitude;
    private double longitude;

    public PhotoEntity()
    {
    }

    public PhotoEntity( long uploaded, String url, double latitude, double longitude)
    {
        this.uploaded = uploaded;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getUploaded()
    {
        return uploaded;
    }

    public void setUploaded( long uploaded )
    {
        this.uploaded = uploaded;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
