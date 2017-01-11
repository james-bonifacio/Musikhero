import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.List;

import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.StyledDocument;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTextField;

public class Gui {

	
	/*TO DO:
	 * 
	 * search through archive
	 * tab avoid negatives
	 * tab right click add new line
	 * 
	 * change tab position
	 * mp3 player
	 * 
	 * */
	
	private String currentKey = "C";
	private JFrame frame;
	private Chords c;
	private Tabs t;
	private JList lstArchive, lstLineup;
	private JPanel noWrapPanel;
	private DefaultListModel lstModelArchive, lstModelLineup;
	private JScrollPane spLineup, spArchive, spChord, spTab;
	private JSplitPane splitPane;
	private StyledDocument doc;
	private JTextPane txtChord;
	private JTextArea txtTab;
	private JTextField txtSearch;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					Gui window = new Gui();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		
		frame = new JFrame("Musikero 2.6");
		frame.setBounds(100, 100, 1276, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[250][grow]", "[][30][grow][30][250]"));
		
		//save button
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				save();
			}
		});
		
		frame.getContentPane().add(btnSave, "flowx,cell 0 0");
		
		//song lstArchive
		lstModelArchive = new DefaultListModel();
	    lstArchive = new JList(lstModelArchive);
	    lstArchive.setForeground(Color.YELLOW);
	    lstArchive.setBackground(Color.BLUE);
	    spArchive = new JScrollPane(lstArchive);
	    spArchive.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    lstArchive.addListSelectionListener(new ListSelectionListener() {
	    	public void valueChanged(ListSelectionEvent event) {
	    		
	    		//txt file into txtChord
	    		String fileName = "C:/Musikhero/Chords/" + (String) lstArchive.getSelectedValue() + ".txt";
	    		System.out.println(fileName);
	    		
	    		//when list isnt being cleared
	    		if (!fileName.equals("C:/Musikhero/Chords/null.txt")){
		    		FileReader reader = null;
					try {
						reader = new FileReader(fileName);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            try {
						txtChord.read(reader,fileName);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		            fileName = "C:/Musikhero/Tabs/" + (String) lstArchive.getSelectedValue() + ".txt";
		    		System.out.println(fileName);
		    		
		    		reader = null;
					try {
						reader = new FileReader(fileName);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            try {
						txtTab.read(reader,fileName);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		            if (!lstLineup.isSelectionEmpty()){
		    			lstLineup.clearSelection();
		    		}
	    		}
	            
	            setCurrentKey();
	            
	            
	            
	    	}
	    });//end lstArchive listener
	    
	    lstArchive.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent evt) {
	        	
	        	JList list = (JList)evt.getSource();
	        	int index = list.locationToIndex(evt.getPoint());
	        	 
	        	String firstLine = (String) lstArchive.getSelectedValue();
	        	
	           
	            if (evt.getClickCount() == 2) {

	                // Double-click detected
	               
	                
	                
	                boolean isInList = false;
					
					for(int i = 0; i < lstModelLineup.getSize(); i++) {
						
						String checkList = (String) lstModelLineup.getElementAt(i);
						
						if (firstLine.equals(checkList)){
							System.out.println(lstModelLineup.getElementAt(i));
							isInList = true;
						}

					 }
					
					if (!isInList){
						lstModelLineup.addElement(firstLine);
						System.out.println("added " + firstLine + " to lineup");
					}
					
					saveLineup();
	            }//end if double click 
	        }
	    });
		
		File folder = new File("C:/Musikhero/Chords/");
		 
	    File[] listOfFiles = folder.listFiles();
	    String[] listOfNames  = new String[20];
	    
	    //load songs into archive
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	
	    	String fileName = listOfFiles[i].getName();
	    	fileName = fileName.substring(0, fileName.length() - 4); 
	    
	    	lstModelArchive.addElement(fileName);
	    }
	    
	    JLabel lblSongArchive = new JLabel("Song Archive");
	    lblSongArchive.setBackground(Color.GRAY);
	    lblSongArchive.setFont(new Font("Monospaced", Font.PLAIN, 20));
	    frame.getContentPane().add(lblSongArchive, "cell 0 1");
	    
	    JLabel lblSongLineup = new JLabel("Song Lineup");
	    lblSongLineup.setBackground(Color.LIGHT_GRAY);
	    lblSongLineup.setFont(new Font("Monospaced", Font.PLAIN, 20));
	    frame.getContentPane().add(lblSongLineup, "cell 0 3");
	    
	   
	    lstArchive.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    lstArchive.setFont(new Font("Monospaced", Font.BOLD, 25));
		frame.getContentPane().add(spArchive, "cell 0 2,grow");
		
		lstModelLineup = new DefaultListModel();
		lstLineup = new JList(lstModelLineup);
		lstLineup.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
	    		//txt file into txtChord
	    		String fileName = "C:/Musikhero/Chords/" + (String) lstLineup.getSelectedValue() + ".txt";
	    		System.out.println(fileName);
	    		
	    		//when list isnt being cleared
	    		if (!fileName.equals("C:/Musikhero/Chords/null.txt")){
	    		
		    		FileReader reader = null;
					try {
						reader = new FileReader(fileName);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            try {
						txtChord.read(reader,fileName);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		            fileName = "C:/Musikhero/Tabs/" + (String) lstLineup.getSelectedValue() + ".txt";
		    		System.out.println(fileName);
		    		
		    		reader = null;
					try {
						reader = new FileReader(fileName);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            try {
						txtTab.read(reader,fileName);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		            if (!lstArchive.isSelectionEmpty()){
		    			lstArchive.clearSelection();
		    		}
		            
	    		}
	            
	            
	            setCurrentKey();
	            	
			}
		});
		lstLineup.setFont(new Font("Monospaced", Font.BOLD, 25));
		lstLineup.setForeground(Color.YELLOW);
		lstLineup.setBackground(Color.RED);
		lstLineup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spLineup = new JScrollPane(lstLineup);
		
		//load lineup
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("C:/Musikhero/Lineup.txt"));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String str=null;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			while((str = in.readLine()) != null){
			    lines.add(str);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] lineup = lines.toArray(new String[lines.size()]);
		
		
		for (int i = 0; i < lineup.length; i++) {
	    	
	    	String fileName = lineup[i]; 
	    	lstModelLineup.addElement(fileName);
	    }
		
		MouseAdapter listener = new ReorderListener(lstLineup);
	    lstLineup.addMouseListener(listener);
	    lstLineup.addMouseMotionListener(listener);
		
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBackground(Color.WHITE);
		popupMenu.setFont(new Font("Monospaced", Font.PLAIN, 31));
		addPopup(lstLineup, popupMenu);
		
		JMenuItem mntmRemove = new JMenuItem("     Remove from Lineup");
		mntmRemove.setBackground(Color.WHITE);
		mntmRemove.setFont(new Font("Arial", Font.PLAIN, 21));
		popupMenu.add(mntmRemove);
		
		//remove song from lineup
		mntmRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int selectedIndex = lstLineup.getSelectedIndex();
               if (selectedIndex != -1) {
            	    lstModelLineup.remove(selectedIndex);
            	}
               
               saveLineup();
            }
        });
		
		JMenuItem mntmClear = new JMenuItem("     Clear List");
		mntmClear.setBackground(Color.WHITE);
		mntmClear.setFont(new Font("Arial", Font.PLAIN, 21));
		popupMenu.add(mntmClear);
		frame.getContentPane().add(spLineup, "cell 0 4, grow");
		
		//clear lineup
		mntmClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 lstModelLineup.removeAllElements();
            	 saveLineup();
            }
        });
		
		 lstLineup.addMouseListener(new MouseAdapter() {
		        public void mousePressed(MouseEvent e) {
		        	 if ( SwingUtilities.isRightMouseButton(e) )
		             {
		                 int row = lstLineup.locationToIndex(e.getPoint());
		                 lstLineup.setSelectedIndex(row);
		             }
		        }
		    });

		
		JButton btnG = new JButton("G");
		btnG.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				changeKey("G");
			}
		});
		
		frame.getContentPane().add(btnG, "flowx,cell 1 0");
		
		JButton btnC = new JButton("C");
		btnC.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				changeKey("C");
			}
		});
		frame.getContentPane().add(btnC, "cell 0 0");
		
		JButton btnD = new JButton("D");
		
		btnD.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				changeKey("D");
			}
		});
		frame.getContentPane().add(btnD, "cell 0 0");
		
		JButton btnE = new JButton("E");
		btnE.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				changeKey("E");
			}
		});
		frame.getContentPane().add(btnE, "cell 0 0");
		
		JButton btnF = new JButton("F");
		btnF.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				
				changeKey("F");
			}
		});
		frame.getContentPane().add(btnF, "cell 0 0");
	
		
		JButton btnA = new JButton("A");
		
		btnA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				changeKey("A");
				
			}
		});
		frame.getContentPane().add(btnA, "cell 1 0");
		
		JButton btnB = new JButton("B");
		btnB.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				changeKey("B");
			}
		});
		frame.getContentPane().add(btnB, "cell 1 0");
		
		JButton btnSharp = new JButton("#");
		btnSharp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeKey("#");
			}
		});
		frame.getContentPane().add(btnSharp, "cell 1 0");
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.9);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPanel contentPane  = new JPanel();
		
		frame.getContentPane().add(contentPane, "cell 1 1 1 4,grow");
		contentPane.setLayout(new BorderLayout(0, 0));
		
		txtChord = new JTextPane();
		
		noWrapPanel = new JPanel( new BorderLayout() );
		noWrapPanel.add( txtChord );
		
		txtChord.setFont(new Font("Monospaced", Font.PLAIN, 25));
		spChord = new JScrollPane(noWrapPanel);
		spChord.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		splitPane.setLeftComponent(spChord);
		
		txtTab = new JTextArea("E|----------------------------------------------------------------------------------------------------------|"
				+ "\r\n" + "B|----------------------------------------------------------------------------------------------------------|"
				+ "\r\n" + "G|----------------------------------------------------------------------------------------------------------|"
				+ "\r\n" + "D|----------------------------------------------------------------------------------------------------------|"
				+ "\r\n" + "A|----------------------------------------------------------------------------------------------------------|"
				+ "\r\n" + "E|----------------------------------------------------------------------------------------------------------|");
		txtTab.setFont(new Font("Monospaced", Font.PLAIN, 25));
		spTab = new JScrollPane(txtTab);
		splitPane.setRightComponent(spTab);
		
		contentPane.add(splitPane);
		
		JButton btnTab = new JButton("Tab");
		btnTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (splitPane.isVisible()){
					splitPane.remove(spChord);
					splitPane.remove(spTab);
					splitPane.setVisible(false);
					contentPane.removeAll();
					contentPane.add(spChord);
				}else{
					contentPane.removeAll();				
					splitPane.setLeftComponent(spChord);				
					splitPane.setRightComponent(spTab);
					splitPane.setDividerLocation(0.65);
					splitPane.setVisible(true);
					contentPane.add(splitPane);
					
				}
				
				contentPane.revalidate();
	            contentPane.repaint();
			}
		});
		frame.getContentPane().add(btnTab, "cell 1 0");
		
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lstLineup.clearSelection();
				lstArchive.clearSelection();
				txtChord.setText("");
				txtTab.setText("E|----------------------------------------------------------------------------------------------------------|"
				+ "\r\n" + "B|----------------------------------------------------------------------------------------------------------|"
				+ "\r\n" + "G|----------------------------------------------------------------------------------------------------------|"
				+ "\r\n" + "D|----------------------------------------------------------------------------------------------------------|"
				+ "\r\n" + "A|----------------------------------------------------------------------------------------------------------|"
				+ "\r\n" + "E|----------------------------------------------------------------------------------------------------------|");
			}
		});
		frame.getContentPane().add(btnNew, "cell 1 0");
		
		JLabel lblSearch = new JLabel("Search:");
		frame.getContentPane().add(lblSearch, "cell 1 0,aligny baseline");
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 22));
		frame.getContentPane().add(txtSearch, "cell 1 0");
		txtSearch.setColumns(10);
		
		
		
		
	}//end initialize
	
	public void changeKey(String changeTo){
		String out = "";
		
		String change = changeTo;
		
		//chords
		if (change.equals("#")){
			
			if (currentKey.equals("B")){
				
				change = "C";
				
			}else if(currentKey.equals("E")){
				
				change = "F";
				
			}else{
				
				change = currentKey + "#";
			}
		}
		
		
		for (String line : txtChord.getText().split("\\n")){
			
			String lastTwo = "";
			
			//remove nextline characters
			if (line != null && line.length() >= 1) {
				
			    lastTwo = line.substring(line.length() - 1);
			    
			    if (lastTwo.equals("\r") || lastTwo.equals("\n")){
					line = line.substring(0, line.length()-1);
				}
			}
			
			line = line + "      ";
			
			if (line.substring(0, 4).equals("KEY:")){
				
				out = out + "KEY:";
				
				line = line.substring(4);
			}
			
			//remove trailing spaces
			line = line.replaceFirst("\\s+$", "");
			
			
			if (c.isChord(line)){
				
				out = out + c.transposeChord(line, currentKey, change) + "\n";
				
			}else{
				
				out = out + line + "\n";
			}		
			
		}
		
		
		txtChord.setText(out);
		
		//tabs
		
		out = "";
		int increase = c.chordToInt(changeTo) - c.chordToInt(currentKey);
		
		for (String line : txtTab.getText().split("\\n")){
		
			
			out = out + t.transposeTab(line, increase) + "\n";
			
		}
		
		txtTab.setText(out);
		
		currentKey = change;
		save();
		
		
	}// end changeKey
	
	public void save(){
		String fileName = "C:/Musikhero/Chords/No name.txt";
		int count = 0;
		String firstLine = "";
		
		//get fileName from first line
		for (String line : txtChord.getText().split("\\n")){
			
			if (count == 0){
				firstLine = line;
				firstLine = firstLine.replaceFirst("\\s+$", "");
			}
			count++;
		}
		
		
		//save chords
		fileName = "C:/Musikhero/Chords/" + firstLine;
		fileName = fileName + ".txt";
		
		
		try {
			
            File newTextFile = new File(fileName);

            FileWriter fw = new FileWriter(newTextFile);
            
            for (String line : txtChord.getText().split("\\n")){
				
            	fw.write(line + "\n");
				
			}
            
            fw.close();

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
		System.out.println("saved chords to " + fileName);
		
		//save tabs
		fileName = "C:/Musikhero/Tabs/" + firstLine;
		fileName = fileName + ".txt";
		
		try {
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			out.write( txtTab.getText() );
			out.close();

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
		
		System.out.println("saved tabs to " + fileName);
		
		boolean isInList = false;
		
		for(int i = 0; i < lstModelArchive.getSize(); i++) {
			
			String checkList = (String) lstModelArchive.getElementAt(i);
			
			if (firstLine.equals(checkList)){
				System.out.println(lstModelArchive.getElementAt(i));
				isInList = true;
			}

		 }
		
		if (!isInList){
			lstModelArchive.addElement(firstLine);
		}
		
		saveLineup();
	}
	
	public void setCurrentKey(){
		
		boolean done = false;
        String firstChord = "C";
        
        //get key of song
        for (String line : txtChord.getText().split("\\n")){
			
        	
			String lastTwo = "";
		
			if (!done){
    			if (line != null && line.length() >= 1) {  
    			    lastTwo = line.substring(line.length() - 1);
    			    
    			    if (lastTwo.equals("\r") || lastTwo.equals("\n")){
    					line = line.substring(0, line.length()-1);
    				}
    			}
    			
    			line = line + "      ";
    			
    			
    			if (line.substring(0, 4).equals("KEY:")){
    				line = line.substring(4);
    			}
    			
				if (c.isChord(line)){
					
					for (int i = 0; i < line.length(); i++){
						if (!done){
    						if (c.chordToInt(Character.toString(line.charAt(0))) != -1){
    							
    							firstChord = Character.toString(line.charAt(0));
    							System.out.println("key is " + firstChord);
    							done = true;
    						}
						}
					}
					
				}
			}
			
		}// end for
       
        currentKey = firstChord;
	}
	
	public void saveLineup(){
		String lineup = "";
		for (int i = 0;i < lstModelLineup.getSize();i++){
			lineup = lineup + (String)lstModelLineup.getElementAt(i) + "\r\n";
		}
		
		try {
			File file = new File("C:/Musikhero/Lineup.txt");
			FileWriter fileWriter = new FileWriter(file);
			
			fileWriter.write(lineup);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}//end main
