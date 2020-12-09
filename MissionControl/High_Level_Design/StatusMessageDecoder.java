package MissionControl.High_Level_Design;

import java.util.Scanner;

public class StatusMessageDecoder {

    private String statusMessage;
    private String messageByteArray[];

    public StatusMessageDecoder()
    {
        statusMessage = "";
        messageByteArray = new String[21];
    }
    
    public StatusMessageDecoder(String statusMessage)
    {
        this.statusMessage = statusMessage;
        messageByteArray = new String[21];
    }

    public void decode()
    {
        //TODO decoding
        
    }

    @Override
    public String toString()
    {
        //TODO return user-understandable string
    }
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Rover Status Message: ");
        StatusMessageDecoder decoder = new StatusMessageDecoder(sc.nextLine());
        decoder.decode();
        System.out.print("\n"+decoder.toString());
    }
    
}
