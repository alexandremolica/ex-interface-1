package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;
import model.services.TaxService;

public class Program {

	public static void main(String[] args) throws ParseException {
		/* 
		 * Exemplo 1 de interface
		 * classes (entities): CarRental, Invoice, Vehicle
		 * Classes (services): TaxService (é a interface), RentalSerce e
		 * BrazilTaxService
		 * utilizacao de inversao de controle e injecao de dependencia
		 *  injecao de dependencia: forma de realizar a inversao de controle,
		 *   um componente externo instancia sua dependencia, que eh injetada
		 *   no objeto pai.
		 *   inversao de controle: padrao que consiste em retirar da classe 
		 *   a responsabilidade de instanciar suas dependencias
		*/
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
			
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:ss");
		
		System.out.println("Enter rental data: ");
		System.out.print("Car model: ");
		String carModel = sc.nextLine();
		System.out.print("Pickup (dd/mm/yyyy hh:ss): ");
		Date start = sdf.parse(sc.nextLine());
		System.out.print("Return (dd/mm/yyyy hh:ss): ");
		Date finish = sdf.parse(sc.nextLine());
		
		CarRental cr = new CarRental(start, finish, new Vehicle(carModel));
		

		System.out.print("Enter price per hour: ");
		double pricePerHour = sc.nextDouble();
		System.out.print("Enter price per day: ");
		double pricePerDay = sc.nextDouble();
		
		// upcasting em BrazilTaxService : ocorre uma inversao de controle 
		// (retira da classe - RentalService -
		// a responsabilidade de instanciar sua dependencia)
		RentalService rentalService = new RentalService(pricePerDay, pricePerHour, new BrazilTaxService());
		
		rentalService.processInvoice(cr);
		
		System.out.println("INVOICE: ");
		System.out.println("Basic payment: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
		System.out.println("Tax : " + String.format("%.2f", cr.getInvoice().getTax()));
		System.out.println("Total payment : " + String.format("%.2f", cr.getInvoice().getTotalPayment()));
		
		sc.close();

	}

}
