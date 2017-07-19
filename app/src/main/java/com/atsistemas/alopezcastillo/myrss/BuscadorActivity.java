package com.atsistemas.alopezcastillo.myrss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.atsistemas.alopezcastillo.myrss.utils.Constantes;
import com.atsistemas.alopezcastillo.myrss.utils.Conversor;

public class BuscadorActivity extends AppCompatActivity {
    private EditText etTitulo,etCuerpo;
    private CheckBox cbTitulo,cbCuerpo,cbFecha;
    private DatePicker dpFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        etTitulo=(EditText)findViewById(R.id.etTitulo);
        etCuerpo=(EditText)findViewById(R.id.etCuerpo);
        dpFecha=(DatePicker) findViewById(R.id.dpFecha);
        cbTitulo=(CheckBox)findViewById(R.id.cbTitulo);
        cbCuerpo=(CheckBox)findViewById(R.id.cbCuerpo);
        cbFecha=(CheckBox)findViewById(R.id.cbFecha);

    }
    /**
     * Cierra la vista de Buscador y vuelve al menú.
     * @param view
     */
    public void cerrar(View view) {

        finish();
    }
    public void buscar(View view)
    {

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        if(!cbTitulo.isChecked()){etTitulo.setText("");}
        resultIntent.putExtra("titular", etTitulo.getText());
        if(!cbCuerpo.isChecked()){etCuerpo.setText("");}
        resultIntent.putExtra("cuerpo",etCuerpo.getText());
        if(cbFecha.isChecked())
        {resultIntent.putExtra("fechaId", Conversor.getDateFromDatePicker(dpFecha).getTime());}
        else
        {resultIntent.putExtra("fechaId",0L);}
        setResult(Constantes.COD_FILTRO, resultIntent);
        finish();

    }


}
