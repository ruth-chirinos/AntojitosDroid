package com.delicoin.antojitos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.delicoin.antojitos.model.Data;
import com.delicoin.antojitos.tindercard.FlingCardListener;
import com.delicoin.antojitos.tindercard.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import delicoin2.com.delicoin.R;


public class MainActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> al;
    private SwipeFlingAdapterView flingContainer;
    private CardView cardView;

    public static void removeBackground()
    {
        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item);

        ScreenResolution screenRes = deviceDimensions();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardView = (CardView) findViewById(R.id.cardLayout);
        cardView.setLayoutParams(params);
        cardView.getLayoutParams().height=screenRes.height-100;

        setContentView(R.layout.activity_main);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        al = new ArrayList<>();
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/ensalada_de_mango.jpg", "Test111."));
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/granola_falluta.jpg", "Test112."));
        /*al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/ensalada_diferente.jpg", "Test113."));
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/ensalada_casero_jota.jpg", "Test114."));
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/ensalada_hei.jpg", "Test115."));
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/ensalada_italiana.jpg", "Test116."));
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/ensalada_casero_jota.jpg", "Test117."));
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/ensalada_de_mango.jpg", "Test118."));
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/granola_falluta.jpg", "Test119."));
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/ensalada_diferente.jpg", "Test11_10."));
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/ensalada_hei.jpg", "Test11_11."));
        al.add(new Data("https://delicoin.firebaseapp.com/pics/480/hei/ensalada_italiana.jpg", "Test11_12."));*/


        myAppAdapter = new MyAppAdapter(al, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //al.remove(0);
                //<!--Rcc--
                Data removed = al.remove(0);
                al.add(removed);
                //<!--Rcc-->
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {

                //al.remove(0);
                //<!--Rcc--
                Data removed = al.remove(0);
                al.add(removed);
                //<!--Rcc-->
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onBottomCardExit(Object dataObject) {
                //al.remove(0);
                //<!--Rcc--
                Data removed = al.remove(0);
                al.add(removed);
                //<!--Rcc-->
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                //view.findViewById(R.id.background).setAlpha(0); //rrcc
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                //view.findViewById(R.id.item_swipe_bottom_indicator).setAlpha(scrollProgressPercentVertical < 0 ? -scrollProgressPercentVertical : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                //view.findViewById(R.id.background).setAlpha(0); //rrcc

                myAppAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }


    public class MyAppAdapter extends BaseAdapter {


        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                //viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText); //RRCC
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.cardImageViewText);
                //viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background); //rrcc
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            Glide.with(MainActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;


    }

    private class ScreenResolution {
        int width, height;
        public ScreenResolution(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    ScreenResolution deviceDimensions() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // getsize() is available from API 13
        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return new ScreenResolution(size.x, size.y);
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            // getWidth() & getHeight() are depricated
            return new ScreenResolution(display.getWidth(), display.getHeight());
        }
    }
}
