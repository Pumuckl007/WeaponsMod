package weapons.utils;

public enum Type
{
	Type_Color("Type Color"),
	Type_Power("Type Power"),
	Type_Constant("Type Constant"),
	Type_Inventory("Type Invetnory"),
	Type_Generic("Type Generic");
	private String name;
	Type(String name){
		this.name = name;
	}
	public String toString(){
		return this.name;
	}
}
