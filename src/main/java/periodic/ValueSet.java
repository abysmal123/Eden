package periodic;

public final class ValueSet {

	protected int values;
	
	protected int[] subValues;		// 00, 01, 10, 11
	
	protected ValueSet() {
		
		values = 0;
		subValues = new int[4];
	}
		
	protected ValueSet(int values) {
		
		this.values = values;
		subValues = new int[4];
	}
	
	protected ValueSet(int values, int[] subValues) {
		
		this.values = values;
		this.subValues = subValues;
	}

}
