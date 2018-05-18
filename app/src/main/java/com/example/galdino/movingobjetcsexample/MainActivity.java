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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private ViewGroup mainLayout;
    private ImageView image;
    private TextView tvSide;
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
        tvSide = findViewById(R.id.tv_side);
        image.setOnTouchListener(onTouchListener());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        //
    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            public int halfScreen;
            public RelativeLayout.LayoutParams layoutParams;
            public boolean directionRight;
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
                        int centerSide;
                        if(directionRight)
                        {
                            centerSide = halfScreen + (halfScreen/2) - (layoutParams.width/2);
                        }
                        else
                        {
                            centerSide = halfScreen - (halfScreen/2)- (layoutParams.width/2);
                        }
                        layoutParams.leftMargin = centerSide;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        directionRight = true;
                        int movement = x - xDelta;
                        int rightPosition =  movement + layoutParams.width;

                        if(layoutParams.leftMargin >= movement)
                        {
                            directionRight = false;
                        }
                        // para não chegar nas bordas
                        if(!directionRight) {
                            rightPosition -= 30;
                        }
                        else
                        {
                            rightPosition += 30;
                        }

                        if(movement > 0 && rightPosition <= mScreenWidth)
                        {
                            halfScreen = mScreenWidth/2;

                            if(directionRight)
                            {
                                if(layoutParams.leftMargin > halfScreen)
                                {
                                    tvSide.setText(R.string.right);
                                }
                            }
                            else
                            {
                                if(rightPosition < halfScreen)
                                {
                                    tvSide.setText(R.string.left);
                                }
                            }

                            //layoutParams.topMargin = y - yDelta;
                            layoutParams.leftMargin = movement;
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
