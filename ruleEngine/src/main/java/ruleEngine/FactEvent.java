package ruleEngine;

import java.util.HashMap;
import java.util.Map;

public class FactEvent {
	String app;

	String factTable;
	Map<String, String[]> eventFields;
	
	public FactEvent(String app, String factTable, Map<String, String[]> eventFields){
		this.app = app;
		this.factTable = factTable;
		this.eventFields = new HashMap<String, String[]>(eventFields);
	}
	
	public Map<String, String[]> getFactEventMap(){
		Map<String, String[]> factEventMap = new HashMap<String, String[]>(eventFields);
		factEventMap.put("app", new String[] {app});
		factEventMap.put("factTable", new String[] {factTable});
		
		return factEventMap;
	}
	
	public String getEventFieldValue(String fieldName) throws Exception{
		String value = null;
		
		if(eventFields.containsKey(fieldName)){
			if(eventFields.get(fieldName).length > 0){
				value = eventFields.get(fieldName)[0];
			}else{
				throw new Exception("No value associated to this column but the column " + fieldName + "exists");
			}
		}else{
			throw new Exception("The column " + fieldName + " does not exist in this FactEvent");
		}
		return value;
	}
	
	public boolean checkEventFieldPresence(String eventFieldName){
		return eventFields.containsKey(eventFieldName);
	}
	
	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	
}
