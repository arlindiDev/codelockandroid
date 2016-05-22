package arlind.sandbox.codelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;

/**
 * Created by Arlind on 3/11/16.a
 */
public class PinCodeView extends EditText {
    private int count = 0;
    private int length_of_code = 4;
    private int outer_circle_color = Color.parseColor("#3F51B5");
    private int inner_circle_color = Color.parseColor("#3F51B5");
    private int outer_circle_width = 3;
    private float inner_padding = 40;
    private float radius = 80;
    private Paint paintStroke;
    private Paint paintFill;
    private OnCodeCompleteListener onCodeCompleteListener;

    private Handler mHandler;
    private Runnable mRunnable;
    private Interpolator interpolator = new CycleInterpolator(2);
    private int wrong_animation_duration = 600;

    public PinCodeView(Context context) {
        super(context);
        init(context, null);
    }

    public PinCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PinCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public interface OnCodeCompleteListener {
        void onCodeComplete(PinCodeView pinCodeView1, String code);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void init(Context context, AttributeSet attrs) {

        paintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStroke.setStrokeWidth(outer_circle_width);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setColor(outer_circle_color);
        setBackgroundColor(Color.TRANSPARENT);

        paintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFill.setColor(inner_circle_color);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > length_of_code) {
                    return;
                }
                setCount(s.length());
                if (getCount() == length_of_code && onCodeCompleteListener != null) {
                    onCodeCompleteListener.onCodeComplete(PinCodeView.this, getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = getWidth();
        float total_circle_width = radius + inner_padding;

        float view_widths = total_circle_width * length_of_code;

        float offset_left = ((width - view_widths + radius / 2)) / 2;
        float xpos = offset_left + radius / 2;

        if (wrong_animation) {
            drawCircles(canvas, xpos + deltaX);
        } else {
            drawCircles(canvas, xpos);
        }

    }

    private void drawCircles(Canvas canvas, float x) {
        for (int i = 0; i < length_of_code; i++) {
            if (i < count) {
                canvas.drawCircle(x, getHeight() / 2, radius / 2, paintFill);
            }
            canvas.drawCircle(x, getHeight() / 2, radius / 2, paintStroke);
            x += radius + inner_padding;
        }
    }

    float deltaX = 0;
    boolean wrong_animation = false;

    public void wrongCode() {
        System.out.println("WRONG CODE");


        mHandler = new Handler();
        final int fps = 40;
        mRunnable = new Runnable() {
            //boolean in handler or dont allow pinch to zoom when doubletap zoom in progress
            int time = 0;
            long startTime = System.currentTimeMillis();
            float startX = 0;
            float endX = 100;
            float offsetX = 20;

            @Override
            public void run() {
                time += fps;
                if (wrong_animation_duration >= time) {

                    mHandler.postDelayed(mRunnable, fps);
                    float t = interpolate(startTime, wrong_animation_duration);
                    deltaX = calculateDelta(t, startX, endX);

                    wrong_animation = true;
                    postInvalidate();

                } else {
                    //ANIMATION FINISHED
                    wrong_animation = false;
                }
            }
        };

        mHandler.postDelayed(mRunnable, fps);
    }


    private float calculateDelta(float t, float start, float target) {
        return start + t * (target - start);
    }

    private float interpolate(long startTime, float ZOOM_TIME) {
        long currTime = System.currentTimeMillis();

        float elapsed = (currTime - startTime) / ZOOM_TIME;

        elapsed = Math.min(1f, elapsed);

        return interpolator.getInterpolation(elapsed);
    }

    public int getLength_of_code() {
        return length_of_code;
    }

    public void setLength_of_code(int length_of_code) {
        this.length_of_code = length_of_code;
    }

    public int getOuter_circle_color() {
        return outer_circle_color;
    }

    public void setOuter_circle_color(int outer_circle_color) {
        this.outer_circle_color = outer_circle_color;
    }

    public int getInner_circle_color() {
        return inner_circle_color;
    }

    public void setInner_circle_color(int inner_circle_color) {
        this.inner_circle_color = inner_circle_color;
    }

    public int getOuter_circle_width() {
        return outer_circle_width;
    }

    public void setOuter_circle_width(int outer_circle_width) {
        this.outer_circle_width = outer_circle_width;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public OnCodeCompleteListener getOnCodeCompleteListener() {
        return onCodeCompleteListener;
    }

    public void setOnCodeCompleteListener(OnCodeCompleteListener onCodeCompleteListener) {
        this.onCodeCompleteListener = onCodeCompleteListener;
    }
}
