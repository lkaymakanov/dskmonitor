package wizard;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.is_bg.ltf.db.common.ConnectionProperties;
import net.is_bg.ltf.db.common.DBConfig;
import net.is_bg.ltf.db.common.impl.logging.LogFactorySystemOut;
import swing.utils.Utils;
import swing.utils.WizardButtons;
import wizard.dao.Dao;
import wizard.factories.ConnectionFactory;
import wizard.factories.ElapsedTimerFactory;
import wizard.factories.VisitFactory;
import wizard.pages.ConnectionPage;
import wizard.pages.DskFilePage;
import wizard.pages.Page;
import wizard.pages.PageCallBack;
import wizard.worker.GuiWorker;
import wizard.worker.TASK;



public class Wizard extends JFrame  {
	
	

	private static final long serialVersionUID = 1782579312920885244L;
	private final static String ENCODING = "UTF-8";    //set the file encoding
	
	CardLayout card;
	JPanel wizpanel;
	
	//action listener 
	ActionListenHandler wizListener;
	
	
	
	//the pages
	ConnectionPage connPage;
	//UsersPage  userPage;
	DskFilePage dskPage;
	//UserObjectPage userObjects;
	//ExportPage exPage;
	
	
	
	//wizard sizes
	Dimension dLittle = new Dimension(400, 260);
	


	Dimension dBig = new Dimension(800, 600);
	Dimension dBtabs = new Dimension(450, 490);
	
	//the handler of page buttons click
	PageCallBackHandler pageCallBack;
	
	//the navigation buttons
	WizardButtons buttons = new WizardButtons();
	
	//navigation button panel
	JPanel buttonp = new JPanel();
	
	//the list with pages in wizard
	List<Page> pages = new ArrayList<Page>();
	
	
	
	//the wizard itself
	private static Wizard wizard = null;
	private Dao dao;
	
	

	
	
	//public  DBTYPE dbType = DBTYPE.ORCL;
	
	//WHEHER WE SAFE ALL IN IN ONE FILE OR EACH OBJECT IN SEPARATE FILE
	static final String   FILE_NAME = "usr_obj_";
	
	
	//constructor
	public Wizard() {
		// TODO Auto-generated constructor stub
		super("БДСК мониторинг ");
	}

	//let it be a singleton pattern
	public static Wizard getWizard(){
		if(wizard == null) wizard = new Wizard();
		return wizard;
	}
		
	
	
	//Program Entry 
	public static void main(String []args){
		
		
		System.setProperty("file.encoding", ENCODING);
		
		//use the system style for GUI
	    final boolean  SYSTEM_LOOK_AND_FEEL = false;
		try {
			if(SYSTEM_LOOK_AND_FEEL)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Wizard.getWizard();  //invoke this to initialize wizard reference
		wizard.init();
	}
	
	
	//set sizes & show wizard frame
	private void showWiz(){
		setSize(dLittle);
		setResizable(true);
		setVisible(true);
	}
	
	//init wizard
	private void init(){
		wizListener = new ActionListenHandler();
		
		wizpanel = new JPanel();
		
		card = new CardLayout();   //use card layout to switch panels
		wizpanel.setLayout(card);
		getContentPane().add(wizpanel);
		
		//this isn't probably the best GUI face made :)
	    getContentPane().add(BorderLayout.SOUTH, buttonp);
	    
	    //register buttons listener
	    buttons.AddActionListenter(wizListener);
	    
	    //init to first page
	    getCallBack().setStateFirstPage();
	    
	    //add the buttons to panel
		buttonp.add(buttons.getBtnBack());
		buttonp.add(buttons.getBtnConnect());
		buttonp.add(buttons.getBtnNext());
		
		
		//create pages
		connPage = new ConnectionPage();
		dskPage  = new DskFilePage();
		
		//userPage = new UsersPage();
		//exPage = new ExportPage();
		//userObjects = new UserObjectPage();
		
	
		
		
		//add pages to page list
		pages.add(connPage);
		pages.add(dskPage);
		//pages.add(userPage);
		//pages.add(userObjects);	
		//pages.add(exPage);
		
		//add pages to wizard
		for(int i = 0; i< pages.size() ; i++){
			wizpanel.add(String.valueOf(i), pages.get(i));
		}
		
		//init database objects interfaces
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//init database factories
		DBConfig.initDBConfig(new LogFactorySystemOut(),  new VisitFactory(), new ConnectionFactory(), new ElapsedTimerFactory());
		
		//init dao
		dao = new Dao(DBConfig.getConnectionFactory());
		
		//show the wizard
		showWiz();
	}
	
	//navigation button is pushed  -- handler
	public  void wizbuttonPushed(String btnName){
		
		//hadle connect
		if(btnName.equals("btnConnect")){
			btnConnectCliked();
		}
		
		//back button pushed
		if(btnName.equals("btnBack")){
			btnBackCliked();
		}
		
		
		//next button pushed
		if(btnName.equals("btnNext")){
			btnNextCliked();
		}
	}
	
	
	
	/**
	 * Set connection from properties.
	 * @param properties
	 */
	public static void setConnectionProperties(ConnectionProperties prop){
		getWizard().getConnPage().setDbName(prop.getName_to_display());
		((ConnectionFactory)DBConfig.getConnectionFactory()).setProperties(prop);
	}
	
	
	//handle btn connect
	private void btnConnectCliked(){
		System.out.println("btnConnect clicked... ");
		ConnectionProperties prop =  connPage.getConnectionProperties();
		
		//set the connection properties in connection factory
		setConnectionProperties(prop);
		
		//load municipalities
		try{
			dskPage.getComboMunicipality().initCombo(Arrays.asList(dao.getMunicipalities()));
		}catch (Exception e) {
			// TODO: handle exception
			Utils.showErrMsg(this, e.getMessage());
			return;
		}
		
		//goto page 2
		goToNextPage();
		
		//start get users  TASK
		//GuiWorker wk = new GuiWorker(this, TASKS.GET_USERS);
		//wk.execute();
			
	}
	

	//handle btn back
	private void btnBackCliked(){
		System.out.println("btn Back clicked... ");
		
		//we are on tabs
		if(Page.getCurrenPage() == 1){
			setSize(dLittle); //set small size
		}
		
		//goto previous page
		card.previous(wizpanel);
		
		//set state to previous page
		getCallBack().toPrevious();
	}
	
	//handle btn next
	private void btnNextCliked(){
		System.out.println("btn Next clicked... ");
		
		//we reached last page
		if(Page.getCurrenPage() == pages.size() - 1){
			System.out.println("Last Page... ");
			
			//DO THE EXPORT HERE
			//start export TASK
			GuiWorker wk = new GuiWorker(TASK.EXPORT);
			wk.execute();
		}
		
		//we are on users page
		if(Page.getCurrenPage()  == 1){
			
			//set selectedall by default
			//userObjects.setSelectedAll();
			
			//start get user Objects TASK
			/*
			GuiWorker wk = new GuiWorker(TASKS.GET_USEROBJECTS);
			wk.execute();*/
		}
		
		//we are on usersObject page
		if(Page.getCurrenPage()  == 2){
			//goto export page
			goToNextPage();	
		}
}
	
	//now invoke in the thread routine
	public void goToNextPage(){
			//check for error first
			//if(isErr())  return;
	
			//goto next  page
			card.next(wizpanel);
			
			//set state to next page
			getCallBack().toNext();
	}

	
	//getters & setters
	public ActionListenHandler getWizListener() {		
		return wizListener;
	}


	public ConnectionPage getConnPage() {
		return connPage;
	}



	
	//pages call back implementation  class
	class PageCallBackHandler implements PageCallBack{
		
		//remove all buttons from panel
		private  void removeAllButtons(){
			buttons.getBtnConnect().setVisible(false);
			buttons.getBtnBack().setVisible(false);
			buttons.getBtnNext().setVisible(false);
		}
		
		//we go to first page
		private void setStateFirstPage(){
			removeAllButtons();
			
			//add only  connect button
			buttons.getBtnConnect().setVisible(true);
		}
		
		//we go to some page between first and last page
		private void setStateMiddlePage(){
			//remove connect  button
			removeAllButtons();
			buttons.getBtnBack().setVisible(true);
			buttons.getBtnNext().setVisible(false);
			buttons.getBtnNext().setText("Next>>");
			
			setSize(dBig);
		}
		
		//we go to last page
		private void setStateLastPage(){
			setStateMiddlePage();
			buttons.getBtnNext().setText("Export>>");
		}
		

		//go to next page
		@Override
		public void toNext() {
			// TODO Auto-generated method stub
			Page.nextPage();   //increase page counter
			if(Page.getCurrenPage() > 0 && Page.getCurrenPage() <  pages.size() - 1){
				setStateMiddlePage();
			}else{
				setStateLastPage();
			}
		}

		
		//go to previous page
		@Override
		public void toPrevious() {
			// TODO Auto-generated method stub
			Page.previousPage();   //decrease page counter
			if(Page.getCurrenPage() == 0){
				setStateFirstPage();
			}else{
				setStateMiddlePage();
			}
		}

	
		
	}
	

	//implementation of pages call back interface
	public  PageCallBackHandler getCallBack (){
		if(pageCallBack == null)  return new PageCallBackHandler();
		return pageCallBack;
	}



    
    
    public DskFilePage getDskPage() {
		return dskPage;
	}

	public void btnDskSearch(){
    	GuiWorker wk = new GuiWorker(TASK.SEARCH_DSK_FILES);
		wk.execute();
    }
	
	
	public void btnShowFile(){
		GuiWorker wk = new GuiWorker(TASK.GET_FILE_CONTENT);
		wk.execute();
	}


/*	//GUI worker interface
	@Override
	public void getUsers() {
		System.out.println("get Users  worker started...");
		// TODO Auto-generated method stub
		//select the users from data base 

		//set users in user page
		//userPage.getCombousrs().initCombo(users);
		
		
		//go to next page
		goToNextPage();
	}

	
	
	
	

	

	@Override
	public void enableButtons(boolean b) {
		// TODO Auto-generated method stub
		buttons.Enable(b);
	}
	*/

}



