package wizard.models;

import java.io.Serializable;

import net.is_bg.ltf.db.common.interfaces.IAbstractModel;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractModel.
 */
public abstract class AbstractModel  implements IAbstractModel, Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2245832058738573909L;
	
	/** The id. */
	private long id = -1;
	
	/** The index. */
	private long index = 0;             //index of model
	
	/** The index cnt. */
	private static long indexCnt = 0;   //index counter 
	
	
	/**
	 * Instantiates a new abstract model.
	 */
	public AbstractModel() {
		// default constructor
		index = indexCnt++; 
	}
	
	/**
	 * Instantiates a new abstract model.
	 *
	 * @param id the id
	 */
	public AbstractModel(long id) {
		this.id = id;
		index = indexCnt++; 
	}

	/* (non-Javadoc)
	 * @see net.is_bg.ltf.db.common.interfaces.IAbstractModel#getId()
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * get the index of model.
	 *
	 * @return the index
	 */
	public  long getIndex(){
		return index;
	}
	
	/**
	 * Add 1 to index counter.
	 *
	 * @return the next index
	 */
	public static long getNextIndex(){
		return indexCnt++; 
	}
	
	/**
	 * set the index of model.
	 *
	 * @param index the new index
	 */
	public void setIndex(long index) {
		this.index = index;
	}
}
