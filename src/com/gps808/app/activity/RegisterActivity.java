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

public class RegisterActivity extends BaseActivity {

	private EditText register_phone, register_name, register_user,
			register_pass, register_imei, register_sim, register_platno;
	private FancyButton save_ok, register_capture;
	private TextView register_buy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		HeaderFragment headerFragment = (HeaderFragment) this
				.getSupportFragmentManager().findFragmentById(R.id.title);
		headerFragment.setTitleText("用户注册");
		register_phone = (EditText) findViewById(R.id.register_phone);
		register_name = (EditText) findViewById(R.id.register_name);
		register_user = (EditText) findViewById(R.id.register_user);
		register_pass = (EditText) findViewById(R.id.register_pass);
		register_imei = (EditText) findViewById(R.id.register_imei);
		register_sim = (EditText) findViewById(R.id.register_sim);
		register_platno = (EditText) findViewById(R.id.register_platno);
		save_ok = (FancyButton) findViewById(R.id.save_ok);
		register_capture = (FancyButton) findViewById(R.id.register_capture);
		register_pass.setKeyListener(inputType);
		register_user.setKeyListener(inputType);
		register_imei.setKeyListener(inputType);
		register_capture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this,
						CaptureActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		register_buy = (TextView) findViewById(R.id.register_buy);
		register_buy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
		register_buy.getPaint().setAntiAlias(true);// 抗锯齿
		register_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Utils.toExploer(
						RegisterActivity.this,
						"http://mokebao.molink.cn/index.php?g=Wap&m=Index&a=index&token=bldbtc1458693848");
			}
		});
		save_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (StringUtils.isEmpty(register_name.getText().toString())) {
					Utils.ToastMessage(RegisterActivity.this, "名称不能为空");
					return;
				}
				if (StringUtils.isEmpty(register_user.getText().toString())) {
					Utils.ToastMessage(RegisterActivity.this, "登录名称不能为空");
					return;
				}

				if (StringUtils.isEmpty(register_pass.getText().toString())) {
					Utils.ToastMessage(RegisterActivity.this, "密码不能为空");
					return;
				}
				if (StringUtils.isEmpty(register_imei.getText().toString())) {
					Utils.ToastMessage(RegisterActivity.this, "请输入imei");
					return;
				}
				// if (StringUtils.isEmpty(register_sim.getText().toString())) {
				// Utils.ToastMessage(RegisterActivity.this, "请输入sim");
				// return;
				// }
				// if
				// (StringUtils.isEmpty(register_platno.getText().toString())) {
				// Utils.ToastMessage(RegisterActivity.this, "请输入车牌号");
				// return;
				// }
				if (register_pass.length() < 6) {
					Utils.ToastMessage(RegisterActivity.this, "密码长度最少6位");
					return;
				}
				// if (StringUtils.isEmpty(register_phone.getText().toString()))
				// {
				// Utils.ToastMessage(RegisterActivity.this, "手机号不能为空");
				// return;
				// }
				// if
				// (!StringUtils.isPhone(register_phone.getText().toString())) {
				// Utils.ToastMessage(RegisterActivity.this, "手机号码不正确,请重新输入");
				// return;
				// }
				toRregister();
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

	private void toRregister() {
		String url = UrlConfig.getRegister();
		StringEntity entity = null;
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject();
			jsonObject.put("userName", register_name.getText().toString());
			jsonObject.put("loginName", register_user.getText().toString());
			jsonObject.put("password",
					CyptoUtils.MD5(register_pass.getText().toString()));
			jsonObject.put("phoneNo", register_phone.getText().toString());
			jsonObject.put("imei", register_imei.getText().toString());
			jsonObject.put("sim", register_sim.getText().toString());
			jsonObject.put("plateNo", register_platno.getText().toString());
			LogUtils.DebugLog("post json", jsonObject.toString());
			entity = new StringEntity(jsonObject.toString(), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpUtil.post(RegisterActivity.this, url, entity, "application/json",
				new jsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						// TODO Auto-generated method stub
						if (Utils.requestOk(response)) {
							Utils.ToastMessage(RegisterActivity.this, "注册成功");
							Intent intent = new Intent();
							intent.putExtra("user", register_user.getText()
									.toString());
							intent.putExtra("pass", register_pass.getText()
									.toString());
							setResult(1, intent);
							finish();
						} else {
							Utils.ToastMessage(RegisterActivity.this,
									Utils.getKey(response, "errorMsg"));
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
				register_imei.setText(arg2.getStringExtra("code"));
			}
		}
		super.onActivityResult(arg0, arg1, arg2);
	}
}
