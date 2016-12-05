package assignment6.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class EavesdropService {
	
	public String getData(){
		return "Hello from Eavesdrop service.";	
	}
	
	public Elements getElements(String source) {
		Document doc = null;
		try {	
			doc = Jsoup.connect(source).get();
		} catch (IOException e) {
			return null;
			
		}
		if (doc != null) {
			Elements links = doc.select("body a");
		    return links;			
		}
		else {
			return null;
		}
	}
	
}
