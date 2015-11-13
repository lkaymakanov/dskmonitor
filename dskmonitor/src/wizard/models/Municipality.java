package wizard.models;


public class Municipality extends AbstractModel implements Cloneable {
	private static final long serialVersionUID = -9189590568841813208L;
	private String fullName;
	private String name;
	private String code;
	private String ebk;
	private String url;
	private long parentMunicipalityId;
	
	//private Province province = new Province();
	
	public Municipality(long id, 
						String fullName, 
						String name, 
						String code,
						String ebk, 
						String url, 
						long parentMunicipalityId
						) {
		super(id);
		this.fullName = fullName;
		this.name = name;
		this.code = code;
		this.ebk = ebk;
		this.url = url;
		this.parentMunicipalityId = parentMunicipalityId;
	}
	
	public Municipality(){
		//nothing
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEbk() {
		return ebk;
	}

	public void setEbk(String ebk) {
		this.ebk = ebk;
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getParentMunicipalityId() {
		return parentMunicipalityId;
	}

	public void setParentMunicipalityId(long parentMunicipalityId) {
		this.parentMunicipalityId = parentMunicipalityId;
	}

	
}
