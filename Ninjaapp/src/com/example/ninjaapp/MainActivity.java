package com.example.ninjaapp;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/** Called when the user clicks the New button */
	public void NewClick(View view) {
		Intent intent = new Intent(this, CurListActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Load button */
	public void LoadClick(View view) {
		Intent intent = new Intent(this, InventoryView.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Option button */
	public void OptionClick(View view) {
		Intent intent = new Intent(this, OptionsPage.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Quit button */
	public void QuitClick(View view) {
	    // Do something in response to button .
	}
}
