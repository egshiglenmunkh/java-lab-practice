package java_labs;

public class StringMethod {
	
	//Returns the string created by adding 's2' after position 'index' of 's1'.
    static String addString(String s1, int index, String s2){
        String s11 = s1.substring(0, index + 1);
        String s12 = s1.substring(index + 1, s1.length());
        String s22 = s11.concat(s2.concat(s12)); 
        
        return s22;
        
    }
    
    //Returns reversed string of 's'.
    static String reverse(String s){
    	
    	String s1 = "";
    	Integer a = s.length() - 1;
    	
    	for (; a >= 0 ; a --) {
    	s1 += (s.charAt(a));
    }
    	return s1;
    }
    //Returns a string with all 's2's removed from 's1'.
    static String removeString(String s1, String s2){
    	
    	StringBuilder s3 = new StringBuilder(s1);
    	int index;
    	
    	while ((index = s3.indexOf(s2)) != -1) {
    		s3.delete(index, index + s2.length());
    	}
    	
    	return s3.toString();   
    }
    
    public static void main(String[] args){
        System.out.println(addString("0123456", 3, "-"));
        System.out.println(reverse("abc"));
        System.out.println(removeString("01001000", "00"));   
    }

}
