package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name="Component.findAll", query="SELECT c FROM Component c"),
	@NamedQuery(name="Component.dropAll", query="DELETE FROM Component c")})
public class Component {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="COMPONENT_ITEM")
	private Item isUsed;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="RESULT_ITEM")
	private Item toMake;

	public Component() {
		super();
	}

	public Component(int id, Item isUsed, Item toMake) {
		super();
		this.id = id;
		this.isUsed = isUsed;
		this.toMake = toMake;
	}

	public Component(Item isUsed, Item toMake) {
		super();
		this.isUsed = isUsed;
		this.toMake = toMake;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Item getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Item isUsed) {
		this.isUsed = isUsed;
	}

	public Item getToMake() {
		return toMake;
	}

	public void setToMake(Item toMake) {
		this.toMake = toMake;
	}
	
	
	
}
