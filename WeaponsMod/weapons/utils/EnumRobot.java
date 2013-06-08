package weapons.utils;

import weapons.client.rendering.RenderUtils;


public enum EnumRobot
{
	MaxPower(1, 10000, "Max Power"),
	StartPower(1, 0, "Starting Power"),
	InventorySize(4, 8, "Inventory Size"),
	R_BaseColor("Render Part Base Color"),
	R_BBase("Render Part Base"),
    R_BBlock("Render Part Shiled"),
    R_BEye(255, 182, 56, 255, "Render Part Eye"),
    R_BThrust(0, 255, 255, 255, "Render Part Thrust"),
    R_BTurnbottom("Render Part Turn Bottom"),
    R_LArm("Render Part Left Arm"),
    R_LZap(0, 255, 255, 255, "Render Part Left Arm Tazer"),
    R_RArm("Render Part Right Arm"),
    R_RZap(0, 255, 255, 255, "Render Part Right Arm Tazer"),
	R_ColorR(212, "Render Color Red"),
	R_ColorG(215, "Render Color Green"),
	R_ColorB(219, "Render Color Blue");
	private int color;
	private int r;
	private int g;
	private int b;
	private int alpha;
	private boolean hascolor = true;
	private Type type;
	private int speacalvalue;
	private String name;
	EnumRobot(int color, String name){
		this.color = color;
		this.type = Type.Type_Color;
		this.name = name;
	}
	EnumRobot(int type, int specailvalue, String name){
		switch(type){
			case(0):{
				this.type = Type.Type_Generic;
				this.speacalvalue = specailvalue;
				break;
			}
			case(1):{
				this.type = Type.Type_Power;
				this.speacalvalue = specailvalue;
				break;
			}
			case(2):{
				this.type = Type.Type_Constant;
				this.speacalvalue = specailvalue;
				break;
			}
			case(4):{
				this.type = Type.Type_Inventory;
				this.speacalvalue = specailvalue;
				break;
			}
			default:{
				this.type = Type.Type_Generic;
				this.speacalvalue = specailvalue;
				break;
			}
		}
		this.name = name;
	}
	EnumRobot(String name){
		this.color = 0;
		hascolor = false;
		this.type = Type.Type_Color;
		this.name = name;
	}
	EnumRobot(int r, int g, int b, int alpha, String name){
		this.r = r;
		this.g = g;
		this.b = b;
		this.alpha = alpha;
		this.type = Type.Type_Color;
		this.name = name;
	}
	public int getValue(){
		return this.speacalvalue;
	}
	public Type getType(){
		return this.type;
	}
	public int getR(){
		return this.r;
	}
	public int getG(){
		return this.g;
	}
	public int getB(){
		return this.b;
	}
	public int getColor(){
		return this.color;
	}
	public String getName(){
		return this.name;
	}
	
	@Override
	public String toString()
	{
		StringBuilder string = new StringBuilder();
		string.append(super.toString());
		string.append("\n");
		string.append(name);
		return string.toString();
	}
	public void color(){
		if(this.hascolor){
			RenderUtils.color(r, g, b, alpha);
		}
		else{
			RenderUtils.color(EnumRobot.R_ColorR.getColor(), EnumRobot.R_ColorG.getColor(), EnumRobot.R_ColorB.getColor(), 255);
		}
	}	
}
