package gaml.extension.unity.commands.wizard;


public class DataGeometries {
	private String speciesName;
	private Double height = 1.0;
	private Double buffer = 0.0;
	private Boolean hasCollider = false;
	private String tag = "";
	private Boolean is3D = true;
	private Boolean isSelectable = false;
	private Boolean isGrabable = false;
	private String color = "#gray";
	
	
	public DataGeometries() {
		super();
	}
	public String getSpeciesName() {
		return speciesName;
	}
	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Boolean getHasCollider() {
		return hasCollider;
	}
	public void setHasCollider(Boolean hasCollider) {
		this.hasCollider = hasCollider;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Double getBuffer() {
		return buffer;
	}
	public void setBuffer(Double buffer) {
		this.buffer = buffer;
	}
	public Boolean getIs3D() {
		return is3D;
	}
	public void setIs3D(Boolean is3d) {
		is3D = is3d;
	}
	public Boolean getIsSelectable() {
		return isSelectable;
	}
	public Boolean getIsGrabable() {
		return isGrabable;
	}
	public void setIsGrabable(Boolean isGrabable) {
		this.isGrabable = isGrabable;
	}
	public void setIsSelectable(Boolean isSelectable) {
		this.isSelectable = isSelectable;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}