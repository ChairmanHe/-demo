package com.example.demo.utils;

import com.example.demo.view.CustomDialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

public class DialogUtil {
	
	public final static int ID_UNDEFINED = -1;
	public final static int ID_SHOW_BUTTON = -2;
	
	public static CustomDialog showAlertDialog(Context context,final Handler handler, int idTitle, int idContent,int idLeftButton,int idRightButton,final int leftMsgWhat,final int RightMsgWhat){
		String title = context.getResources().getString(idTitle);
		String content = context.getResources().getString(idContent);
		String leftButtonText = context.getResources().getString(idLeftButton);
		String rightButtonText = context.getResources().getString(idRightButton);
		CustomDialog dialog = showAlertDialog(context, handler, title, content, leftButtonText, rightButtonText, leftMsgWhat, RightMsgWhat);
		return dialog;
	}
	
	public static CustomDialog showAlertDialog(Context context,final Handler handler, String content,final int leftMsgWhat){
		CustomDialog dialog = showAlertDialog(context, handler, null, content, null, null, leftMsgWhat, ID_UNDEFINED);
		return dialog;
	}
	
	public static CustomDialog showAlertDialog(Context context,final Handler handler, int idContent,final int leftMsgWhat){
		String content = context.getResources().getString(idContent);
		CustomDialog dialog = showAlertDialog(context, handler, null, content, null, null, leftMsgWhat, ID_UNDEFINED);
		return dialog;
	}
	
	public static CustomDialog showAlertDialog(Context context,final Handler handler, String title, String content,final int leftMsgWhat,final int RightMsgWhat){
		CustomDialog dialog = showAlertDialog(context, handler, title, content, null, null, leftMsgWhat, RightMsgWhat);
		return dialog;
	}
	
	/**
	 * 
	 * @param context
	 * @param handler
	 * @param title
	 * @param content
	 * @param ButtonText
	 * @param MsgWhat
	 * @param hideLeftOrRight Direction.Left:������ߵİ�ť��Direction.RIGHT�������ұߵİ�ť
	 * @return
	 */
	public static CustomDialog showAlertDialognoshow(Context context,final Handler handler, String title, String content,String ButtonText,final int MsgWhat,Direction hideLeftOrRight){
		CustomDialog dialog=null;
		if(Direction.LEFT.equals(hideLeftOrRight)){
			dialog = showAlertDialognoshow(context, handler, title, content, null, ButtonText, ID_UNDEFINED, MsgWhat);
		}else{
			dialog = showAlertDialognoshow(context, handler, title, content, ButtonText, null, MsgWhat, ID_UNDEFINED);
		}
		return dialog;
	}
	
	public enum Direction {
		 LEFT,RIGHT
	}
	/**
	 * 
	 * @param context
	 * @param handler
	 * @param title
	 * @param content
	 * @param leftButtonText
	 * @param leftMsgWhat
	 * @param hideLeftOrRight Direction.Left:������ߵİ�ť��Direction.RIGHT�������ұߵİ�ť
	 * @return
	 */
	public static CustomDialog showAlertDialog(Context context,final Handler handler, String title, String content,String ButtonText,final int MsgWhat,Direction hideLeftOrRight){
		CustomDialog dialog=null;
		if(Direction.LEFT.equals(hideLeftOrRight)){
			dialog = showAlertDialog(context, handler, title, content, null, ButtonText, ID_UNDEFINED, MsgWhat);
		}else{
			dialog = showAlertDialog(context, handler, title, content, ButtonText, null, MsgWhat, ID_UNDEFINED);
		}
		return dialog;
	}
	
	
	public static CustomDialog showAlertDialog(Context context, String title, String content){
		CustomDialog dialog = showAlertDialog(context, null, title, content, null, null, ID_UNDEFINED,ID_SHOW_BUTTON);
		return dialog;
	}
	
	public static CustomDialog showAlertDialog(Context context, String title, String content,final int leftMsgWhat){
		CustomDialog dialog = showAlertDialog(context, null, title, content, null, null, leftMsgWhat,ID_UNDEFINED);
		return dialog;
	}
	
	public static CustomDialog showAlertDialog(Context context, String title, String content,String leftButtonText,final int leftMsgWhat){
		CustomDialog dialog = showAlertDialog(context, null, title, content, leftButtonText, null, leftMsgWhat,ID_UNDEFINED);
		return dialog;
	}

	public static CustomDialog showAlertDialog(Context context, int idTitle, String content, int idLeftButtonText) {
		String title = context.getResources().getString(idTitle);
		String leftButtonText = context.getResources().getString(idLeftButtonText);
		CustomDialog dialog = showAlertDialog(context, null, title, content, leftButtonText, null, ID_SHOW_BUTTON, ID_SHOW_BUTTON);
		return dialog;
	}

	public static CustomDialog showAlertDialog(Context context, int idTitle, int idContent) {
		String title = context.getResources().getString(idTitle);
		String content = context.getResources().getString(idContent);
		CustomDialog dialog = showAlertDialog(context, null, title, content, null, null, ID_UNDEFINED,ID_SHOW_BUTTON);
		return dialog;
	}
	
	public static CustomDialog showAlertDialog(Context context, String content){
		CustomDialog dialog = showAlertDialog(context, null, null, content, null, null, ID_UNDEFINED,ID_SHOW_BUTTON);
		return dialog;
	}
	
	public static CustomDialog showAlertDialog(Context context, int idContent){
		String content = context.getResources().getString(idContent);
		CustomDialog dialog = showAlertDialog(context, null, null, content, null, null, ID_UNDEFINED,ID_SHOW_BUTTON);
		return dialog;
	}
	public static CustomDialog showAlertDialog(Context context,final Handler handler,String title, String content,String leftButtonText,String rightButtonText,final int leftMsgWhat,final int rightMsgWhat){
		CustomDialog dialog = showAlertDialog(context, handler, title, content, leftButtonText, rightButtonText, leftMsgWhat, rightMsgWhat, null);
		return dialog;
	}
	public static CustomDialog showAlertDialognoshow(Context context,final Handler handler,String title, String content,String leftButtonText,String rightButtonText,final int leftMsgWhat,final int rightMsgWhat){
		CustomDialog dialog = showAlertDialognoshow(context, handler, title, content, leftButtonText, rightButtonText, leftMsgWhat, rightMsgWhat, null);
		return dialog;
	}
	
	public static CustomDialog showAlertDialog(Context context,final Handler handler,String title, String content,String leftButtonText,String rightButtonText,final int leftMsgWhat,final int rightMsgWhat, final Object data){
		final CustomDialog dialog = new CustomDialog(context);
		dialog.hidleProgressBar();
		dialog.hidePercent();
		dialog.setContent(content);
		if (title != null && !"".equals(title)) {
			dialog.setTitleGravity(Gravity.CENTER);
			dialog.setTitle(title);
		}
		if (leftButtonText != null && !"".equals(leftButtonText)) {
			dialog.setLeftButtonText(leftButtonText);
		}
		if (rightButtonText != null && !"".equals(rightButtonText)) {
			dialog.setRightButtonText(rightButtonText);
		}
		if (handler == null && leftMsgWhat == ID_UNDEFINED && rightMsgWhat == ID_UNDEFINED) {
			dialog.hideButton();
		} else {
			if (leftMsgWhat != ID_UNDEFINED) {
				dialog.setLeftButtonOnClick(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (handler != null && leftMsgWhat != ID_SHOW_BUTTON) {
							dialog.setDismissFlag(leftMsgWhat);
							if (data != null) {
								Message msg = new Message();
								msg.what = leftMsgWhat;
								msg.obj = data;
								handler.sendMessage(msg);
							} else {
								handler.sendEmptyMessage(leftMsgWhat);
							}
						}
						dialog.dismiss();
					}
				});
			} else {
				dialog.hideLeftButton();
			}
			if (rightMsgWhat != ID_UNDEFINED) {
				dialog.setRightButtonOnClick(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (handler != null && rightMsgWhat != ID_SHOW_BUTTON) {
							dialog.setDismissFlag(rightMsgWhat);
							handler.sendEmptyMessage(rightMsgWhat);
						}
						dialog.dismiss();
					}
				});
			} else {
				dialog.hideRightButton();
			}
		}
		try {
			dialog.show();
		} catch (WindowManager.BadTokenException e) {
			e.printStackTrace();
		}
		return dialog;
	}
	
	public static CustomDialog showAlertDialognoshow(Context context,final Handler handler,String title, String content,String leftButtonText,String rightButtonText,final int leftMsgWhat,final int rightMsgWhat, final Object data){
		final CustomDialog dialog = new CustomDialog(context);
		dialog.hidleProgressBar();
		dialog.hidePercent();
		dialog.setContent(content);
		if (title != null && !"".equals(title)) {
			dialog.setTitleGravity(Gravity.CENTER);
			dialog.setTitle(title);
		}
		if (leftButtonText != null && !"".equals(leftButtonText)) {
			dialog.setLeftButtonText(leftButtonText);
		}
		if (rightButtonText != null && !"".equals(rightButtonText)) {
			dialog.setRightButtonText(rightButtonText);
		}
		if (handler == null && leftMsgWhat == ID_UNDEFINED && rightMsgWhat == ID_UNDEFINED) {
			dialog.hideButton();
		} else {
			if (leftMsgWhat != ID_UNDEFINED) {
				dialog.setLeftButtonOnClick(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (handler != null && leftMsgWhat != ID_SHOW_BUTTON) {
							dialog.setDismissFlag(leftMsgWhat);
							if (data != null) {
								Message msg = new Message();
								msg.what = leftMsgWhat;
								msg.obj = data;
								handler.sendMessage(msg);
							} else {
								handler.sendEmptyMessage(leftMsgWhat);
							}
						}
						dialog.dismiss();
					}
				});
			} else {
				dialog.hideLeftButton();
			}
			if (rightMsgWhat != ID_UNDEFINED) {
				dialog.setRightButtonOnClick(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (handler != null && rightMsgWhat != ID_SHOW_BUTTON) {
							dialog.setDismissFlag(rightMsgWhat);
							handler.sendEmptyMessage(rightMsgWhat);
						}
						dialog.dismiss();
					}
				});
			} else {
				dialog.hideRightButton();
			}
		}
		
		return dialog;
	}
	
	public static CustomDialog showProgressDialog(Context context,final Handler handler,String title, String content,String leftButtonText,String rightButtonText,final int leftMsgWhat,final int rightMsgWhat){
		CustomDialog dialog = showProgressDialog(context, handler, true, true, title, content, leftButtonText, rightButtonText, leftMsgWhat, rightMsgWhat, false, false);
		return dialog;
	}
	
	public static CustomDialog showProgressDialog(Context context,final Handler handler,boolean isShowProgressBar,boolean isShowPercent,String title, String content,String rightButtonText,final int rightMsgWhat){
		CustomDialog dialog = showProgressDialog(context, handler, isShowProgressBar, isShowPercent, title, content, null, rightButtonText, ID_UNDEFINED, rightMsgWhat, false, false);
		return dialog;
	}
	
	public static CustomDialog showProgressDialog(Context context,final Handler handler,boolean isShowProgressBar,boolean isShowPercent, String content,String rightButtonText,final int rightMsgWhat){
		CustomDialog dialog = showProgressDialog(context, handler, isShowProgressBar, isShowPercent, null, content, null, rightButtonText, ID_UNDEFINED, rightMsgWhat, false, false);
		return dialog;
	}
	
	public static CustomDialog showProgressDialog(Context context,final Handler handler, String content,final int rightMsgWhat){
		CustomDialog dialog = showProgressDialog(context, handler, true, true, null, content, null, null, ID_UNDEFINED, rightMsgWhat, false, false);
		return dialog;
	}
	
	public static CustomDialog showProgressDialog(Context context, String content){
		CustomDialog dialog = showProgressDialog(context, null, true, false, null, content, null, null, ID_UNDEFINED, ID_UNDEFINED, true, true);
		return dialog;
	}
	public static CustomDialog showProgressDialog(Context context, boolean isShowProgressBar,boolean isShowPercent,String content){
		CustomDialog dialog = showProgressDialog(context, null, isShowProgressBar, isShowPercent, null, content, null, null, ID_UNDEFINED, ID_UNDEFINED, false, false);
		return dialog;
	}
	
	public static CustomDialog showProgressDialog(Context context,final Handler handler,boolean isShowProgressBar,boolean isShowPercent,String title, String content,String leftButtonText,String rightButtonText,final int leftMsgWhat,final int rightMsgWhat, boolean isShowTip, boolean isProgress){
		final CustomDialog dialog = new CustomDialog(context, isProgress);
		dialog.setContent(content);
		if (!isShowProgressBar) {
			dialog.hidleProgressBar();
		}
		if (!isShowPercent) {
			dialog.hidePercent();
		}
		if (title != null && !"".equals(title)) {
			dialog.setTitleGravity(Gravity.CENTER);
			dialog.setTitle(title);
		} else {
			dialog.hideTitle();
		}
		if (leftButtonText != null && !"".equals(leftButtonText)) {
			dialog.setLeftButtonText(leftButtonText);
		}
	
		if (rightButtonText != null && !"".equals(rightButtonText)) {
			dialog.setRightButtonText(rightButtonText);
		}
		if (handler == null && leftMsgWhat == ID_UNDEFINED && rightMsgWhat == ID_UNDEFINED) {
			dialog.hideButton();
		} else {
			if (leftMsgWhat != ID_UNDEFINED) {
				dialog.setLeftButtonOnClick(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (handler != null) {
							handler.sendEmptyMessage(leftMsgWhat);
						} else {
							dialog.dismiss();
						}
					}
				});
			} else {
				dialog.hideLeftButton();
			}
			if (rightMsgWhat != ID_UNDEFINED) {
				dialog.setRightButtonOnClick(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (handler != null) {
							handler.sendEmptyMessage(rightMsgWhat);
						} else {
							dialog.dismiss();
						}
					}
				});
			} else {
				dialog.hideRightButton();
			}
		}
		if (isShowTip) {
			dialog.showTip();
		}
		
		try {
			dialog.show();
		} catch (WindowManager.BadTokenException e) {
			e.printStackTrace();
		}
		return dialog;
	}
	
	public static void dismissDialog(CustomDialog dialog){
		if (dialog != null && dialog.isShowing()) {
			try {
				dialog.dismiss();
			} catch (WindowManager.BadTokenException e) {
				e.printStackTrace();
			}
		}
	}

	
}
