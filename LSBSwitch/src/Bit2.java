public class Bit2 {
    private boolean MSB;
    private boolean LSB;

    public boolean getMSB(){ return MSB; }
    public boolean getLSB(){
        return LSB;
    }

    public int get() {
        int val = 0;
        if (MSB) {  val += 2; }
        if (LSB) {  val += 1; }
        return val;
    }

    public Bit2(byte b, int i) {
        //i (from 0 to 3) - 0 refer to the 2 MSB of the byte, and 3 refer to the 2 LSB of the byte
        MSB = (b & (1<<(7-2*i))) != 0;
        LSB = (b & (1<<(6-2*i))) != 0;
    }
    public Bit2(int i) {
        MSB = (i & 2) == 2;
        LSB = (i & 1) == 1;
    }
}
