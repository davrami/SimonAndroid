package dam2.simon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mTxtPaint = new Paint();
        String str = "AYUDA";   //Dibujamos el canvas
        Paint.FontMetrics fm = new Paint.FontMetrics();
        mTxtPaint.setColor(Color.GRAY);
        mTxtPaint.setTextSize(123.0f);

        mTxtPaint.getFontMetrics(fm);

        int margin = 5;

        canvas.drawRect(100 - margin, 100 + fm.top - margin,
                100 + mTxtPaint.measureText(str) + margin, 100 + fm.bottom
                        + margin, mTxtPaint);

        mTxtPaint.setColor(Color.CYAN);

        canvas.drawText(str, 100, 100, mTxtPaint);

    }
}
