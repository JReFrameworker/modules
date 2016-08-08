package com.nianticproject.holoholo.sfida.unity;

import jreframeworker.annotations.methods.DefineMethod;
import jreframeworker.annotations.methods.MergeMethod;
import jreframeworker.annotations.types.MergeType;

@MergeType
public class NotifyLegendaryPokemon extends com.nianticproject.holoholo.sfida.unity.SfidaUnityPlugin {

	@MergeMethod
	@Override
	public boolean notifySpawnedLegendaryPokemon(String paramString){
		vibrate();
		return super.notifySpawnedLegendaryPokemon(paramString);
	}
	
	@DefineMethod
	private static void vibrate(){
		// TODO: replace with Android vibrate method call
		System.out.println("buzz buzz");
//		Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
//		v.vibrate(500);
	}
}
