package com.example.museumapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SecondFloor extends View {
    private Paint paint;
    private int width;
    private int height;

    public SecondFloor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setColor(Color.rgb(0,24,69));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth() - 20;
        height = getHeight() - 20;  // reserve some space
        float ratio = (float)56.9 / height;
        float main_wall_starting_X = 16/ratio,
                mail_wall_width = (float)26.9/ratio,
                main_wall_starting_Y = 20,
                mail_wall_height = (float)56.7/ratio,

                inner_starting_X = main_wall_starting_X + (float)6.4/ratio,
                inner_width = (float)14.1/ratio,
                inner_starting_Y = (float)17.95/ratio,
                inner_height = (float)28/ratio,

                widthOfStair = (float)4.3/ratio,

                stair1_starting_X = main_wall_starting_X + mail_wall_width-(float)4.8/ratio,
                stair1_starting_y = 20,
                stair1_height = (float)8/ratio,

                stair2_starting_X = 20,
                stair2_heigt = (float)6.2/ratio,
                stair2_width = (float)16/ratio,
                stair2_starting_Y = height-(float)9.3/ratio;


        // outer wall
        canvas.drawLine(main_wall_starting_X, main_wall_starting_Y, main_wall_starting_X, mail_wall_height, paint);
        canvas.drawLine(main_wall_starting_X, mail_wall_height, main_wall_starting_X + mail_wall_width, mail_wall_height, paint);
        canvas.drawLine(main_wall_starting_X, main_wall_starting_Y, main_wall_starting_X + mail_wall_width, main_wall_starting_Y, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width, main_wall_starting_Y, main_wall_starting_X + mail_wall_width, mail_wall_height, paint);

        // inner rail
        canvas.drawLine(inner_starting_X , inner_starting_Y, inner_starting_X, inner_starting_Y + inner_height, paint);
        canvas.drawLine(inner_starting_X , inner_starting_Y, inner_starting_X + inner_width, inner_starting_Y, paint);
        canvas.drawLine(inner_starting_X + inner_width, inner_starting_Y, inner_starting_X + inner_width, inner_starting_Y + inner_height, paint);
        canvas.drawLine(inner_starting_X , inner_starting_Y + inner_height, inner_starting_X + inner_width, inner_starting_Y + inner_height, paint);

        // stair 1
        paint.setStrokeWidth(2f);
        canvas.drawLine(stair1_starting_X, stair1_starting_y, stair1_starting_X, stair1_starting_y + stair1_height, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height, stair1_starting_y + widthOfStair, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height, stair1_starting_y, paint);

        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height + (float)0.48/ratio, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height + (float)0.48/ratio, stair1_starting_y, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height + 2*(float)0.48/ratio, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height + 2*(float)0.48/ratio, stair1_starting_y, paint);
        canvas.drawLine(main_wall_starting_X + mail_wall_width - stair1_height + 3*(float)0.48/ratio, stair1_starting_y + widthOfStair, main_wall_starting_X + mail_wall_width - stair1_height + 3*(float)0.48/ratio, stair1_starting_y, paint);

        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - (float)0.48/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - (float)0.48/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 2*(float)0.48/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 2*(float)0.48/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 3*(float)0.48/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 3*(float)0.48/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 4*(float)0.48/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 4*(float)0.48/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 5*(float)0.48/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 5*(float)0.48/ratio, paint);
        canvas.drawLine(stair1_starting_X, stair1_starting_y + stair1_height - 6*(float)0.48/ratio, main_wall_starting_X + mail_wall_width, stair1_starting_y + stair1_height - 6*(float)0.48/ratio, paint);


        // stair 2
        paint.setStrokeWidth(5f);
        canvas.drawLine(stair2_starting_X, stair2_starting_Y, stair2_width, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X, stair2_starting_Y, stair2_starting_X, stair2_starting_Y - stair2_heigt, paint);
        canvas.drawLine(stair2_starting_X, stair2_starting_Y - stair2_heigt, stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt, stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + (float)7.4/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + (float)7.4/ratio , stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + (float)7.4/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio - (float)2.1/ratio, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + (float)7.4/ratio , stair2_starting_Y - stair2_heigt + (float)1.1/ratio - (float)2.1/ratio, widthOfStair + (float)7.4/ratio + widthOfStair, stair2_starting_Y - stair2_heigt + (float)1.1/ratio - (float)2.1/ratio, paint);

        paint.setStrokeWidth(2f);
        canvas.drawLine(stair2_starting_X + widthOfStair, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + (float)0.48/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + (float)0.48/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 2*(float)0.48/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + 2*(float)0.48/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 3*(float)0.48/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + 3*(float)0.48/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 4*(float)0.48/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + 4*(float)0.48/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 5*(float)0.48/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + 5*(float)0.48/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 6*(float)0.48/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + 6*(float)0.48/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 7*(float)0.48/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + 7*(float)0.48/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 8*(float)0.48/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + 8*(float)0.48/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 9*(float)0.48/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + 9*(float)0.48/ratio, stair2_starting_Y, paint);
        canvas.drawLine(stair2_starting_X + widthOfStair + 10*(float)0.48/ratio, stair2_starting_Y - stair2_heigt + (float)1.1/ratio, stair2_starting_X + widthOfStair + 10*(float)0.48/ratio, stair2_starting_Y, paint);

    }
}
