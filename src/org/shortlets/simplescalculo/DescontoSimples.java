package org.shortlets.simplescalculo;

import java.util.ArrayList;
import java.util.Locale;



import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker.OnValueChangeListener;

public class DescontoSimples extends Activity implements OnInitListener{
	private static final String DESC_TOTAL = "DESC_TOTAL";
	private static final String PERCENTUAL = "PERCENTUAL";

	private double valorDeEntrada;
	private double percentualCorrente;

	private EditText valorDeEntradaTexto;
	private TextView percentualTexto;
	private EditText descontoTexto;
	private EditText totalDescontoFinal;
	private NumberPicker npInteiro;
	private NumberPicker npDecimal;
	private int pInteira=10;
	private int pDecimal;
	// Falar
    private ImageButton btnSpeak;
    protected static final int RESULT_SPEECH = 1;
    private int MY_DATA_CHECK_CODE = 0;
    protected TextToSpeech tts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desconto_simples);

		if (savedInstanceState == null) {
			valorDeEntrada = 0.0;
			percentualCorrente = 0.0;
			
		} else {

			valorDeEntrada = savedInstanceState.getDouble(DESC_TOTAL);
			percentualCorrente = savedInstanceState.getInt(PERCENTUAL);
		}

		
		descontoTexto = (EditText) findViewById(R.id.idDescontoTexto);
		totalDescontoFinal = (EditText) findViewById(R.id.idTotalDescontoFinal);
		
		
		
		valorDeEntradaTexto = (EditText) findViewById(R.id.idValorTexto);

		valorDeEntradaTexto.addTextChangedListener(formatarTextoParaNumerico);
		//SeekBar percentualSeekBar = (SeekBar) findViewById(R.id.idPercentualSeekBar);
        percentualTexto = (TextView) findViewById(R.id.idPercentual);
		//percentualSeekBar.setOnSeekBarChangeListener(setSeekBarListener);

		
		npInteiro = (NumberPicker) findViewById(R.id.npInteiro);
		npInteiro.setMaxValue(100);
		npInteiro.setMinValue(0);
		npInteiro.setWrapSelectorWheel(true);
		npInteiro.setValue(10);
		npInteiro.setOnValueChangedListener(parteInteiraChangedListener);
		
		npDecimal = (NumberPicker) findViewById(R.id.npDecimal);
		npDecimal.setMaxValue(99);
		npDecimal.setMinValue(0);
		npDecimal.setValue(0);
		npDecimal.setWrapSelectorWheel(true);
		npDecimal.setOnValueChangedListener(parteDecimalChangedListener);
		
		
		//Reconhecimento de Fala
       // txtText = (TextView) findViewById(R.id.txtText);
        
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(onClickSpeekButtonListener);
        
        // Leitura do resultado ::
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.desconto_simples, menu);
		return true;
	}
	
	private OnClickListener onClickSpeekButtonListener =new OnClickListener() {
		 
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "pt-BR");

            try {
                startActivityForResult(intent, RESULT_SPEECH);
                valorDeEntradaTexto.setText("");
            } catch (ActivityNotFoundException a) {
                Toast t = Toast.makeText(getApplicationContext(),
                        "Seu dispositivo n‹o suporta texto!",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
        switch (requestCode) {
        case RESULT_SPEECH: {
            if (resultCode == RESULT_OK && null != data) {
 
                ArrayList<String> text = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
 
                valorDeEntradaTexto.setText(text.get(0));
            }
            break;
        }
 
        }
        
        
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                tts = new TextToSpeech(this, this);
            } 
            else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
         }
    }
	
	private OnValueChangeListener parteInteiraChangedListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			pInteira = picker.getValue();
			calcularDesconto();

		}

	};
	
	private OnValueChangeListener parteDecimalChangedListener = new OnValueChangeListener() {

		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			pDecimal = picker.getValue();
			calcularDesconto();

		}

	};

	private void calcularDesconto() {
		// atualizar valor de porcentagem na tela
		
		percentualCorrente = Double.valueOf(pInteira +"."+pDecimal).doubleValue();
		percentualTexto.setText("Taxa ("+percentualCorrente + "%)");
		// calcular desconto
		double customTipAmount = valorDeEntrada * percentualCorrente * .01;
		// calcular total de desconto
		double customTotalAmount = valorDeEntrada - customTipAmount;

		// mostrar valores
		descontoTexto.setText(String.format("%.02f", customTipAmount));
		totalDescontoFinal.setText(String.format("%.02f", customTotalAmount));
		
		String valorDesconto = descontoTexto.getText().toString();
		String valorDescontoTotal = totalDescontoFinal.getText().toString();
		String text ="O seu desconto foi de "+valorDesconto +". Total a pagar "+valorDescontoTotal;
		valorDeEntradaTexto.getText().toString();
	
		if (valorDeEntradaTexto.getText().toString().length() > 0) {
			
			Toast.makeText(DescontoSimples.this, "Saying: " + text,Toast.LENGTH_LONG).show();
			tts.speak(text, TextToSpeech.QUEUE_ADD, null);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putDouble(DESC_TOTAL, valorDeEntrada);
		outState.putDouble(PERCENTUAL, percentualCorrente);
	}

 

	private TextWatcher formatarTextoParaNumerico = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			try {
				valorDeEntrada = Double.parseDouble(s.toString());
			} catch (NumberFormatException e) {
				valorDeEntrada = 0.0;
			}

			calcularDesconto();
		}

		@Override
		public void afterTextChanged(Editable s) {
		} 

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		} 
	};

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			Toast.makeText(DescontoSimples.this,"Text-To-Speech engine foi inicialiado", Toast.LENGTH_LONG).show();
			tts.setLanguage(new Locale("pt-BR"));
		
		
		} else if (status == TextToSpeech.ERROR) {
			Toast.makeText(DescontoSimples.this,
					"Erro ao iniciaizar o  Text-To-Speech engine",
					Toast.LENGTH_LONG).show();
		}
		
	} 
}
