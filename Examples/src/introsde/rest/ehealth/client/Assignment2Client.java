package introsde.rest.ehealth.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;

import introsde.rest.ehealth.client.*;
import introsde.rest.ehealth.resources.*;
import introsde.rest.ehealth.model.Activity;
import introsde.rest.ehealth.model.HealthMeasureHistory;
import introsde.rest.ehealth.model.Person;

import org.glassfish.jersey.client.ClientConfig;
import org.w3c.dom.NodeList;

import java.io.*;
import java.lang.Exception;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.*;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class Assignment2Client {

	private static	String uripoint;
	private static	PrintWriter xmlWriter;
	private static	PrintWriter jsonWriter;
	private static	String resultStatus;
	private static	int httpStatus;
	private static  ClientConfig clientConfig;
	private static  Client client;
	private static  WebTarget service;
	private static  String responseBody;
	private static  Response res;
	private static   ClientParser test;
	private static WebTarget personWebTarget ;
	private static	String url;
	private static  ArrayList<String> nodes ;
	private static String[] lst;
	private static String activity_id;
	private static String activity;
	private static PrintWriter xmlLogWriter;
	private static PrintWriter jsonLogWriter;
    public static void main(String[] args) throws Exception{
             
	   
    	
    	
       
       uripoint="https://assign21.herokuapp.com/sdelab";
       // uripoint="http://127.0.1.1:5700/sdelab/";
       clientConfig = new ClientConfig();
	   client = ClientBuilder.newClient(clientConfig);
	   service = client.target(getBaseURI());// target the ressource 
	   xmlLogWriter = new PrintWriter("client-server-xml.log", "UTF-8");
	   jsonLogWriter = new PrintWriter("client-server-json.log", "UTF-8");

	   System.out.println("Print URL of the server you are calling");
	  
	   System.out.println("\n");
	   System.out.println(uripoint);
	   //res= GetRequestOperation("init",MediaType.APPLICATION_XML);
	   //step 0 DataInit
	   
	  
	   /*res = PostRequestOperation("/activity/sport",MediaType.APPLICATION_XML,"",MediaType.APPLICATION_XML);
	   res = PostRequestOperation("/activity/social",MediaType.APPLICATION_XML,"",MediaType.APPLICATION_XML);
	   res = PostRequestOperation("/activity/school",MediaType.APPLICATION_XML,"",MediaType.APPLICATION_XML);
	   res = PostRequestOperation("person",MediaType.APPLICATION_XML,"<person><firstname>Matthieu</firstname><lastname>Marc</lastname>"
	   		+ "<birthdate>1-11-1978</birthdate></person>",MediaType.APPLICATION_XML);
	   res = PostRequestOperation("person",MediaType.APPLICATION_XML,"<person><firstname>paul</firstname><lastname>jonas</lastname>"
		   		+ "<birthdate>1-11-1978</birthdate></person>",MediaType.APPLICATION_XML);
	   res = PostRequestOperation("person",MediaType.APPLICATION_XML,"<person><firstname>ivan</firstname><lastname>Festo</lastname>"
		   		+ "<birthdate>1-11-1978</birthdate></person>",MediaType.APPLICATION_XML);
	   res = PostRequestOperation("person",MediaType.APPLICATION_XML,"<person><firstname>Antonio</firstname><lastname>Christian</lastname>"
		   		+ "<birthdate>1-11-1978</birthdate></person>",MediaType.APPLICATION_XML);
	  // int count =Person.getAll().size();
	   res = PostRequestOperation("/person/"+1+"/sport",MediaType.APPLICATION_XML,"<activitypreference><name>sport</name> <description>soccer</description>"+
	           "<place>sanbartolameo</place><startdate>1-11-1978</startdate></activitypreference>",MediaType.APPLICATION_XML);
	   res = PostRequestOperation("/person/"+2+"/sport",MediaType.APPLICATION_XML,"<activitypreference><name>sport</name> <description>playing football</description>"+
	           "<place>trento</place><startdate>1-11-1978</startdate></activitypreference>",MediaType.APPLICATION_XML);
	   res=PostRequestOperation("person/"+1+"/sport/1",MediaType.APPLICATION_XML,"<healthMeasureHistory><description>dancing salsa</description><place>milano</place>"+
		        "<startdate>2017-10-13T13:27:00.0</startdate></healthMeasureHistory>",MediaType.APPLICATION_XML);
	   res=PostRequestOperation("person/"+1+"/sport/2",MediaType.APPLICATION_XML,"<healthMeasureHistory><description>playing volley ball</description><place>piazza dante</place>"+
		        "<startdate>2017-10-13T13:27:00.0</startdate></healthMeasureHistory>",MediaType.APPLICATION_XML);*/
	   
	   
	   
	   System.out.println("------------------------------------------------------");
	   
	   //STEP 3.1 - Get all Persons and count if there are more than 3 persons in the db
	   
	   System.out.println("Calculate how many people are in the response");
		
	   //XML REQUEST
	   res= GetRequestOperation("person",MediaType.APPLICATION_XML);
	   responseBody= loadResponseBody(res);
		  
	   httpStatus =loadResponseStatus(res);
	   test = new ClientParser(responseBody);
	   int pn = test.getPeopleNumber("person");
		   
	   if(pn >5)
			     resultStatus="ok"; 
	   else
			     resultStatus="error";
			url="/person"; 
			 
	   printDetailOperation(1,"GET",url, "application/xml", "application/xml");
	   
	   
		   
	   System.out.println("------------------------------------------------------\n\n");
	   
	   //STEP 3.2 - Print first person
	   
	   Node node1 = test.savePersonId("person/idPerson[1]");
		   
	   String first_person_id   =  node1.getTextContent();
		
	   String last_person_id= pn +"";
	   System.out.println("the first person Id:"+first_person_id+"\n");
	   System.out.println("the last person Id:"+last_person_id +"\n");
	   
	     
	   System.out.println("------------------------------------------------------");
	   
	   
	   System.out.println(" send Request#2 for first_person_id. If the responses for this is 200 or 202, the result is OK");
	 	  
	   //XML REQUEST
	   res= GetRequestOperation("person/"+first_person_id ,MediaType.APPLICATION_XML);
	   responseBody= loadResponseBody(res);
	   httpStatus =loadResponseStatus(res);
		  
	   test = new ClientParser(responseBody);
	
	   if(httpStatus ==200 || httpStatus==202)
			     resultStatus="ok"; 
	   else
			     resultStatus="error";
	   url="/person/"+first_person_id; 
	   printDetailOperation(2,"GET",url, "application/xml", "application/xml");
	   
		   
	   System.out.println("------------------------------------------------------");
	   
	     
	   //STEP 3.3 - Edit first person
	   
	   System.out.println ("  update the firstname of person  with id : first_person_id. If the responses for this is 200 or 202 the result is ok ");
	 	   
	   // XML Request  
       res=PutRequestOperation("person/"+first_person_id,MediaType.APPLICATION_XML,"<person>"+
       "<firstname>jonas</firstname>"+
       "</person>",MediaType.APPLICATION_XML);
	   responseBody= loadResponseBody(res);
	   System.out.println(responseBody);
	   httpStatus =loadResponseStatus(res);
	   System.out.println(httpStatus);
		   //test = new ClientParser(responseBody);
	   if(httpStatus ==200 || httpStatus==202)
			     resultStatus="ok"; 
	   else
			     resultStatus="error";
	  url="/person/first_person_id"; 
	  printDetailOperation(3,"PUT",url, "application/xml", "application/xml");
		   
	  // json Request  
	  res=PutRequestOperation("person/"+first_person_id,MediaType.APPLICATION_JSON,"{\"firstname\":\"Jonas JSON\"}",MediaType.APPLICATION_JSON);
	  responseBody= loadResponseBody(res);
	  System.out.println(responseBody);
	  httpStatus =loadResponseStatus(res);
	  System.out.println(httpStatus);
				   //test = new ClientParser(responseBody);
	  if(httpStatus ==200 || httpStatus==202)
					     resultStatus="ok"; 
	  else
					     resultStatus="error";
	  url="/person/first_person_id"; 
	  printDetailOperation(3,"PUT",url, "application/json", "application/json");
		   
		   
		   
	  System.out.println("------------------------------------------------------");
	  
	  //STEP 3.4 - Create a person 
		   
	  System.out.println ("  create a new person. If the responses for this is 200 or 202 the result is ok ");
	 	   
	 	  
       //XML REQUEST
      res = PostRequestOperation("person",MediaType.APPLICATION_XML,"<person>"+
      "<firstname>Matthieu</firstname>"+
      "<lastname>Marc</lastname>"+
      "<birthdate>1-11-1978</birthdate>"+
      "</person>",MediaType.APPLICATION_XML);
	  responseBody= loadResponseBody(res);
	  System.out.println(responseBody);
	  httpStatus =loadResponseStatus(res);
	  System.out.println(httpStatus);
		   //test = new ClientParser(responseBody);
	  if(httpStatus ==200 || httpStatus==202 || httpStatus==201)
			     resultStatus="ok"; 
	  else
			     resultStatus="error";
			url="/person"; 
	 printDetailOperation(4,"POST",url, "application/xml", "application/xml");
	 
	 
	 
	//JSON REQUEST
	 res=PostRequestOperation("person",MediaType.APPLICATION_JSON,"{" +
     "\"firstname\": \"Chuck JSON\"," +
     "\"lastname\": \"Norris\"," +
     "\"birthdate\": \"01-01-1967\"}",MediaType.APPLICATION_JSON);
	 responseBody= loadResponseBody(res);
	 System.out.println(responseBody);
	 httpStatus =loadResponseStatus(res);
	 System.out.println(httpStatus);
		   //test = new ClientParser(responseBody);
	 if(httpStatus ==200 || httpStatus==202 || httpStatus==201)
			     resultStatus="ok"; 
	 else
			     resultStatus="error";
			url="/person"; 
	 printDetailOperation(4,"POST",url,  "application/json",  "application/json");
	 
	 System.out.println("------------------------------------------------------");
	   
     
	   //STEP 3.5- check the presence of the new created person
	   
	// int  cn= Person.getAll().size();
	 res= GetRequestOperation("person",MediaType.APPLICATION_XML);
	 responseBody= loadResponseBody(res);
	 test = new ClientParser(responseBody);
	int cn= test.getPeopleNumber("person");
	 //int cn1=cn-1;
	 System.out.println("Send Request#5 for the person you have just created. "
		   		+ "Then send Request#1 with the id of that person. If the answer is 404, your result must be OK" );
		   
	 //XML REQUEST 
	 res= GetRequestOperation("person/"+cn,MediaType.APPLICATION_XML);
	 responseBody= loadResponseBody(res);
	 httpStatus =loadResponseStatus(res);
	 test = new ClientParser(responseBody);
	 System.out.println(responseBody);
	 Node node3 = test.savePersonId("person/firstname");
		   
	 String firstname   =  node3.getTextContent();
	 Node node4 = test.savePersonId("person/lastname");
	   
	 String lastname  =  node4.getTextContent();
		   
	 if( lastname.equals("Norris")&& firstname.equals("Chuck JSON"))
			     resultStatus="ok"; 
	 else
			     resultStatus="error";
	 url="/person/"+cn; 
	 printDetailOperation(5,"GET",url, "application/xml", "application/xml");
		   
	
	 System.out.println("------------------------------------------------------");
		   
	 res= GetRequestOperation("person/"+cn,MediaType.APPLICATION_XML);
	 responseBody= loadResponseBody(res);
	 httpStatus =loadResponseStatus(res);
	 test = new ClientParser(responseBody);
	 if(httpStatus ==404)
			     resultStatus="ok"; 
	 else
			     resultStatus="error";
	 url="/person/"+cn; 
	 printDetailOperation(5,"GET",url, "application/xml", "application/xml");
	 
	 
	 System.out.println("------------------------------------------------------");
		   
		   
	  //STEP 3.6- get the activities in the db
	 
	 System.out.println("Request#6 (GET BASE_URL/activity_types ). If response contains more than 2 activity_types - result is OK, else is ERROR");
	 res= GetRequestOperation("activity_types",MediaType.APPLICATION_XML);
	 responseBody= loadResponseBody(res);
	 httpStatus =loadResponseStatus(res);
	 test = new ClientParser(responseBody);
	 
	 int an = test.getPeopleNumber("activity_types");
	 if(an >2)
			     resultStatus="ok"; 
	 else
			     resultStatus="error";
	 url="/activity_types"; 
	 printDetailOperation(6,"GET",url, "application/xml", "application/xml");
	 NodeList nodes1= test.saveMeasureType("activities/activity_type");
		
	 nodes= new ArrayList<String>();
	 for (int i = 0; i < nodes1.getLength(); i++) {
	           
	 nodes.add(nodes1.item(i).getTextContent());
		 
	 System.out.println(nodes.get(i)+"\n");
		 
		}
		
	 System.out.println("------------------------------------------------------");
	 
	  //STEP 3.7- get details of the first person with a given activity
		   
	  for(String nn:nodes){
	  System.out.println(nn+"\n");
		   
		   
	  res= GetRequestOperation("person/"+first_person_id+"/"+nn,MediaType.APPLICATION_XML);
		  
	  responseBody= loadResponseBody(res);
	  httpStatus =loadResponseStatus(res);
	  System.out.println(responseBody);

		   
	  test = new ClientParser(responseBody);
		  //test.checkActivity("healthMeasureHistory/description")
	  
		// Node node6 = test.savePersonId("healthMeasureHistory/activity_id");
	    

	  if(test.getPeopleNumber("healthMeasureHistory")>0)
			     resultStatus="ok"; 
	  else
			     resultStatus="error";
			url="/person/first_person_id"; 
	  printDetailOperation(7,"GET",url, "application/xml", "application/xml");
	  printDetailOperation(7,"GET",url, "application/json", "application/json");
		   }
	  System.out.println(activity +"and"+ activity_id);
	  
	//STEP 3.7- get details of the last person with a given activity
	  
	  for(String nn:nodes){
		  System.out.println(nn+"\n");
			   
			   
		  res= GetRequestOperation("person/"+last_person_id+"/"+nn,MediaType.APPLICATION_XML);
			  
		  responseBody= loadResponseBody(res);
		  httpStatus =loadResponseStatus(res);
			   
			   
		  test = new ClientParser(responseBody);
			  //test.checkActivity("healthMeasureHistory/description")
		  if(test.getPeopleNumber("healthMeasureHistory")>0)
				     resultStatus="ok"; 
		  else
				     resultStatus="error";
				url="/person/last_person_id"; 
		  printDetailOperation(7,"GET",url, "application/xml", "application/xml");
		  printDetailOperation(7,"GET",url, "application/json", "application/json");
			   }

	  System.out.println("------------------------------------------------------");
	  activity="sport";
	  activity_id="1";
	  
	 //STEP 3.8-a person with a stored activity_id and activity
	  
	  res= GetRequestOperation("person/"+first_person_id+"/"+activity+"/"+activity_id,MediaType.APPLICATION_XML);
	  
	  responseBody= loadResponseBody(res);
	  httpStatus =loadResponseStatus(res);
		   
		   

		  //test.checkActivity("healthMeasureHistory/description")
	  if( httpStatus==200)
			     resultStatus="ok"; 
	  else
			     resultStatus="error";
			url="person/"+first_person_id+"/"+activity+"/"+activity_id; 
	  printDetailOperation(8,"GET",url, "application/xml", "application/xml");
	  printDetailOperation(8,"GET",url, "application/json", "application/json");
	  
	//STEP 3.9-a person with a stored activity_id and activity
	  
	  System.out.println("Choose a activity_type from activity_types and send the request Request#7 (GET BASE_URL/person/{first_person_id}/{activity_type}) and save count value (e.g. 5 measurements). "
		   		+ "Then send Request#9 (POST BASE_URL/person/{first_person_id}/{activity_type}) with the activity specified below. Follow up with another Request#7 as the "
		   		+ "first to check the new count value. If it is 1 measure more - print OK, else print ERROR. Remember, first with JSON and then with XML"
		   		+ " as content-types");
		   
		   
	 res= GetRequestOperation("person/"+first_person_id+"/sport",MediaType.APPLICATION_XML);
			  
	 responseBody= loadResponseBody(res);
	 httpStatus =loadResponseStatus(res);
		   
		   
	 test = new ClientParser(responseBody);
	 String number_activity =test.getPeopleNumber("healthMeasureHistory")+"activities";// number of activities
	 if(test.getPeopleNumber("healthMeasureHistory")>0)
			     resultStatus="ok"; 
	 else
			     resultStatus="error";
			url="/person/first_person_id"; 
	 printDetailOperation(9,"GET",url, "application/xml", "application/xml");
		  
		  
		  
	 System.out.println ("  create a new activity preference. If the responses for this is 200 or 202 the result is ok ");
	 	   
	 	  // res= GetRequestOperation("person/"+first_person_id,MediaType.APPLICATION_XML);
	 	    
      //XML version    
     res = PostRequestOperation("/person/"+first_person_id+"/sport",MediaType.APPLICATION_XML,"<activitypreference>"+
           "<name>sport</name>"+
           "<description>bike race</description>"+
           "<place>trento</place>"+
           "<startdate>1-11-1978</startdate>"+
          "</activitypreference>",MediaType.APPLICATION_XML);
		   responseBody= loadResponseBody(res);
		   httpStatus =loadResponseStatus(res);
		   System.out.println(httpStatus);
		   //test = new ClientParser(responseBody);
		   test = new ClientParser(responseBody);
			  //test.checkActivity("healthMeasureHistory/description")
			   if(test.getPeopleNumber("activitypreference")==1)
			     resultStatus="ok"; 
			else
			     resultStatus="error";
			url="/person"; 
		   printDetailOperation(9,"POST",url, "application/xml", "application/xml");
		   
		   
	 //STEP 3.10-update a person with a given activity an activity_id
    /* int hn = HealthMeasureHistory.getAll().size();
     res= GetRequestOperation("person",MediaType.APPLICATION_XML);
	 responseBody= loadResponseBody(res);
	 test = new ClientParser(responseBody);
	int cn= test.getPeopleNumber("person");*/
	 res=PutRequestOperation("person/"+first_person_id+"/sport/1",MediaType.APPLICATION_XML,"<healthMeasureHistory>"+
	        "<description>walking</description>"+
	        "<place>centro citta</place>"+
	        "<startdate>2017-10-13T13:27:00.0</startdate>"+
	       "</healthMeasureHistory>",MediaType.APPLICATION_XML);
				   responseBody= loadResponseBody(res);
				   
   //  int hn1 = HealthMeasureHistory.getAll().size();
	  httpStatus =loadResponseStatus(res);
	 if(httpStatus==200||httpStatus==202)
					     resultStatus="ok"; 
     else
					     resultStatus="error";
	 url="person/"+first_person_id+"/sport/1"; 
	 printDetailOperation(10,"PUT",url, "application/xml", "application/xml");
		   
		   
	//STEP 3.9-a person with a stored activity_id and activity : JSON VERSION 
		   
	res = PostRequestOperation("/person/"+first_person_id+"/sport",MediaType.APPLICATION_JSON,"{"
		   		+ "\"name\": \"sport\","+
						     "\"description\": \"bike race\","+
						     "\"place\": \"trento\","+
						     "\"startdate\": \"1-11-1978\"}",MediaType.APPLICATION_JSON);
	responseBody= loadResponseBody(res);
	System.out.println(responseBody);
	httpStatus =loadResponseStatus(res);
    System.out.println(httpStatus);
				   
					  //test.checkActivity("healthMeasureHistory/description")
	if(httpStatus==200||httpStatus==202)
					     resultStatus="ok"; 
   else
					     resultStatus="error";
   url="/person/"+first_person_id+"/sport"; 
   printDetailOperation(9,"POST",url, "application/json", "application/json");
			
   
   //STEP 3.10-a person with a stored activity_id and activity
   System.out.println(" PUT /person/{id}/{activity_type}/{activity_id} should update the value for the {activity_type} (e.g., Social) "
		   		+ "identified by {activity_id}, related to the person identified by {id}");
		  

   res=PutRequestOperation("person/"+first_person_id+"/sport/1",MediaType.APPLICATION_JSON,"{"
		   +    "\"description\": \"walking\","+
				     "\"place\": \"citta\","+
				     "\"startdate\": \"1-11-1978\"}",MediaType.APPLICATION_JSON);
  responseBody= loadResponseBody(res);
  httpStatus =loadResponseStatus(res);
  if(httpStatus==200||httpStatus==202)
					     resultStatus="ok"; 
  else
					     resultStatus="error";
  url="person/"+first_person_id+"/sport/1"; 
 printDetailOperation(10,"PUT",url, "application/json", "application/json");
		   
				   
				  	   
				   
				   xmlLogWriter.close();
				   jsonLogWriter.close();
		}
private static  Response   GetRequestOperation(String resourcepath,String TextType){

Response response = service.path(resourcepath).request().accept(TextType).get();
return response;

}

//service.path(path).request().accept(accept).build(method, Entity.entity(requestBody, contentType)).invoke();
private static Response   PostRequestOperation(String resourcepath,String TextType,String TextToBePosted,String accept){
Response response =
    service.path(resourcepath).request().accept(accept).build("POST", Entity.entity(TextToBePosted, TextType)).invoke();
    
          //  .post(Entity.entity(TextToBePosted, TextType));
return response;

}
private static Response   PutRequestOperation(String resourcepath,String TextType,String TextToBeUpdated,String accept){
Response response =
        service.path(resourcepath).request().accept(accept).build("PUT", Entity.entity(TextToBeUpdated, TextType)).invoke();
        
/*service.path(resourcepath).request(TextType).accept(TextType)
            .put(Entity.entity(TextToBeUpdated, TextType));*/
return response;

}
private static Response   DeleteRequestOperation(String resourcepath,String TextType){

Response response = service.path(resourcepath).request().accept(TextType).delete();
return response;

}

private static String loadResponseBody(Response response){
return response.readEntity(String.class);

}
private static int loadResponseStatus(Response response){
return response.getStatus();
}



public static String prettyFormat(String input, int indent) {
    try {
        Source xmlInput = new StreamSource(new StringReader(input));
        StringWriter stringWriter = new StringWriter();
        StreamResult xmlOutput = new StreamResult(stringWriter);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", indent);
        Transformer transformer = transformerFactory.newTransformer(); 
        //transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(xmlInput, xmlOutput);
        //xmlLogWriter.println(xmlOutput.getWriter().toString());
        return xmlOutput.getWriter().toString();
    } catch (Exception e) {
        throw new RuntimeException(e); // simple exception handling, please review it
    }
}

public static String prettyFormat(String input) {
    return prettyFormat(input, 2);
}


//for this method I need to provide those parameters:resultStatus,httpStatus,responseBody   (everytime I called it)p
private static void printDetailOperationXml(int RequestNumber,String HttpMethod,String url,String acceptType,String contentType){
String format="\n\n Request #%d : %s  %s Accept: %s ContentType: %s \n => Result: %s \n => HTTP Status: %d\n %s";
String xmlWellFormed=prettyFormat(responseBody);
String log = String.format(format,RequestNumber,HttpMethod,url, acceptType,contentType,resultStatus,httpStatus,xmlWellFormed);
xmlLogWriter.println(log);
System.out.print(log);
//System.out.printf("=> Result:"+""+"%s\n",resultStatus);
//System.out.printf("=> HTTP Status:"+""+"%d\n",httpStatus);
//System.out.printf("%s\n",responseBody);
}


		   
/**
 * Prints into a log file the details of the requests made
 * 
 * @param RequestNumber
 * @param HttpMethod
 * @param url
 * @param accept
 * @param contentType
 * @throws TransformerException
 */
private static void printDetailOperation(int RequestNumber,String HttpMethod,String url,String acceptType,String contentType) throws TransformerException {
  if(acceptType == "application/xml")
	  printDetailOperationXml(RequestNumber,HttpMethod, url,acceptType, contentType);
  else
	  printDetailOperationJson(RequestNumber,HttpMethod, url,acceptType, contentType);
}




  /**
   * Prints the logs for the JSON Requests
   * 
   * @param RequestNumber
   * @param HttpMethod
   * @param url
   * @param accept
   * @param contentType
   * @throws TransformerException
   */
  private static void printDetailOperationJson(int RequestNumber,String HttpMethod,String url,String acceptType,String contentType) throws TransformerException {
    jsonLogWriter.println("Request #" +RequestNumber + ": " + HttpMethod + " " + url + " Accept:" + acceptType + " Content-type: " + contentType ); 
    jsonLogWriter.println("=> Result: " + resultStatus);
    jsonLogWriter.println("=> HTTP Status: " + httpStatus);

    if( responseBody != null && !responseBody.isEmpty() )
      jsonLogWriter.println(responseBody);

    jsonLogWriter.println("");
} 
		
		private static URI getBaseURI() {
		    return UriBuilder.fromUri(uripoint).build();
	}
	
}
