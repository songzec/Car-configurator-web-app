package client;

import java.util.Scanner;

import model.Automobile;

public class SelectCarOption {
	public void selectCarOption(Automobile auto) {
		Scanner scanner = new Scanner(System.in);
		String userInput;
		auto.print();
		for(int i = 0; i < auto.getOpsets().size(); ++i) {
			if (auto.getOptionSetName(i) != null) {
				System.out.println("Please input your choice of "
											+ auto.getOptionSetName(i) + ":");
				userInput = scanner.nextLine();
				auto.setOptionChoice(auto.getOptionSetName(i), userInput);
				System.out.println("You have chosen: "
						+ auto.getOptionChoice(auto.getOptionSetName(i))
						+ ", $"
						+ auto.getOptionChoicePrice(auto.getOptionSetName(i)));
			}
		}
		
		System.out.println("Total price is: $" + auto.getTotalPrice());
		System.out.println();
	}
}
