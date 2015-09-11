package ruleEngine;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.DefaultAuthenticator;
import org.junit.Ignore;
import org.junit.Test;
import org.sql2o.Sql2o;

import quickforms.dao.Database;
import quickforms.dao.Logger;
import quickforms.dao.Pair;
import ruleEngine.services.NotificationService;

public class RuleEngineTest {
    
	@Test
	@Ignore
	public void testDAOputFactProcess(){
		
		Map<String, String[]> columns = new HashMap<String, String[]>();
		columns.put("Subscribed", new String[] {"1"});
		columns.put("Email", new String[] {"testingyea@testing.org"});
		columns.put("DueDate", new String[] {"2017/12/03"});
		columns.put("factTable", new String[]{"users"});
		FactEvent factEvent = new FactEvent("pregapp", "users", columns);
		
		Sql2o sql2o = new Sql2o("jdbc:sqlserver://localhost\\DESERVICESSERVER:1433;databaseName=pregapp", "pregapp", "pregapp");
		Database db = new Database(sql2o.getDataSource());
		
		try {
			db.putFactProcess(factEvent.getFactEventMap(), db);
		} catch (Exception e) {
			Logger.log("pregapp", e);
			assertTrue( false );
		}
		assertTrue( true );
	}
	
	@Test
	public void testNotificationServiceSendEmailFromGmail(){
		NotificationService notif = new NotificationService();
		notif.sendEmailFromGmail("hola2", "mesg2", "katchengli@gmail.com", "autoReplyPregApp@gmail.com", "roger", new DefaultAuthenticator("autoReplyPregApp", "autoReplyPregApp1"));
	}
	
	@Test
	@Ignore
	public void testDAOgetFactTableColumns(){
		
		Sql2o sql2o = new Sql2o("jdbc:sqlserver://localhost\\DESERVICESSERVER:1433;databaseName=pregapp", "pregapp", "pregapp");
		Database db = new Database(sql2o.getDataSource());
		
		try {
			db.getFactTableColumns("users", new ArrayList<String>(Arrays.asList("Email")), new ArrayList<Pair>(Arrays.asList(new Pair("Subscribed", "1"))));
		} catch (Exception e) {
			Logger.log("pregapp", e);
			assertTrue( false );
		}
		assertTrue( true );
	}
	
	@Test
	public void ruleEngineTest(){
		Sql2o sql2o = new Sql2o("jdbc:sqlserver://localhost\\DESERVICESSERVER:1433;databaseName=pregapp", "pregapp", "pregapp");
		Database db = new Database(sql2o.getDataSource());
		Map<String, String[]> columns = new HashMap<String, String[]>();
		columns.put("Subscribed", new String[] {"1"});
		columns.put("Email", new String[] {"katchengli@gmail.com"});
		columns.put("DueDate", new String[] {"2017/12/04"});
		columns.put("sendWelcomeEmail", null);
		columns.put("factTable", new String[]{"users"});
		FactEvent factEvent = new FactEvent("PregApp", "users", columns);
		
		RuleEngine re = new RuleEngine();
		
		re.execute(factEvent, db);
	}

}
