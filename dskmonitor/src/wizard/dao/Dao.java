package wizard.dao;


import java.sql.Connection;
import java.util.List;

import wizard.sql.DskFileContentSelect;
import wizard.sql.DskFileSelect;
import wizard.sql.SelectItem;
import wizard.sql.SelectItemStatement;
import net.is_bg.ltf.db.common.AbstractMainDao;
import net.is_bg.ltf.db.common.BindVariableData;
import net.is_bg.ltf.db.common.interfaces.IAbstractModel;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactory;

public class Dao extends AbstractMainDao{

	public Dao(IConnectionFactory connectionFactory) {
		super(connectionFactory);
		// TODO Auto-generated constructor stub
	}
	
	public  SelectItem[] executeSelectItems(String sqlString,  BindVariableData params, int isolationLevel) {
		SelectItemStatement selectItem = new SelectItemStatement(sqlString, params);
		dbExecutor.execute(selectItem, isolationLevel);
		return selectItem.getSelectItems();
	}
	

	/**
	 * Get municipalities for combo criteria box.
	 * @return
	 */
	public SelectItem[] getMunicipalities(){
		String sql = " select -1 municipality_id,   '----'  fullname\n"+
				" union all\n"+
				" select distinct(c.municipality_id) ,\n"+
				" m.fullname from config c join municipality m on c.municipality_id = m.municipality_id order by  fullname\n";
		return executeSelectItems(sql,  new BindVariableData(), Connection.TRANSACTION_READ_COMMITTED);
	}

	/**
	 * Select Dsk Files by criteria.
	 * @param municipality_id
	 * @param company_id
	 * @param kinddebtreg_id
	 * @return
	 */
	public List<IAbstractModel> getFiles(long municipality_id, long company_id, long kinddebtreg_id){
		DskFileSelect sel = new DskFileSelect(municipality_id, company_id, kinddebtreg_id);
		execute(sel);
		return sel.getResult();
	}
	
	/**
	 * Get the file content.
	 * @param fileId
	 * @param fileTypeId
	 * @param header
	 * @return
	 */
	public List<String> getFileContent(long fileId, long fileTypeId, String header){
		DskFileContentSelect sel = new DskFileContentSelect(fileId, fileTypeId, header);
		execute(sel);
		return sel.getResult();
	}
	
}
