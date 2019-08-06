/**
 * This class is based on the StudentInfoSet XML diagram provided
 * It basically populates a hashmap with studentInfo objects,
 * mapping each StudentInfo to its studentID.
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class StudentInfoSet {

	private Map<String, StudentInfo> studentMap;
	private String description;
	
	public StudentInfoSet()
	{
		studentMap = new HashMap<String, StudentInfo>();
	}
	
	//This method is where the XML file is loaded and the hashmap is populated with data from the XML file
	public void loadStudentsFromXML(Document doc)
	{
		DOMUtilities myUtility = new DOMUtilities();	
		Node rootXML = doc.getDocumentElement();
		
		//Get descriptions
		ArrayList<Node> description = new ArrayList<Node>(myUtility.getAllChildNodes(rootXML, "description"));
		
		//Get students
		ArrayList<Node> students = new ArrayList<Node>(myUtility.getAllChildNodes(rootXML, "student"));
		
		for(Node e: description)
		{
			this.description = "Description: "+myUtility.getTextContent(e);
		}
		
		for(Node e: students)
		{
			//Getting all of the entries under each of these tags in the XML document
			ArrayList<Node> firstnames = new ArrayList<Node>(myUtility.getAllChildNodes(e, "firstname"));
			ArrayList<Node> lastnames = new ArrayList<Node>(myUtility.getAllChildNodes(e, "lastname"));
			ArrayList<Node> pictureURLS = new ArrayList<Node>(myUtility.getAllChildNodes(e, "pictureURL"));
			ArrayList<Node> DOBs = new ArrayList<Node>(myUtility.getAllChildNodes(e, "birthday"));
			ArrayList<Node> papers = new ArrayList<Node>(myUtility.getAllChildNodes(e, "paper"));
			
			String studentID;
			String firstname;
			String lastname;
			String birthdate;
			String gender;
			String pictureFile;
			String[] paper = new String[10];
			
			//For each student, set their attributes based on the XML diagram
			studentID = myUtility.getAttributeString(e, "studentID");
			gender = myUtility.getAttributeString(e, "gender");
			
			firstname = myUtility.getTextContent(firstnames.get(0));
			lastname = myUtility.getTextContent(lastnames.get(0));
			
			birthdate = myUtility.getAttributeString(DOBs.get(0), "day")+"/"+
						myUtility.getAttributeString(DOBs.get(0), "month")+"/"+
						myUtility.getAttributeString(DOBs.get(0), "year");
			
			pictureFile = myUtility.getTextContent(pictureURLS.get(0));
			
			StudentInfo myStudent = new StudentInfo(studentID, firstname, lastname, birthdate, gender.charAt(0));
			
			int index = 0;
			for(Node p: papers)
			{
				paper[index] = myUtility.getTextContent(p);
				myStudent.addPaper(paper[index]);
				index++;
			}
			
			//Setting student's image
			myStudent.setPictureFromFileName(pictureFile);
			
			//Adds student to the hashmap
			this.addStudent(myStudent);
		}
	}
	
	public void addStudent(StudentInfo student)
	{
		studentMap.put(student.getStudentID(), student);
	}
	
	public void removeStudent(String studentID)
	{
		studentMap.remove(studentID);
	}
	
	public StudentInfo getStudent(String studentID)
	{
		return studentMap.get(studentID);
	}
	
	public void clear()
	{
		studentMap.clear();
	}
	
	public Iterator<StudentInfo> iterator()
	{
		return studentMap.values().iterator();
	}
	
	public int size()
	{
		return studentMap.size();
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public boolean containsKey(String key)
	{
		if(studentMap.containsKey(key))
		{
			return true;
		}
		return false;
	}
	
	public Set<String> getStudentKeys()
	{
		return studentMap.keySet();
	}
	
}
