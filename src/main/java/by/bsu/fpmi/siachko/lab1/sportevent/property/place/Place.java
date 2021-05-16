package by.bsu.fpmi.siachko.lab1.sportevent.property.place;

import by.bsu.fpmi.siachko.lab1.dao.CsvIgnore;
import by.bsu.fpmi.siachko.lab1.exception.ServiceLayerException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

@XmlRootElement
public class Place {

    @JsonProperty("country")
    private String country;
    @JsonProperty("region")
    private String region;
    @JsonProperty("locality")
    private String locality;
    @JsonProperty("zipCode")
    private String zipCode;
    @JsonProperty("address")
    private String address;
    @CsvIgnore
    @JsonIgnore
    private Pattern pattern = Pattern.compile("\\d{5,6}, [A-Z]([a-z]|-| )*, [A-Z]([a-z]|-| )*, [A-Z]([a-z]|-)*, (\\w|-|,| )+");

    public Place(String country, String region, String locality, String zipCode, String address) {
        this.country = country;
        this.region = region;
        this.locality = locality;
        this.zipCode = zipCode;
        this.address = address;
    }

    public Place(String place) throws ServiceLayerException
    {
        if (!pattern.matcher(place).matches()){
            throw new ServiceLayerException();
        }
        StringTokenizer stringTokenizer = new StringTokenizer(place, ", ");
        this.zipCode = stringTokenizer.nextToken();
        this.country = stringTokenizer.nextToken();
        this.region = stringTokenizer.nextToken();
        this.locality = stringTokenizer.nextToken();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stringTokenizer.nextToken());
        while (stringTokenizer.hasMoreTokens()){
            stringBuilder.append(", ");
            stringBuilder.append(stringTokenizer.nextToken());
        }
        this.address = stringBuilder.toString();
    }

    public Place()
    {

    }

    @XmlElement
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @XmlElement
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @XmlElement
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @XmlElement
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @XmlElement
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return zipCode + ", "
                + country + ", "
                + region + ", "
                + locality + ", "
                + address;
    }
}
