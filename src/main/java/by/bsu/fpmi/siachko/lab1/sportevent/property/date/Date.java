package by.bsu.fpmi.siachko.lab1.sportevent.property.date;

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
public class Date {

    @JsonProperty("dayOfWeek")
    String dayOfWeek;
    @JsonProperty("day")
    int day;
    @JsonProperty("month")
    int month;
    @JsonProperty("year")
    int year;
    @JsonIgnore
    @CsvIgnore
    Pattern pattern = Pattern.compile("\\d{2}/(0\\d{1}|1[0-2])/\\d{1,9}");

    public Date(String dayOfWeek, int day, int month, int year) {
        this.dayOfWeek = dayOfWeek;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(String dayOfWeek, String date) throws ServiceLayerException
    {
        if (!pattern.matcher(date).matches()){
            throw new ServiceLayerException();
        }
        this.dayOfWeek = dayOfWeek;
        StringTokenizer stringTokenizer = new StringTokenizer(date, "/");
        this.day = Integer.parseInt(stringTokenizer.nextToken());
        this.month = Integer.parseInt(stringTokenizer.nextToken());
        this.year = Integer.parseInt(stringTokenizer.nextToken());
    }

    public Date()
    {

    }

    @XmlElement
    public String getDayOfWeek() {
        return dayOfWeek.toString();
    }

    @XmlElement
    public int getDay() {
        return day;
    }

    @XmlElement
    public int getMonth() {
        return month;
    }

    @XmlElement
    public int getYear() {
        return year;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return dayOfWeek + ", "
                + day + "/"
                + month + "/"
                + year;
    }
}
