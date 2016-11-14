package com.example.martin.proyectoprueban2;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Bean.SexoBean;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class Listado extends Activity {

    ListView LISTSEXO;
    TextView lblcodigo;
    TextView lblnombre;
    TextView linea;
    EditText edt1;
    Button btn1 ;

    ArrayList codigo = new ArrayList();
    ArrayList nombre = new ArrayList();

    ArrayList combinacion = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LISTSEXO = (ListView) findViewById(R.id.lstvw1);
        edt1 = (EditText)findViewById(R.id.edt1);
        btn1 = (Button)findViewById(R.id.btn1);

        ListadoSexo();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String validar = edt1.getText().toString();

                if(validar.isEmpty()){

                    ListadoSexo();
                    edt1.setError("Ingrese Nonbre xfavor...");

                }else {

                    Buscar(validar);

                }

            }
        });

    }

    public void Buscar( String nombreRecibido){

        codigo.clear();
        nombre.clear();
        combinacion.clear();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://examenfinal2016.esy.es/PROYECTOWEBSERVICE/CONTROLADOR/SexoControlador.php?op=5";

        RequestParams requestParams = new RequestParams();
        requestParams.add("nombsex",nombreRecibido);

        //Toast.makeText(getApplicationContext(),"->"+nombre+"<-",Toast.LENGTH_SHORT).show();

        RequestHandle post = client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(statusCode==200){

                    try{

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for(int i=0; i<jsonArray.length();i++) {

                            codigo.add(jsonArray.getJSONObject(i).getInt("CODSEXO"));
                            nombre.add(jsonArray.getJSONObject(i).getString("NOMBSEXO"));

                            //Este array se creo para concatenar los array's cargados , sirve para el BaseAdapter.
                            combinacion.add(codigo + " " + nombre);
                        }

                        if(combinacion.size()==0){

                            combinacion.clear();
                            LISTSEXO.setAdapter(new baseAdapter(getApplicationContext()));
                            Toast.makeText(getApplicationContext(),"No hay resultados...",Toast.LENGTH_SHORT).show();

                        }else {

                            LISTSEXO.setAdapter(new baseAdapter(getApplicationContext()));
                        }

                    }catch (Exception ex){ }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(getApplicationContext(),"NO LLEGO EL PARAMETRO ADECUADO...",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void ListadoSexo(){

        nombre.clear();
        codigo.clear();
        combinacion.clear();

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://examenfinal2016.esy.es/PROYECTOWEBSERVICE/CONTROLADOR/SexoControlador.php?op=1", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(statusCode == 200){

                    try{
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for(int i=0; i<jsonArray.length();i++){

                           codigo.add(jsonArray.getJSONObject(i).getInt("CODSEXO"));
                            nombre.add(jsonArray.getJSONObject(i).getString("NOMBSEXO"));

                            //Este array se creo para concatenar los array's cargados , sirve para el BaseAdapter.
                            combinacion.add(codigo+" "+nombre);


                        }

                        //funcion utilizado para comprobar si los datos llegaron al APP o ocurrio algo.
                        /*for(int j=0; j<nombre.size();j++){Toast.makeText(getApplicationContext()," "+nombre.get(j),Toast.LENGTH_SHORT).show();}*/

                        LISTSEXO.setAdapter(new baseAdapter(getApplicationContext()));

                    }catch (Exception ex)
                    {}

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Toast.makeText(getApplicationContext(),"NO OCURRE NADA",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private class baseAdapter extends BaseAdapter{

        Context context;
        LayoutInflater layoutInflater;

        public baseAdapter(Context applicationContext) {

            this.context = applicationContext;
            layoutInflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return combinacion.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.grilla,null);

            lblcodigo = (TextView)viewGroup.findViewById(R.id.lblcodigo);
            lblnombre = (TextView)viewGroup.findViewById(R.id.lblnombre);
            linea = (TextView)viewGroup.findViewById(R.id.lbllinea);

            lblcodigo.setText(codigo.get(position).toString());
            lblcodigo.setTextColor(getResources().getColor(R.color.colorPrimary));

            lblnombre.setText(nombre.get(position).toString());
            lblnombre.setTextColor(getResources().getColor(R.color.colorAccent));

            return viewGroup;

        }
    }

}
