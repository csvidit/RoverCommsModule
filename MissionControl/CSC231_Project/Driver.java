package MissionControl.CSC231_Project;

public class Driver {

    public static void main(String[] args) {
        
        HexDecimalConverter h = new HexDecimalConverter();

        String a = "A5";
        int b = 25;

        System.out.println(h.hexToDeci(a));
        System.out.println(h.deciToHex(b));


    }
    
}
