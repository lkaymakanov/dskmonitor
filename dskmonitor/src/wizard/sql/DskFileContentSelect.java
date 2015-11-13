package wizard.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.is_bg.ltf.db.common.SelectSqlStatement;

public class DskFileContentSelect extends SelectSqlStatement{
	
	long fileId; 
	long fileTypeId;
	
	private List<String> result = new  ArrayList<String>();
	
	public DskFileContentSelect(long fileId, long fileTypeId, String header) {
		// TODO Auto-generated constructor stub
		this.fileId = fileId;
		this.fileTypeId = fileTypeId;
		result.add(header);
	}
	
	@Override
	protected String getSqlString() {
		// TODO Auto-generated method stub
		String sql = " select fc.filerow \n"+
				" from bankfile f\n" +
				//if(fileTypeId == 1) sql+=
				" join bankfilecnt fc on fc.bankfile_id=  f.bankfile_id\n";
				//else sql+=" join dsk_pkg.dskpaid fc on fc.dskfile_id=  f.dskfile_id\n";
				sql+=" where 1=1\n"+
				" and f.bankfile_id = ? \n"+
				" order by fc.rownum;";

		return sql;
	}
	
	@Override
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		// TODO Auto-generated method stub
		bindVarData.setLong(fileId);
		
		bindVarData.setParameters(prStmt);
	}

	@Override
	protected void retrieveResult(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		while (rs.next()) {
			result.add(rs.getString(1));
		}
	}

	public List<String> getResult() {
		return result;
	}
	
	
}
