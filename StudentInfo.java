/**
 * This class is based on the StudentInfo XML diagram. It holds attributes for
 * a student and creates a GUI interface for viewing a student's name, DOB, gender and image. 
 * Student info is editable through the interface.
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StudentInfo {

	private String firstname, lastname;
	private String birthdate, studentID;
	private String pictureFileLocation;
	private char gender;
	private List<String> enrolledPapers;
	
	public StudentInfo(String studentID)
	{
		this.studentID = studentID;
		this.firstname = null;
		this.lastname = null;
		this.birthdate = null;
		this.gender = '-';
		this.enrolledPapers = new ArrayList<String>();
	}
	
	public StudentInfo(String studentID, String firstname,
					String lastname, String birthdate, char gender)
	{
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.studentID = studentID;
		this.gender = gender;
		this.enrolledPapers = new ArrayList<String>();
	}
	
	public void addPaper(String paper)
	{
		enrolledPapers.add(paper);
	}
	
	public List<String> getEnrolledPapers()
	{
		return this.enrolledPapers;
	}
	
	public void setPictureFromFileName(String filename)
	{
		this.pictureFileLocation = filename;		
	}
	
	public String getPictureFileName()
	{
		return this.pictureFileLocation;
	}
	
	public String getFirstName()
	{
		return this.firstname;
	}
	
	public String getLastName()
	{
		return this.lastname;
	}
	
	public String getBirthDate()
	{
		return this.birthdate;
	}
	
	public String getStudentID()
	{
		return this.studentID;
	}
	
	public char getGender()
	{
		return this.gender;
	}
	
	public JComponent getStudentPanel()
	{
		return new studentPanel(this);
	}
	
	@SuppressWarnings("serial")
	private class studentPanel extends JPanel {
		
		private studentPanel(StudentInfo student)
		{
			//Adding and changing names
			JLabel firstname_label = new JLabel("First name");
			JTextField firstname = new JTextField(student.getFirstName());
			JLabel lastname_label = new JLabel("Last name");
			JTextField lastname = new JTextField(student.getLastName());
			
			//If no name, sets default sizes
			if(student.firstname == null)
			{
				firstname.setPreferredSize(new Dimension(75, firstname.getPreferredSize().height));
			}
			
			if(student.lastname == null)
			{
				lastname.setPreferredSize(new Dimension(75, lastname.getPreferredSize().height));
			}
			
			//Changes the value of the student's name based on what the user types
			DocumentListener dl = new DocumentListener() {
				public void changedUpdate(DocumentEvent de) {
					student.firstname = firstname.getText();
					student.lastname = lastname.getText();
					System.out.println(student.getFirstName() + " " + student.getLastName());
				}

				public void insertUpdate(DocumentEvent de) {
					student.firstname = firstname.getText();
					student.lastname = lastname.getText();
					System.out.println(student.getFirstName() + " " + student.getLastName());
				}
				
				public void removeUpdate(DocumentEvent de) {
					student.firstname = firstname.getText();
					student.lastname = lastname.getText();
					System.out.println(student.getFirstName() + " " + student.getLastName());
				}
			};

			firstname.getDocument().addDocumentListener(dl);	
			lastname.getDocument().addDocumentListener(dl);
			
			//Adding date of birth 
			JLabel dob = new JLabel("dd/mm/yyyy");
			JComboBox<String> day = new JComboBox<String>();
			JComboBox<String> month = new JComboBox<String>();
			JComboBox<String> year = new JComboBox<String>();
		    
			int[] months = new int[12];
			int[] years = new int[100];
			int[] days = new int[31];
		   
		    //Adding months to comboBox
		    for(int i = 0 ; i < 12; i++)
		    {
		    	months[i] = i+1;
		    	month.addItem(""+months[i]);
		    }
		    
		    //Adding years to the comboBox
		    for(int i = 0 ; i < 100; i++)
		    {
		    	years[i] = 1918+i;
		    	year.addItem(""+years[i]);
		    }
		    
		    //Adding days to the comboBox. Yes I hard-coded 31 days for all months. :(
		    for(int i = 0 ; i < 31; i++)
		    {
		    	days[i] = i+1;
		    	day.addItem(""+days[i]);
		    }  
			
			if(student.getBirthDate() != null)
			{
				String birthdate[] = student.getBirthDate().split("/");
				
				day.setSelectedItem(birthdate[0]);
				month.setSelectedItem(birthdate[1]);
				year.setSelectedItem(birthdate[2]);
			}
			
			//Changes the user's D.O.B based on what the user enters
			ActionListener dobAL = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent ae) 
				{
					student.birthdate = day.getSelectedItem()+"/"+month.getSelectedItem()+"/"+year.getSelectedItem();		
					System.out.println(student.birthdate);
				}
				
			};
			
			day.addActionListener(dobAL);
			month.addActionListener(dobAL);
			year.addActionListener(dobAL);
			
			//Adding gender buttons
			JRadioButton male = new JRadioButton("Male");
			JRadioButton female = new JRadioButton("Female");
			
			if(student.getGender() == 'M')
			{
				male.setSelected(true);
			}
			else if(student.getGender() == 'F')
			{
				female.setSelected(true);
			}
			
			//Sets the students gender based on user's choice.
			ActionListener genderAL = new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					if(male.isSelected())
					{
						student.gender = 'M';
						System.out.println(student.getGender());
					}
					else
					{
						student.gender = 'F';
						System.out.println(student.getGender());
					}
				}
			};
			
			male.addActionListener(genderAL);
			female.addActionListener(genderAL);
		
			ButtonGroup myButtons = new ButtonGroup();
			myButtons.add(male);
			myButtons.add(female);
			
			//Adding image
			BufferedImage myImage = null;
			JLabel imageLabel;
			
			try 
			{
				myImage = ImageIO.read(new File(student.getPictureFileName()));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			imageLabel = new JLabel(new ImageIcon(myImage.getScaledInstance(640, 640, 0)));

			//Adding it all to the panel
			this.add(firstname_label, BorderLayout.NORTH);
			this.add(firstname, BorderLayout.NORTH);
			this.add(lastname_label, BorderLayout.NORTH);
			this.add(lastname, BorderLayout.NORTH);
			
			this.add(dob, BorderLayout.CENTER);
			this.add(day, BorderLayout.CENTER);
			this.add(month, BorderLayout.CENTER);
			this.add(year, BorderLayout.CENTER);
			
			this.add(male, BorderLayout.SOUTH);
			this.add(female, BorderLayout.SOUTH);
			
			this.add(imageLabel, BorderLayout.CENTER);
		}
	}
}
