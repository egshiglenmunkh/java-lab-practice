
package java_labs;
import java.util.Scanner;
public class SimpleCalculator {
	public static void main(String args[]) {
		
		try (Scanner scanner = new Scanner(System.in);){
			System.out.println("Enter calculation: ");
			String calc = scanner.nextLine();
			String num = calc.replaceAll("[^0-9]", " " );
			String [] nmbrs = num.split(" ");
			String num1 = nmbrs[0];
			String num2 = nmbrs[1];
			int n1 = Integer.parseInt(num1);
			int n2 = Integer.parseInt(num2);
			String sign = calc.replaceAll("[0-9]", "");			
			
			if ((n1 == 0 || n2 == 0) && sign.equals("+") ) {
				throw new AddZeroException();
			}
			if ((n1 == 0 || n2 == 0) && sign.equals("-")) {
				throw new SubtractZeroException();
			}
			if (n1 < 0 || n1 > 1000 || n2 < 0 || n2 > 1000) {
				throw new OutOfRangeException();
			}
			
			int sum_sub = 0;
			if (sign.equals("+")) {
				sum_sub = n1 + n2;
			}else if (sign.equals("-")) {
				sum_sub = n1 - n2;
			}
			System.out.println(sum_sub);
		}
		catch (OutOfRangeException ore){
			System.out.println("OutOfRangeException");
		}
		catch (AddZeroException aze) {
			System.out.println("AddZeroException");
		}
		catch (SubtractZeroException sze) {
			System.out.println("SubtractZeroException");
		}
	}
}
class OutOfRangeException extends Exception{
	public OutOfRangeException() {
		super();
	}
}
class AddZeroException extends Exception {
	public AddZeroException() {
		super();
	}
}
class SubtractZeroException extends Exception{
	public SubtractZeroException() {
		super();
	}
}