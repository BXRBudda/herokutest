package assignment6.application;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

import assignment6.resource.Meeting;
import assignment6.resource.Meetings;
import assignment6.service.EavesdropService;

@Path("/projects")
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
			ArrayList<String> years = getYears(source);
			
			for(int i = 0; i < years.size();i++){
				//System.out.print(i);
				String l = years.get(i);
				array.add(getCount(source + l)); 
			}
			
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
		            output(outputStream, meetings);
		         }
		      };
	}
	
	protected int getCount(String source ) throws Exception{
		EavesdropService service = new EavesdropService();
		Elements links = service.getElements(source);
		//System.out.println(links);
		Set<String> set = new HashSet<String>();
		
		//ArrayList<String> array = new ArrayList<String>(); //create new array list to hold the projects 
		int i = 5;
		//final Projects projects = new Projects();
		
		while(i < links.size()){
			Element link = links.get(i);
			//System.out.println(link.html());
			String name = link.html();
			String[] words = name.split("\\.");
			//Use the substring to handle the edge case of 2 meetings in a day.
			String day = words[1].substring(0, 10);
			//System.out.println(day);
			set.add(day);
			i++;
		}
		//System.out.println(array);
		
		return  set.size();
		
	}
	
	

	protected ArrayList<String> getYears(String source ) throws Exception{
		EavesdropService service = new EavesdropService();
		Elements links = service.getElements(source);
		
		ArrayList<String> array = new ArrayList<String>(); //create new array list to hold the projects 
		int i = 5;
		//final Projects projects = new Projects();
		
		while(i < links.size()){
			Element link = links.get(i);
			//Get only year, sans the / from the directory name
			String name = link.html().substring(0, 4);
			array.add(name);
			i++;
		}
		//System.out.println(array);
		
		
		return  array;
	}
	
	protected void output(OutputStream os, Meetings meetings ) throws IOException {
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
