package introsde.rest.ehealth.client;
import introsde.rest.ehealth.*;
import introsde.rest.ehealth.model.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

public class ClientParser{

    Document doc;
    XPath xpath;
    String file;
	private String[] lst;

    public ClientParser(String f) throws ParserConfigurationException, SAXException, IOException {
    this.file=f;
    this.loadXML(file);
}


    public void loadXML(String f) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
       
        InputSource xmlStringAsSource = new InputSource(new StringReader(f));
       
        doc = builder.parse(xmlStringAsSource);
        
       //doc = builder.parse(f);
        getXPathObj();
    }

    public XPath getXPathObj() {

        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        return xpath;
    }
   
  // count the number of people 
   int getPeopleNumber(String nodename)throws XPathExpressionException { 
       XPathExpression expr = xpath.compile("//"+nodename);
       Object result = expr.evaluate(doc, XPathConstants.NODESET);
       NodeList nodes = (NodeList) result;
       int m =nodes.getLength();
       return m;

}


   public Node savePersonId(String nodename) throws XPathExpressionException {

       
       XPathExpression expr = xpath.compile("//"+nodename);
       Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
       return node;
   }
  /* Node node1 = test.getActivityPlace("person/idPerson");
        System.out.println(node1.getTextContent());*/
//save first_person_id and last_person_id
  
   
   
   
   String getpersonName(String url)throws XPathExpressionException{
	  XPathExpression expr = xpath.compile(url);//person[1]/firstname
	  Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
	    return node.getTextContent();
      
   }

NodeList saveMeasureType(String nodename) throws XPathExpressionException{
	
         XPathExpression expr = xpath.compile("/"+nodename);
         Object   result = expr.evaluate(doc, XPathConstants.NODESET);
         NodeList      nodes = (NodeList) result;
         return nodes;

   }

Node checkActivity(String url)throws XPathExpressionException{
	  XPathExpression expr = xpath.compile("//"+url);//person[1]/firstname
	  Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
	  return node;
   
}



}
