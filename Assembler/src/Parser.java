import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
	private Scanner in = null;
	private String current = null;
	private String inline = null;
	private boolean hasMore = false;
	private ArrayList<String> inlines = null;
	private int lines = 0;
	
	private Pattern patternComment = Pattern.compile(".*//.*");
	private Matcher matcher = null;
	
	public static enum COMMAND {A_COMMAND, C_COMMAND, L_COMMAND};

	public static void main(String[] args) {
		
		StringBuilder output = new StringBuilder();
		code decode = new code();
		
		
		try (Scanner in = new Scanner(Paths.get("test1.asm"))) {
			Parser parser = new Parser(in);
			DecimalFormat df= new DecimalFormat("000000000000000");

			while (parser.hasMoreCommands()) {
				//System.out.println(n++);
				parser.advance();
				if (parser.commandType() == COMMAND.A_COMMAND) {
//System.out.println(parser.symbol());
					Integer n = Integer.valueOf(parser.symbol());
					output.append("0" + df.format(Integer.valueOf(Integer.toBinaryString(n))) + "\r\n");
				} 
				else if (parser.commandType() == COMMAND.C_COMMAND) {
//System.out.println(decode.comp(parser.comp()));
					output.append("111" + decode.comp(parser.comp()) + decode.dest(parser.dest()) 
									+ decode.jump(parser.jump()) + "\r\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try (PrintWriter out = new PrintWriter("test1.txt")){
			
			out.write(output.toString());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}

	public Parser(Scanner in) {
		this.in = in;
		this.inlines = new ArrayList<String>();
	}
	
	/*
	public Parser(Scanner in1, Scanner in2) {
		this.hasNext = in1;
		this.getNext = in2;
	}
	*/
	
	public boolean hasMoreCommands() {
		while (in.hasNextLine()) {
			inline = in.nextLine().trim();
			
			if (inline.length() != 0 && !inline.startsWith("//")) {
				matcher = patternComment.matcher(inline);
//System.out.println(inline);
				inlines.add(inline);
				hasMore = true;
				return true;
			} else {
				continue;
			}

		}
		hasMore = false;
		return false;
	}
	
	public void advance() {
		
		if (hasMore && !inlines.isEmpty() && matcher.matches()) {
			inline = inlines.get(lines++);
			String[] containsComment = inline.split("//");
			current = containsComment[0].trim();
		} 
		else if (hasMore) {
			current = inlines.get(lines++);;
		}
		else {
			return;
		}
	}
	
	public COMMAND commandType() {
		
		if (current.startsWith("@")) {
			return COMMAND.A_COMMAND;
		} 
		else if (current.startsWith("(") && current.endsWith(")")) {
			return COMMAND.L_COMMAND;
		}
		else {
			return COMMAND.C_COMMAND;
		}
	}
	
	
	public String symbol() {
		if (COMMAND.A_COMMAND == commandType()) {
//System.out.println("what @:" + current);
			return current.substring(1);
		} 
		else if (COMMAND.L_COMMAND == commandType()) {
//System.out.println("what: @" + current);
			return current.substring(current.indexOf("(") + 1, current.indexOf(")"));
		}
		else {
			return null;
		}
	}
	
	public String dest() {
		if (COMMAND.C_COMMAND == commandType() && current.contains("=")) {
				return current.split("=")[0];
		} else {
			return "null";
		}
	}
	
	public String comp() {
		if (dest() != "null" && jump() == "null") {
			return current.substring(current.indexOf('=') + 1);
		} 
		else if (dest() == "null" && jump() != "null") {
			return current.substring(0, current.indexOf(';'));
		}
		else if (dest() != "null" && jump() != "null") {
			return current.substring(current.indexOf('=') + 1, current.indexOf(';'));
		}
		else {
			return current;
		}
	}
	
	public String jump() {
		if (COMMAND.C_COMMAND == commandType() && current.contains(";")) {
			return current.split(";")[1];
		} else {
			return "null";
		}
	}
}
