package org.shortlets.simplescalculo.calculadora;

import android.util.Log;



public class Calculadora {
	public static double  calcularJurosSimples(double valorPresente,double txJuros,double tempo,PeriodoResouces periodo ,PeriodoResouces taxaAoPeriodo){
		txJuros = txJuros * .01;
		TaxaTempo taxaTempo = new TaxaTempo();
		taxaTempo.calcular(txJuros, tempo, periodo, taxaAoPeriodo);
		return valorPresente + (valorPresente*taxaTempo.taxa*taxaTempo.tempo);
	}
	public static double  calcularJurosCompostos(double valorPresente,double txJuros,double tempo,PeriodoResouces periodo ,PeriodoResouces taxaAoPeriodo){
		txJuros = txJuros * .01;
		TaxaTempo taxaTempo = new TaxaTempo();
		taxaTempo.calcular(txJuros, tempo, periodo, taxaAoPeriodo);
		return valorPresente *( Math.pow(1+taxaTempo.taxa,taxaTempo.tempo));
	}
	
	
	public static void main(String[] args){
		System.out.printf("\nJuros(A,A)::%.02f",Calculadora.calcularJurosSimples(150, 0.89, 6,PeriodoResouces.ANO,PeriodoResouces.ANO));
	    System.out.printf("\nJuros(A,M)::%.02f",Calculadora.calcularJurosSimples(150, 0.89, 6,PeriodoResouces.ANO,PeriodoResouces.MES));
		System.out.printf("\nJuros(A,D)::%.02f",Calculadora.calcularJurosSimples(150, 0.89, 6,PeriodoResouces.ANO,PeriodoResouces.DIA));
		System.out.println("\n+++++++++++++++++++++++++++++++++++");
		System.out.printf("\nJuros(M,A)::%.02f",Calculadora.calcularJurosSimples(150, 0.89, 6,PeriodoResouces.MES,PeriodoResouces.ANO));
		System.out.printf("\nJuros(M,M)::%.02f",Calculadora.calcularJurosSimples(150, 0.89, 6,PeriodoResouces.MES,PeriodoResouces.MES));
		System.out.printf("\nJuros(M,D)::%.02f",Calculadora.calcularJurosSimples(150, 0.89, 6,PeriodoResouces.MES,PeriodoResouces.DIA));
		System.out.println("\n+++++++++++++++++++++++++++++++++++");
		System.out.printf("\nJuros(D,A)::%.02f",Calculadora.calcularJurosSimples(150, 0.89, 6,PeriodoResouces.DIA,PeriodoResouces.ANO));
		System.out.printf("\nJuros(D,M)::%.02f",Calculadora.calcularJurosSimples(150, 0.89, 6,PeriodoResouces.DIA,PeriodoResouces.MES));
		System.out.printf("\nJuros(D,D)::%.02f",Calculadora.calcularJurosSimples(150, 0.89, 6,PeriodoResouces.DIA,PeriodoResouces.DIA));

	}
	
	private static class TaxaTempo{
		protected double tempo;
		protected double taxa;

      protected void calcular(double _txJuros,double _tempo,PeriodoResouces periodo ,PeriodoResouces taxaAoPeriodo){
  		switch (periodo) {
  		case ANO: 
  			_tempo = _tempo * 12;
  			_txJuros = taxaAoPeriodo.equals(PeriodoResouces.ANO) ? _txJuros / 12
  					: taxaAoPeriodo.equals(PeriodoResouces.DIA) ? _txJuros * 365 / 12:_txJuros;
  			break;

  		case MES:
  			_tempo =   taxaAoPeriodo.equals(PeriodoResouces.ANO) ? _tempo / 12
  					: taxaAoPeriodo.equals(PeriodoResouces.DIA) ? _tempo / 12
  					: _tempo;
  			Log.i("PERIODO MES tempo::", ""+ _tempo );
  			_txJuros = taxaAoPeriodo.equals(PeriodoResouces.DIA) ? _txJuros * 360
  					: _txJuros;
  			Log.i("PERIODO MES tx::", ""+ _txJuros );
  			break;
  			
  		case DIA:
  			_tempo =   taxaAoPeriodo.equals(PeriodoResouces.ANO) ? _tempo / 365
  					: taxaAoPeriodo.equals(PeriodoResouces.MES) ? _tempo / 365
  					: _tempo;
  			Log.i("PERIODO DIA::", ""+ tempo );
  			_txJuros = taxaAoPeriodo.equals(PeriodoResouces.MES) ? _txJuros * 12
  					: _txJuros;
  			break;
  		}
  		this.tempo =_tempo;
  		this.taxa = _txJuros;
      }
	   	
	}

}
