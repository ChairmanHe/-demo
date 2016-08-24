package com.example.demo.view;

import com.example.demo.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
/**
 * 从底部弹出的dialog
 * @author Administrator
 *
 */
public class BottomDialog extends Dialog{
	private View dialog;
	private Button takingPhoto,localPhoto,exitBtn;
	public BottomDialog(Context context) {
		this(context,R.style.couponDialogStyle);
	}

	public BottomDialog(Context context, int theme) {
		super(context, theme);
		dialog = LayoutInflater.from(context).inflate(R.layout.bottom_dialog_layout, null);
		takingPhoto = (Button) dialog.findViewById(R.id.taking_pictures);
		localPhoto = (Button) dialog.findViewById(R.id.taking_img);
		exitBtn = (Button) dialog.findViewById(R.id.exit_img);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(dialog);
		Window wind = getWindow();    
		WindowManager.LayoutParams l = wind.getAttributes(); 
		// lp.alpha = 0.97f;
		// lp.dimAmount = 0.7f ;
		l.gravity = Gravity.BOTTOM;
		l.width = WindowManager.LayoutParams.MATCH_PARENT;
		wind.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		wind.setWindowAnimations(android.R.style.Animation_InputMethod);
		setCancelable(true);
		setCanceledOnTouchOutside(false);
	}
	//分别给三个dialog设置监听
	public void setFirstButtonClick(android.view.View.OnClickListener click){
		takingPhoto.setOnClickListener(click);
	}
	
	public void setSecondButtonClick(android.view.View.OnClickListener click){
		localPhoto.setOnClickListener(click);
	}
	
	public void setThirdButtonClick(android.view.View.OnClickListener click){
		exitBtn.setOnClickListener(click);
	}
	public void setButtonText(String... txt){
		takingPhoto.setText(txt[0]);
		localPhoto.setText(txt[1]);
	}
	
	public void setButtonTextColor(String... txt){
		takingPhoto.setTextColor(Color.parseColor(txt[0]));
		localPhoto.setTextColor(Color.parseColor(txt[1]));
	}
}
