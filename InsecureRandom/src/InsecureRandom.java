import java.security.SecureRandom;
import java.util.Random;

import jreframeworker.annotations.fields.DefineField;
import jreframeworker.annotations.methods.MergeMethod;
import jreframeworker.annotations.types.MergeType;

@MergeType
public class InsecureRandom extends SecureRandom {

	@DefineField
	private Random random;
	
	@MergeMethod
	public int nextInt(){
		if(random == null){
			int fixedSeed = 0;
			random = new Random(fixedSeed);
		}
		return random.nextInt();
	}
	
}
