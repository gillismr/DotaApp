package model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemFlatBonus {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Enumerated(EnumType.STRING)
	private FlatBonusType type;
	private double value;
	
	@ManyToOne
	@JoinColumn(name="ITEM_ID")
	private Item source;

	public ItemFlatBonus() {
		super();
	}

	public ItemFlatBonus(int id, FlatBonusType type, double value, Item source) {
		super();
		this.id = id;
		this.type = type;
		this.value = value;
		this.source = source;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FlatBonusType getType() {
		return type;
	}

	public void setType(FlatBonusType type) {
		this.type = type;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Item getSource() {
		return source;
	}

	public void setSource(Item source) {
		this.source = source;
	}
	
	
}
