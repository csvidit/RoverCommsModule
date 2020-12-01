package High_Level_Design;
public class Field {

    public static class Latitude {

        /**
         * @param val
         * @return
         */
        public static int encode(double val) {
            if (val > 90 || val < -90)
                return -9999;
            return (int) ((val + 90) * 2);
        }

        /**
         * @param val
         * @return
         * 
         * NOT FINAL CODE. SYNTAX ERRORS EXIST
         */
        public static double decode(int encodedVal) {
            return (encodedVal - 180) / 2;
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

}
