
package java.lang;

import jreframeworker.annotations.fields.DefineFieldFinality;
import jreframeworker.annotations.fields.DefineFieldVisibility;
import jreframeworker.annotations.methods.MergeMethod;
import jreframeworker.annotations.types.DefineTypeFinality;
import jreframeworker.annotations.types.MergeType;

@DefineTypeFinality(type = "java.lang.String", finality = false)
@DefineFieldFinality(type = "java.lang.String", field = "value", finality = false)
@DefineFieldVisibility(type = "java.lang.String", field = "value", visibility = "protected")
@MergeType
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