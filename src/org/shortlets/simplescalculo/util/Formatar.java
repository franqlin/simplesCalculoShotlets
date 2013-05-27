package org.shortlets.simplescalculo.util;

import org.shortlets.simplescalculo.calculadora.PeriodoResouces;

import android.annotation.SuppressLint;

public class Formatar {

	
	@SuppressLint("UseValueOf")
	public static Double EditTextToDouble(CharSequence s){
		Double val;
		try{
			val = Double.parseDouble(s.toString());
		}catch (NumberFormatException e) {
			val = new Double(0.0);
		}
		
		return val;
	}
	
	public static Integer EditTextToInteiro(CharSequence s){
		Integer val;
		try{
			val = Integer.parseInt(s.toString());
		}catch (NumberFormatException e) {
			val = new Integer(0);
		}
		
		return val;
	}

}
