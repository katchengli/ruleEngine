package ruleEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.Authenticator;

import org.apache.commons.mail.DefaultAuthenticator;

import quickforms.dao.Database;
import quickforms.dao.Logger;
import quickforms.dao.Pair;
import ruleEngine.FactEvent;
import ruleEngine.RuleSet;
import ruleEngine.services.NotificationService;

public class PregApp extends RuleSet {

	public PregApp() {
	}

	@Override
	public String evaluate(FactEvent factEvent, Database db){
		
		String jsonForm = "";
		
		NotificationService ns = new NotificationService();
		try {
			if(factEvent.getEventFieldValue("factTable").equals("users")){
				// Params
				String subject = "Welcome to the Francine App!";
				String message = "Welcome!\n\nYou've successfully subscribed to your new favourite pregnancy application, "
						+ "Francine! You've entered your due date as being the " + factEvent.getEventFieldValue("DueDate") 
						+ ".\n\nThe Francine App Team";
				String recipientEmail = factEvent.getEventFieldValue("Email");
				String senderEmail = "autoReplyPregApp@gmail.com";
				String appName = factEvent.getApp();
				Authenticator authenticator = new DefaultAuthenticator("autoReplyPregApp", "autoReplyPregApp1");

				// Actions
				ns.sendEmailFromGmail(subject, message, recipientEmail, senderEmail, appName, authenticator);
				jsonForm = db.putFactProcess(factEvent.getFactEventMap(), db);
			}
			
			if(factEvent.checkEventFieldPresence("unsubscribe")){
				List<String> columns = new ArrayList<String>();
				columns = Arrays.asList("UsersKey");
				List<Pair> whereClause = new ArrayList<Pair>();
				whereClause = Arrays.asList(new Pair("Email", factEvent.getEventFieldValue("Email")));
				
				List<List<String>> users = db.getFactTableColumns("users", columns, whereClause);
				
				for(List<String> i : users){
					db.updateFact(factEvent.getFactEventMap(), "pregApp", "users", i.get(0), null);
				}
				
			}

		} catch (Exception e) {
			Logger.log(factEvent.getApp(), e);
		}
		
		return jsonForm;
	}

}
