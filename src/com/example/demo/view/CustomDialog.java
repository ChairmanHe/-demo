package com.example.demo.view;

import com.example.demo.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 这是一个自定义的dialog
 * @author Administrator
 *
 */
public class CustomDialog extends Dialog{
	private View view, contentLayout;
	private Button leftButton,rightButton;
	private TextView titleTextView,percentTextView,contentTextView;
	private LinearLayout titleLayout,buttonLayout;
	private ProgressBar progressBar;
	private ListView list;
	private int dismissFlag = -1;
	
	public CustomDialog(Context context) {
		this(context,R.style.CustomDialogStyle, false);
	}
	
	public CustomDialog(Context context, boolean isProgressDialog) {
		this(context,R.style.CustomDialogStyle, isProgressDialog);
	}
	
	public CustomDialog(Context context, int theme, boolean isProgressDialog) {
		super(context, theme);
		if (isProgressDialog) {
			view = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog_layout, null);	
		} else {
			view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_layout, null);	
		}
		contentLayout = view.findViewById(R.id.content_layout);
		leftButton = (Button) view.findViewById(R.id.leftButton);
		rightButton = (Button) view.findViewById(R.id.rightButton); 
		percentTextView = (TextView) view.findViewById(R.id.percent); 
		contentTextView = (TextView) view.findViewById(R.id.content);
//		lineImageView = view.findViewById(R.id.lineImageView);
		buttonLayout = (LinearLayout) view.findViewById(R.id.buttonLayout);
		titleTextView = (TextView) view.findViewById(R.id.title);
		titleLayout = (LinearLayout) view.findViewById(R.id.titleLayout);
		progressBar = (ProgressBar) view.findViewById(R.id.progress);
//		tipTextView = (TextView) view.findViewById(R.id.tip);
		list = (ListView) view.findViewById(R.id.dialog_list);
	}
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(view);
	}
	
	public void setLeftButtonOnClick(android.view.View.OnClickListener onClickListener){
		leftButton.setOnClickListener(onClickListener);
	}
	
	public void setRightButtonOnClick(android.view.View.OnClickListener onClickListener){
		rightButton.setOnClickListener(onClickListener);
	}
	
	public void setTitle(String title){
		titleTextView.setText(title);
	}
	
//	public void hileLineBeloweTitle(){
//		lineOne.setVisibility(View.GONE);
//	}
	public void setTitleGravity(int gravity){
		titleTextView.setGravity(gravity);
	}
	
	public void setPercent(int percent){
		percentTextView.setText(percent + "%");
	}
	
	public void setContent(String content){
		contentTextView.setText(content);
	}
	
	public void setLeftButtonText(String text){
		leftButton.setText(text);
	}
	
	public void setRightButtonText(String text){
		rightButton.setText(text);
	}
	
	
	public void showTip(){
		hideLeftButton();
		hideRightButton();
		buttonLayout.setVisibility(View.VISIBLE);

//		if (TIPS_SWITCH) {
//			tipTextView.setVisibility(View.VISIBLE);
//			tipTextView.setText("���ڻ�ȡ���?);
//		} else {
//			lineImageView.setVisibility(View.GONE);
//		}
	}
	
	public void hideLeftButton(){
		leftButton.setVisibility(View.GONE);
	}
	
	public void hideRightButton(){
		rightButton.setVisibility(View.GONE);
	}
	
	public void hideButton(){
		buttonLayout.setVisibility(View.GONE);
	}
	
	public void hidePercent(){
		percentTextView.setVisibility(View.GONE);
	}
	
	public void hideTitle(){
		
		titleTextView.setVisibility(View.GONE);
		titleLayout.setVisibility(View.GONE);
	}
	
	public void hidleProgressBar(){
		progressBar.setVisibility(View.GONE);
	}
	
	public ListView getListView() {
		contentLayout.setVisibility(View.GONE);
		list.setVisibility(View.VISIBLE);
		return list;
	}
	
	
	public int getDismissFlag() {
		return dismissFlag;
	}
	
	public void setDismissFlag(int dismissFlag) {
		this.dismissFlag = dismissFlag;
	}
}
