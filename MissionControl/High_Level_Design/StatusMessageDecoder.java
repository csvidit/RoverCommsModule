package MissionControl.High_Level_Design;

import java.util.Scanner;
import java.util.ArrayList;

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

    public static void decode(String message)
    {
        //statusMessage = "";
        //messageByteArray = new String[21];
        
        //String intTime, ambLight; 
        //int x = 0;
        
        String text = message;

        String[] parts = new String[7];
        int length = text.length();
        int count = 0;
        
        //ArrayList<String> parts = new ArrayList<>();
        // parts.add(text.substring(count, Math.min(length, count + 12)));
        // parts.add(text.substring(count, Math.min(length, count + 4)));
        // parts.add(text.substring(count, Math.min(length, count + 4)));
        // parts.add(text.substring(count, Math.min(length, count + 6)));
        // parts.add(text.substring(count, Math.min(length, count + 6)));
        // parts.add(text.substring(count, Math.min(length, count + 4)));
        // parts.add(text.substring(count, Math.min(length, count + 10)));

        parts[0] = (text.substring(count, Math.min(length, count + 12)));
        parts[1] = (text.substring(count, Math.min(length, count + 4)));
        parts[2] = (text.substring(count, Math.min(length, count + 4)));
        parts[3] = (text.substring(count, Math.min(length, count + 6)));
        parts[4] = (text.substring(count, Math.min(length, count + 6)));
        parts[5] = (text.substring(count, Math.min(length, count + 4)));
        parts[6] = (text.substring(count, Math.min(length, count + 10)));

        //Internal time = 12
        //long intTime = Field.InternalTime.decode(Field.Converter.hexToDeci(parts[0]);


        //Ambient Light = 4
        //double ambLight = Field.AmbientLight.decode(Field.Converter.hexToDeci(parts[1]));

        //Longitude = 4
        double longitude = Field.Longitude.decode(Field.Converter.hexToDeci(parts[2]));
        
        count = 0;
        //Latitude(15) + Wind Speed(9) = 6
        String latWS = Field.Converter.hexToBin(parts[3]);
        double latitude = Field.Latitude.decode(Integer.parseInt(latWS.substring(count, Math.min(length, count + 15)), 2));
        double windS = Field.WindSpeed.decode(Integer.parseInt(latWS.substring(count, Math.min(length, count + 9)), 2));

        count = 0;
        //Elevation(15) + Internal Temperature(9) = 6
        String elevIntTemp = Field.Converter.hexToBin(parts[4]);
        double elevation = Field.Elevation.decode(Integer.parseInt(elevIntTemp.substring(count, Math.min(length, count + 15)), 2));
        double intTemp = Field.InternalTemperature.decode(Integer.parseInt(elevIntTemp.substring(count, Math.min(length, count + 9)), 2));

        count = 0;
        //Subsystem Indicator(10) + Subsystem Code(6) = 4
        String subsystem = Field.Converter.hexToBin(parts[5]);
        int subsystemIndicator = Integer.parseInt(subsystem.substring(count, Math.min(length, count + 15)), 2);
        int subsystemCode = Integer.parseInt(subsystem.substring(count, Math.min(length, count + 9)), 2);

        count = 0;
        //Hours of Operation(20) + Ambient Temperature(10) + Alert Indicator(1) + Charge Level(9) = 10
        String hoursOpPlus = Field.Converter.hexToBin(parts[6]);
        int hoursOp = Integer.parseInt(hoursOpPlus.substring(count, Math.min(length, count + 20)), 2);
        double ambTemp = Field.AmbientTemperature.decode(Integer.parseInt(hoursOpPlus.substring(count, Math.min(length, count + 10)), 2));
        int alertIndicator = Integer.parseInt(hoursOpPlus.substring(count, Math.min(length, count + 1)), 2);
        double chargeL = Field.ChargeLevel.decode(Integer.parseInt(hoursOpPlus.substring(count, Math.min(length, count + 9)), 2));

        
        System.out.println("Decoded Status Message");
        System.out.println("Internal time: ");
        System.out.println("Ambient Light: ");
        System.out.println("Longitude: " + longitude);
        System.out.println("Wind Speed: " + windS);
        System.out.println("Latitude: " + latitude);
        System.out.println("Elevation: " + elevation);
        System.out.println("Internal temperature: " + intTemp);
        System.out.println("Subsystem Indicator: " + subsystemIndicator);
        System.out.println("Subsystem Code: " + subsystemCode);
        System.out.println("Hours of Operation: " + hoursOp);
        System.out.println("Ambient Temperature: " + ambTemp);
        System.out.println("Alert Indicator: " + alertIndicator);
        System.out.println("Charge Level: " + chargeL);


    }

    @Override
    public String toString()
    {
        String toString="";
        for(int i=0; i<21; i++)
        {
            toString+="\n"+messageByteArray[i];
        }
        return toString;
    }
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Rover Status Message: ");
        String message = new String(sc.nextLine());
        decode(message);
        

    }
        
    
}
