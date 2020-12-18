package MissionControl;

import java.util.Scanner;
//import java.util.ArrayList;

public class StatusMessageDecoder {

    // private String statusMessage;
    // private String messageByteArray[];

    // public StatusMessageDecoder()
    // {
    //     statusMessage = "";
    //     messageByteArray = new String[23];
    // }
    
    // public StatusMessageDecoder(String statusMessage)
    // {
    //     this.statusMessage = statusMessage;
    //     messageByteArray = new String[23];
    // }

    public static void decode(String message)
    {
        String text = message;

        String[] parts = new String[7];
        int length = text.length();
        int count = 0;

        parts[0] = (text.substring(count, Math.min(length, count + 12)));
        count += 12;
        parts[1] = (text.substring(count, Math.min(length, count + 4)));
        count += 4;
        parts[2] = (text.substring(count, Math.min(length, count + 4)));
        count += 4;
        parts[3] = (text.substring(count, Math.min(length, count + 6)));
        count += 6;
        parts[4] = (text.substring(count, Math.min(length, count + 6)));
        count += 6;
        parts[5] = (text.substring(count, Math.min(length, count + 4)));
        count += 4;
        parts[6] = (text.substring(count, Math.min(length, count + 10)));
        count += 10;

        //delete later
        System.out.println(parts[0]);
        System.out.println(parts[1]);
        System.out.println(parts[2]);
        System.out.println(parts[3]);
        System.out.println(parts[4]);
        System.out.println(parts[5]);
        System.out.println(parts[6]);

        
        //Internal time = 12
        //long intTime = Field.InternalTime.decode(Field.Converter.hexToDeci(parts[0]);

        
        //Ambient Light = 4
        //double ambLight = Field.AmbientLight.decode(Field.Converter.hexToDeci(parts[1]));
        //ambLight = Math.round(ambLight * 100.0) / 100.0;

        
        //Longitude = 4
        double longitude = Field.Longitude.decode(Field.Converter.hexToDeci(parts[2]));
        longitude = Math.round(longitude * 100.0) / 100.0;
        
        
        //Latitude(15) + Wind Speed(9) = 6
        count = 0;
        String latWS = Field.Converter.hexToBin(parts[3]);
        
        double latitude = Field.Latitude.decode(Integer.parseInt(latWS.substring(count, Math.min(length, count + 15)), 2));
        latitude = Math.round(latitude * 100.0) / 100.0;
        count += 15;
        
        double windS = Field.WindSpeed.decode(Integer.parseInt(latWS.substring(count, Math.min(length, count + 9)), 2));
        windS = Math.round(windS * 100.0) / 100.0;


        //Elevation(15) + Internal Temperature(9) = 6
        count = 0;
        String elevIntTemp = Field.Converter.hexToBin(parts[4]);
        double elevation = Field.Elevation.decode(Integer.parseInt(elevIntTemp.substring(count, Math.min(length, count + 15)), 2));
        elevation = Math.round(elevation * 100.0) / 100.0;
        count += 15;
        double intTemp = Field.InternalTemperature.decode(Integer.parseInt(elevIntTemp.substring(count, Math.min(length, count + 9)), 2));
        intTemp = Math.round(intTemp * 100.0) / 100.0;


        //Subsystem Indicator(10) + Subsystem Code(6) = 4
        count = 0;
        String subsystem = Field.Converter.hexToBin(parts[5]);
        int subsystemIndicator = Integer.parseInt(subsystem.substring(count, Math.min(length, count + 10)), 2);
        
        count += 10;
        int subsystemCode = Integer.parseInt(subsystem.substring(count, Math.min(length, count + 6)), 2);


        //Hours of Operation(20) + Ambient Temperature(10) + Alert Indicator(1) + Charge Level(9) = 10
        count = 0;
        String hoursOpPlus = Field.Converter.hexToBin(parts[6]);
        int hoursOp = Integer.parseInt(hoursOpPlus.substring(count, Math.min(length, count + 20)), 2);

        count += 20;
        double ambTemp = Field.AmbientTemperature.decode(Integer.parseInt(hoursOpPlus.substring(count, Math.min(length, count + 10)), 2));
        ambTemp = Math.round(ambTemp * 100.0) / 100.0;
        
        count += 10;
        int alertIndicator = Integer.parseInt(hoursOpPlus.substring(count, Math.min(length, count + 1)), 2);
        
        count += 1;
        double chargeL = Field.ChargeLevel.decode(Integer.parseInt(hoursOpPlus.substring(count, Math.min(length, count + 9)), 2));
        chargeL = Math.round(chargeL * 100.0) / 100.0;

        
        //Print out the decoded status message
        System.out.println("DECODED STATUS MESSAGE");
        //System.out.println("Internal time: " + intTime);
        //System.out.println("Ambient Light: " + ambLight);
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

    // @Override
    // public String toString()
    // {
    //     String toString="";
    //     for(int i=0; i<21; i++)
    //     {
    //         toString+="\n"+messageByteArray[i];
    //     }
    //     return toString;
    // }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Rover Status Message: ");
        String message = new String(sc.nextLine());
        decode(message);
    }
}
