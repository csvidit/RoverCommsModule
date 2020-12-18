package MissionControl.High_Level_Design;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class Field {

    public static class Latitude {

        /**
         * @param val the actual value
         * @return the encoded value
         */
        public static int encode(double val) {
            /*if (val > 90 || val < -90)
                return -9999;
            return (int) ((val + 90) * 2);*/
            return (int) ((val+90)*100);
        }

        /**
         * @param encodedVal the encoded value
         * @return the actual value (decoded)
         * 
         */
        public static double decode(int encodedVal) {
            double value = encodedVal;
            return ((value/100)-90);
        }
    }

    public static class Longitude { 
        /**
         * @param val the actual value
         * @return the encoded value
         */
        public static int encode(double val) {
            return (int)((val+180)*100);
        }

        
        /**
         * @param encodedVal the encoded value
         * @return the actual value (decoded)
         */
        public static double decode(int encodedVal){
            double value = encodedVal;
            return ((value/100)-180);
        }
    }

    public static class AmbientTemperature { 
        /**
         * @param val the actual value
         * @return the encoded value
         */
        public static int encode(double val) {
            return (int)((val+200)*2);
        }

        
        /**
         * @param encodedVal the encoded value
         * @return the actual value (decoded)
         */
        public static double decode(int encodedVal){
            double value = encodedVal;
            return (value/2)-200;
        }
    }

    public static class InternalTemperature
    {
        /**
         * 
         * @param val the actual value
         * @return the encoded value
         */
        public static int encode(double val)
        {
            return (int)((val+50)*2);
        }

        /**
         * 
         * @param encodedVal the encoded value
         * @return the actual value (decoded)
         */
        public static double decode(int encodedVal)
        {
            double value = encodedVal;
            return ((value/2)-50);
        }
    }

    public static class ChargeLevel
    {
        /**
         * 
         * @param val the actual value
         * @return the encoded value
         */
        public static int encode(double val)
        {
            return (int)(val*4);
        }

        /**
         * 
         * @param encodedVal the encoded value
         * @return the actual value (decoded)
         */
        public static double decode(int encodedVal)
        {
            double value = encodedVal;
            return (value/4);
        }
    }

    public static class WindSpeed
    {
        /**
         * 
         * @param val the actual value
         * @return the encoded value
         */
        public static int encode(double val)
        {
            return (int)(val*2);
        }

        /**
         * 
         * @param encodedVal the encoded value
         * @return the actual value (decoded)
         */
        public static double decode(int encodedVal)
        {
            double value = encodedVal;
            return (value/2);
        }
    }

    public static class Elevation
    {
        /**
         * 
         * @param val the actual value
         * @return the encoded value
         */
        public static int encode(double val)
        {
            return (int)((val+200)*2);
        }

        /**
         * 
         * @param encodedVal the encoded value
         * @return the actual value (decoded)
         */
        public static double decode(int encodedVal)
        {
            double value = encodedVal;
            return ((value/2)-200);
        }
    }

    //public static class LightingLevel
    {
        /**
         * 
         * @param val the actual value
         * @return the encoded value
         */
        //public static long encode(double val)
        {
            //code
        }

        /**
         * 
         * @param encodedVal the encoded value
         * @return the actual value (decoded)
         */
        //public static double decode(int encodedVal, int exponent)
        {
            //code
        }
    }

    public static class InternalTime
    {
        /**
         * @return the time in milliseconds since 01/01/2020
         */
        public static long value()
        {
            LocalTime currTime = LocalTime.now();
            LocalDate currDate = LocalDate.now();
            long currTimeInt = currTime.toEpochSecond(currDate, ZoneOffset.ofHours(0));
            LocalDate newEpochDate = LocalDate.of(2000, 1, 1);
            LocalTime newEpochTime = LocalTime.of(0, 0, 0);
            long newEpochTimeInt = newEpochTime.toEpochSecond(newEpochDate, ZoneOffset.ofHours(0));
            long internalTime = currTimeInt - newEpochTimeInt;
            //line below for testing only
            System.out.print("\n"+internalTime);
            return internalTime;
        }
    }

    public static class Converter
    {
        /**
         * 
         * @param hexNum the hexadecimal value
         * @return the integer value
         */
        public static int hexToDeci(String hexNum)
        {
            int num = Integer.parseInt(hexNum, 16);
            return num;
        }

        /**
         * 
         * @param num the integer value
         * @return the hexadecimal value
         */
        public static String deciToHex(int num)
        {
            String hex = (Integer.toHexString(num));
            hex = hex.toUpperCase();
            return hex;
        }

        public static String deciToHex(long num)
        {
            String hex = (Long.toHexString(num));
            hex = hex.toUpperCase();
            return hex;
        }

        public static String bintoHex(String binary)
        {
            String[] list = SplitString.splitBin(binary, 8);
            int[] listDec = new int[6];
            String hex = "";

            for(int i = 0; i < list.length; i++)
            {
                listDec[i] = Integer.parseInt(list[i], 2);
                String temp = Field.Converter.deciToHex(listDec[i]);

                if( temp.length() < 2)
                {
                    if (temp.length() == 1)
                        temp = "0" + temp;

                }
                hex +=  temp;
            }
            return hex;
        }

        public static String hexToBin(String hex){
            String bin = "";
            String binFragment = "";
            long iHex;
            hex = hex.trim();
            //hex = hex.replaceFirst("0x", "");
        
            for(int i = 0; i < hex.length(); i++){
                iHex = Integer.parseInt(""+hex.charAt(i),16);
                binFragment = Long.toBinaryString(iHex);
        
                while(binFragment.length() < 4){
                    binFragment = "0" + binFragment;
                }
                bin += binFragment;
            }
            return bin;
        }
    }


    public static class SplitString
    {
        //given a string and an integer this method splits the string int pieces at the boundary 
        //specified by the integer
        public static String[] splitBin(String text, int size) 
        {
            ArrayList<String> parts = new ArrayList<>();

            int length = text.length();
            for (int i = 0; i < length; i += size) 
            {
                parts.add(text.substring(i, Math.min(length, i + size)));
            }
            
            return parts.toArray(new String[0]);
        }

        public static String[] split(String text, int[] boundaries)
        {
            String[] parts = new String[10];

            int length = text.length();

            for (int i = 0; i < length; i += boundaries[i]) 
            {
                parts[i] = (text.substring(i, Math.min(length, i + boundaries[i])));
            }
            
            return parts;
        }

        
    }

    
    //public class WaitCondition()

    public static class Driver
    {
        public static void main(String[] args) {
            InternalTime.value();
        }
    }

}
