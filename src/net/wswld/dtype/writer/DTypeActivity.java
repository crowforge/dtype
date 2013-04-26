package net.wswld.dtype.writer;

import java.util.HashMap;
import java.util.Map;

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
import net.wswld.dtype.writer.MyTheme;

/**
 * dType Main Activity (main)
 * @author vsevolod
 * Yes, I know, that this code can be much more compact. I'working on that.
 */
/**
 * @author vsevolod
 *
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
		int Theme = 1; // Default color theme is grey.
		int Font = 2; // Default font is serif.
		int Size = 2; // Default size is 17.
		Theme = prefs.getInt("theme", Theme);
		Font = prefs.getInt("font", Font);
		Size = prefs.getInt("size", Size);
		
		setTheme(this, assembleTheme(Theme, Font, Size)); 
        
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
    
    /**
     * This is the main method for theme assembly, it accepts the keys and returns the theme instance.
     * @param theme Theme key.
     * @param font Font key.
     * @param size Size key.
     * @return
     */
    public MyTheme assembleTheme(int theme, int font, int size) {
    	
    	// Matching the font key to the typeface.
    	Map<Integer, Typeface> fonts = new HashMap<Integer, Typeface>();
        fonts.put(1, Typeface.SANS_SERIF);
        fonts.put(2, Typeface.SERIF);
        fonts.put(3, Typeface.MONOSPACE);
        
        // Matching the size int to the real size. Why wouldn't I just save it as it is, anyway?
        Map<Integer, Integer> sizes = new HashMap<Integer, Integer>();
        sizes.put(1, 12);
        sizes.put(2, 17);
        sizes.put(3, 25);
        
        //Matching the theme key to the instance of the theme + inserting the previously acquired parameters.
        Map<Integer, MyTheme> themes = new HashMap<Integer, MyTheme>();
        themes.put(1, new MyTheme(R.drawable.grey_background, 0, R.color.DrText, fonts.get(font), sizes.get(size)));
        themes.put(2, new MyTheme(R.drawable.blue_background, 0, R.color.LtText, fonts.get(font), sizes.get(size)));
        themes.put(3, new MyTheme(R.drawable.dark_background, 0, R.color.MdText, fonts.get(font), sizes.get(size)));
        
        return themes.get(theme);
    }
    
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
			
			setTheme(this, assembleTheme(Theme, Font, Size));
			
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
    
    public void setTheme(DTypeActivity dTypeActivity, MyTheme theme) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
        final View main_view = (View) findViewById(R.id.mainview);
        main_view.setBackgroundDrawable(getResources().getDrawable(theme.background));
		edit_text.getBackground().setAlpha(theme.alpha);
		edit_text.setTextColor(getResources().getColor(theme.color));
		edit_text.setTypeface(theme.font);
		edit_text.setTextSize(theme.size);
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
