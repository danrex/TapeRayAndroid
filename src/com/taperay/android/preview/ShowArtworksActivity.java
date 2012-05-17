package com.taperay.android.preview;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ShowArtworksActivity extends TapeRayActivity {

	private ContentManager contentManager;
	private String[] artworkTitles;

	private void showArtworks() {
		artworkTitles = contentManager.getArtworkTitles();
		setTitle(contentManager.getCurrentTitle());
		
		final ListView listView = (ListView) findViewById(R.id.list);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowArtworksActivity.this,
				android.R.layout.simple_list_item_1, android.R.id.text1, artworkTitles);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(ShowArtworksActivity.this, ShowPreviewActivity.class);
				contentManager.selectArtwork(position);
				startActivity(i);
			}
		});

		listView.setAdapter(adapter);
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.artworks);

		TapeRayApplication app = (TapeRayApplication) getApplication();
		contentManager = app.getContentManager();
		
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      final String query = intent.getStringExtra(SearchManager.QUERY);
	      
			new Thread(new Runnable() {
				public void run() {
					try {
						contentManager.performSearch(query);
					} catch (Exception e) {
						e.printStackTrace();
						displayNetworkErrorAndFinish();
						return;
					}
					
					runOnUiThread(new Runnable() {
						public void run() {
							showArtworks();
						}
					});					
				}
			}).start();
	    } else {
	    	showArtworks();
	    }
	}
}
