package com.event.manager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.event.model.Event;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class EventFilterManager {
    
    private final static String OUTPUT_CSV_REPORT_NAME = "report_final.csv";
    private final static String INPUT_CSV_REPORT_NAME = "reports.csv";
    private final static String INPUT_XML_REPORT_NAME = "reports.xml";
    private final static String INPUT_JSON_REPORT_NAME = "reports.json";

    private List<Event> listEvents;
    
    
    public EventFilterManager() {
    	this.listEvents = new ArrayList<Event>();
	}



	public static void main(String[] args) throws Exception{
    	
    	EventFilterManager eventFilterManager = new EventFilterManager();
    	
    	eventFilterManager.readAllEvents();
    	eventFilterManager.filterEvents();
    	eventFilterManager.createReport();
    	eventFilterManager.printSummary();
    	
    }
    
    public void readAllEvents() throws Exception{
    	//System.out.println("readAll");
    	
    	
    	this.listEvents.addAll(this.readFromJson());
    	this.listEvents.addAll(this.readFromXML());
    	this.listEvents.addAll(this.readFromCSV());
    	
    	//System.out.println("readAll " + this.listEvents.size());
    	
    	//return list;
    }
    
    public List<Event> readFromJson() throws Exception{
    	//System.out.println("JSON START");
    	
    	ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<Event> list;
        
        try {
			list = Arrays.asList(mapper.readValue(new File(INPUT_JSON_REPORT_NAME), Event[].class));
		} catch (Exception e) {

//			e.printStackTrace();
			System.out.println(e.getMessage());
			list = new ArrayList<Event>();
		}

        //list.stream().forEach(System.out::println);
        //System.out.println("JSON END " + list.size());
        return list;
    }
    
    public List<Event> readFromXML() throws Exception{
    	//System.out.println("XML START");
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        
        List<Event> list;
        Event[] array;
		try {
			array = xmlMapper.readValue(new File(INPUT_XML_REPORT_NAME), Event[].class);
			list = Arrays.asList(array);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
			
			list = new ArrayList<Event>();
		}
        
        //list.stream().forEach(System.out::println);
        //System.out.println("XML END " + list.size());
        return list;
    }
    
    public List<Event> readFromCSV() throws Exception{
    	//System.out.println("CSV START");
    	CsvMapper csvMapper = new CsvMapper();
    	csvMapper.registerModule(new JavaTimeModule());
        
        CsvSchema csvSchema = csvMapper.schemaFor(Event.class);
        csvSchema = csvSchema.withHeader();
        
        List<Event> list;
        MappingIterator<Event> it;
        
		try {
			it = csvMapper
					.readerFor(Event.class)
					.with(csvSchema)
					.readValues(new File(INPUT_CSV_REPORT_NAME));
			
			list = it.readAll();
		} catch (Exception e) {

//			e.printStackTrace();
			System.out.println(e.getMessage());
			list = new ArrayList<Event>();
		}
        
        //System.out.println("CSV END " + list.size());
        return list;
    }
    

	public void filterEvents() {
		
		this.listEvents = this.listEvents
								.stream()
							    .filter(e -> e.getPacketsServiced() > 0)
							    .sorted(Comparator.comparing(Event::getRequestTime))
							    .collect(Collectors.toList());
		
		//System.out.println("listFiltered " + listEvents.size());

	}
    
    
    public void createReport() throws Exception{
    	//System.out.println("createReport START");
    	
    	CsvMapper csvMapper = new CsvMapper();
    	csvMapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, false);
    	csvMapper.registerModule(new JavaTimeModule());
    	
        CsvSchema csvSchema = csvMapper
        					.schemaFor(Event.class)
        					.withHeader()
        					.withColumnSeparator(',')
        					.withoutQuoteChar();
        
        // output writer
        ObjectWriter myObjectWriter = csvMapper.writer(csvSchema);
        File tempFile = new File(OUTPUT_CSV_REPORT_NAME);
        
        FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
        OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
        myObjectWriter.writeValue(writerOutputStream, this.listEvents);
        
        System.out.println(OUTPUT_CSV_REPORT_NAME + " created");

        //System.out.println("createReport END");

    }

	public void printSummary() {
		
		Map<UUID, Long> counting = 
    			this.listEvents.stream()
    			.collect(Collectors.groupingBy(Event::getServiceGuid, Collectors.counting()));

    	System.out.printf("\n\nSummary\n\n%-40s|%5s\n", "Service GUID", "Num of Records");
    	counting.forEach((k,v)->System.out.printf("%-40s|%5s\n", k, v));
	}


	public List<Event> getListEvents() {
		return listEvents;
	}
}
