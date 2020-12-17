package Prototypes;

public class Touple {
    
    private int exponent;
    private int significand;

    public Touple()
    {
        exponent=0;
        significand=0;
    }

    public Touple(int exponent, int significand)
    {
        this.exponent=exponent;
        this.significand=significand;
    }

    public int getExponent() {
        return exponent;
    }

    public int getSignificand() {
        return significand;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    public void setSignificand(int significand) {
        this.significand = significand;
    }

}
