package ruleEngine;

import java.util.ArrayList;
import java.util.List;

import quickforms.dao.Database;

public abstract class RuleSet {
	
	private List<RuleAction> ruleActions;
	private String ruleSetIdentifier;
	
	public RuleSet(){
		ruleActions = new ArrayList<RuleAction>();
	}

	public RuleSet(String identifier){
		ruleSetIdentifier = identifier;
		ruleActions = new ArrayList<RuleAction>();
	}
	
	List<RuleAction> getRuleSet(){
		return ruleActions;
	}
	
	public boolean addRule(RuleAction ruleAction){
		return ruleActions.add(ruleAction);
	}
	
	public String getRuleSetIdentifier() {
		return ruleSetIdentifier;
	}
	
	public void setRuleSetIdentifier(String ruleSetIdentifier) {
		this.ruleSetIdentifier = ruleSetIdentifier;
	}

	public abstract String evaluate(FactEvent factEvent, Database db);
}