/**
 * This class is the solution to the third question of the assignment.
 * It creates a GUI where you can select different students based on their studentId's
 * and it will obtain their respective studentpanel into the window.
 * 
 * BUGS/NOT YET IMPLEMENTED: 
 * Buttons do not do anything.
 * There is no menu bar
 * Not much exception handling
 * Cannot create XML files or save to XML files.
 * 
 * Sorry.
 * 
 * P.S Two of the cats are my cats. 
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudentDisplayGUI {

	private StudentInfoSet mySet;
	private JList<String> myList;
	private JSplitPane mySplitPane;
	private DefaultListModel<String> myModel;
	private StudentInfo myStudent;
	private JTextField desc;
	private JPanel myPanel;
	private JButton getPapers, addPapers, changePic;
	
	public StudentDisplayGUI()
	{
		mainGUI mygui = new mainGUI();
		myPanel = new JPanel();
		mySplitPane = new JSplitPane();
		
		mySet = mygui.secondQuestion(); // loads from the XML

		myModel = new DefaultListModel<String>();
		
		for(String e: mySet.getStudentKeys()) // adding the student ids to the default list model
		{
			myModel.addElement(e);
		}
		
		myList = new JList<String>(myModel); // populate the list with studentids from the listmodel
		mySplitPane.setLeftComponent(myList); // set left side to the list

		myList.setSelectedIndex(0); 
		myStudent = mySet.getStudent(myList.getSelectedValue());
		mySplitPane.setRightComponent(myStudent.getStudentPanel()); // set right side to the student panel
		
		ListSelectionListener lsl = new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				myStudent = mySet.getStudent(myList.getSelectedValue());
				mySplitPane.setRightComponent(myStudent.getStudentPanel()); // set right side to the student panel when changed
			}	
		};
		
		desc = new JTextField(mySet.getDescription());		
		myList.addListSelectionListener(lsl); 
		
		//Creating buttons
		getPapers = new JButton("Get papers");
		addPapers = new JButton("Add paper");
		changePic = new JButton("Change picture");
		
		//Changing sizes of each component
		getPapers.setPreferredSize(new Dimension(100, 25));
		addPapers.setPreferredSize(new Dimension(100, 25));
		changePic.setPreferredSize(new Dimension(125, 25));
		mySplitPane.setPreferredSize(new Dimension(750, 550));
		desc.setPreferredSize(new Dimension(700, 25));
		
		//Adding components to a jpanel
		myPanel.add(mySplitPane, BorderLayout.WEST);
		myPanel.add(desc, BorderLayout.SOUTH);
		
		myPanel.add(getPapers, BorderLayout.EAST);
		myPanel.add(addPapers, BorderLayout.EAST);
		myPanel.add(changePic, BorderLayout.EAST);
	}
	
	//Main method to test the student display Gui
	public static void main(String[] args) 
	{
		StudentDisplayGUI myGUI = new StudentDisplayGUI();
		
		JFrame frame = new JFrame("Student Display GUI");
		
		frame.add(myGUI.myPanel);
		frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
