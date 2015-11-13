package wizard.sql;

public class SelectItem {
	
	String id;
	String value;
	
	public SelectItem(String id, String value){
		this.id = id;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getId() {
		return id;
	}
	
}