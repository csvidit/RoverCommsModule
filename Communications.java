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
            return (encodedVal + 180) / 2;
        }
    }

    public static class ChargeLevel {

        public static int encode(double val) {

        }
    }

}
