package com.example.user.androidzadatak;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListAdapter extends BaseAdapter {

    //Deklaracija varijabli
    private ArrayList<Accomodation> accomodationList;
    private LayoutInflater mInflater;

    //Postavljanje konteksta i liste podataka iz aktivnosti MainActivity
    public ListAdapter(Context context, ArrayList<Accomodation> results) {
        mInflater = LayoutInflater.from(context);
        accomodationList = results;
    }

    public int getCount() {
        return accomodationList.size();
    }

    public Object getItem(int position) {
        return accomodationList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    //ViewHolder sprema pristup UI elementima
    static class ViewHolder {
        ImageView ivThumbnail;
        TextView tvName;
        TextView tvAddress;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Deklaracija viewHoldera
        ViewHolder holder;

        //Ako se lista učitava prvi put, učita se layout i zapiše se pristup do elemenata u viewHolder
        //Na taj način nije potrebno pozivati findViewById za svako učitavanje
        if (convertView == null || convertView.getTag() == null) {
            convertView = mInflater.inflate(R.layout.list_row, parent, false);
            holder = new ViewHolder();

            holder.ivThumbnail = (ImageView) convertView.findViewById(R.id.ivAccomodationThumbnail);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvAccomodationName);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.tvAccomodationAddress);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Dobivanje URI stringa za slike
        String image = accomodationList.get(position).getImage().get(0);
        String uri = "com.example.user.androidzadatak:drawable/" + image;

        //Konvertiranje URI u id slike
        int id = convertView.getResources().getIdentifier(uri, null, null);
        //Konvertiranje slike u bitmap i promjena veličine za očuvanje memorije
        Bitmap bmImage = BitmapFactory.decodeResource(convertView.getResources(), id);
        Bitmap resizedImage = Bitmap.createScaledBitmap(bmImage,(int)(bmImage.getWidth()*0.15), (int)(bmImage.getHeight()*0.15), true);

        //Zapisivanje ostalih podataka u varijable
        String name = accomodationList.get(position).getName();
        String streetAddress = accomodationList.get(position).getStreetAddress();
        String cityAddress = accomodationList.get(position).getCityAddress();

        //Postavljanje podataka na UI elemente
        holder.ivThumbnail.setImageBitmap(resizedImage);
        holder.tvName.setText(name);
        holder.tvAddress.setText(streetAddress + "\n" + cityAddress);

        return convertView;
    }

}