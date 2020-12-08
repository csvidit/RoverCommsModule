package MissionControl.High_Level_Design;

public class Message 
{
    public static class Driver
    {
        public static void main(String[] args) 
        {
            String message;
            
            //input the values
            
            double latitude = 90;
            double longitude = 180;
            double chargeLevel = 100;
            int waitCondition = 92;
            int missionMode = 4;
            //int lightingLevel;
            double ambientTemp = -28.5;
            double internalTemp = -0.5;
            double windSpeed = 182.5;
            long time = 1589656753210L;


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
