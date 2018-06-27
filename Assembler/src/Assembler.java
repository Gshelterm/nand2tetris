import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;


public class Assembler {

	public static void main(String[] args) {
		new Assembler().start();
	}
	
	public void start() {
		StringBuilder output = new StringBuilder();
		
		Parser parser = null;
		code decode = new code();
		SymbolTable labelTable = new SymbolTable();
		
		// handle Symbol
		try (Scanner in = new Scanner(Paths.get("C:\\Users\\asus\\Desktop\\nand2tetris\\nand2tetris\\projects\\06\\pong\\Pong.asm"))) {
			parser = new Parser(in);
			int lineNumber = 0;
			
			while (parser.hasMoreCommands()) {
				parser.advance();
				lineNumber++;
				
				if (parser.commandType() == Parser.COMMAND.L_COMMAND) {
//System.out.println(parser.symbol());
					lineNumber--;
//System.out.println(lineNumber);
					labelTable.addEntry(parser.symbol(), lineNumber);
				}
				else {
					continue;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		try (Scanner in = new Scanner(Paths.get("C:\\Users\\asus\\Desktop\\nand2tetris\\nand2tetris\\projects\\06\\pong\\Pong.asm"))){
			parser = new Parser(in);
			DecimalFormat df= new DecimalFormat("000000000000000");
			int regNum = 16;
			
			while (parser.hasMoreCommands()) {
				parser.advance();
				
				if (parser.commandType() == Parser.COMMAND.A_COMMAND) {
					String symbol = parser.symbol();
					boolean digit = true;
					for (Character c : symbol.toCharArray()) {
						digit &= Character.isDigit(c);
					}
					
					if (digit) {

						Integer addr = Integer.valueOf(symbol);
						output.append("0" + df.format(Long.valueOf(Long.toBinaryString(addr))) + "\r\n");
					}
					else if (labelTable.contains(symbol)) {
						Integer addr = labelTable.getAddress(symbol);
						output.append("0" + df.format(Long.valueOf(Long.toBinaryString(addr))) + "\r\n");
					} 
					else {
						labelTable.addEntry(symbol, regNum);
						output.append("0" + df.format(Long.valueOf(Long.toBinaryString(regNum))) + "\r\n");
						regNum++;
					}

				} 
				else if (parser.commandType() == Parser.COMMAND.C_COMMAND) {
					output.append("111" + decode.comp(parser.comp()) + decode.dest(parser.dest()) 
									+ decode.jump(parser.jump()) + "\r\n");
				}
				else if (parser.commandType() == Parser.COMMAND.L_COMMAND) {
					continue;
				}
				else {
					System.out.println("Wrong instructions");// wrong instruction
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try (PrintWriter out = new PrintWriter("C:\\Users\\asus\\Desktop\\nand2tetris\\nand2tetris\\projects\\06\\pong\\Pong1.hack")){
			out.write(output.toString());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}
}