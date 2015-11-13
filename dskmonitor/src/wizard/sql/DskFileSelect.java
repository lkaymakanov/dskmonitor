package wizard.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wizard.models.DskFile;

import net.is_bg.ltf.db.common.SelectSqlStatement;
import net.is_bg.ltf.db.common.interfaces.IAbstractModel;



public class DskFileSelect extends SelectSqlStatement{
	
	private long company_id;
	private long kinddebtreg_id;
	private long municipality_id;
	
	private List<IAbstractModel> result = new ArrayList<IAbstractModel>();

	public DskFileSelect(long municipality_id, long company_id, long kinddebtreg_id){
		this.municipality_id = municipality_id;
		this.company_id = company_id;
		this.kinddebtreg_id = kinddebtreg_id;
	}
	
	
	@Override
	protected String getSqlString() {
		// TODO Auto-generated method stub
		String sql = " select --*\n"+
				" file.bankfile_id id, file.filename fname,\n"+
				" file.create_date cdate, file.contragent contragent,\n"+
				" file.iban iban, file.end_date edate, file.sum sum,  file.rowcnt rowcnt, file.headerrow hdr,\n"+
				
				" --kdr\n"+
				" kd.kinddebtreg_id, kd.fullname kdrname,\n"+
				
				" --user\n"+
				" u.user_id u_id, u.username uname, u.fullname  ufname,\n"+
				
				" --municipality\n"+
				" m.municipality_id m_id , m.fullname mfname,\n"+
				
				" --file type\n"+
				" file.bankfiletypereg_id ftypereg_id,\n"+
				" reg.description ftype \n"+

				" from bankfile file \n"+
				" join users u on file.user_id = u.user_id\n"+
				" join municipality m on file.municipality_id = m.municipality_id\n"+
				" join kinddebtreg kd on file.kinddebtreg_id = kd.kinddebtreg_id\n"+
				" join bankfiletypereg reg on file.bankfiletypereg_id = reg.bankfiletypereg_id\n"+
				" where 1=1\n"+
				" and file.company_id = ?\n";
				if(municipality_id > 0  ) sql+= " and file.municipality_id = ?\n";
				if(kinddebtreg_id > 0) sql+= " and file.kinddebtreg_id = ? ";
		
		return sql;

	}
	
	@Override
	protected void retrieveResult(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		while (rs.next()) {
			
			DskFile f = new DskFile();
			f.setId(rs.getLong("id"));
			f.setFileName(rs.getString("fname"));
			f.setCreateDate(rs.getDate("cdate"));
			f.setEndDate(rs.getDate("edate"));
			f.setIban(rs.getString("iban"));
			f.setSum(rs.getBigDecimal("sum"));
			f.setContragent(rs.getString("contragent"));
			f.getMuniciplaity().setId(rs.getLong("m_id"));
			f.getMuniciplaity().setFullName(rs.getString("mfname"));
			f.getUser().setId(rs.getLong("u_id"));
			f.getUser().setUsername(rs.getString("uname"));
			f.getUser().setFullName(rs.getString("ufname"));
			f.getFilereg().setId(rs.getLong("ftypereg_id"));
			f.getFilereg().setDescription(rs.getString("ftype"));
			f.setHeader(rs.getString("hdr"));
			
			result.add(f);
		}
	}
	
	
	@Override
	protected void setParameters(PreparedStatement prStmt) throws SQLException {
		// TODO Auto-generated method stub
		bindVarData.setLong(company_id);
		if(municipality_id > 0  ) bindVarData.setLong(municipality_id);
		if(kinddebtreg_id > 0  ) bindVarData.setLong(kinddebtreg_id);
		bindVarData.setParameters(prStmt);
	}


	public List<IAbstractModel> getResult() {
		return result;
	}
	
	

}
