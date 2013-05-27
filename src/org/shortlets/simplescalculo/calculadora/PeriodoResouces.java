package org.shortlets.simplescalculo.calculadora;
import org.shortlets.simplescalculo.R;

import android.content.res.Resources;



public enum PeriodoResouces {
  DIA(R.string.dia)
 ,MES(R.string.mes)
 ,ANO(R.string.ano);
 
 private int resourcesId=-1;
  PeriodoResouces(int idRes) {
	resourcesId = idRes;
}
 public String valor(Resources r) {
     if (-1 != resourcesId) return (r.getString(resourcesId));
     return (this.toString());
 }
}

