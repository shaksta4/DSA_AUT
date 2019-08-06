/**
 * This class was used to test the StudentInfo and StudentInfoSet classes and to show
 * that the functionality works.
 * 
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class mainGUI
{
	public mainGUI()
	{
	}
	
	//The test run for the first question. Change the student name to test with other students.
	public void firstQuestion()
	{
		StudentInfo student1 = new StudentInfo("pky7291", "Shakeel", "Khan", "21/3/1997", 'M');
		student1.setPictureFromFileName("ZULU.jpg");
		
		StudentInfo student2 = new StudentInfo("abc1234", "Johnny", "Depp", "1/8/1956", 'F');
		student2.setPictureFromFileName("CAT.jpg");
		
		StudentInfo student3 = new StudentInfo("abc1234");
		student3.setPictureFromFileName("orcs1.jpeg");
		
		JFrame frame = new JFrame("Test Frame");
		frame.setSize(640, 640);
		
		//CHANGE TO EITHER STUDENT1, STUDENT2,OR STUDENT3 TO TEST
		frame.add(student1.getStudentPanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}

	//The test run for the second question. Is also used in the StudentDisplayGUI
	//It basically reads the XML file and populates a studentInfoSet
	//To test, just paste one of the ID's in the getStudent method used below
	public StudentInfoSet secondQuestion()
	{
		String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
		String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(); 
		builderFactory.setNamespaceAware(true);
		builderFactory.setValidating(true);
		builderFactory.setAttribute(JAXP_SCHEMA_SOURCE, W3C_XML_SCHEMA);
		
		DocumentBuilder builder;
		Document doc = null;
		try 
		{
			builder = builderFactory.newDocumentBuilder();
			InputStream in = new FileInputStream("studentdata.xml");
			doc = builder.parse(in);
			doc.getDocumentElement().normalize();
		} 
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SAXException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		StudentInfoSet mySet = new StudentInfoSet();
		mySet.loadStudentsFromXML(doc);
		
		//CHANGE TO 0122080, 1311607, 9394945, 11994823 TO TEST WITH OTHER STUDENT ID's
		StudentInfo myStudent = mySet.getStudent("0122080");
		
		//Prints a set of student info data based on what's in the XML.
		System.out.println(mySet.getDescription());
		System.out.print("Name: "+myStudent.getFirstName()+" "+myStudent.getLastName()+"\nGender:"+myStudent.getGender());
		System.out.println("\nPicture file path:"+myStudent.getPictureFileName());
		
		return mySet;
	}
	

	public static void main(String[] args) 
	{
		mainGUI mygui = new mainGUI();
	
		//To test first question
		mygui.firstQuestion();
		
		//To test second question
		mygui.secondQuestion();
	}
}
