package com.example.user.androidzadatak;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    //Deklaracija varijabli
    private ArrayList<Integer> thumbImages;
    private LayoutInflater mInflater;

    //Postavljanje konteksta i liste slika iz aktivnosti DetailsActivity
    public ImageAdapter(Context context, ArrayList<Integer> results) {
        mInflater = LayoutInflater.from(context);
        thumbImages = results;
    }

    public int getCount() {
        return thumbImages.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    //ViewHolder sprema pristup UI elementima
    static class ViewHolder {
        ImageView ivThumbnail;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Deklaracija viewHoldera
        ViewHolder holder;

        //Ako se lista učitava prvi put, učita se layout i zapiše se pristup do elemenata u viewHolder
        //Na taj način nije potrebno pozivati findViewById za svako učitavanje
        if (convertView == null || convertView.getTag() == null) {
            convertView = mInflater.inflate(R.layout.grid_item, null);
            holder = new ViewHolder();

            holder.ivThumbnail = (ImageView) convertView.findViewById(R.id.thumbImage);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Pridobivanje id slike iz liste
        int id = thumbImages.get(position);
        //Konvertiranje slike u bitmap i promjena veličine za očuvanje memorije
        Bitmap bmImage = BitmapFactory.decodeResource(convertView.getResources(), id);
        Bitmap resizedImage = Bitmap.createScaledBitmap(bmImage, (int) (bmImage.getWidth() * 0.7), (int) (bmImage.getHeight() * 0.7), true);

        //Postavljanje slika u gridView iteme
        holder.ivThumbnail.setImageBitmap(resizedImage);

        return convertView;
    }
}