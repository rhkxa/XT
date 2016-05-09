package com.gps808.app.activity;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.gps808.app.R;
import com.gps808.app.fragment.HeaderFragment;
import com.gps808.app.utils.BaseActivity;
import com.gps808.app.utils.CyptoUtils;
import com.gps808.app.utils.HttpUtil;
import com.gps808.app.utils.LogUtils;
import com.gps808.app.utils.StringUtils;
import com.gps808.app.utils.UrlConfig;
import com.gps808.app.utils.Utils;
import com.gps808.app.view.FancyButton;

public class DeviceAddActivity extends BaseActivity {
	private EditText device_imei, device_sim, device_platno;
	private FancyButton save_ok, device_capture;
	private TextView device_buy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_add);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		HeaderFragment headerFragment = (HeaderFragment) this
				.getSupportFragmentManager().findFragmentById(R.id.title);
		headerFragment.setTitleText("增加车辆");

		device_imei = (EditText) findViewById(R.id.device_imei);
		device_sim = (EditText) findViewById(R.id.device_sim);
		device_platno = (EditText) findViewById(R.id.device_platno);
		save_ok = (FancyButton) findViewById(R.id.save_ok);
		device_capture = (FancyButton) findViewById(R.id.device_capture);
		device_imei.setKeyListener(inputType);
		device_capture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DeviceAddActivity.this,
						CaptureActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		save_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (StringUtils.isEmpty(device_imei.getText().toString())) {
					Utils.ToastMessage(DeviceAddActivity.this, "请输入imei");
					return;
				}
				if (StringUtils.isEmpty(device_sim.getText().toString())) {
					Utils.ToastMessage(DeviceAddActivity.this, "请输入sim");
					return;
				}
				if (StringUtils.isEmpty(device_platno.getText().toString())) {
					Utils.ToastMessage(DeviceAddActivity.this, "请输入车牌号");
					return;
				}

				toAdd();
			}
		});

	}

	private DigitsKeyListener inputType = new DigitsKeyListener() {
		@Override
		public int getInputType() {
			return InputType.TYPE_TEXT_VARIATION_PASSWORD;
		}

		@Override
		protected char[] getAcceptedChars() {
			char[] data = getResources().getString(
					R.string.login_only_can_input).toCharArray();
			return data;
		}

	};

	private void toAdd() {
		String url = UrlConfig.getAddVehicle();
		StringEntity entity = null;
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("imei", device_imei.getText().toString());
			jsonObject.put("sim", device_sim.getText().toString());
			jsonObject.put("plateNo", device_platno.getText().toString());
			LogUtils.DebugLog("post json", jsonObject.toString());
			entity = new StringEntity(jsonObject.toString(), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpUtil.post(DeviceAddActivity.this, url, entity, "application/json",
				new jsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						// TODO Auto-generated method stub
						if (Utils.requestOk(response)) {
							Utils.ToastMessage(DeviceAddActivity.this, "添加成功");
						} else {
							Utils.ToastMessage(DeviceAddActivity.this, Utils
									.getKey(response, "errorMsg"));
						}
						super.onSuccess(statusCode, headers, response);
					}
				});

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		if (arg0 == 0) {
			if (arg1 == 1) {
				device_imei.setText(arg2.getStringExtra("code"));
			}
		}
		super.onActivityResult(arg0, arg1, arg2);
	}
}
