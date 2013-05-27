package org.shortlets.simplescalculo.util;

public class Calculadora {
	
	public static double  calcularJurosSimples(double valorPresente,double txJuros,int tempo){
		System.out.print("TEste");
		return valorPresente + (valorPresente*txJuros*tempo);
	}
	
	public static void main(String[] args){
		System.out.printf("Juros::%d",Calculadora.calcularJurosSimples(100.0, 1.90, 3));
	}

}
