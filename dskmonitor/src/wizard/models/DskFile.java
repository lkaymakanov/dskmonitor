package wizard.models;

import java.math.BigDecimal;
import java.util.Date;


public class DskFile extends AbstractModel{

	private static final long serialVersionUID = -6223992799438765029L;
	private User user = new User();
	private KindDebtReg kinddebtreg = new KindDebtReg(-1);
	private Municipality municiplaity = new Municipality();
	private Date createDate;
	private Date beginDate;
	private Date endDate;
	
	private String fileName;
	private String contragent;
	private String iban;
	private DskFileTypeReg filereg = new DskFileTypeReg();
	private BigDecimal sum = new BigDecimal(0);
	private String header;
    
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContragent() {
		return contragent;
	}
	public void setContragent(String contragent) {
		this.contragent = contragent;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public DskFileTypeReg getFilereg() {
		return filereg;
	}
	public void setFilereg(DskFileTypeReg filereg) {
		this.filereg = filereg;
	}
	public User getUser() {
		return user;
	}
	public KindDebtReg getKinddebtreg() {
		return kinddebtreg;
	}
	public Municipality getMuniciplaity() {
		return municiplaity;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

}
