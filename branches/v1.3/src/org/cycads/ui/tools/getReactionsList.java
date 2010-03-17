/*
 * Created on 27/11/2009
 */
package org.cycads.ui.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class getReactionsList
{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Element eReaction;
		String reactionId;
		try {
			PrintStream out = new PrintStream(new FileOutputStream(args[0], false));
			db = dbf.newDocumentBuilder();
			for (int j = 1; j < args.length; j++) {
				String fileName = args[j];
				Document doc = db.parse(new File(fileName));
				NodeList nl = doc.getElementsByTagName("reaction");
				for (int i = 0; i < nl.getLength(); i++) {
					eReaction = (Element) nl.item(i);
					reactionId = eReaction.getAttribute("id");
					out.println(reactionId + "\t" + fileName);
				}
			}
			out.close();
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
