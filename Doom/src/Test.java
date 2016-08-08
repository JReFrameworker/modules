import java.io.BeetlejuicePrintStream;

public class Test {
	
	static class TimBurton {}
	
	public static void main(String[] args) {
		TimBurton timBurton = new TimBurton();
		beetlejuice(timBurton);
		beetlejuice(timBurton);
		beetlejuice(timBurton);
	}
	
	private static void beetlejuice(TimBurton timBurton){
		System.out.println(timBurton.toString());
	}

}
