package org.shortlets.simplescalculo;

import org.joda.time.DateTime;
import org.shortlets.simplescalculo.calculadora.Calculadora;
import org.shortlets.simplescalculo.calculadora.TipoTaxa;
import org.shortlets.simplescalculo.util.ViewFormatacaoUtil;
import org.shortlets.simplescalculo.util.ViewSelectDatePopUp;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.ActionBarSherlock.OnCreateOptionsMenuListener;
import com.actionbarsherlock.view.MenuItem;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("ValidFragment")
public class MeuBoleto extends FragmentActivity implements OnCreateOptionsMenuListener{
    private EditText valorAtual;
	private EditText dtVcto;
    private EditText dtPgto;
    private EditText resp;
    private TextView tvResp;
    private EditText edMulta;
	private EditText edJuro;
    //private int dias;
	ActionBarSherlock mSherlock = ActionBarSherlock.wrap(this);
    private DateTime dateTimeVcto;
    private DateTime dateTimePgto;
    private double valorBoleto,valorMulta,valorJuros;
    private Spinner spMulta;
    private Spinner spJuros;
    
    Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        mSherlock.setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        mSherlock.setContentView(R.layout.activity_meu_boleto);
		//ImageButton btVcto = (ImageButton) findViewById(R.id.idEdDtVcto);
		valorAtual = (EditText) findViewById(R.id.idValorPresente);
		valorAtual.addTextChangedListener(atualizarECalcular(Tipo.VALORBOLETO));
		
		dtVcto = (EditText) findViewById(R.id.idEdDtVcto);
		dtVcto.addTextChangedListener(atualizarData(Tipo.DT_VENCIMENTO));
		
		dtPgto = (EditText) findViewById(R.id.idEdDtPgto); 
		dtPgto.addTextChangedListener(atualizarData(Tipo.DT_PAGAMENTO));
		
		edMulta = (EditText) findViewById(R.id.idEdMulta);
		edMulta.addTextChangedListener(atualizarECalcular(Tipo.MULTA));
       
		edJuro = (EditText) findViewById(R.id.idTaxaJuro);
		edJuro.addTextChangedListener(atualizarECalcular(Tipo.JUROS));
		
		
		tvResp  = (TextView) findViewById(R.id.labelResp1);
		tvResp.setText(R.string.valorAtualizado);
		resp =(EditText)findViewById(R.id.idTotalDescontoFinal);
		
		spMulta =(Spinner) findViewById(R.id.idTipoMulta);
		spMulta.setOnItemSelectedListener(onItemSelectedListener);
		
		spJuros =(Spinner) findViewById(R.id.idTipoTx);
		spJuros.setOnItemSelectedListener(onItemSelectedListener);

		
		handler = new Handler();
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
	private TextWatcher atualizarECalcular(final Tipo  tipo) {
		TextWatcher textWatcher = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			
			@Override
			public void afterTextChanged(Editable valor) {				       

				switch (tipo) {
				case VALORBOLETO:
					valorBoleto = ViewFormatacaoUtil.EditTextToDouble(valor);
					break;

				case MULTA:
					valorMulta = ViewFormatacaoUtil.EditTextToDouble(valor);
					break;
					
				case JUROS:
					valorJuros = ViewFormatacaoUtil.EditTextToDouble(valor);
					break;	
				}
				calcular();		
				
			}
		};
		return textWatcher;
	}
	private void calcular() {
		
		TipoTaxa ttMulta = spMulta.getSelectedItem().toString().equals(TipoTaxa.DINHEIRO.valor(getResources()))? TipoTaxa.DINHEIRO : TipoTaxa.PERCENTUAL;
		TipoTaxa ttJuros = spJuros.getSelectedItem().toString().equals(TipoTaxa.DINHEIRO.valor(getResources()))? TipoTaxa.DINHEIRO : TipoTaxa.PERCENTUAL;
		
		Log.i("<<<<<spMulta>>>>",ttMulta.toString());
		Log.i("<<<<<ttJuros>>>>",ttJuros.toString());
		
		double valorAtualizado = Calculadora.calcularBoleto(valorBoleto, valorMulta, valorJuros,dateTimeVcto, dateTimePgto,ttMulta,ttJuros);
		resp.setText(String.format("%.02f", valorAtualizado));		
	}
	private TextWatcher atualizarData(final Tipo  tipo) {
		TextWatcher textWatcher = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
                        switch (tipo) {
						case DT_VENCIMENTO:
							 dateTimeVcto =ViewFormatacaoUtil.createDateTime(dtVcto.getText().toString());
							break;
						case DT_PAGAMENTO:
							dateTimePgto =ViewFormatacaoUtil.createDateTime(dtPgto.getText().toString());
							break;
						default:
							break;
						}
				        
				        handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								//dias = Calculadora.calcularDiferencaEntreDatas(dateTimeVcto, dateTimePgto);
								//resp.setText("::"+ dias);
								calcular();
							}
						}, 500);
						
						 
				
			}
		};
		return textWatcher;
	}



	public void selecionarDataVencimento(View view) {
		DialogFragment dt = new ViewSelectDatePopUp();
		((ViewSelectDatePopUp)dt).assinarActivity(this);
		((ViewSelectDatePopUp)dt).setCampoData(R.id.idEdDtVcto);
		dt.show(getSupportFragmentManager(), "DatePicker");

		//dateTimeVcto = new DateTime(ano, mes, dia, 12, 0, 0, 0);
		//Log.i("dateTimeVcto>>> "," "+dateTimeVcto);

	}
	
	public void selecionarDataPagamento(View view) {
		DialogFragment dt = new ViewSelectDatePopUp();
		((ViewSelectDatePopUp)dt).assinarActivity(this);
		((ViewSelectDatePopUp)dt).setCampoData(R.id.idEdDtPgto);
		dt.show(getSupportFragmentManager(), "DatePicker");

		//dateTimePgto = new DateTime(ano, mes, dia, 12, 0, 0, 0);
		//Log.i("dateTimePgto>>> "," "+dateTimePgto);

	}
	
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
      	          Intent intent = new Intent(getApplicationContext(),MeuBoleto.class);
      	          MeuBoleto.this.finish();  
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
