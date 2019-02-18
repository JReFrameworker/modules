package jref.java.lang;

import com.jreframeworker.annotations.methods.DefineMethod;
import com.jreframeworker.annotations.types.MergeType;

@MergeType(phase=2)
public class MyString extends java.lang.String {
	
	// This example appears to be not possible to achieve with JReFrameworker (or any bytecode modification tool)
	// because it violates an underlying assumption in the JVM
	// https://stackoverflow.com/questions/23471222/trivial-modification-to-java-lang-string-in-rt-jar-causing-vm-segfault
	
	private static final long serialVersionUID = 1L;
	
	// original code - STILL BREAKS!
	@DefineMethod
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if ((object instanceof String)) {
			String str = (String) object;
			int i = this.value.length;
			if (i == str.value.length) {
				char[] arrayOfChar1 = this.value;
				char[] arrayOfChar2 = str.value;
				for (int j = 0; i-- != 0; j++) {
					if (arrayOfChar1[j] != arrayOfChar2[j]) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	// replace equals method with merge method contents + original method contents
//	@DefineMethod
//	@Override
//	public boolean equals(Object object) {
//		if (object instanceof String) {
//			System.out.println("JREF: " + object);
//		}
//		if (this == object) {
//			return true;
//		}
//		if ((object instanceof String)) {
//			String str = (String) object;
//			int i = this.value.length;
//			if (i == str.value.length) {
//				char[] arrayOfChar1 = this.value;
//				char[] arrayOfChar2 = str.value;
//				for (int j = 0; i-- != 0; j++) {
//					if (arrayOfChar1[j] != arrayOfChar2[j]) {
//						return false;
//					}
//				}
//				return true;
//			}
//		}
//		return false;
//	}
	
	// intercept and print string 
//	@MergeMethod
//	@Override
//	public boolean equals(Object object) {
//		if (object instanceof String) {
//			System.out.println("JREF: " + object);
//		}
//		return super.equals(object);
//	}

}
