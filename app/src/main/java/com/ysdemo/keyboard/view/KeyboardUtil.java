package com.ysdemo.keyboard.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ysdemo.keyboard.R;

/**
 * @Author: ys尘笑
 * @date 2015-12-6 下午5:21:36
 * @Description: 键盘功能控制工具类
 */
public class KeyboardUtil {
	private Context ctx;
	private MyKeyboardView keyboardView;
	private Keyboard k1;// 键盘
	private int type = -1;
	private KeyboardListener keyboardListener;

	private PasswordTextView et_pwd1,et_pwd2,et_pwd3,et_pwd4;


	public interface KeyboardListener {
		void onOK();
	}

	/**
	 * @param ctx	必须要用Activity实例作为上下文传入
	 */
	public KeyboardUtil(Context ctx,PasswordTextView pwd1,PasswordTextView pwd2,PasswordTextView pwd3,PasswordTextView pwd4) {
		this.ctx = ctx;
		//此处可替换键盘xml
		k1 = new Keyboard(ctx, R.xml.number);
		try {
			keyboardView = (MyKeyboardView) ((Activity)ctx).findViewById(R.id.keyboard_view);
			keyboardView.setContext(ctx);
			keyboardView.setKeyboard(k1);
			keyboardView.setEnabled(true);
			keyboardView.setPreviewEnabled(false);
			keyboardView.setOnKeyboardActionListener(listener);
			et_pwd1 = pwd1;
			et_pwd2 = pwd2;
			et_pwd3 = pwd3;
			et_pwd4 = pwd4;
		} catch (Exception e) {
			//Log.e("keyboardView", "keyboardView init failed!");
		}
	}

	public void setKeyboardListener(KeyboardListener keyboardListener) {
		this.keyboardListener = keyboardListener;
	}

	public void setType(int typein) {
		type = typein;
	}

	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
		@Override
		public void swipeUp() {
		}

		@Override
		public void swipeRight() {
		}

		@Override
		public void swipeLeft() {
		}

		@Override
		public void swipeDown() {
		}

		@Override
		public void onText(CharSequence text) {
		}

		@Override
		public void onRelease(int primaryCode) {
		}

		@Override
		public void onPress(int primaryCode) {
		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
				// hideKeyboard();
				keyboardListener.onOK();
			} else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
				deleteText();
			} else if (primaryCode == 1000){
				//1000为空键code
			}  else {
				setText(Character.toString((char) primaryCode));
			}
		}
	};

	public void showKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.GONE || visibility == View.INVISIBLE) {
			keyboardView.setVisibility(View.VISIBLE);
		}
	}

	public void hideKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.VISIBLE) {
			keyboardView.setVisibility(View.INVISIBLE);
		}
	}
	/**
	 * 设置显示的密码
	 *
	 * @param text
	 */
	private void setText(String text) {
		// 从左往右依次显示
		if (TextUtils.isEmpty(et_pwd1.getTextContent())) {
			et_pwd1.setTextContent(text);
		} else if (TextUtils.isEmpty(et_pwd2.getTextContent())) {
			et_pwd2.setTextContent(text);
		} else if (TextUtils.isEmpty(et_pwd3.getTextContent())) {
			et_pwd3.setTextContent(text);
		} else if (TextUtils.isEmpty(et_pwd4.getTextContent())) {
			et_pwd4.setTextContent(text);
		}
	}
	/**
	 * 删除刚刚输入的内容
	 */
	private void deleteText() {
		// 从右往左依次删除
		if (!TextUtils.isEmpty(et_pwd4.getTextContent())) {
			et_pwd4.setTextContent("");
		} else if (!TextUtils.isEmpty(et_pwd3.getTextContent())) {
			et_pwd3.setTextContent("");
		} else if (!TextUtils.isEmpty(et_pwd2.getTextContent())) {
			et_pwd2.setTextContent("");
		} else if (!TextUtils.isEmpty(et_pwd1.getTextContent())) {
			et_pwd1.setTextContent("");
		}
	}

}
