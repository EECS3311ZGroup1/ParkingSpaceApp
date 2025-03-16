package yorku.eecs3311.gui;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import yorku.eecs3311.parking.ParkingSpace;
import yorku.eecs3311.gui.list.ListElement;

public class ParkingSelectionWindow {
	private JFrame window;
	private JList<ListElement<ParkingSpace>> parkingSpaceList;
	JPanel panel = new JPanel();
	DefaultListModel<ListElement<ParkingSpace>> model;
	JSplitPane splitPane = new JSplitPane();
	
	public ParkingSelectionWindow() {
		window = new JFrame();
		parkingSpaceList = new JList<ListElement<ParkingSpace>>();
		model = new DefaultListModel<>();
		
		model.addElement(new ListElement<ParkingSpace>("ParkingSpace1", new ParkingSpace(1,"loc1")));
		model.addElement(new ListElement<ParkingSpace>("ParkingSpace2", new ParkingSpace(3,"loc3")));
		model.addElement(new ListElement<ParkingSpace>("ParkingSpace3", new ParkingSpace(5,"loc5")));
		
		
		window.setTitle("York Parking Space Selection");
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);;
		window.setSize(800, 500);
		window.setLocationRelativeTo(null);
		
		parkingSpaceList.setModel(model);
		
		splitPane.setLeftComponent(new JScrollPane(parkingSpaceList));
		splitPane.setRightComponent(panel);
		
		window.add(splitPane);
		
		
	}
	
	public void show() {
		window.setVisible(true);
	}
}
