import java.util.Scanner;

public class Hello {

	public static void main(String[] args) {
		
		System.out.println("Hola bienvenido");
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Dame tu edad: ");
		
		int edad = sc.nextInt();
		
		System.out.printf("Tienes %d años y te faltan %d años para llegar a los 100 años %n", edad, 100 - edad);
		
	}

}
