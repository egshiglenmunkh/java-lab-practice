package Java_assignments;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Battleship {
	static String[][] grid = new String[10][10];
	static String[] letters = {" ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
	static int score = 0;
	static void loadGrid(String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))){

			for (int i = 0; i < 10; i++) {
				String line = reader.readLine();
				
				if (line != null && line.length() > 0) {
					String[] linep = line.split("");
					int col = 0;
					
					for(int j = 0; j < line.length() && col < 10; j++) {
						String c = linep[j];
						if (c.equals(" ")) {
							col++;
							continue;
						}
						else {
							grid[i][col] = c;
							col++;
						}
					}
				}
			}
			
		}	catch (IOException e) {
				System.out.println("Error reading file: " + e.getMessage() + ". Placing ships randomly.");
				randomGrid();
			}		
	}
	static void displayGrid() {
		for (int j = 0; j <= 10; j++) {
			if ( j == 0) {
				System.out.print("  ");
			}
			System.out.print(letters[j]);
			System.out.print("  ");
		}
		System.out.println();
		
		for (int j = 0; j <= 10; j++) {
			System.out.print(j == 0? "   " : "-");
			System.out.print("  ");
		}
		System.out.println();
		
		for (int i = 0; i < 10; i++) {
			System.out.print(String.format("%-3d|", (i + 1)));
		
			for (int j = 0; j < 10; j++) {
					String cell = grid[i][j];
					System.out.print(String.format(" %-2s", cell.equals("-")? " " : cell));
				}
				System.out.println();	
		}
	}
	static void randomGrid(){
		Ship[] shipsToPlace = {
				new AircraftCarrier(), 
		        new Battleships(),      
		        new Battleships(),  
		        new Submarine(),        
		        new Submarine(),     
		        new Destroyer(),        
		        new PatrolBoat(),       
		        new PatrolBoat(),     
		        new PatrolBoat(),       
		        new PatrolBoat()
		};
		for (Ship ship : shipsToPlace) {
			boolean placed = false;
			while (!placed) {
				int x = (int)(Math.random() * 10);
				int y = (int)(Math.random() * 10);
				boolean horizontal = Math.random() < 0.5;
				
				if (canPlaceShip(ship, x, y, horizontal)) {
					for(int i = 0; i < ship.getSize(); i++) {
						if(horizontal) {
							grid[x][y + i] = ship.getMarker();
						}
						else {
							grid[x + i][y] = ship.getMarker();
						}
					}
					placed = true;
				}
			}
		}
	}
	static boolean canPlaceShip(Ship ship, int x, int y, boolean horizontal) {
		int size = ship.getSize();
		if(horizontal) {
			if(y + size > 10) {
				return false;
			}
		}
		else if( x + size > 10) {
			return false;
		}
		int startX = Math.max(0, x - 1);
		int endX = Math.min(9, horizontal ? x + 1 : x + size );
		int startY = Math.max(0, y - 1);
		int endY = Math.min(9, horizontal ? y + size  : y + 1);
		
		for (int i = startX; i <= endX; i++) {
			for (int j = startY; j <= endY; j++) {
				if (!grid[i][j].equals("-")) {
					return false;
				}
			}
		}
		return true;
	}
	static String play(String action, String[] letters) {
		try {
			if (action == null || action.length() < 2 ) {
				throw new InvalidCoordinateException();
			}
			
			String letter = action.replaceAll("[^A-Za-z]", "").toUpperCase();
			String number = action.replaceAll("[^0-9]", "");
			int column = Arrays.binarySearch(letters, letter) - 1;
			int row = Integer.parseInt(number) - 1;
			
			if (column > 10 || row > 10 || column < 0 || row < 0 ) {
				throw new InvalidCoordinateException();
			}
			String cell = grid[row][column];
			
			if (cell.equals("-")) {
				grid[row][column] = "X";	
				return "Miss";
			}
			else if (cell.startsWith("X") || cell.equals("X")) {
				throw new HitException();
			}
			else {
				Ship[] ships = {
						new AircraftCarrier(),
		                new Battleships(),
		                new Submarine(),
		                new Destroyer(),
		                new PatrolBoat()
				};
				for (Ship ship : ships) {
					if(ship.getMarker().equals(cell)) {
						grid[row][column] = ship.getMarked();
						score += ship.getSize();
						return "Hit " + ship.getMarker();
					}
				}
				throw new InvalidCoordinateException();
			} 	
		}
		catch (HitException e) {
			return "Try again";
		}
		catch (InvalidCoordinateException e) {
			return "Try again";
		}
	}

	
	public static void main(String[] args) {				
		try(Scanner scanner = new Scanner(System.in)){
			String input = scanner.nextLine().trim();
			String[] info = input.split("\\s+", 3);

			int bomb = Integer.parseInt(info[0]);
			String mode = info[1].toUpperCase();
			String fileName = info[2];
			
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j <10; j++) {
					grid[i][j] = "-";
				}
			}	
			try {
				if (bomb < 0) {
					throw new BombInputException();
					}
			}catch(BombInputException e) {
				System.out.println("BombInputException");
				System.exit(1);
				return;
			}catch(NumberFormatException e) {
				System.out.println("BombInputException");
				System.exit(1);
				return;
			} 
			try {
				if (!mode.equals("D") && !mode.equals("R")) {
					throw new ModeInputException();
					}
				}
				catch(ModeInputException e) {
					System.out.println("ModeInputException");
					System.exit(1);
					return;
				}
			
				File file = new File(fileName);
				if (!file.exists()) {
					System.out.println("File doesn't exist. Placing ships randomly.");
					randomGrid();
				}
				else {
					loadGrid(fileName);
				}
				
			if (mode.equals("D")) {
				
				int i = 0;
				
				while(i < bomb) {
					displayGrid();
					String action = scanner.nextLine().trim();
					String result = play(action, letters);
					System.out.println(result);
					
					if (result.equals("Miss") || result.startsWith("Hit ")) {
						i++;
					}
				}
				displayGrid();
				System.out.println("Score " + score);
			}
			else if (mode.equals("R")){
				int i = 0;
				
				while (i < bomb) {
					String action = scanner.nextLine().trim();
					String result = play(action, letters);
					System.out.println(result);
					
					if (result.equals("Miss") || result.startsWith("Hit ")) {
						i++;
					}
				}
				displayGrid();
				System.out.println("Score"+ score);
				}
			}
		}	
	}
			
class InvalidCoordinateException extends RuntimeException{
	public InvalidCoordinateException() {
		super();
	}
}
class BombInputException extends RuntimeException {
	public BombInputException() {
		super();
	}
}
class ModeInputException extends RuntimeException{
	
	public ModeInputException() {
		super();	
	}
}
class HitException extends RuntimeException{
	public HitException() {
		super();
	}
}
class Ship{
	String type;
	int size;
	String marker;
	String marked;
	int num;
	
	public Ship(String type, int size, String marker,String marked, int num) {
		this.type = type;
		this.size = size;
		this.marker = marker;
		this.marked = marked;
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public int getSize() {
		return size;
	}
	public String getMarker() {
		return marker;
	}
	public String getMarked() {
		return marked;
	}
	public int getNum() {
		return num;
	}
}
class AircraftCarrier extends Ship {
	public AircraftCarrier() {
		super("AircraftCarrier", 6, "A", "Xa", 1);
	}
}
class Battleships extends Ship {
	public Battleships() {
		super("Battleship", 4, "B", "Xb", 2);
	}
}
class Submarine extends Ship {
	public Submarine() {
		super("Submarine", 3, "S", "Xs", 2);
	}
}
class Destroyer extends Ship {
	public Destroyer() {
		super("Destroyer", 3, "D", "Xd", 1);
	}
}
class PatrolBoat extends Ship {
	public PatrolBoat() {
		super("Patrol Boat", 2, "P", "Xp", 4);
	}
}
