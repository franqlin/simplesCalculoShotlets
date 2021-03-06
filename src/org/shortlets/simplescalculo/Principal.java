package org.shortlets.simplescalculo;

import org.shortlets.simplescalculo.util.ViewUtil;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Principal extends Activity {
	private ImageButton bntDescSimples;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_principal);

		//Botao(1) Desconto Simples
		bntDescSimples = (ImageButton) findViewById(R.id.idBntDescSimples);
		//bntDescSimples.setOnTouchListener(ViewUtil.getOnTouchListenerColor(Color.BLACK, Color.WHITE));
		bntDescSimples.setOnLongClickListener(ViewUtil.getOnLongClickListener(getString(R.string.descontoSimplesDescricao)));
		bntDescSimples.setOnClickListener(ViewUtil.getOnClickListenerIntent(getApplicationContext(), DescontoSimples.class, this));
		//fim Eventos do bot�o(1) 
		

		
		// Botao juros
		ImageButton bntJuros =(ImageButton) findViewById(R.id.idBntJuros);
		//bntJuros.setOnTouchListener(ViewUtil.getOnTouchListenerColor(Color.BLACK, Color.WHITE));
        bntJuros.setOnLongClickListener(ViewUtil.getOnLongClickListener(getString( R.string.jurosDescricao)));
        bntJuros.setOnClickListener(ViewUtil.getOnClickListenerIntent(getApplicationContext(), Juros.class, this));
        
        
		// Botao Financiamentos
		ImageButton bntFinanciamentos =(ImageButton) findViewById(R.id.idBntFinanciamentos);
		//bntFinanciamentos.setOnTouchListener(ViewUtil.getOnTouchListenerColor(Color.BLACK, Color.TRANSPARENT));
        bntFinanciamentos.setOnLongClickListener(ViewUtil.getOnLongClickListener(getString( R.string.financiamentosDescricao)));
        bntFinanciamentos.setOnClickListener(ViewUtil.getOnClickListenerIntent(getApplicationContext(), TabActivity.class, this));
        
        // Meu desconto
        //idBntMeuBoleto
		ImageButton bntMeuBoleto =(ImageButton) findViewById(R.id.idBntMeuBoleto);
		//bntMeuBoleto.setOnTouchListener(ViewUtil.getOnTouchListenerColor(Color.BLACK, Color.WHITE));
		bntMeuBoleto.setOnLongClickListener(ViewUtil.getOnLongClickListener(getString( R.string.title_activity_meu_boleto)));
		bntMeuBoleto.setOnClickListener(ViewUtil.getOnClickListenerIntent(getApplicationContext(), MeuBoleto.class, this));
        
		//Calendario 
		ImageButton bntGcal = (ImageButton) findViewById(R.id.idBntgCalc);
		//bntGcal.setOnTouchListener(ViewUtil.getOnTouchListenerColor(Color.BLACK, Color.WHITE));
		bntGcal.setOnClickListener(ViewUtil.getOnClickListenerIntent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI, this));
		bntGcal.setOnLongClickListener(ViewUtil.getOnLongClickListener(getString(R.string.agendaDescricao)));
		
		ImageButton bntCalendar = (ImageButton) findViewById(R.id.idBntCalendar);
		//bntCalendar.setOnTouchListener(ViewUtil.getOnTouchListenerColor(Color.BLACK, Color.WHITE));
		bntCalendar.setOnClickListener(ViewUtil.openCaledar(this));
		bntCalendar.setOnLongClickListener(ViewUtil.getOnLongClickListener(getString(R.string.caledarioDescricao)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	




}
