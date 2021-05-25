package by.bsu.fpmi.siachko.lab1.sportevent;

import by.bsu.fpmi.siachko.lab1.dao.CsvEnum;
import by.bsu.fpmi.siachko.lab1.dao.CsvIgnore;
import by.bsu.fpmi.siachko.lab1.sportevent.events.game.Game;
import by.bsu.fpmi.siachko.lab1.sportevent.events.match.Match;
import by.bsu.fpmi.siachko.lab1.sportevent.events.race.Race;
import by.bsu.fpmi.siachko.lab1.sportevent.property.attendance.Attendance;
import by.bsu.fpmi.siachko.lab1.sportevent.property.date.Date;
import by.bsu.fpmi.siachko.lab1.sportevent.property.place.Place;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "Event")
@XmlSeeAlso({Race.class, Match.class, Game.class})
public abstract class SportEvent {

    @JsonIgnore
    @CsvIgnore
    private static int nextUuid = 1;

    @JsonProperty("uuid")
    protected int uuid;
    @CsvEnum
    @JsonProperty("type")
    protected EventType eventType;
    @JsonProperty("date")
    protected Date date;
    @JsonProperty("place")
    protected Place place;
    @JsonProperty("attendance")
    protected Attendance attendance;

    public SportEvent(EventType eventType, Date date, Place place, Attendance attendance) {
        this.uuid = nextUuid;
        nextUuid++;
        this.eventType = eventType;
        this.date = date;
        this.place = place;
        this.attendance = attendance;
    }

    public SportEvent()
    {

    }

    public static void setNextUuid(int nextUuid) {
        SportEvent.nextUuid = nextUuid;
    }

    @XmlElement()
    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    @XmlElement()
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @XmlElement()
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @XmlElement()
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @XmlElement()
    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "Date : " + date + "\n"
                + "Place: " + place + "\n"
                + "Attendance: " + attendance + "\n";
    }
}
