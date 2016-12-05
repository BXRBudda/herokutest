package assignment6.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "meeting")
@XmlAccessorType(XmlAccessType.FIELD)
public class Meeting {
	
	private int year;
	private int count;
	
	public Meeting(){
		this.year = 0;
		this.count = 0;
	}
	
	public Meeting(int y, int c){
		this.year = y;
		this.count =c;
			
	}
	
	public void setYear(int y ){
		this.year = y;
	}
	
	public int getYear(){
		return year;
		
	}
	
	
	public void setCount(int c ){
		this.count = c;
	}
	
	public int getCount(){
		return count;
		
	}
	
	

}
