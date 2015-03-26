package tests;

import com.prcse.datamodel.Account;
import com.prcse.datamodel.Customer;

public class CustomerCreateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Account account = new Account("dash@gmail.com", "admin", true);
		
		Customer customer = new Customer("vlad@gmail.com",
											"password",
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											null,
											true);
		
		System.out.println("account id = " + account.getId());
		System.out.println(account.toString());
		
		System.out.println("customer id = " + customer.getAccount().getId());
		System.out.println(customer.toString());
	}

}
