package assignment6;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Path("/solum")
public class HelloWorldResource {
	
	String source = "http://eavesdrop.openstack.org/meetings/solum_team_meeting/";
	
	public HelloWorldResource() {
		
	}
	
	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public String helloWorld() {
		System.out.println("Inside helloworld");
		return "Hello world ";
	}
	
	
	
	@GET
	@Path("/meetings")
	@Produces("application/xml")
	public StreamingOutput getMeetings() throws Exception {
			
			ArrayList<Integer> array = new ArrayList<Integer>();
			
			for(int i = 0; i<4;i++){
				//System.out.print(i);
				int l = i + 2013;
				array.add(getCount(source + l)); 
			}
			
			ArrayList<String> years = getYears(source);
			
			ArrayList<Meeting> list = new ArrayList<Meeting>();
			for(int i = 0; i < years.size();i++){
				int y = Integer.parseInt(years.get(i));
				int c = array.get(i);
				Meeting m = new Meeting(y,c);
				list.add(m);
			}
			final Meetings meetings = new Meetings();
			meetings.setMeetings(list);
			
			System.out.println(years);
			System.out.println(array);
				
		    return new StreamingOutput() {
		         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
		            outputCourses(outputStream, meetings);
		         }
		      };			}
	
	protected int getCount(String source ) throws Exception{
		EavesdropService service = new EavesdropService();
		Elements links = service.getElements(source);
		
		ArrayList<String> array = new ArrayList<String>(); //create new array list to hold the projects 
		int i = 5;
		//final Projects projects = new Projects();
		
		while(i < links.size()){
			Element link = links.get(i);
			
			String name = link.html();
			array.add(name);
			i++;
		}
		//System.out.println(array);
		
		return  array.size();
		
	}
	
	

	protected ArrayList<String> getYears(String source ) throws Exception{
		EavesdropService service = new EavesdropService();
		Elements links = service.getElements(source);
		
		ArrayList<String> array = new ArrayList<String>(); //create new array list to hold the projects 
		int i = 5;
		//final Projects projects = new Projects();
		
		while(i < links.size()){
			Element link = links.get(i);
			
			String name = link.html().substring(0, 4);
			array.add(name);
			i++;
		}
		
		
		return  array;
	}
	
	protected void outputCourses(OutputStream os, Meetings meetings ) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Meetings.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);	
			jaxbMarshaller.marshal(meetings, os);
			
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
	
		
	
}
