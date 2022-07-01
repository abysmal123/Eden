import java.io.IOException;

import periodic.*;

public class Test {
	
	public static void main(String[] args) throws IOException {
		
		for (int i = 0; i < 256; i++) {
			String r = toEightBitString(i);
			if (!PeriodicECAFiniteLength.hasEden(r)) {
				System.out.println(r);
			}
		}
	}
	
	public static String toEightBitString(int num) {
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			buffer.insert(0, num & 1);
			num >>= 1;
		}
		return buffer.toString();
	}
}
