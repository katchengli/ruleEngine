package ruleEngine;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import quickforms.dao.Logger;

public class RuleSetsParser {

	public List<String> parse(){
		
		DocumentBuilderFactory builderFactory =
				DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			//InputStream is = new FileInputStream("C:/Users/Katherine/eclipseJEELunaWorkspace/RuleEngine/ruleEngine/src/test/java/ruleEngine/RuleSets.xml");
			
            InputStream is = new FileInputStream("webapps/scheduler/RuleSets.xml");
			Document document = builder.parse(is);
			XPath xPath =  XPathFactory.newInstance().newXPath();
			String expression = "/rulesets/appName";

			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			
			List<String> rulesets = new ArrayList<String>();
			for (int i = 0; i < nodeList.getLength(); i++) {
				rulesets.add(nodeList.item(i).getFirstChild().getNodeValue()); 
			}
			
			return rulesets;

		} catch (Exception e) {
			Logger.log("RuleSetsParser", e);  
		}
		
		return new ArrayList<String>();
	}
}
