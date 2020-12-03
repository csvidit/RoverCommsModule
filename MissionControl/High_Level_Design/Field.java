package MissionControl.High_Level_Design;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
public class Field {

    public static class Latitude {

        /**
         * @param val
         * @return
         */
        public static int encode(double val) {
            /*if (val > 90 || val < -90)
                return -9999;
            return (int) ((val + 90) * 2);*/
            return (int) ((val+90)*100);
        }

        /**
         * @param val
         * @return
         * 
         * NOT FINAL CODE. SYNTAX ERRORS EXIST
         */
        public static double decode(int encodedVal) {
            //return (encodedVal - 180) / 2;
            return ((encodedVal/100)-90);
        }
    }

    public static class Longitude { 
        /**
         * @param val
         * @return 
         */
        public static int encode(double val) {
            return (int)((val+180)*100);
        }

        
        /**
         * @param encodedVal
         * @return
         */
        public static double decode(int encodedVal){
            return ((encodedVal/100)-180);
        }
    }

    public static class AmbientTemperature { 
        /**
         * @param val
         * @return 
         */
        public static int encode(double val) {
            return (int)((val+200)*2);
        }

        
        /**
         * @param encodedVal
         * @return
         */
        public static double decode(int encodedVal){
            return (encodedVal/2)-200;
        }
    }

    public static class InternalTemperature
    {
        /**
         * 
         * @param val
         * @return
         */
        public static int encode(double val)
        {
            return (int)((val+50)*2);
        }

        /**
         * 
         * @param encodedVal
         * @return
         */
        public static double decode(int encodedVal)
        {
            return ((encodedVal/2)-50);
        }
    }

    public static class ChargeLevel
    {
        /**
         * 
         * @param val
         * @return
         */
        public static int encode(double val)
        {
            return (int)(val*4);
        }

        /**
         * 
         * @param encodedVal
         * @return
         */
        public static double decode(int encodedVal)
        {
            return (encodedVal/4);
        }
    }

    public static class WindSpeed
    {
        /**
         * 
         * @param val
         * @return
         */
        public static int encode(double val)
        {
            return (int)(val*2);
        }

        /**
         * 
         * @param encodedVal
         * @return
         */
        public static double decode(int encodedVal)
        {
            return (encodedVal/2);
        }
    }

    public static class Elevation
    {
        /**
         * 
         * @param val
         * @return
         */
        public static int encode(double val)
        {
            return (int)((val+200)*2);
        }

        /**
         * 
         * @param encodedVal
         * @return
         */
        public static double decode(int encodedVal)
        {
            return ((encodedVal/2)-200);
        }
    }

    public static class LightingLevel
    {
        /**
         * 
         * @param val
         * @return
         */
        public static long encode(double val)
        {
            //code
        }

        /**
         * 
         * @param encodedVal
         * @return
         */
        public static double decode(int encodedVal, int exponent)
        {
            //code
        }
    }

    public static class InternalTime
    {
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

    public class HexDecimalConverter
    {
        public int hexToDeci(String hexNum)
        {
            int num = Integer.parseInt(hexNum, 16);
            return num;
        }

        public String deciToHex(int num)
        {
            String hex = Integer.toHexString(num);
            return hex;
        }
    }

    public static class Driver
    {
        public static void main(String[] args) {
            InternalTime.value();
        }
    }

}
