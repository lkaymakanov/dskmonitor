package wizard.worker;

import javax.swing.SwingWorker;

import wizard.Wizard;

public class GuiWorker extends SwingWorker<String, Void>{
	
	private TASK  task;

	Wizard w = Wizard.getWizard();
	//constructor
	public GuiWorker( TASK task){
		this.task = task;
	}
	
	//things done in background
	@Override
    public String doInBackground() {
		doProcess();
        return "Done";
    }

	
	//do the actual work
	private  void doProcess(){
		System.out.println("Entered in doProcess...");
		if(task == TASK.SEARCH_DSK_FILES) w.getDskPage().searchDskFiles();
		if(task == TASK.GET_FILE_CONTENT) w.getDskPage().getFileContent();
	}
	
	
	
	//finished processing
    @Override
    public void done() {
        try {
        	System.out.println("Entered in done...");
        	//enable buttons on finish
    		if(task == TASK.SEARCH_DSK_FILES) w.getDskPage().setGridTableData();
    		if(task == TASK.GET_FILE_CONTENT) w.getDskPage().printFileContentToConsole();
        } 
        catch (Exception ignore) 
        {
        	
        }
    }
    
    
    
    
    
}