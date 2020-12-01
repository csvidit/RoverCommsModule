//import java.util.ArrayList;

public class HexDecimalConverter
{
    // public HexDecimalConverter()
    // {

    // }
    
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