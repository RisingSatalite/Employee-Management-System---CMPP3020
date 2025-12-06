package employees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagementSystem {
	static List<EmployeeInfo> EmployeeList = new ArrayList<>();
	static List<department> DepartmentList = new ArrayList<>();
	static List<Position> PositionList= new ArrayList<>();
	
	//For reading user inputs
	static Scanner input = new Scanner(System.in);
	
	// TODO Auto-generated method stub
	public static void main(String[] args) {
		System.out.println("Loading employee managemnt system");
		while(true) {
			System.out.println("Enter what to do");
			String userInput = input.nextLine();
			
			//Close the system
			if(userInput.equalsIgnoreCase("close") || userInput.equalsIgnoreCase("exit")) {
				break;
			}
			
			//Add employee
			if(userInput.equalsIgnoreCase("new")){
				while(true) {
					System.out.println("Add new employee, type na to cancel");
					String type = input.nextLine();
					
					//Need to add loops to get all needed information
					if(type.equalsIgnoreCase("fulltime")){
						System.out.println("Addeding full time employee");
						fullTimeEmployee newEmployee = new fullTimeEmployee();
						EmployeeList.add(newEmployee);
						break;
					}
					
					if(type.equalsIgnoreCase("parttime")){
						System.out.println("Addeding full time employee");
						partTimeEmployee newEmployee = new partTimeEmployee();
						EmployeeList.add(newEmployee);
						break;
					}
					
					if(type.equalsIgnoreCase("manager")){
						System.out.println("Addeding manager");
						Manager newEmployee = new Manager();
						EmployeeList.add(newEmployee);
						break;
					}
					
					//Cancel changes
					if(type.equalsIgnoreCase("na")){
						System.out.println("Cancelled");
						break;
					}
				}
				
			}
			
			if(userInput.equalsIgnoreCase("view")){
				for (EmployeeInfo item : EmployeeList) {
				    System.out.println(item);
				}
			}
		}
		System.out.println("Closing employee managemnt system");
	}
}
