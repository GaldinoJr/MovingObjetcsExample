package com.example.galdino.movingobjetcsexample;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private ViewGroup mainLayout;
    private ImageView image;

//    private int xDelta;
//    private int yDelta;
    private int mScreenWidth;
//    private int finalWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (RelativeLayout) findViewById(R.id.main);
        image = (ImageView) findViewById(R.id.image);

        image.setOnTouchListener(onTouchListener());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        //
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            public int yDelta;
            public int xDelta;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;

                        break;

                    case MotionEvent.ACTION_UP:
                        Toast.makeText(getApplicationContext(),
                                "thanks for new location!", Toast.LENGTH_SHORT)
                                .show();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        boolean directionRight = true;
                        int movement = x - xDelta;
                        int rightPosition =  layoutParams.leftMargin + layoutParams.width;

                        if(movement > 0 && rightPosition <= mScreenWidth)
                        {
                            int halfScreen = mScreenWidth/2;
                            if(layoutParams.leftMargin >= movement)
                            {
                                directionRight = false;
                            }
                            layoutParams.leftMargin = movement;

                            if(directionRight)
                            {
                                if(layoutParams.leftMargin > halfScreen)
                                {
                                    Toast.makeText(MainActivity.this,"passou para a direita", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                if(rightPosition < halfScreen)
                                {
                                    Toast.makeText(MainActivity.this,"passou para a esquerda", Toast.LENGTH_SHORT).show();
                                }
                            }

                            //layoutParams.topMargin = y - yDelta;
                            layoutParams.rightMargin = 0;
                            layoutParams.bottomMargin = 0;
                            view.setLayoutParams(layoutParams);
                        }
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }
}
