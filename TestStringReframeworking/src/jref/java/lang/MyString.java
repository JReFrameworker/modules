package jref.java.lang;

import com.jreframeworker.annotations.methods.MergeMethod;
import com.jreframeworker.annotations.types.MergeType;

@MergeType(phase=2)
public class MyString extends java.lang.String {
	
	private static final long serialVersionUID = 1L;
	
	@MergeMethod
	@Override
	public boolean equals(Object object) {
		if (object instanceof String) {
			System.out.println(object);
		}
		return super.equals(object);
	}

}
