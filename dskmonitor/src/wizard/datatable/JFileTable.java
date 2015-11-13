package wizard.datatable;


import java.awt.Dimension;

import javax.swing.JPanel;

import swing.utils.JAbstractColumn;
import swing.utils.JAbstractTable;
import wizard.models.DskFile;

public class JFileTable extends JAbstractTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5256474988218785197L;

	private static JAbstractColumn col [] ={
		new JAbstractColumn("Файл", 10, false),
		new JAbstractColumn("Контрагент", 10, false),
		new JAbstractColumn("ИБАН", 10, false),
		new JAbstractColumn("Потр.", 10, false),
		new JAbstractColumn("Община", 10, true),
		new JAbstractColumn("Вид файл", 10, true),
		new JAbstractColumn("Сума", 10, false)
		
	};


	
	public JFileTable(JPanel panel, Dimension d){
		super(panel, col, d);	
	}

	

	@Override
	public MyTableModel getTableModel() {
		// TODO Auto-generated method stub
		if(model == null) {
			
			model = new MyTableModel() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 6907000292662541470L;

				@Override
				public void setValueAt(Object arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				protected void printDebugData() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public Object getValueAt(int row, int col) {
					// TODO Auto-generated method stub
					if(model.getData() == null) return null;			
		        	DskFile file = (DskFile)model.getData()[row][0];
		        	if(col == 0) {return file.getFileName();}
		        	if(col == 1) {return file.getContragent();}
		        	if(col == 2) {return file.getIban();}
		        	if(col == 3) {return file.getUser().getUsername();}
		        	if(col == 4) {return file.getMuniciplaity().getFullName();}
		        	if(col == 5) {return file.getFilereg().getDescription();}
		        	if(col == 6) {return file.getSum();}
		            return null;
				}
			};
			
		}
		return model;
	}

}
