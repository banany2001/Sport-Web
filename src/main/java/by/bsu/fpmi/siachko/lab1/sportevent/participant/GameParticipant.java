package by.bsu.fpmi.siachko.lab1.sportevent.participant;

import by.bsu.fpmi.siachko.lab1.dao.CsvIgnore;
import by.bsu.fpmi.siachko.lab1.exception.ServiceLayerException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.regex.Pattern;

@XmlRootElement
public class GameParticipant extends Participant{

    @JsonProperty("points")
    private int points;

    @JsonIgnore
    @CsvIgnore
    private Pattern pattern = Pattern.compile("(0|[1-9]\\d*)");

    public GameParticipant(String name, int points) {
        super(name);
        this.points = points;
    }

    public GameParticipant(String name, String points) throws ServiceLayerException
    {
        super(name);
        if (!pattern.matcher(points).matches()){
            throw new ServiceLayerException();
        }
        this.points = Integer.parseInt(points);
    }

    public GameParticipant()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return name + " got " + points;
    }
}
