import java.io.IOException;

import periodic.*;

public class Test {
	
	public static void main(String[] args) throws IOException {
	
		String r = "01101001";
		ShowProcedureTreeECA.setPath("graph/test/");
		ShowProcedureTreeECA.storeImage(r);
	}
	
}
