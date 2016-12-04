package com.ysdemo.keyboard.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import com.ysdemo.keyboard.R;

public class MyKeyboardView extends KeyboardView {
	
	private Context context;

	public MyKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
    public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        List<Key> keys = getKeyboard().getKeys();
    }
}