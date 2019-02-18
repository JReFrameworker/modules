package jref.java.lang;

import com.jreframeworker.annotations.fields.DefineFieldFinality;
import com.jreframeworker.annotations.fields.DefineFieldVisibility;
import com.jreframeworker.annotations.types.DefineTypeFinality;

@DefineTypeFinality(phase=1, type="java.lang.String", finality=false)
@DefineFieldFinality(phase=1, type="java.lang.String", field="value", finality=false)
@DefineFieldVisibility(phase=1, type="java.lang.String", field="value", visibility="protected")
public class Prebuild {}
