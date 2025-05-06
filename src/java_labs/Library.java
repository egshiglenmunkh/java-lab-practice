package java_labs;

public class Library {
    private Book[] books;
    private static final int total_b = 10;
    
    public Library() {
        books = new Book[total_b];
        for (int i = 0; i < total_b; i++) {
            books[i] = new Book();
        }
    }
    
    public void borrow_b(int n) {
        if (n < 0 || n >= total_b) {
            System.out.println("error");
            return;
        }
        if (books[n].getState().equals("Borrowed")) {
            System.out.println("Book is borrowed");
            return;
        }
        else {
            books[n].setState("Borrowed");
            System.out.println("Book " + n + " has been borrowed");        
        }
    }
    
    public void return_b(int n) {
        if (n < 0 || n >= total_b) {
            System.out.println("error");
            return;
        }
        
        if (books[n].getState().equals("Returned")) {
            System.out.println("Book "+ n + " has been returned");
        }
        else {
            books[n].setState("Returned");
            System.out.println("Book " + n + " has been returned");
        }
    }
}
