import java.util.HashMap;
import java.util.Map;

public class code {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private instrMap map = null;
	
	public code() {
		map = new instrMap();
	}
	
	public String dest(String destC) {
		return map.getDest(destC);
	}
	
	public String comp(String compC) {
		return map.getComp(compC);
	}
	
	public String jump(String jumpC) {
		return map.getJump(jumpC);
	}

}


class instrMap {
	private Map<String, String> cDest = new HashMap<String, String>();
	private Map<String, String> cComp = new HashMap<String, String>();
	private Map<String, String> cJmp = new HashMap<String, String>();
	
	public instrMap() {
		cDest.put("null", "000");
		cDest.put("M", "001");
		cDest.put("D", "010");
		cDest.put("MD", "011");
		cDest.put("A", "100");
		cDest.put("AM", "101");
		cDest.put("AD", "110");
		cDest.put("AMD", "111");
		
		cComp.put("0", "0101010");
		cComp.put("1", "0111111");
		cComp.put("-1", "0111010");
		cComp.put("D", "0001100");
		cComp.put("A", "0110000");
		cComp.put("M", "1110000");
		cComp.put("!D", "0001101");
		cComp.put("!A", "0110001");
		cComp.put("!M", "1110001");
		cComp.put("-D", "0001111");
		cComp.put("-A", "0110011");
		cComp.put("-M", "1110011");
		cComp.put("D+1", "0011111");
		cComp.put("A+1", "0110111");
		cComp.put("M+1", "1110111");
		cComp.put("D-1", "0001110");
		cComp.put("A-1", "0110010");
		cComp.put("M-1", "1110010");
		cComp.put("D+A", "0000010");
		cComp.put("D+M", "1000010");
		cComp.put("D-A", "0010011");
		cComp.put("D-M", "1010011");
		cComp.put("A-D", "0000111");
		cComp.put("M-D", "1000111");
		cComp.put("D&A", "0000000");
		cComp.put("D&M", "1000000");
		cComp.put("D|A", "0010101");
		cComp.put("D|M", "1010101");
		
		
		cJmp.put("null", "000");
		cJmp.put("JGT", "001");
		cJmp.put("JEQ", "010");
		cJmp.put("JGE", "011");
		cJmp.put("JLT", "100");
		cJmp.put("JNE", "101");
		cJmp.put("JLE", "110");
		cJmp.put("JMP", "111");
	}
	
	public String getDest(String str) {
		return cDest.get(str);
	}
	
	public String getComp(String str) {
		return cComp.get(str);
	}
	
	public String getJump(String str) {
		return cJmp.get(str);
	}
}