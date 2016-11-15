package com.masnegocio.semilla;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


public class ActivityWithLoading extends AppCompatActivity {

    LoadingDialog   loadingDialog   = null;
    //SkipDialog      skipDialog      = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openLoadingView(){
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this, android.R.style.Theme_Translucent_NoTitleBar);
            loadingDialog.show();
        }
    }
    public void closeLoadingView(){
        loadingDialog.stopAnimate();
        loadingDialog.dismiss();
        loadingDialog   = null;
    }

    class LoadingView extends View {

        static  final   float   CANVAS_INDICATOR_BASE_RADIUS_FACTOR             = 3.5f;
        static  final   float   CANVAS_INDICATOR_NEEDLE_RADIUS_FACTOR           = 5.0f;
        static  final   float   BASE_INDICATOR_CENTER_RADIUS_FACTOR             = 4.5f;
        static  final   float   BASE_INDICATOR_NEEDLE_CENTER_RADIUS_FACTOR      = 9.f;
        static  final   int     MILISECONDS_UPDATE_INTERVAL                     =  50;
        static  final   double  NEEDLE_ADVANCE_IN_RADIANS                       = 0.020;
        static  final String LOADING_TEXT                                    = "CARGANDO";

        private Thread animationThread;
        private boolean     animate = true;
        private Handler handler = new Handler(Looper.getMainLooper());
        Context context;
        int     stage           = 0;
        float   needle_angle    = (float) Math.PI;
        Canvas canvas_view;

        public LoadingView(Context context) {
            super(context);
            this.context    = context;
            setBackgroundColor(Color.argb(200, 200, 100, 0));
            startAnimate();
        }

        public void stopAnimate(){
            animate = false;
        }

        public void startAnimate()
        {
            animationThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (animate) {
                        try {
                            Thread.sleep(MILISECONDS_UPDATE_INTERVAL);
                            handler.post(new Runnable() {
                                @Override
                                public void run() { invalidate();}
                            });
                        } catch (Exception e) { }
                    }
                }
            });
            animationThread.start();
        }

        public LoadingView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas_view = canvas;
            //paintOuterCircleInCanvas();
            //paintInnerCircleInCanvas();

            //drawLines
            for (int i = 1 ; i<=40; ++i) {
                Paint paint = getPaintForRadialLine(getColorForStage(stage+i,(int)(255*(0.02 *i))));
                Point p_origin = pointOnInnerCircleWithLength(stage + i);
                Point p_destin = pointOnOuterCircleWithLength(stage + i);
                canvas_view.drawLine(p_origin.x, p_origin.y, p_destin.x, p_destin.y, paint);
            }
            stage++;

            //paintNeedleCircleInCanvas();
            //drawNeedle(NEEDLE_ADVANCE_IN_RADIANS);
            addLoadingText();
        }

        public void paintOuterCircleInCanvas(){
            paintCircleInCanvas(Color.argb(100, 100, 100, 100),
                                canvas_view.getWidth()/2,
                                canvas_view.getHeight()/2,
                                canvas_view.getWidth()/CANVAS_INDICATOR_BASE_RADIUS_FACTOR,
                                false);
        }
        public void paintInnerCircleInCanvas(){
            paintCircleInCanvas(Color.argb(120, 90, 90, 90),
                                canvas_view.getWidth()/2,
                                canvas_view.getHeight()/2,
                                canvas_view.getWidth()/(BASE_INDICATOR_CENTER_RADIUS_FACTOR*CANVAS_INDICATOR_BASE_RADIUS_FACTOR),
                                false);
        }
        public void paintNeedleCircleInCanvas(){
            paintCircleInCanvas(Color.argb(255, 194, 0, 47),
                                canvas_view.getWidth()/2,
                                canvas_view.getHeight()/2,
                                canvas_view.getWidth()/(BASE_INDICATOR_NEEDLE_CENTER_RADIUS_FACTOR*BASE_INDICATOR_CENTER_RADIUS_FACTOR),
                                true);
        }
        public void paintCircleInCanvas(int color, float x, float y, float radius, boolean addShadow){
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStrokeJoin(Paint.Join.ROUND);
            if (addShadow)
                paint.setShadowLayer(0, 4, 4, Color.BLACK);

            canvas_view.drawCircle(x, y, radius, paint);
        }
        public void drawNeedle( double delta_angle){
            if (needle_angle <= 1.25*(Math.PI))
                needle_angle += (delta_angle*2.0);
            else if (needle_angle > 1.15* Math.PI && needle_angle <= 1.75*(Math.PI))
                needle_angle += (delta_angle*3.5);
            else if (needle_angle > 1.75* Math.PI && needle_angle <= 2*(Math.PI))
                needle_angle += (delta_angle*2.0);
            else if (needle_angle > (2*(Math.PI) + .1) && needle_angle <= (2*(Math.PI) + .25))
                needle_angle -= (delta_angle);
            else
                needle_angle += (delta_angle);

            float r    = canvas_view.getWidth()/CANVAS_INDICATOR_NEEDLE_RADIUS_FACTOR;
            float cx   = canvas_view.getWidth()/2.f;
            float cy   = canvas_view.getHeight()/2.f;
            float x    = (float)(cx + r* Math.cos(needle_angle));
            float y    = (float)(cy + r* Math.sin(needle_angle));

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setColor(Color.argb(255, 194, 0, 47));
            paint.setStrokeWidth(4f);

            canvas_view.drawLine(cx, cy, x, y, paint);
        }

        public int getColorForStage(int currentStage, int alpha){
            return Color.argb(alpha,251,251,251);
        }

        public Point pointOnInnerCircleWithLength(int length){
            double r    = canvas_view.getWidth()/(CANVAS_INDICATOR_BASE_RADIUS_FACTOR+.75);
            double cx   = canvas_view.getWidth()/2;
            double cy   = canvas_view.getHeight()/2;
            double x    = cx + r* Math.cos(Math.PI / 22 * length);
            double y    = cy + r* Math.sin(Math.PI / 22 * length);
            return new Point((int)x,(int)y);
        }

        public Point pointOnOuterCircleWithLength(int length){
            double r    = canvas_view.getWidth()/(CANVAS_INDICATOR_BASE_RADIUS_FACTOR + .2);
            double cx   = canvas_view.getWidth()/2;
            double cy   = canvas_view.getHeight()/2;
            double x    = cx + r* Math.cos(Math.PI / 22 * length);
            double y    = cy + r* Math.sin(Math.PI / 22 * length);
            return new Point((int)x,(int)y);
        }

        public Paint getPaintForRadialLine(int paint_color){
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setColor(paint_color);
            paint.setStrokeWidth(getPixelInDP(2,getContext()));
            return paint;
        }

        public void addLoadingText(){
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setTextSize(20);
            Rect bounds = new Rect();
            paint.getTextBounds(LOADING_TEXT,0,LOADING_TEXT.length(),bounds);
            int width = bounds.width();
            canvas_view.drawText(LOADING_TEXT,
                                canvas_view.getWidth()/2 - (int)(width/2),
                                canvas_view.getHeight()/2 + (canvas_view.getWidth()/CANVAS_INDICATOR_BASE_RADIUS_FACTOR) + (canvas_view.getWidth()*.05f),
                                paint);
        }
    }

    public class LoadingDialog extends Dialog {
        LoadingView loadingView;
        public LoadingDialog(Context context, int themeResId) {
            super(context, themeResId);
            loadingView = new LoadingView(context);
            this.setContentView(loadingView);
            this.setCancelable(false);
        }
        public void stopAnimate(){
            loadingView.stopAnimate();
        }
    }

    public static int getPixelInDP(int pixel, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, context.getResources().getDisplayMetrics());
    }


    /*public void openSkipView(TourGuide currentTourGuide){
        if (skipDialog == null) {
            skipDialog = new SkipDialog(this, android.R.style.Theme_Translucent_NoTitleBar,currentTourGuide );
            skipDialog.show();
        }
    }
    public void closeSkipView(){
        skipDialog.dismiss();
        skipDialog   = null;
    }


    public class SkipDialog extends Dialog
    {
        TourGuide currentTourGuide;
        Button btn_skip;
        View skipView;
        LayoutInflater layoutInflater;
        public SkipDialog(Context context, int themeResId, final TourGuide currentTourGuide) {
            super(context, themeResId);
            this.currentTourGuide = currentTourGuide;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            skipView = layoutInflater.inflate(R.layout.show_case_view,null);
            this.setContentView(skipView);
            this.setCancelable(false);

            btn_skip = (Button) skipView.findViewById(R.id.btn_skip);
            btn_skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentTourGuide.cleanUp();
                    closeSkipView();
                }
            });
        }
    }*/

}