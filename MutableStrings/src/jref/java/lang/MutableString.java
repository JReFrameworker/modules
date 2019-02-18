
package jref.java.lang;

import com.jreframeworker.annotations.methods.MergeMethod;
import com.jreframeworker.annotations.types.MergeType;

@MergeType(phase=2)
public final class MutableString extends java.lang.String {
	
	@MergeMethod
	@Override
	 public String replace(CharSequence s1, CharSequence s2){
		String result = super.replace(s1, s2);
		// hey Java, you forgot to update your value... so I fixed it :)
		value = result.toCharArray();
		return result;
	}

}