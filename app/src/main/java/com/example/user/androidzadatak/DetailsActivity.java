package com.example.user.androidzadatak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends Activity {

    //Deklaracija i inicijalizacija varijabli
    private Accomodation accomodation = null;
    private ImageView mainImageView = null;
    private RatingBar ratingBar = null;
    private TextView tvDetailsName = null;
    private TextView tvDetailsAddress = null;
    private TextView tvDetailsDescription = null;
    private GridView gvImages = null;
    private ImageView ivExpandedImage = null;
    private ImageAdapter imageAdapter = null;
    private Animator mCurrentAnimator = null;
    private int mShortAnimationDuration = 0;
    private int j = 0;
    private GestureDetector detector = null;
    private Boolean isMultipleSelected = false;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Pohrana pristupa UI elementima
        mainImageView = (ImageView) findViewById(R.id.imageDetailsMain);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvDetailsName = (TextView) findViewById(R.id.tvDetailsName);
        tvDetailsAddress = (TextView) findViewById(R.id.tvDetailsAddress);
        tvDetailsDescription = (TextView) findViewById(R.id.tvDetailsDescription);
        gvImages = (GridView) findViewById(R.id.gridView);

        //Preuzimanje proslijeđenih podataka iz prijašnje aktivnosti
        Bundle bundle = this.getIntent().getExtras();

        //Ako ima podataka, kreira se novi objekt tipa Accomodation i podaci se zapišu u objekt
        if (bundle != null) {

            accomodation = new Accomodation();
            accomodation.setId(Integer.parseInt(bundle.get("id").toString()));

            ArrayList images = new ArrayList();
            images.add(0, bundle.get("img1").toString());
            images.add(1, bundle.get("img2").toString());
            images.add(2, bundle.get("img3").toString());
            images.add(3, bundle.get("img4").toString());

            accomodation.setImage(images);
            accomodation.setName(bundle.get("name").toString());
            accomodation.setStreetAddress(bundle.get("streetAddress").toString());
            accomodation.setCityAddress(bundle.get("cityAddress").toString());
            accomodation.setRating(Integer.parseInt(bundle.get("rating").toString()));
            accomodation.setDescription(bundle.get("description").toString());
        }

        //Dobivanje URI stringa za glavnu sliku
        String image = accomodation.getImage().get(0);
        String uri = "com.example.user.androidzadatak:drawable/" + image;
        //Konvertiranje URI u id slike
        int idImageDrawable = this.getResources().getIdentifier(uri, null, null);
        //Konvertiranje slike u bitmap i promjena veličine za očuvanje memorije
        Bitmap bmImage = BitmapFactory.decodeResource(this.getResources(), idImageDrawable);
        Bitmap resizedImage = Bitmap.createScaledBitmap(bmImage, (int) (bmImage.getWidth() * 0.5), (int) (bmImage.getHeight() * 0.5), true);

        //Postavljanje podataka na UI elemente
        mainImageView.setImageBitmap(resizedImage);
        setTitle(accomodation.getName());
        ratingBar.setRating(accomodation.getRating());
        tvDetailsName.setText(accomodation.getName());
        tvDetailsAddress.setText(accomodation.getStreetAddress() + "\n" + accomodation.getCityAddress());
        tvDetailsDescription.setText(accomodation.getDescription());

        //ID brojevi ostalih slika se pohrane u listu thumbImages
        final ArrayList<Integer> thumbImages = new ArrayList<>();
        for(int i=1; i<4; i++)
        {
            String uriImages = "com.example.user.androidzadatak:drawable/" + accomodation.getImage().get(i);
            int idImage = this.getResources().getIdentifier(uriImages, null, null);
            thumbImages.add(idImage);
        }

        //Kreira se novi detektor za listanje slika
        detector = new GestureDetector(this, new SwipeGestureDetector(thumbImages));
        //Kreiranje novog adaptera sa listom thumbImages i povezivanje adaptera sa UI gridViewom
        imageAdapter = new ImageAdapter(this, thumbImages);
        gvImages.setAdapter(imageAdapter);
        //Kreiranje listenera koji osluškuje klik događaj, ako nema selektiranih thumbnaila, pozove se metoda za povećavanje slike
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                j = pos;

                if(isMultipleSelected==false) {
                    isMultipleSelected=true;
                    //Metoda za povećavanje selektirane slike
                    zoomImageFromThumb(v, thumbImages.get(pos));
                }
            }
        });

        //Postavljanje trajanja animacije povećavanja
        mShortAnimationDuration = DetailsActivity.this.getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    /**
     * Metoda poveća označenu sliku uz animaciju
     * @param thumbView UI View slike
     * @param imageResId id slike
     */
    private void zoomImageFromThumb(final View thumbView, int imageResId) {

        if(mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        ivExpandedImage = (ImageView) findViewById(R.id.ivExpandedImage);

        ivExpandedImage.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (detector.onTouchEvent(event)) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        ivExpandedImage.setImageResource(imageResId);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;

        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds.width() / startBounds.height())
        {
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;

        } else {

            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        ivExpandedImage.setVisibility(View.VISIBLE);

        ivExpandedImage.setPivotX(0f);
        ivExpandedImage.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();

        set.play(ObjectAnimator.ofFloat(ivExpandedImage, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(ivExpandedImage, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(ivExpandedImage, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(ivExpandedImage, View.SCALE_Y, startScale, 1f));

        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        final float startScaleFinal = startScale;

        ivExpandedImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();

                set.play(ObjectAnimator.ofFloat(ivExpandedImage, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(ivExpandedImage, View.Y, startBounds.top))
                        .with(ObjectAnimator.ofFloat(ivExpandedImage, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator.ofFloat(ivExpandedImage, View.SCALE_Y, startScaleFinal));

                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        ivExpandedImage.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        ivExpandedImage.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
                isMultipleSelected=false;
            }
        });
    }

    /**
     * Klasa koja omogućuje listanje povećanih slika koje se nalaze u gridViewu
     */
    private class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener{

        private ArrayList<Integer> thumbImages;

        public SwipeGestureDetector(ArrayList<Integer> array) {
            thumbImages = array;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            try {

                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){

                    if(thumbImages.size() > j) {
                        j++;

                        if(j < thumbImages.size()) {
                            ivExpandedImage.setImageResource(thumbImages.get(j));
                            return true;
                        } else {
                            j = 0;
                            ivExpandedImage.setImageResource(thumbImages.get(j));
                            return true;
                        }
                    }
                } else if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    if(j > 0) {
                        j--;
                        ivExpandedImage.setImageResource(thumbImages.get(j));
                        return true;
                    } else {
                        j = thumbImages.size() - 1;
                        ivExpandedImage.setImageResource(thumbImages.get(j));
                        return true;
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}





