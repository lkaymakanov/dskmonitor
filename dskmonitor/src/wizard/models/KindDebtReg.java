package wizard.models;


public class KindDebtReg extends AbstractModel {

	private static final long serialVersionUID = 7012589540395486765L;
	
	private String code;
	private String fullName;
	private String name;
	private String acc;
	private int active;
	private int levelPar;
	
	public KindDebtReg(long id) {
		super(id);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAcc() {
		return acc;
	}
	public void setAcc(String acc) {
		this.acc = acc;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getLevelPar() {
		return levelPar;
	}
	public void setLevelPar(int levelPar) {
		this.levelPar = levelPar;
	}

}
