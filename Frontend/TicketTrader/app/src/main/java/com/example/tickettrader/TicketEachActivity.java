package com.example.tickettrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class TicketEachActivity extends AppCompatActivity {

    private static final String TAG = "TicketEachActivity";
    String sport ;
    String awayLogo ;
    String net_id;
    String date;
    int price;
    int userID;
    Button  btn_message;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_each);

       btn_message = findViewById(R.id.btnMessage);


        getIncomingIntent();

        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketEachActivity.this, Message_HTML.class);
                startActivity(intent);
            }
        });


    }

    private void getIncomingIntent(){


            sport = getIntent().getStringExtra("sport");
            awayLogo = getIntent().getStringExtra("logoURL");
            price = getIntent().getIntExtra("price",-1);
            userID = getIntent().getIntExtra("userID",-1);
            net_id = getIntent().getStringExtra("net_id");
            date = getIntent().getStringExtra("gameDate");


            loadPage(awayLogo, price, sport,date);

    }

    private void loadPage(String awayLogo, int price, String sport, String date)
    {
        ImageView away_logo = findViewById(R.id.awayLogo);
        ImageView isu_logo = findViewById(R.id.isuLogo);
        TextView tv_game_date = findViewById(R.id.game_date_ticket);
        TextView tv_sport = findViewById(R.id.sportTV);
        setPrice(price);

//        TextView tv_gameDate = findViewById(R.id.)



//        tv_price.setText(price);
        tv_sport.setText(sport);
        tv_game_date.setText(date);
        Glide.with(this).load(awayLogo).into(away_logo);
        Glide.with(this).load("https://i.imgur.com/Mhi5WN9.png").into(isu_logo);
    }



//    private void setPrice(String price){
//
//        TextView tv_price = findViewById(R.id.tv_sport);
//        tv_price.setText(price);
//    }

    private void setPrice(int price){


        TextView tv_price = findViewById(R.id.priceTv);
        tv_price.setText("$"+String.valueOf(price));
    }
}
