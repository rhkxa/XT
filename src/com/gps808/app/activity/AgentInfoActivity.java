package com.gps808.app.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.gps808.app.R;
import com.gps808.app.fragment.HeaderFragment;
import com.gps808.app.models.AgentInfo;
import com.gps808.app.utils.BaseActivity;
import com.gps808.app.utils.HttpUtil;
import com.gps808.app.utils.StringUtils;
import com.gps808.app.utils.UrlConfig;
import com.gps808.app.utils.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AgentInfoActivity extends BaseActivity {

	private TextView angent_name, angent_people, angent_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agent_info);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		HeaderFragment headerFragment = (HeaderFragment) this
				.getSupportFragmentManager().findFragmentById(R.id.title);
		headerFragment.setTitleText("服务商信息");
		angent_name = (TextView) findViewById(R.id.angent_name);
		angent_people = (TextView) findViewById(R.id.angent_people);
		angent_phone = (TextView) findViewById(R.id.angent_phone);
		angent_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!StringUtils.isEmpty(angent_phone.getText().toString())) {
					Utils.callPhone(AgentInfoActivity.this, angent_phone
							.getText().toString());
				}
			}
		});
		getData();
	}

	private void getData() {
		String url = UrlConfig.getAgent();
		HttpUtil.get(AgentInfoActivity.this, url,
				new jsonHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						// TODO Auto-generated method stub
						AgentInfo agent = JSON.parseObject(response.toString(),
								AgentInfo.class);
						angent_name.setText(agent.getAgentName());
						angent_people.setText(agent.getName());
						angent_phone.setText(agent.getPhone());
					}
				});
	}
}
