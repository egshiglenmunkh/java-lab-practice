package java_labs;

public class Animal {} 

abstract class Mammal extends Animal {
	public abstract void bark();
	public abstract void getFood();
}

abstract class Reptile extends Animal {
	public abstract void getFood1();
}

final class Cat extends Mammal{
	private String name;
	private float weight;
	private String nameSlave;
	
	public String getName() {
		return this.name;
	}
	public void setName(String gn) {
		this.name = gn;
	}
	
	public float getWeight() {
		return this.weight;
	}
	public void setWeight(int w) {
		this.weight = w;
	}
	public String getNameSlave() {
		return this.nameSlave;
	}
	public void setNameSlave(String s) {
		this.nameSlave = s;
	}
	
	public void bark() {
		System.out.println("Meow");
	}
	
	public void getFood() {
		System.out.println("Fish");
	}	
}


final class Dog extends Mammal {
	private String name;
	private float weight;
	private String nameMaster;
	
	public String getName() {
		return this.name;
	}
	public void setName(String gn) {
		this.name = gn;
	}
	
	public float getWeight() {
		return this.weight;
	}
	public void setWeight(int w) {
		this.weight = w;
	}
	public String getNameSlave() {
		return this.nameMaster;
	}
	public void setNameSlave(String s) {
		this.nameMaster = s;
	}
	
	public void bark() {
		System.out.println("BowBow");
	}
	
	public void getFood() {
		System.out.println("Apple");
	}	
}

final class Crocodile extends Reptile {
	public void getFood1() {
		System.out.println("Meat");
	}
}





