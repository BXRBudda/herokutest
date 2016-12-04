package assignment6;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "meetings")
@XmlAccessorType (XmlAccessType.FIELD)
public class Meetings 
{
    @XmlElement(name = "meeting")
    private List<Meeting> meetings = null;
 
    public List<Meeting> getMeetings() {
        return meetings;
    }
 
    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}