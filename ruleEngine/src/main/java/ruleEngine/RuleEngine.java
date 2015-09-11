package ruleEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import quickforms.dao.Database;
import quickforms.dao.Logger;

public class RuleEngine {

	private Map<String, RuleSet> ruleSets;

	public RuleEngine(){
		ruleSets = new HashMap<String, RuleSet>();
		readRuleSetsXML(ruleSets);
	}

	private void readRuleSetsXML(Map<String, RuleSet> ruleSets2) {
		RuleSetsParser ruleParser = new RuleSetsParser();
		List<String> rulesetsList = ruleParser.parse();

		try{
			for(int i=0; i< rulesetsList.size(); i++){
				//Class<?> theClass = Class.forName("ruleEngine." + rulesetsList.get(i)); // reflection
				Class<?> theClass = Class.forName("rules." + rulesetsList.get(i)); // reflection
				RuleSet ruleSetClass = (RuleSet)theClass.newInstance();
				ruleSetClass.setRuleSetIdentifier(rulesetsList.get(i));

				ruleSets.put(rulesetsList.get(i), ruleSetClass);
			}
		}catch(Exception e){
			Logger.log("RuleEngineRuleSetsXMLParsing", e);
		}

	}

	public String execute(FactEvent factEvent, Database db) {
		String json = "";
		Logger.log("RuleEngineRuleSetsXMLParsing", factEvent.app);
		Logger.log("RuleEngineRuleSetsXMLParsing", ruleSets.keySet().toString());
		Logger.log("RuleEngineRuleSetsXMLParsing", factEvent.getFactEventMap().toString());
		if(ruleSets.containsKey(factEvent.app)){
			json = ruleSets.get(factEvent.app).evaluate(factEvent, db);
		}else{
			try {
				json = db.putFactProcess(factEvent.getFactEventMap(), db);
			} catch (Exception e) {
				Logger.log(factEvent.app, e);
			}
		}

		return json;
	}

	public void addRuleSet(RuleSet ruleSet){
		ruleSets.put(ruleSet.getRuleSetIdentifier(), ruleSet);
	}
}
