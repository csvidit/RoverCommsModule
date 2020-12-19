package MissionControl;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.io.File;
import java.nio.file.Path;

public class CommandMessageEncoder
{
    public static class Driver
    {
        public static void main(String[] args)
        {
            String message;

            //input the values

            //Path messageInput = new Path("/");

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
            //System.out.print("\nWait Condition: ");
            //waitCondition=sc.nextInt();
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

            //Hard code values for testing
            // latitude = 90;
            // longitude = 180;
            // chargeLevel = 100;
            // waitCondition = 92;
            // missionMode = 4;
            // lightingLevel = 23;
            // ambientTemp = -28.5;
            // internalTemp = -0.5;
            // windSpeed = 182.5;
            // time = 158965675;

            //time
            String sTime = Field.Converter.deciToHex(time);

            if(sTime.length() < 12)
            {
                String temp = "";
                for(int i = 0; i < 12-(sTime.length()); i++)
                {
                    temp+= "0";
                }
                sTime = temp + sTime;
            }

            //wait condition for the time field
            if (missionMode == 10)
            {
                waitCondition = (int)(time);
            }
            else
            {
                waitCondition = 0;
            }
           
            String sWaitCon = Field.Converter.deciToHex(waitCondition);
            if(sWaitCon.length() < 8)
            {
                String temp = "";
                for(int i = 0; i < 8-(sWaitCon.length()); i++)
                {
                    temp+= "0";
                }
                sWaitCon = temp + sWaitCon;
            }

            //longitude
            String sLong = Field.Converter.deciToHex(Field.Longitude.encode(longitude));
            if(sLong.length() < 8)
            {
                String temp = "";
                for(int i = 0; i < 4-(sLong.length()); i++)
                {
                    temp+= "0";
                }
                sLong = temp + sLong;
            }

            //Lighting Level
            String sLightL = Field.Converter.deciToHex(lightingLevel);
            if(sLightL.length() < 8)
            {
                String temp = "";
                for(int i = 0; i < 4-(sLightL.length()); i++)
                {
                    temp+= "0";
                }
                sLightL = temp + sLightL;
            }

            //Latitude and Charge Level
            String latCLBinary = Integer.toBinaryString(Field.Latitude.encode(latitude)) + Integer.toBinaryString(Field.ChargeLevel.encode(chargeLevel));
            latCLBinary = Field.Converter.bintoHex(latCLBinary);

            //Ambient Temperature, Mission Mode, Internal Temperature and Wind Speed
            String ambTempPlus = Integer.toBinaryString(Field.AmbientTemperature.encode(ambientTemp))
            + Integer.toBinaryString(missionMode)
            + Integer.toBinaryString(Field.InternalTemperature.encode(internalTemp))
            + Integer.toBinaryString(Field.WindSpeed.encode(windSpeed));
            ambTempPlus = Field.Converter.bintoHex(ambTempPlus);


            message = sTime + sWaitCon + sLightL + sLong + latCLBinary + ambTempPlus;
            System.out.println(message);

            if (message.length() == 42)
                System.out.println("Correct");
            
            //To check individual outputs
            System.out.println( time + "- " + sTime);
            System.out.println( waitCondition + "- " + sWaitCon);
            System.out.println( lightingLevel + "- " + sLightL);
            System.out.println( longitude + "- " + Field.Longitude.encode(longitude) + "- " + sLong);
            
        }
    }

}
