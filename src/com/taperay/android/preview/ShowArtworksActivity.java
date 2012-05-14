package com.taperay.android.preview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ShowArtworksActivity extends Activity {
	
	private ContentManager contentManager;
	private String[] artworkTitles;

	private void displayArtworks() {
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
		
		listView.post(new Runnable() {
			public void run() {
		        //setTitle("ficken!"); //String.format("%d", artworkTitles.length));
	    		//listView.setAdapter(adapter);
			}
		});
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.artworks);
        
    	TapeRayApplication app = (TapeRayApplication) getApplication();
    	contentManager = app.getContentManager();

		final ProgressDialog dialog = ProgressDialog.show(this, "", 
                "Loading artwork list, please wait...", true);

	    new Thread(new Runnable() {
	        public void run() {
	        	artworkTitles = contentManager.getArtworkTitles();
	        	dialog.dismiss();
	    		displayArtworks();
	        }
	    }).start();
    }
}
