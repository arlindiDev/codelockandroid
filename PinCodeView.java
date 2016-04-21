package com.cgt.allridepassengerapp.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by imac on 3/11/16.a
 */
public class DigitEditText extends EditText
{
    private int count = 0;
    private int total_count = 5;
    private float inner_padding = 40;
    private float radius = 80;
    private Paint paintStroke;
    private Paint paintFill;

    public DigitEditText(Context context)
    {
        super(context);
        init(context, null);
    }

    public DigitEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public DigitEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //radius = getHeight() / 2;//) - (10f * getHeight() / 100f);
        System.out.println("ARLINDOS " + radius + " " + getHeight());
    }

    public void init(Context context, AttributeSet attrs)
    {
        int color = Color.parseColor("#afaeae");
        paintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStroke.setStrokeWidth(3);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setColor(color);

        paintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFill.setColor(color);

        //radius = getHeight() / 2; //) - (10f * getHeight() / 100f);
        System.out.println("ARLINDOS " + radius + " " + getHeight());

        addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() > total_count)
                {
                    return;
                }
                setCount(s.length());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    public int getCount()
    {
        return this.count;
    }

    public void setCount(int count)
    {
        this.count = count;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        float width = getWidth();
        float total_circle_width = radius + inner_padding;

        float view_widths = total_circle_width * total_count;

        float offset_left = ((width - view_widths + radius / 2)) / 2;
        float xpos = offset_left + radius / 2;

        for (int i = 0; i < total_count; i++)
        {
            if (i < count)
            {
                canvas.drawCircle(xpos, getHeight() / 2, radius / 2, paintFill);
            }
            canvas.drawCircle(xpos, getHeight() / 2, radius / 2, paintStroke);
            xpos += radius + inner_padding;
        }

    }

}
