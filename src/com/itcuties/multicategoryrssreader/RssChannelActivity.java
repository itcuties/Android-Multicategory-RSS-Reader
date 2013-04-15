package com.itcuties.multicategoryrssreader;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.itcuties.multicategoryrssreader.data.RssItem;
import com.itcuties.multicategoryrssreader.listeners.ListListener;
import com.itcuties.multicategoryrssreader.util.RssReader;

/**
 * Each category tab activity.
 * @author itcuties
 *
 */
public class RssChannelActivity extends Activity {
	
	// A reference to this activity
    private RssChannelActivity local;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_rss_channel);
		
		// Get the RSS URL that was set in the RssTabActivity
		String rssUrl = (String)getIntent().getExtras().get("rss-url");
		
		// Set reference to this activity
        local = this;
         
        GetRSSDataTask task = new GetRSSDataTask();
         
        // Start process RSS task
        task.execute(rssUrl);
		
	}
	
	/**
	 * This class downloads and parses RSS Channel feed.
	 * 
	 * @author itcuties
	 *
	 */
	private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem> > {
        @Override
        protected List<RssItem> doInBackground(String... urls) {
            try {
                // Create RSS reader
                RssReader rssReader = new RssReader(urls[0]);
             
                // Parse RSS, get items
                return rssReader.getItems();
             
            } catch (Exception e) {
                Log.e("RssChannelActivity", e.getMessage());
            }
             
            return null;
        }
         
        @Override
        protected void onPostExecute(List<RssItem> result) {
             
            // Get a ListView from the RSS Channel view
            ListView itcItems = (ListView) findViewById(R.id.rssChannelListView);
                         
            // Create a list adapter
            ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(local,android.R.layout.simple_list_item_1, result);
            // Set list adapter for the ListView
            itcItems.setAdapter(adapter);
                         
            // Set list view item click listener
            itcItems.setOnItemClickListener(new ListListener(result, local));
        }
    }
	
}
