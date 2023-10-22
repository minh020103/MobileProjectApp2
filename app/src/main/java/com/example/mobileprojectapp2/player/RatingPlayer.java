package com.example.mobileprojectapp2.player;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.mobileprojectapp2.R;

public class RatingPlayer {

    private ImageView imgStar1;
    private ImageView imgStar2;
    private ImageView imgStar3;
    private ImageView imgStar4;
    private ImageView imgStar5;

    public IRatingPlayer iRatingPlayer;

    public void setiRatingPlayer(IRatingPlayer iRatingPlayer) {
        this.iRatingPlayer = iRatingPlayer;
    }

    public RatingPlayer(View view) {
        this.imgStar1 = view.findViewById(R.id.imgStar1);
        this.imgStar2 = view.findViewById(R.id.imgStar2);
        this.imgStar3 = view.findViewById(R.id.imgStar3);
        this.imgStar4 = view.findViewById(R.id.imgStar4);
        this.imgStar5 = view.findViewById(R.id.imgStar5);
    }

    public void setStarForRating(int starNumber){
        switch (starNumber){
            case 1:
                imgStar1.setImageResource(R.drawable.icon_star_selected);
                imgStar2.setImageResource(R.drawable.icon_star);
                imgStar3.setImageResource(R.drawable.icon_star);
                imgStar4.setImageResource(R.drawable.icon_star);
                imgStar5.setImageResource(R.drawable.icon_star);
                break;
            case 2:
                imgStar1.setImageResource(R.drawable.icon_star_selected);
                imgStar2.setImageResource(R.drawable.icon_star_selected);
                imgStar3.setImageResource(R.drawable.icon_star);
                imgStar4.setImageResource(R.drawable.icon_star);
                imgStar5.setImageResource(R.drawable.icon_star);
                break;
            case 3:
                imgStar1.setImageResource(R.drawable.icon_star_selected);
                imgStar2.setImageResource(R.drawable.icon_star_selected);
                imgStar3.setImageResource(R.drawable.icon_star_selected);
                imgStar4.setImageResource(R.drawable.icon_star);
                imgStar5.setImageResource(R.drawable.icon_star);
                break;
            case 4:
                imgStar1.setImageResource(R.drawable.icon_star_selected);
                imgStar2.setImageResource(R.drawable.icon_star_selected);
                imgStar3.setImageResource(R.drawable.icon_star_selected);
                imgStar4.setImageResource(R.drawable.icon_star_selected);
                imgStar5.setImageResource(R.drawable.icon_star);
                break;
            case 5:
                imgStar1.setImageResource(R.drawable.icon_star_selected);
                imgStar2.setImageResource(R.drawable.icon_star_selected);
                imgStar3.setImageResource(R.drawable.icon_star_selected);
                imgStar4.setImageResource(R.drawable.icon_star_selected);
                imgStar5.setImageResource(R.drawable.icon_star_selected);
                break;
            default:
                imgStar1.setImageResource(R.drawable.icon_star);
                imgStar2.setImageResource(R.drawable.icon_star);
                imgStar3.setImageResource(R.drawable.icon_star);
                imgStar4.setImageResource(R.drawable.icon_star);
                imgStar5.setImageResource(R.drawable.icon_star);
                break;
        }
    }





    public void rating(){
        imgStar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgStar1.setImageResource(R.drawable.icon_star_selected);
                imgStar2.setImageResource(R.drawable.icon_star);
                imgStar3.setImageResource(R.drawable.icon_star);
                imgStar4.setImageResource(R.drawable.icon_star);
                imgStar5.setImageResource(R.drawable.icon_star);
                iRatingPlayer.layDanhGia(1);
            }
        });
        imgStar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgStar1.setImageResource(R.drawable.icon_star_selected);
                imgStar2.setImageResource(R.drawable.icon_star_selected);
                imgStar3.setImageResource(R.drawable.icon_star);
                imgStar4.setImageResource(R.drawable.icon_star);
                imgStar5.setImageResource(R.drawable.icon_star);
                iRatingPlayer.layDanhGia(2);
            }
        });
        imgStar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgStar1.setImageResource(R.drawable.icon_star_selected);
                imgStar2.setImageResource(R.drawable.icon_star_selected);
                imgStar3.setImageResource(R.drawable.icon_star_selected);
                imgStar4.setImageResource(R.drawable.icon_star);
                imgStar5.setImageResource(R.drawable.icon_star);
                iRatingPlayer.layDanhGia(3);
            }
        });
        imgStar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgStar1.setImageResource(R.drawable.icon_star_selected);
                imgStar2.setImageResource(R.drawable.icon_star_selected);
                imgStar3.setImageResource(R.drawable.icon_star_selected);
                imgStar4.setImageResource(R.drawable.icon_star_selected);
                imgStar5.setImageResource(R.drawable.icon_star);
                iRatingPlayer.layDanhGia(4);
            }
        });
        imgStar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgStar1.setImageResource(R.drawable.icon_star_selected);
                imgStar2.setImageResource(R.drawable.icon_star_selected);
                imgStar3.setImageResource(R.drawable.icon_star_selected);
                imgStar4.setImageResource(R.drawable.icon_star_selected);
                imgStar5.setImageResource(R.drawable.icon_star_selected);
                iRatingPlayer.layDanhGia(5);
            }
        });
    }

    public interface IRatingPlayer{
        void layDanhGia(int rating);
    }
}
