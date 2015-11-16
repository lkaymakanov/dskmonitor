package wizard.pages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.is_bg.ltf.db.common.DBConfig;
import net.is_bg.ltf.db.common.interfaces.IAbstractModel;

import swing.utils.GraphicsConsole;
import swing.utils.JAbstractCombo;
import wizard.Wizard;
import wizard.dao.Dao;
import wizard.datatable.JFileTable;
import wizard.models.DskFile;
import wizard.sql.SelectItem;

public class DskFilePage extends Page{

	private static final long serialVersionUID = -1844219850238937990L;
	private JAbstractCombo comboMunicipality = new JComboCriteria();
	private JAbstractCombo comboFavour = new JComboCriteria();
	public GraphicsConsole 	console;
	private JButton searchButton = new JButton("Търси...");
	private JFileTable dataTable = new JFileTable(null, new Dimension(760, 70));
	List<IAbstractModel>  tableData;
	List<String> fileContent;
	
	public DskFilePage() {
		// TODO Auto-generated constructor stub
		initPage();
	}
	

	@Override
	protected void initPage() {
		// TODO Auto-generated method stub
		searchButton.setName("searchDsk");
		searchButton.addActionListener(Wizard.getWizard().getWizListener());
		
		//init favours
		initComboFavour();
		
		//add search panel
		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.WEST;
		p.add(createSearchPanel(), gc);
		
		//data table
		gc.gridx = 0;
		gc.gridy = 1;
		gc.insets = new Insets(10, 0, 0, 0);
		gc.anchor = GridBagConstraints.CENTER;
		p.add(dataTable.getScrollPane(), gc);
		
		//add console output
		gc.gridx = 0;
		gc.gridy = 2;
		gc.insets = new Insets(20, 0, 0, 0);
		gc.anchor = GridBagConstraints.CENTER;
		p.add(createFileContentPanel(), gc);
		
		//add panel to page
		add(p);
	}
	
	/**
	 * Init combo with favours.
	 */
	private void initComboFavour(){
		List<SelectItem> l = new ArrayList<SelectItem>();
		l.add(new SelectItem("-1", "-----"));
		l.add(new SelectItem("2", "ДНИ"));
		l.add(new SelectItem("5", "ТБО"));
		comboFavour.initCombo(l);
	}
	
	/**
	 * Create the file output console panel
	 * @return
	 */
	private JPanel createFileContentPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		panel.add(new JLabel("Файлово съдържание:"), gc);
		
		//button show file content
		JButton showCnt = new JButton("Покажи...");
		showCnt.setName("showCnt");
		showCnt.addActionListener(Wizard.getWizard().getWizListener());
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		panel.add(showCnt, gc);
		
		//the console
		JPanel consolePanel = new JPanel();
		//consolePanel.setLayout(new GridBagLayout());
		console = new GraphicsConsole(consolePanel, new Color(0,0,0), new Color(0,255,0), new Dimension(700, 300)); 
		//consolePanel.add(console);
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 2;
		panel.add(consolePanel, gc);
		return panel;
				
	}
	
	/***
	 * Creates the search panel in header.
	 * @return
	 */
	private JPanel createSearchPanel(){
		//panela za tyserne 
		JPanel searchP = new JPanel();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		
		searchP.setLayout(gb);
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.gridwidth = 2;
		gc.insets = new Insets(0, 0, 0, 0);
		searchP.add(new JLabel("Критерии за търсене:"), gc);
		
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		searchP.add(new JLabel("Община:"), gc);
		
		gc.gridx = 1;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.WEST;
		searchP.add(comboMunicipality, gc);
		
		
		gc.gridx = 2;
		gc.gridy = 1;
		gc.insets = new Insets(0, 10, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		searchP.add(new JLabel("Услуга:"), gc);
		
		gc.gridx = 3;
		gc.gridy = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		searchP.add(comboFavour, gc);
		
		gc.gridx = 4;
		gc.gridy = 1;
		gc.insets = new Insets(0, 30, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		searchP.add(searchButton, gc);
		
		return searchP;
	}
	
	
	/***
	 * Combo with criterias.
	 * @author lubo
	 *
	 */
	public class JComboCriteria extends JAbstractCombo{

	
		private static final long serialVersionUID = 4560880760009467178L;

		@Override
		public String getValToShow(int arg0) {
			Object o = getSelObject();
			if(o == null)   return "No Items";
			
			//show the criteria in combo
			return ((SelectItem)o).getValue();
		}
		
	}
	
	/**
     * Search dsk files.
     */
	public void searchDskFiles(){
    	
    	//get kinddebtreg id
    	long  kinddebtreg_id = Long.valueOf(((SelectItem)getComboFavour().getSelObject()).getId());
    	
    	//get municipality id
    	long  municipality_id = Long.valueOf(((SelectItem)getComboMunicipality().getSelObject()).getId());
    	
    	//execute select  & fill data grid
    	tableData = new Dao(DBConfig.getConnectionFactory()).getFiles(municipality_id, 103, kinddebtreg_id);
    }
	
	/**
	 * Set list in data grid.
	 */
	public  void setGridTableData() {
    	if(tableData != null && tableData.size() > 0) getDataTable().setTableDataFromObjecs(tableData);
    	else getDataTable().setTableDataFromObjecs(new  ArrayList<IAbstractModel>());
	}
	
	/**
	 * Get file content from database.
	 */
	public void getFileContent(){
		Object [][] data = dataTable.getTableModel().getData();
		int selIndex = dataTable.getSelectedRow();
		
		fileContent = null;
		
		if(selIndex != -1 && data != null){
			DskFile selFile = (DskFile)data[selIndex][0];
			fileContent = new Dao(DBConfig.getConnectionFactory()).getFileContent(selFile.getId(), selFile.getFilereg().getId(), selFile.getHeader());
		}
	}
	
	/**
	 * Show file content to graphics console.
	 */
	public void printFileContentToConsole(){
		console.clear();
		if(fileContent != null) for(int i =0; i < fileContent.size(); i++) console.writeln(fileContent.get(i));
	}


	
	public JAbstractCombo getComboMunicipality() {
		return comboMunicipality;
	}


	public JAbstractCombo getComboFavour() {
		return comboFavour;
	}


	public JFileTable getDataTable() {
		return dataTable;
	}
	
	
   

}
