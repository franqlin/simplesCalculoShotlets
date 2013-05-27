package org.shortlets.simplescalculo;



import org.shortlets.simplescalculo.calculadora.TipoCalculo;
import org.shortlets.simplescalculo.util.ViewFormatacaoUtil;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.ActionBarSherlock.OnCreateOptionsMenuListener;
import com.actionbarsherlock.view.MenuItem;




import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker.OnValueChangeListener;

public class DescontoSimples extends Activity implements OnCreateOptionsMenuListener{


	ActionBarSherlock mSherlock = ActionBarSherlock.wrap(this);
	private double percentualCorrente;
	private TipoCalculo  tipoCalculo= TipoCalculo.DESCONTO;
    private EditText valorPresente;
    private EditText taxa;
    private EditText resp;
    private TextView labelResp;
    private double dValorPresente;
    private double dTaxa;
    private double valorfinal=0.0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
        mSherlock.setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        mSherlock.setContentView(R.layout.activity_desconto_simples);
		valorPresente = (EditText) findViewById(R.id.idValorPresente);
		valorPresente.addTextChangedListener(formatarEcalcular(Tipo.VALORPRESENTE));
	
		taxa = (EditText) findViewById(R.id.idPorcentagem);
		taxa.addTextChangedListener(formatarEcalcular(Tipo.TAXA));  
        
		resp = (EditText) findViewById(R.id.idTotalDescontoFinal);
		labelResp = (TextView) findViewById(R.id.labelResp1);
		
	}

	private TextWatcher formatarEcalcular(final Tipo tipoCalculo) {
		
		TextWatcher tw = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				switch (tipoCalculo) {
				case VALORPRESENTE:
					dValorPresente = ViewFormatacaoUtil.EditTextToDouble(s);
					break;

				case TAXA:
					dTaxa = ViewFormatacaoUtil.EditTextToDouble(s);
					break;
				}
				calcular();
			}


			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		};
			return tw;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 return mSherlock.dispatchCreateOptionsMenu(menu);
	}
	
	

	private void calcular() {
       
       
       switch (tipoCalculo) {
	case DESCONTO:
		labelResp.setText(R.string.labelRespDesconto);
		valorfinal = dValorPresente - (dValorPresente * dTaxa * .01);
		break;

	case ACRESCIMO:
		labelResp.setText(R.string.labelRespAcrescimo);
		valorfinal = dValorPresente + (dValorPresente * dTaxa * .01);
		break;
	}
       resp.setText(String.format("%.02f", valorfinal));  
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		//outState.putDouble(DESC_TOTAL, valorDeEntrada);
		//outState.putDouble(PERCENTUAL, percentualCorrente);
	}

	public void escolherTipoCalculo(View view) {
		   
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    
	    switch(view.getId()) {
	        case R.id.idRadioDesconto:
	            if (checked)
	            	tipoCalculo= TipoCalculo.DESCONTO; 
	                calcular();
	            break;
	        case R.id.idRadioAcrescimo:
	            if (checked)
	            	tipoCalculo= TipoCalculo.ACRESCIMO; 
	            	calcular();
	            break;
	    }
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
            alertaBuscar.setMessage("Valor Presente: "+dValorPresente +
            		"\nTaxa: "+dTaxa+"%\n"+
            		"Valor final: "+valorfinal);
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
      	          Intent intent = new Intent(getApplicationContext(),DescontoSimples.class);
      	          DescontoSimples.this.finish();  
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



	
}
