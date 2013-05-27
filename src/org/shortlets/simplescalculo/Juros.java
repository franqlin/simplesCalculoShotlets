package org.shortlets.simplescalculo;

import org.shortlets.simplescalculo.calculadora.Calculadora;
import org.shortlets.simplescalculo.calculadora.Periodo;
import org.shortlets.simplescalculo.calculadora.TipoCalculo;
import org.shortlets.simplescalculo.util.ViewUtil;
import org.shortlets.simplescalculo.util.ViewFormatacaoUtil;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.ActionBarSherlock.OnCreateOptionsMenuListener;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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
import android.widget.TextView;

public class Juros extends Activity implements OnCreateOptionsMenuListener {

	private double dValorPresent;
	private double dPercentual;
	private int iTempo;
	
	ActionBarSherlock mSherlock = ActionBarSherlock.wrap(this);
	private EditText valorPresente;
	private EditText taxa;
	private EditText tempo;
	private EditText valorFuturo;
	private Spinner  txPeriodo;
	private Spinner  tipoPeriodo;
    private TipoCalculo  tipoCalculo= TipoCalculo.SIMPLES; 
    private TextView respValorFuturo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_juros);
		
        mSherlock.setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        mSherlock.setContentView(R.layout.activity_juros);
        
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
		valorFuturo.setOnLongClickListener(ViewUtil.getOnLongClickListener("Valor Futuro"));
		
		respValorFuturo  = (TextView) findViewById(R.id.labelResp1);  
		respValorFuturo.setText("Valor Futuro");
		//respValorFuturo.setTextColor(Color.CYAN);
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
		 return mSherlock.dispatchCreateOptionsMenu(menu);
	}
	
	private final int SALVAR_ID = Menu.FIRST;
	private final int BUSCAR_ID = Menu.FIRST + 1;
	private final int ATUALIZAR_ID = Menu.FIRST + 2;
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        

		menu.add(Menu.NONE, SALVAR_ID, Menu.NONE,"Salvar")
            .setIcon( R.drawable.ic_compose)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(Menu.NONE, BUSCAR_ID, Menu.NONE,"Buscar")
            .setIcon(R.drawable.ic_search)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(Menu.NONE, ATUALIZAR_ID, Menu.NONE,"Atualizar")
            .setIcon( R.drawable.ic_refresh)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		// TODO Auto-generated method stub
	
		switch (item.getItemId()) {
		case SALVAR_ID:
			
			break;

		case BUSCAR_ID:
		
			       
            AlertDialog.Builder alertaBuscar = 
            new AlertDialog.Builder(this);         
            alertaBuscar.setTitle("Resposta");
           
            alertaBuscar.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                 // String value = input.getText().toString();
                  // Do something with value!
                    //You will get input data in this variable. 
                	dialog.cancel();
          
                  }
                });
                           
      
         
           AlertDialog choicesDialog = alertaBuscar.create();
           choicesDialog.show(); // show the Dialog            
         return true;
 
		case ATUALIZAR_ID:
			Log.i("Item selecionado no menu ",  "ATUALIZAR_ID ");
		       
            AlertDialog.Builder alertaAtualizarBuscar = 
            new AlertDialog.Builder(this);         
            alertaAtualizarBuscar.setTitle("Limpar tela?");
            alertaAtualizarBuscar.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
      	          Intent intent = new Intent(getApplicationContext(),Juros.class);
      	          Juros.this.finish();  
      	          startActivity(intent);
                    dialog.cancel();  
          
                  }
                });
                           
      
          // create an AlertDialog from the Builder
           AlertDialog choicesDialogAtlza = alertaAtualizarBuscar.create();
           choicesDialogAtlza.show(); // show the Dialog            
         return true;
		}
     	
		
		return super.onOptionsItemSelected(item);
	}

	
	private TextWatcher formatarEcalcular(final Tipo  tipo){
		TextWatcher formatarEcalcular = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				switch (tipo) {
				case VALORPRESENTE:
				    dValorPresent = ViewFormatacaoUtil.EditTextToDouble(s);
				    Log.i("VALOR_PRESENTE::", "::"+dValorPresent);
					break;
				case TAXA:
					dPercentual = ViewFormatacaoUtil.EditTextToDouble(s);
					 Log.i("VALOR_TAXA::", "::"+dPercentual);
					break;
				case TEMPO:
					iTempo =  ViewFormatacaoUtil.EditTextToInteiro(s);
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
		
		Periodo txPeriodo_ = txPeriodo.getSelectedItem().toString().equals(Periodo.DIA.valor(getResources()))? Periodo.DIA 
				: txPeriodo.getSelectedItem().toString().equals(Periodo.MES.valor(getResources()))? 
			    Periodo.MES:Periodo.ANO;
		Periodo tpPeriodo_ = tipoPeriodo.getSelectedItem().toString().equals(Periodo.DIA.valor(getResources()))? Periodo.DIA 
				: tipoPeriodo.getSelectedItem().toString().equals(Periodo.MES.valor(getResources()))? 
				Periodo.MES:Periodo.ANO;
		
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
