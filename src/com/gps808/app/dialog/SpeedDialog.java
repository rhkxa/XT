package com.gps808.app.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.gps808.app.R;
import com.gps808.app.view.FancyButton;

public class SpeedDialog extends Dialog {

	private CheckBox chose_fast, chose_normal, chose_lower;
	private List<CheckBox> checkboxList = new ArrayList<CheckBox>();
	private FancyButton dialog_no, dialog_ok;
	private Context context;
	private int speed = 500;

	public SpeedDialog(Context context) {
		super(context, R.style.Dialog);
		this.context = context;
		setCanceledOnTouchOutside(false);
	}

	public SpeedDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_speed);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub

		chose_fast = (CheckBox) findViewById(R.id.chose_fast);
		chose_normal = (CheckBox) findViewById(R.id.chose_normal);
		chose_lower = (CheckBox) findViewById(R.id.chose_lower);

		dialog_no = (FancyButton) findViewById(R.id.dialog_no);
		dialog_ok = (FancyButton) findViewById(R.id.dialog_ok);

		dialog_no.setOnClickListener(click);
		dialog_ok.setOnClickListener(click);
		checkboxList.add(chose_lower);
		checkboxList.add(chose_fast);
		checkboxList.add(chose_normal);
		for (CheckBox item : checkboxList) {
			item.setOnClickListener(checkClick);
		}
		chose_fast.setChecked(true);

	}

	private void setCheckable() {
		for (CheckBox item : checkboxList) {
			item.setChecked(false);
		}
	}

	private android.view.View.OnClickListener checkClick = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			setCheckable();
			switch (arg0.getId()) {
			case R.id.chose_lower:
				chose_lower.setChecked(true);
				speed = 2000;
				break;
			case R.id.chose_fast:
				chose_fast.setChecked(true);
				speed = 500;
				break;
			case R.id.chose_normal:
				chose_normal.setChecked(true);
				speed = 1000;
				break;

			}
		}
	};

	private android.view.View.OnClickListener click = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.dialog_no:

				break;

			case R.id.dialog_ok:
				onOkClickListener.onOK(speed);

				break;
			}
			dismiss();
		}
	};
	private OnOkClickListener onOkClickListener;

	public void setOnOkClickListener(OnOkClickListener l) {
		onOkClickListener = l;
	}

	public interface OnOkClickListener {
		public void onOK(int speed);
	}

}
