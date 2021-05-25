package by.bsu.fpmi.siachko.lab1.sportevent.participant;

import by.bsu.fpmi.siachko.lab1.dao.CsvIgnore;
import by.bsu.fpmi.siachko.lab1.exception.ServiceLayerException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.regex.Pattern;

@XmlRootElement
public class MatchParticipant extends Participant{

    @JsonProperty("result")
    private boolean result;

    @JsonIgnore
    @CsvIgnore
    Pattern pattern = Pattern.compile("(V|X)");

    public MatchParticipant(String name, boolean result) {
        super(name);
        this.result = result;
    }

    public MatchParticipant(String name, String result) throws ServiceLayerException
    {
        super(name);
        if (!pattern.matcher(result).matches()){
            throw new ServiceLayerException();
        }
        if (result.equals("V")){
            this.result = true;
        }
        else {
            this.result = false;
        }
    }

    public MatchParticipant()
    {

    }

    public String getName() {
        return name;
    }

    @XmlElement
    public boolean isResult() {
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        String ans = name;
        if (result){
            ans += " won";
        }
        else {
            ans += " lost";
        }
        return ans;
    }
}
