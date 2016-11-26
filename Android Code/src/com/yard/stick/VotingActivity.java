package com.yard.stick;

import org.imaginem.Imaginem;
import org.imaginem.Parser;
import org.imaginem.items.ImageItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yard.stick.util.GoogleAccount;
import com.yard.stick.util.Voting;

public class VotingActivity extends Activity {

	private ImageItem m_Item;
	private Imaginem m_v2Wrap;
	private Voting m_Voting;
	private RadioGroup m_radioGroup;
	private int count = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voting);
		ImageLoader imgLoad = new ImageLoader(this);
		m_Voting = new Voting(this);
		m_v2Wrap = new Imaginem(GoogleAccount.getUsername(this));
		
		try {

			String imgjson = getIntent().getStringExtra("ImageItem");
			m_Item = Parser.imageItem(imgjson);
			TextView txtDown = (TextView) findViewById(R.id.txtDown);
			txtDown.setText(Integer.toString(m_Item.getDownVotes()));
			// txtDown.setText("2");

			TextView txtUp = (TextView) findViewById(R.id.txtUp);
			txtUp.setText(Integer.toString(m_Item.getUpVotes()));
			// txtUp.setText("3");

			TextView txtScore = (TextView) findViewById(R.id.txtScore);
			txtScore.setText(Double.toString(m_Item.getScore()));

			ImageView imageView = (ImageView) findViewById(R.id.masterView);
			imgLoad.DisplayImage(m_Item.getImageURL(), imageView);

			m_radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
			
			
			
			Button voteUp = (Button) findViewById(R.id.voteUpButton);
			voteUp.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					try {
						count = 1;
						int checkedRadioButton = m_radioGroup.getCheckedRadioButtonId();
						switch (checkedRadioButton) {
						case R.id.onexButton:
							count = 1;
							break;
						case R.id.tenxButton:
							count = 10;
							break;
						case R.id.hundxButton:
							count = 100;
							break;
						}
						
											// Start lengthy operation in a background thread
				        new Thread(new Runnable() {
				             public void run() {
				            	 m_Voting.VoteUp(m_v2Wrap, m_Item, count);    	 
				             }
				         }).start();


						Toast.makeText(VotingActivity.this, "Your votes are being submitted", Toast.LENGTH_LONG).show();
						finish();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});

			Button voteDown = (Button) findViewById(R.id.voteDownBUtton);
			voteDown.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					try {
						count = 1;
						int checkedRadioButton = m_radioGroup.getCheckedRadioButtonId();
						switch (checkedRadioButton) {
						case R.id.onexButton:
							count = 1;
							break;
						case R.id.tenxButton:
							count = 10;
							break;
						case R.id.hundxButton:
							count = 100;
							break;
						}
						

						// Start lengthy operation in a background thread
				         new Thread(new Runnable() {
				             public void run() {
									m_Voting.VoteDown(m_v2Wrap, m_Item, count);
				             }
				         }).start();

						Toast.makeText(VotingActivity.this, "Your votes are being submitted", Toast.LENGTH_LONG).show();
						finish();

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
