package net.wswld.dtype.writer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * dType Main Activity (main)
 * @author vsevolod
 * Yes, I know, that this code can be much more compact. I'working on that.
 */
public class DTypeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /**
         * Enabling dithering for the whole window to ensure gradients shown right later.
         */
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        /** 
         * Reliable Input Preservation
         * Preserves/writes the copy of inputed text into the built-in db.
         * TD: Possibly it could be a separate void/function. 
         */
        SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
        final EditText edit_text = (EditText) findViewById(R.id.editText1);
		edit_text.setText(prefs.getString("doutput_preserved", null), TextView.BufferType.EDITABLE);		
        
		/** Getting the saved values for all the visual options.*/
		int Theme = 1;
		int Font = 1;
		int Size = 1;
		Theme = prefs.getInt("theme", Theme);
		Font = prefs.getInt("font", Font);
		Size = prefs.getInt("size", Size);
		
		SetApplicableStyle(this, Theme, Font, Size); 
        
		/** 
		 * Done Button 
		 * This is the Done button code. 
		 */
        final Button button = (Button) findViewById(R.id.button1);
        button.getBackground().setAlpha(117);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	final EditText text = (EditText) findViewById(R.id.editText1);
            	String dOutput = text.getText().toString();
            	Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, dOutput);
                startActivity(Intent.createChooser(intent, "Share with"));
            }
        });
    };

    @Override
    public void onResume() {
        super.onResume();
 //       setContentView(R.layout.main);
        
        /**
         * Checking, whether the splash should be shown (only on first run).
         * "firstrun056" boolean name is changed on every new version to ensure, that the
         * splash is shown on update.
         */
        SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
		if (prefs.getBoolean("firstrun058", true)) {
								           
		           SharedPreferences.Editor editor = prefs.edit();
		           editor.putBoolean("firstrun058", false);
		           editor.commit(); // apply changes

		           Intent Splash = new Intent(this, IntroSplashActivity.class);
		           startActivity (Splash);        
		};
		
		//final EditText edit_text = (EditText) findViewById(R.id.editText1);
		
		/**
		 * Declaring style variables + defaults.
		 */
		int Theme = 1;
		int Font = 1;
		int Size = 1;
		
		/**
		 * Checking whether anything has changed in stored theme options.
		 * Note that this is onReturn after the Opt. 
		 * Possibly getting vars and setting them should be a separate void.
		 */
		if (prefs.getBoolean("opt_changed", true)) {
			Theme = prefs.getInt("theme", Theme);
			Font = prefs.getInt("font", Font);
			Size = prefs.getInt("size", Size);
			
			SetApplicableStyle(this, Theme, Font, Size);
			
			/** Setting opt_changed to false. */
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("opt_changed", false);
	        editor.commit(); // apply changes
		}
		
		/** Checking on the state of cleared boolean **/
		if (prefs.getBoolean("cleared", true)) {
			
			/** Clearing the preserved text */
	        final EditText edit_text = (EditText) findViewById(R.id.editText1);
			edit_text.setText(prefs.getString("doutput_preserved", null), TextView.BufferType.EDITABLE);
			
			/** Setting cleared to false. */
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("cleared", false);
	        editor.commit(); // apply changes
		}

    };
    

    public void SetApplicableStyle (DTypeActivity dTypeActivity, int Theme, int Font, int Size) {
    	// Retrieving the EditText and the View as objects
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
    	final View main_view = (View) findViewById(R.id.mainview);
		
    	// Setting the theme
		switch(Theme){
		case 1:
			SetThemeLight (this);
		break;
		case 2:
			SetThemeBlue (this);		
		break;
		case 3:
			SetThemeDark (this);
		break;
		}
		
		// Setting the font
		switch(Font){
		case 1:
			SetFontSans (this);
		break;
		case 2:
			SetFontSerif (this);		
		break;
		case 3:
			SetFontMono (this);
		break;
		}
		
		// Setting the size
		switch(Size){
		case 1:
			SetSizeSm (this);
		break;
		case 2:
			SetSizeMd (this);		
		break;
		case 3:
			SetSizeBg (this);
		break;
		}
    }
    
    /**
     * Setting theme to light.
     * @param dTypeActivity
     */
    public void SetThemeLight (DTypeActivity dTypeActivity) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
    	final View main_view = (View) findViewById(R.id.mainview);	
		main_view.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_background));
		edit_text.getBackground().setAlpha(0);
		edit_text.setTextColor(getResources().getColor(R.color.DrText));
		
    }

    /**
     * Setting theme to blue.
     * @param dTypeActivity
     */
    public void SetThemeBlue (DTypeActivity dTypeActivity) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
    	final View main_view = (View) findViewById(R.id.mainview);	
		main_view.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_background));
		edit_text.getBackground().setAlpha(0);
		edit_text.setTextColor(getResources().getColor(R.color.LtText));

    }

    /**
     * Setting theme to dark.
     * @param dTypeActivity
     */
    public void SetThemeDark (DTypeActivity dTypeActivity) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
    	final View main_view = (View) findViewById(R.id.mainview);	
		main_view.setBackgroundDrawable(getResources().getDrawable(R.drawable.dark_background));
		edit_text.getBackground().setAlpha(0);
		edit_text.setTextColor(getResources().getColor(R.color.MdText));

    } 
   
    /**
     * Setting font to sans-serif.
     * @param dTypeActivity
     */
    public void SetFontSans (DTypeActivity dTypeActivity) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
    	edit_text.setTypeface(Typeface.SANS_SERIF);
		
    }

    /**
     * Setting font to serif.
     * @param dTypeActivity
     */
    public void SetFontSerif (DTypeActivity dTypeActivity) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
    	edit_text.setTypeface(Typeface.SERIF);
    }

    /**
     * Setting font to monospace.
     * @param dTypeActivity
     */
    public void SetFontMono (DTypeActivity dTypeActivity) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
    	edit_text.setTypeface(Typeface.MONOSPACE);
    } 
    
    /**
     * Setting font size to big.
     * @param dTypeActivity
     */
    public void SetSizeSm (DTypeActivity dTypeActivity) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
    	edit_text.setTextSize(12);
		
    }
    
    /**
     * Setting font size to medium.
     * @param dTypeActivity
     */
    public void SetSizeMd (DTypeActivity dTypeActivity) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
    	edit_text.setTextSize(17);
    
    }

    /**
     * Setting font size to small.
     * @param dTypeActivity
     */   
    public void SetSizeBg (DTypeActivity dTypeActivity) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
    	edit_text.setTextSize(25);
    } 
    
    /**
     * OnKeyDown events.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    
    	/**
    	 * Accessing Opt on 'menu' keypress.
    	 */
        if (keyCode == KeyEvent.KEYCODE_MENU) {
	    	Intent opt = new Intent(this, OptionsActivity.class);
	        startActivity (opt);
        };
        
        /**
         * Explicit minimize on 'back' keystroke.
         * Note that output gets written to the db.
         */
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
        	final EditText text = (EditText) findViewById(R.id.editText1);
        	String dOutput = text.getText().toString();
        	SharedPreferences.Editor editor = prefs.edit();
	        editor.putString("doutput_preserved", dOutput);
	        editor.commit(); // apply changes
        	moveTaskToBack(true);
        };
		return true;
    };
}


	