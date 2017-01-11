
public class Tabs {
	
	public static String transposeTab(String toConv, int halfSteps){
		
		String add = "";
		String conv = "";
		
		//go through each char
		for (int i = 0; i < toConv.length(); i++){
			
			add = Character.toString(toConv.charAt(i));
			
			if (i < (toConv.length() - 2)){
				if ((Character.isDigit(toConv.charAt(i))) && (Character.isDigit(toConv.charAt(i+1)))){
					add = toConv.substring(i, i+2);
					i++;
				}
			}
			
			if (isInteger(add)){
				
				int c = Integer.parseInt(add);
				c = c + halfSteps;
				add = Integer.toString(c);
			}
			
			conv = conv + add;
			
		}//end for 
		
		return conv;
	}//end transpose
	
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}//end isInteger
}
                                                                                                                                                                                                                      ///////////////////////////// / 