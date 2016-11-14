package com.example.martin.proyectoprueban2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Bean.SexoBean;

/**
 * Created by Martin on 08/11/2016.
 */

public class Personalizacion extends BaseAdapter {

    private static ArrayList<SexoBean> lista;
    private LayoutInflater minflater;

    public Personalizacion(Context context , ArrayList<SexoBean> lista) {

        this.minflater = minflater.from(context);
        this.lista = lista;

    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null){

                convertView = minflater.inflate(R.layout.grilla,null);
                holder = new ViewHolder();
                holder.LBLCODIGO=(TextView)convertView.findViewById(R.id.lblcodigo);
                holder.LBLNOMBRE=(TextView)convertView.findViewById(R.id.lblnombre);
                convertView.setTag(holder);

        }else{
                holder = (ViewHolder)convertView.getTag();
        }

            holder.LBLCODIGO.setText(lista.get(position).getCodigoSexo());
            holder.LBLNOMBRE.setText(lista.get(position).getNombreSexo());

        return convertView;
    }

    static class ViewHolder{

        TextView LBLCODIGO;
        TextView LBLNOMBRE;

    }
}
