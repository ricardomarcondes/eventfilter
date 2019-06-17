package com.event.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
	private static final ZoneId systemDefault = ZoneId.systemDefault();

	
	@Override
	public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
	

			//Case for JSON Dates
			Long dateTime = p.getValueAsLong();
			if(null != dateTime && dateTime > 0) {
				Date date = new Date(dateTime);		
				
			    return ZonedDateTime.ofInstant(date.toInstant(), systemDefault);
			}else {//Case for XML or CSV Dates
				ZonedDateTime zonedDateTime = ZonedDateTime.parse(p.getText(), dateTimeFormatter);
				
			    return zonedDateTime;
			}
	
	}
	

}
