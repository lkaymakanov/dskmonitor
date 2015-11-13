package wizard.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import net.is_bg.ltf.db.common.BindVariableData;
import net.is_bg.ltf.db.common.SelectSqlStatement;

public class SelectItemStatement extends SelectSqlStatement{

	
private String sqlString;

	
	private SelectItem[] selectItems;

	public SelectItemStatement(String sqlString, BindVariableData aBindVarData) {
		super();
		this.sqlString = sqlString;
		this.bindVarData = aBindVarData;
	}
	
	public SelectItemStatement(String sqlString) {
		super();
		this.sqlString = sqlString;
	}

	@Override
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		bindVarData.setParameters(prStmt);
	}
	
	@Override
	protected String getSqlString() {
		return sqlString;
	}

	@Override
	protected void retrieveResult(ResultSet rs) throws SQLException {
		List<SelectItem> itemList = new ArrayList<SelectItem>();
		while (rs.next()) {
			SelectItem selItem = new SelectItem(rs.getString(1), rs.getString(2));
			itemList.add(selItem);
		}
		selectItems = itemList.toArray(new SelectItem[itemList.size()]);
	}

	public SelectItem[] getSelectItems() {
		return selectItems;
	}

}
