package com.example.user.androidzadatak;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends Activity {

    //Deklaracija i inicijalizacija varijabli
    private ListView listView = null;
    private ListAdapter listAdapter = null;
    private LoadAccomodationsTask accomodationsTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pohrana pristupa do liste na UI
        listView = (ListView) findViewById(R.id.listViewAccomodation);

        //Pokretanje metode za punjenje liste u pozadini
        accomodationsTask = new LoadAccomodationsTask();
        accomodationsTask.execute();
    }

    /**
     * Klasa koja se izvršava pozadini, puni listu sa podacima i pokreće novu aktivnost
     */
    public class LoadAccomodationsTask extends AsyncTask<Void, Void, ArrayList<Accomodation>> {

        @Override
        protected ArrayList<Accomodation> doInBackground(Void... params) {

            ArrayList<Accomodation> list;

            //Punjenje privremene liste podacima iz metode loadData()
            list = loadData();

            return list;
        }

        @Override
        protected void onPostExecute(final ArrayList<Accomodation> list) {
            super.onPostExecute(list);

            //Kreiranje novog adaptera sa tom listom podataka i povezivanje adaptera sa UI listom
            listAdapter = new ListAdapter(MainActivity.this, list);
            listView.setAdapter(listAdapter);

            //Kreiranje listenera koji osluškuje klik događaj
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    //Pristup označenom objektu i pretvorba u tip Accomodation
                    Object o = listView.getItemAtPosition(position);
                    Accomodation fullObject = (Accomodation) o;

                    //Kreiranje novog intenta za pokretanje nove aktivnosti
                    Intent i = new Intent(MainActivity.this, DetailsActivity.class);

                    //Zapisivanje podataka iz označenog objekta u privremene varijable
                    int idAccomodation = fullObject.getId();
                    ArrayList<String> images = fullObject.getImage();
                    String name = fullObject.getName();
                    String streetAddress = fullObject.getStreetAddress();
                    String cityAddress = fullObject.getCityAddress();
                    int rating = fullObject.getRating();
                    String description = fullObject.getDescription();

                    //Postavljanje podataka u intent za prijenos na drugu aktivnost
                    i.putExtra("id", idAccomodation);
                    i.putExtra("img1", images.get(0));
                    i.putExtra("img2", images.get(1));
                    i.putExtra("img3", images.get(2));
                    i.putExtra("img4", images.get(3));
                    i.putExtra("name", name);
                    i.putExtra("streetAddress", streetAddress);
                    i.putExtra("cityAddress", cityAddress);
                    i.putExtra("rating", rating);
                    i.putExtra("description", description);

                    //Pokretanje nove aktivnosti i prosljeđivanje podataka
                    startActivity(i);
                }
            });

        }

        @Override
        protected void onCancelled() {
            accomodationsTask = null;
        }
    }

    /**
     * Metoda kreira 10 objekata tipa Accomodation i pohrani ih u listu
     * @return list
     */
    public ArrayList<Accomodation> loadData()
    {
        ArrayList<Accomodation> list;
        list = new ArrayList<>();


        Accomodation a1 = new Accomodation();
        a1.setId(1);

        ArrayList<String> images1 = new ArrayList<>();
        images1.add("hotel_astoria");
        images1.add("astoria1");
        images1.add("astoria2");
        images1.add("astoria3");

        a1.setImage(images1);
        a1.setName("Best Western Premier Hotel Astoria");
        a1.setStreetAddress("Petrinjska 71");
        a1.setCityAddress("10000 Zagreb");
        a1.setRating(4);
        a1.setDescription("Posluga u sobu, Restoran, Klimatizacijski uređaj, Minibar, Kabelska / Satelitska TV, Sušilo za kosu, TV, Privatna kupaonica, Privatna kupaonica, Privatni zahod, 'Lunch' paketi");



        Accomodation a2 = new Accomodation();
        a2.setId(2);

        ArrayList<String> images2 = new ArrayList<>();
        images2.add("hotel_esplanade");
        images2.add("esplanade1");
        images2.add("esplanade2");
        images2.add("esplanade3");

        a2.setImage(images2);
        a2.setName("Esplanade Zagreb Hotel");
        a2.setStreetAddress("Mihanovićeva 1");
        a2.setCityAddress("10000 Zagreb");
        a2.setRating(5);
        a2.setDescription("Posluga u sobu, Restoran, Klimatizacijski uređaj, Minibar, Kabelska / Satelitska TV, Sušilo za kosu, TV, Ogrtači, Tuš, Privatna kupaonica, Privatni zahod, 'Lunch' paketi");



        Accomodation a3 = new Accomodation();
        a3.setId(3);

        ArrayList<String> images3 = new ArrayList<>();
        images3.add("hotel_dubrovnik");
        images3.add("dubrovnik1");
        images3.add("dubrovnik2");
        images3.add("dubrovnik3");

        a3.setImage(images3);
        a3.setName("Dubrovnik Hotel Zagreb");
        a3.setStreetAddress("Gajeva 1");
        a3.setCityAddress("10000 Zagreb");
        a3.setRating(4);
        a3.setDescription("Posluga u sobu, Restoran, Dopušten pristup kućnim ljubimcima, Bar / Lounge, Klimatizacijski uređaj, Sobe za nepušače, Minibar, Hladnjak, Kabelska / Satelitska TV, Sušilo za kosu, TV, Tuš, Privatna kupaonica, Privatna kupaonica, 'Lunch' paketi");



        Accomodation a4 = new Accomodation();
        a4.setId(4);

        ArrayList<String> images4 = new ArrayList<>();
        images4.add("hotel_arcotel");
        images4.add("arcotel1");
        images4.add("arcotel2");
        images4.add("arcotel3");

        a4.setImage(images4);
        a4.setName("Arcotel Allegra Zagreb");
        a4.setStreetAddress("Branimirova 29");
        a4.setCityAddress("10000 Zagreb");
        a4.setRating(4);
        a4.setDescription("Posluga u sobu, Restoran, Klimatizacijski uređaj, Minibar, Kabelska / Satelitska TV, Sušilo za kosu, TV, CD čitač, DVD čitač, Tuš, Privatna kupaonica, Privatna kupaonica, Privatni zahod");



        Accomodation a5 = new Accomodation();
        a5.setId(5);

        ArrayList<String> images5 = new ArrayList<>();
        images5.add("hotel_sheraton");
        images5.add("sheraton1");
        images5.add("sheraton2");
        images5.add("sheraton3");

        a5.setImage(images5);
        a5.setName("Sheraton Zagreb Hotel");
        a5.setStreetAddress("Kneza Borne 2");
        a5.setCityAddress("10000 Zagreb");
        a5.setRating(5);
        a5.setDescription("Posluga u sobu, Restoran, Klimatizacijski uređaj, Minibar, Kabelska / Satelitska TV, Povezane sobe, Sušilo za kosu, TV, Ogrtači, Tuš, Privatna kupaonica, Privatna kupaonica, Privatni zahod, Kafić/Kafeterija");



        Accomodation a6 = new Accomodation();
        a6.setId(6);

        ArrayList<String> images6 = new ArrayList<>();
        images6.add("hotel_jagerhorn");
        images6.add("jagerhorn1");
        images6.add("jagerhorn2");
        images6.add("jagerhorn3");

        a6.setImage(images6);
        a6.setName("Hotel Jagerhorn");
        a6.setStreetAddress("Ilica 14, Gornji Grad");
        a6.setCityAddress("10000 Zagreb");
        a6.setRating(3);
        a6.setDescription("Restoran, Bar / Lounge, Klimatizacijski uređaj, Kabelska / Satelitska TV, Sušilo za kosu, TV, Privatna kupaonica, Privatna kupaonica, Kafić/Kafeterija");



        Accomodation a7 = new Accomodation();
        a7.setId(7);

        ArrayList<String> images7 = new ArrayList<>();
        images7.add("hotel_doubletree");
        images7.add("doubletree1");
        images7.add("doubletree2");
        images7.add("doubletree3");

        a7.setImage(images7);
        a7.setName("DoubleTree by Hilton Zagreb");
        a7.setStreetAddress("Ulica Grada Vukovara 269a");
        a7.setCityAddress("10000 Zagreb");
        a7.setRating(4);
        a7.setDescription("Posluga u sobu, Klimatizacijski uređaj, Minibar, Kabelska / Satelitska TV, Sušilo za kosu, TV, Tuš, Privatna kupaonica, Privatna kupaonica, Privatni zahod");



        Accomodation a8 = new Accomodation();
        a8.setId(8);

        ArrayList<String> images8 = new ArrayList<>();
        images8.add("hotel_jadran");
        images8.add("jadran1");
        images8.add("jadran2");
        images8.add("jadran3");

        a8.setImage(images8);
        a8.setName("Hotel Jadran Zagreb");
        a8.setStreetAddress("Vlaška 50, Gornji Grad");
        a8.setCityAddress("10000 Zagreb");
        a8.setRating(3);
        a8.setDescription("Restoran, Sušilo za kosu, TV, Tuš, Privatna kupaonica, Privatna kupaonica, Privatni zahod");



        Accomodation a9 = new Accomodation();
        a9.setId(9);

        ArrayList<String> images9 = new ArrayList<>();
        images9.add("hotel_westin");
        images9.add("westin1");
        images9.add("westin2");
        images9.add("westin3");

        a9.setImage(images9);
        a9.setName("The Westin Zagreb");
        a9.setStreetAddress("Krsnjavoga 1");
        a9.setCityAddress("10000 Zagreb");
        a9.setRating(5);
        a9.setDescription("Posluga u sobu, Klimatizacijski uređaj, Minibar, Kabelska / Satelitska TV, Sušilo za kosu, TV, Ogrtači, Tuš, Privatna kupaonica, Privatna kupaonica, Privatni zahod");



        Accomodation a10 = new Accomodation();
        a10.setId(10);

        ArrayList<String> images10 = new ArrayList<>();
        images10.add("hotel_stella");
        images10.add("stella1");
        images10.add("stella2");
        images10.add("stella3");

        a10.setImage(images10);
        a10.setName("Best Western Hotel Stella");
        a10.setStreetAddress("Nadinska 27");
        a10.setCityAddress("10000 Zagreb");
        a10.setRating(3);
        a10.setDescription("Restoran, Bar / Lounge, Klimatizacijski uređaj, Minibar, Kabelska / Satelitska TV, Aparat za kavu / čaj, Sušilo za kosu, TV, Tuš, Privatna kupaonica, Privatna kupaonica, Privatni zahod, Kafić/Kafeterija, 'Lunch' paketi");


        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(a7);
        list.add(a8);
        list.add(a9);
        list.add(a10);

        return list;
    }

}
