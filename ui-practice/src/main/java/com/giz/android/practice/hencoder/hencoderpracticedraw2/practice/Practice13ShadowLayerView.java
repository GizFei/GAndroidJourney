package com.giz.android.practice.hencoder.hencoderpracticedraw2.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice13ShadowLayerView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice13ShadowLayerView(Context context) {
        super(context);
    }

    public Practice13ShadowLayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice13ShadowLayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // 使用 Paint.setShadowLayer() 设置阴影
        paint.setShadowLayer(
                40f,    // 模糊半径
                0f,         // x方向偏移
                0f,        // y方向偏移
                0xFF888888     // 颜色
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setTextSize(120);
        canvas.drawText("Hello HenCoder", 50, 200, paint);

        canvas.translate(50f, 300f);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0f, 0f, 200f, 100f, paint);
    }
}
