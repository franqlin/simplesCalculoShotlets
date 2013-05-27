package org.shortlets.simplescalculo;

import org.shortlets.simplescalculo.calculadora.Calculadora;
import org.shortlets.simplescalculo.calculadora.PeriodoResouces;
import org.shortlets.simplescalculo.calculadora.TipoCalculo;
import org.shortlets.simplescalculo.util.Formatar;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class Juros extends Activity {

	private double dValorPresent;
	private double dPercentual;
	private int iTempo;
	
	private EditText valorPresente;
	private EditText taxa;
	private EditText tempo;
	private EditText valorFuturo;
	private Spinner  txPeriodo;
	private Spinner  tipoPeriodo;
    private TipoCalculo  tipoCalculo= TipoCalculo.SIMPLES; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juros);
		
		if(savedInstanceState ==null){
			dValorPresent =0.0;
			dValorPresent=0.0;
			iTempo=0;
		}else{
			
		}
		// valor presente e seus eventos
		valorPresente = (EditText) findViewById(R.id.idValorPresente);
		valorPresente.addTextChangedListener(formatarEcalcular(Tipo.VALORPRESENTE));
		// taxa(%)
		taxa = (EditText) findViewById(R.id.idValorPercentual);
		taxa.addTextChangedListener(formatarEcalcular(Tipo.TAXA));
		//tempo (D,M,A)
		tempo = (EditText) findViewById(R.id.idTempo);
		tempo.addTextChangedListener(formatarEcalcular(Tipo.TEMPO));
		
		txPeriodo =(Spinner) findViewById(R.id.idPeriodoTx);
		txPeriodo.setOnItemSelectedListener(onItemSelectedListener);
		
		tipoPeriodo=(Spinner) findViewById(R.id.idTipoPeriodo);
		tipoPeriodo.setOnItemSelectedListener(onItemSelectedListener);
		
		valorFuturo = (EditText) findViewById(R.id.idTotalDescontoFinal);
	}
   
	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		         
			calcular();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.juros, menu);
		return true;
	}
	
	private TextWatcher formatarEcalcular(final Tipo  tipo){
		TextWatcher formatarEcalcular = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				switch (tipo) {
				case VALORPRESENTE:
				    dValorPresent = Formatar.EditTextToDouble(s);
				    Log.i("VALOR_PRESENTE::", "::"+dValorPresent);
					break;
				case TAXA:
					dPercentual = Formatar.EditTextToDouble(s);
					 Log.i("VALOR_TAXA::", "::"+dPercentual);
					break;
				case TEMPO:
					iTempo =  Formatar.EditTextToInteiro(s);
					Log.i("TEMPO::", "::"+iTempo);
					break;
				default:
					break;
				}
				calcular();
			}


			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
			return formatarEcalcular;
	}
	
	private void calcular() {
		//Log.i("PERIODO TAXA::", "==ano"+ Periodo.ANO.valor(getResources()) );
		//Log.i("TIPO PERIODO::","Ž igual a ano "+ tipoPeriodo.getSelectedItem().toString().equals(Periodo.ANO.valor(getResources())) );
		
		PeriodoResouces txPeriodo_ = txPeriodo.getSelectedItem().toString().equals(PeriodoResouces.DIA.valor(getResources()))? PeriodoResouces.DIA 
				: txPeriodo.getSelectedItem().toString().equals(PeriodoResouces.MES.valor(getResources()))? 
			    PeriodoResouces.MES:PeriodoResouces.ANO;
		PeriodoResouces tpPeriodo_ = tipoPeriodo.getSelectedItem().toString().equals(PeriodoResouces.DIA.valor(getResources()))? PeriodoResouces.DIA 
				: tipoPeriodo.getSelectedItem().toString().equals(PeriodoResouces.MES.valor(getResources()))? 
				PeriodoResouces.MES:PeriodoResouces.ANO;
		
		//Log.i("PERIODO TAXA::", ""+ txPeriodo_.valor(getResources()) );
		//Log.i("TIPO PERIODO::", ""+ tpPeriodo_.valor(getResources()) );
		double valor=0.0;
		switch (tipoCalculo) {
		case SIMPLES:
			Log.i("juros::", tipoCalculo.toString());
			 valor=Calculadora.calcularJurosSimples(dValorPresent, dPercentual, iTempo, tpPeriodo_,txPeriodo_);
			break;
  
		case COMPOSTO:
			Log.i("jurosComposto::", tipoCalculo.toString());
			 valor=Calculadora.calcularJurosCompostos(dValorPresent, dPercentual, iTempo, tpPeriodo_,txPeriodo_);
			break;
		}
		
		
		valorFuturo.setText(String.format("%.02f", valor));
	}
	
	public void onRadioButtonClicked(View view) {
	   
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    
	    switch(view.getId()) {
	        case R.id.idDjurosSimplesRadio:
	            if (checked)
	            	tipoCalculo= TipoCalculo.SIMPLES; 
	                calcular();
	            break;
	        case R.id.idDjurosComposto:
	            if (checked)
	            	tipoCalculo= TipoCalculo.COMPOSTO; 
	            	calcular();
	            break;
	    }
	}


}
