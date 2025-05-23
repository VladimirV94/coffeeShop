package apps.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class AbstacratInterface implements ICommandsId{
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	protected int getSelectedCommandId()
	{
		try 
        {
        	 System.out.print("Enter:");
             return Integer.parseInt(br.readLine());
        }
		catch(NumberFormatException nfe)
		{
			System.err.println("Invalid Format!");
			return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
		}
		catch(IOException nfe)
		{
			System.err.println(nfe);
			return RETURN_TO_PREVIOUS_MENU_COMMAND_ID;
		}
	}

	protected String getEnteredPromocode()
	{
		try 
        {
        	 System.out.print("Promocode:");
             return br.readLine();
        }
		catch(IOException nfe)
		{
			System.err.println(nfe);
			return "";
		}
	}

	protected void sendMessage(Object object) {
		System.out.println(object);
	}

	protected void sendMessage() {
		System.out.println();
	}
}
