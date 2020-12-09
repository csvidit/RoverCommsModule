package MissionControl.High_Level_Design;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.io.File;
import java.nio.file.Path;

public class Message 
{
    public static class Driver
    {
        public static void main(String[] args) 
        {
            String message;
            
            //input the values
            
            Path messageInput = new Path("/");
            
            /**If reading from standard input, use the code below */
            Scanner sc = new Scanner(System.in);

            /**If reading from file, use the code below */
            //Scanner sc = new Scanner(File fileName);

            double latitude, longitude, chargeLevel, ambientTemp, internalTemp, windSpeed;
            int waitCondition, missionMode, lightingLevel;
            long time;

            System.out.print("\nLatitude: ");
            latitude=sc.nextDouble();
            System.out.print("\nLongitude: ");
            longitude=sc.nextDouble();
            System.out.print("\nCharge Level: ");
            chargeLevel=sc.nextDouble();
            System.out.print("\nWait Condition: ");
            waitCondition=sc.nextInt();
            System.out.print("\nMission Mode: ");
            missionMode=sc.nextInt();
            System.out.print("\nLighting Level: ");
            lightingLevel=sc.nextInt();
            System.out.print("\nAmbient Temperature: ");
            ambientTemp=sc.nextDouble();
            System.out.print("\nInternal Temperature: ");
            internalTemp=sc.nextDouble();
            System.out.print("\nWind Speed: ");
            windSpeed=sc.nextDouble();
            System.out.print("\nTime: ");
            time=sc.nextLong();

            /*latitude = 90;
            longitude = 180;
            chargeLevel = 100;
            waitCondition = 92;
            missionMode = 4;
            //lightingLevel = 
            ambientTemp = -28.5;
            internalTemp = -0.5;
            windSpeed = 182.5;
            time = 1589656753210L;*/


            //System.out.println(Field.Latitude.encode(-7.34));
            //System.out.println(Field.HexDecimalConverter.deciToHex(Field.Latitude.encode(90)));
            
            //Formatting of the command message
            message = Field.HexDecimalConverter.deciToHex(time)
            + Field.HexDecimalConverter.deciToHex(waitCondition)
            //LightingLevel 
            + Field.HexDecimalConverter.deciToHex(Field.Longitude.encode(longitude))
            + Field.HexDecimalConverter.deciToHex(Field.Latitude.encode(latitude)) 
            + Field.HexDecimalConverter.deciToHex(Field.ChargeLevel.encode(chargeLevel))
            + Field.HexDecimalConverter.deciToHex(Field.AmbientTemperature.encode(ambientTemp))
            + Field.HexDecimalConverter.deciToHex(missionMode)
            + Field.HexDecimalConverter.deciToHex(Field.InternalTemperature.encode(internalTemp))
            + Field.HexDecimalConverter.deciToHex(Field.WindSpeed.encode(windSpeed));
            
            System.out.println(message);
        }
    }

}
