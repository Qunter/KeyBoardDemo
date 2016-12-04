package com.ysdemo.keyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ysdemo.keyboard.view.KeyboardUtil;
import com.ysdemo.keyboard.view.KeyboardUtil.KeyboardListener;
import com.ysdemo.keyboard.view.PasswordTextView;

public class MainActivity extends Activity {
	private RelativeLayout rl_keyboard;
	private KeyboardUtil keyboardUtil;
	private int change_type;
	private Button btn_showKey,btn_hideKey;
	private PasswordTextView et_pwd1,et_pwd2,et_pwd3,et_pwd4;
	private String[] password = new String[4];
	private String[] passwordhc = new String[4];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initTestBtn();
		initPwdTv();
		initKeyboard();
		initListener();
	}
	
	private void initTestBtn() {
		btn_showKey = (Button) findViewById(R.id.btn_showKey);
		btn_hideKey = (Button) findViewById(R.id.btn_hideKey);
		btn_showKey.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showKeyBoard();
			}
		});
		btn_hideKey.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideKeyBoard();
			}
		});
	}
	private void initPwdTv(){
		et_pwd1 = (PasswordTextView) findViewById(R.id.et_pwd1);
		et_pwd2 = (PasswordTextView) findViewById(R.id.et_pwd2);
		et_pwd3 = (PasswordTextView) findViewById(R.id.et_pwd3);
		et_pwd4 = (PasswordTextView) findViewById(R.id.et_pwd4);
	}


	private void initKeyboard() {
		keyboardUtil = new KeyboardUtil(this,et_pwd1,et_pwd2,et_pwd3,et_pwd4);
		rl_keyboard = (RelativeLayout) findViewById(R.id.rl__keyboard);
		keyboardUtil.setKeyboardListener(new KeyboardListener() {

			@Override
			public void onOK() {
				hideKeyBoard();
				change_type = -1;
			}
		});
	}
	/**
	 * 显示键盘
	 */
	protected void showKeyBoard() {
		rl_keyboard.setVisibility(View.VISIBLE);
		keyboardUtil.setType(change_type);
		keyboardUtil.showKeyboard();
	}

	/**
	 * 显示键盘
	 */
	protected void hideKeyBoard() {
		rl_keyboard.setVisibility(View.GONE);
		keyboardUtil.hideKeyboard();
		keyboardUtil.setType(-1);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (rl_keyboard.getVisibility() != View.GONE) {
				hideKeyBoard();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	/*
	监听第四个tv的更改事件，如果第四个更改，则保存密码
	 */
	private void initListener() {
		//监听最后一个密码框的文本改变事件回调
		et_pwd4.setOnTextChangedListener(new PasswordTextView.OnTextChangedListener() {
			@Override
			public void textChanged(String content) {
				if(password[0]==null) {
					password[0] = et_pwd1.getTextContent();
					password[1] = et_pwd2.getTextContent();
					password[2] = et_pwd3.getTextContent();
					password[3] = et_pwd4.getTextContent();
					clearText();
				}else{
					passwordhc[0] = et_pwd1.getTextContent();
					passwordhc[1] = et_pwd2.getTextContent();
					passwordhc[2] = et_pwd3.getTextContent();
					passwordhc[3] = et_pwd4.getTextContent();
					if (password[0].equals(passwordhc[0]) && password[1].equals(passwordhc[1]) && password[2].equals(passwordhc[2]) && password[3].equals(passwordhc[3])) {
						Toast.makeText(getApplicationContext(),"密码设置成功",Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getApplicationContext(),"两次输入不一致  请重新输入",Toast.LENGTH_SHORT).show();
						clearText();
						clearPsw();
						clearPswhc();
					}
				}
				startTimer();
			}
		});
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			clearText();
		}
	};

	//这里是为了让第四个显示的输入框先显示,然后再整个清除
	private void startTimer() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}).start();
	}

	/**
	 * 清除输入的内容--重输
	 */
	private void clearText() {
		et_pwd1.setTextContent("");
		et_pwd2.setTextContent("");
		et_pwd3.setTextContent("");
		et_pwd4.setTextContent("");
	}
	/**
	 * 清除密码缓存内的内容--重输
	 */
	private void clearPswhc(){
		for(int i=0;i<passwordhc.length;i++){
			passwordhc[i]=null;
		}
	}

	/**
	 * 清除密码内的内容--重输
	 */
	private void clearPsw(){
		for(int i=0;i<password.length;i++){
			password[i]=null;
		}
	}

}
