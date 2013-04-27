package weapons.client;

public class RenderIds {

    public static int test;

	public static void setRenderId(int id, int varid){
		System.out.println("render id regeiteded to " + id + " to render id " + varid);
    	switch(varid){
    		case 1:{
    			id = test;
    			break;
    		}
    	}
    }
}
