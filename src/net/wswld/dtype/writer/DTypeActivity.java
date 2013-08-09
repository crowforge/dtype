package net.wswld.dtype.writer;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import net.wswld.dtype.writer.MyTheme;

/**
 * <h1>dType Main Activity</h1>
 * This is the main activity of the app, where all the magic happens.
 * {@link #onCreate(android.os.Bundle) onCreate()} and {@link #onResume()} methods are the generic
 * Android methods and contain all the necessary code to get the main screen running. I know that
 * all this code could be a little more compact. I'm working on that.<br /><br />
 * Other methods are called by these two or on some event. These include:<ol>
 *     <li>{@link #assembleTheme(int, int, int)} matches the <code>font</code>,
 *     <code>theme</code> and <code>size</code> values retrieved from the database to the actual
 *     values and objects (<code>Typeface</code>) and returns the {@link MyTheme} object. This
 *     theme object is then processed by {@link #setTheme(DTypeActivity, MyTheme)} within
 *     {@link #onCreate(android.os.Bundle) onCreate()} and {@link #onResume()} methods and its
 *     properties are applied to the activity.</li>
 *     <li>{@link #setTheme(DTypeActivity, MyTheme)} is the method that applies a theme object to
 *     the main activity.</li>
 *     <li>{@link #onKeyDown(int, android.view.KeyEvent)} processes the events that happen on
 *     button pressed. In particular it launches the {@link OptionsActivity} activity on
 *     <code>menu</code> button pressed and exits on <code>back</code> button pressed. Pretty
 *     straight-forward stuff. However it also performs saving the contains of the
 *     <code>EditText</code> field on exit.</li>
 *     <li>{@link #countWords(String)} does word counting. The counter is applied to the
 *     <code>TextView</code> in overridden <code>onTextChanged</code> within
 *     {@link #onCreate(android.os.Bundle) onCreate()}.</li>
 * </ol>
 */
public class DTypeActivity extends Activity {
    /**
     * Generic Android <code>onCreate</code> method. Contains the lion share of runtime code.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*
         * Enabling dithering for the whole window to ensure gradients shown right later.
         */
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

        /*
         * Preserves/writes the copy of inputed text into the built-in db.
         * TD: Possibly it could be a separate void/function. 
         */
		final TextView wordCount = (TextView) findViewById(R.id.counterView);
		final EditText edit_text = (EditText) findViewById(R.id.editText1);

        /*
         * TextWatcher instance with overridden onTextChanged to apply word/symbol counters to the
         * corresponding textViews every time the EditText value has changed.
         */
        final TextWatcher textWatcher = new TextWatcher() {
        	@Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
        		wordCount.setText(String.valueOf(s.length()) + "/" + countWords(s.toString()));
        	}
            
        	@Override
            public void afterTextChanged(Editable s) {}
        	
        	@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
        };
        
        edit_text.addTextChangedListener(textWatcher); 

        // Getting the preserved value for EditText from the DB
        SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
        // Applying the preserved value to EditText
        edit_text.removeTextChangedListener(textWatcher); 
		edit_text.setText(prefs.getString("doutput_preserved", null), TextView.BufferType.EDITABLE);
		edit_text.addTextChangedListener(textWatcher); 
        
		// Getting the preserved values for all the visual options from the DB
		int Theme = 1; // Default color theme is grey.
		int Font = 2; // Default font is serif.
		int Size = 2; // Default size is 17.
		Theme = prefs.getInt("theme", Theme);
		Font = prefs.getInt("font", Font);
		Size = prefs.getInt("size", Size);

        // Assembling and applying the theme
		setTheme(this, assembleTheme(Theme, Font, Size)); 

		// This is the Done button code. It performs the export of the EditText output.
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
     * This method matches the <code>font</code>, <code>theme</code> and <code>size</code> values
     * retrieved from the database to the actual values and objects (<code>Typeface</code>) and
     * returns the {@link MyTheme} object.
     * @param theme Theme value.
     * @param font Font value.
     * @param size Size value.
     * @return {@link MyTheme} instance with the corresponding parameters.
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
        themes.put(1, new MyTheme(R.drawable.grey_background, 0, R.color.DrText, R.color.MdGrey, fonts.get(font), sizes.get(size)));
        themes.put(2, new MyTheme(R.drawable.blue_background, 0, R.color.LtText, R.color.MdBlue, fonts.get(font), sizes.get(size)));
        themes.put(3, new MyTheme(R.drawable.dark_background, 0, R.color.MdText, R.color.MdDark, fonts.get(font), sizes.get(size)));
        
        return themes.get(theme);
    }

    /**
     * Generic Android <code>onResume</code> method. Contains the lion share of runtime code.
     */
    @Override
    public void onResume() {
        super.onResume();
        /*
         * Checking, whether the splash should be shown (only on first run).
         * "firstrun056" boolean name is changed on every new version to ensure, that the
         * splash is shown on update.
         */
        SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
		if (prefs.getBoolean("firstrun66", true)) {
								           
		           SharedPreferences.Editor editor = prefs.edit();
		           editor.putBoolean("firstrun66", false);
		           editor.commit(); // apply changes

		           Intent Splash = new Intent(this, IntroSplashActivity.class);
		           startActivity (Splash);        
		};

		// Declaring style variables + defaults.
		int Theme = 1;
		int Font = 1;
		int Size = 1;
		
		/*
		 * Checking whether anything has changed in stored theme, size or font values.
		 */
		if (prefs.getBoolean("opt_changed", true)) {
			Theme = prefs.getInt("theme", Theme);
			Font = prefs.getInt("font", Font);
			Size = prefs.getInt("size", Size);

			setTheme(this, assembleTheme(Theme, Font, Size));

			
			// Setting opt_changed to false.
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("opt_changed", false);
	        editor.commit(); // apply changes
		}
		
		// Checking on the state of cleared boolean
		if (prefs.getBoolean("cleared", true)) {
			
			// Clearing the preserved text
	        final EditText edit_text = (EditText) findViewById(R.id.editText1);
			edit_text.setText(prefs.getString("doutput_preserved", null), TextView.BufferType.EDITABLE);
			
			// Setting cleared to false.
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("cleared", false);
	        editor.commit(); // apply changes
		}

    };

    /**
     * This is the method that applies a {@link MyTheme} object to the main activity.
     * @param dTypeActivity main activity.
     * @param theme {@link MyTheme} object.
     */
    public void setTheme(DTypeActivity dTypeActivity, MyTheme theme) {
    	final EditText edit_text = (EditText) findViewById(R.id.editText1);
        final View main_view = (View) findViewById(R.id.mainview);
        final TextView wordCounter = (TextView) findViewById(R.id.counterView);

        if((edit_text != null) && (main_view != null) && (wordCounter != null)){
        main_view.setBackgroundDrawable(getResources().getDrawable(theme.background));
		edit_text.getBackground().setAlpha(theme.alpha);
		edit_text.setTextColor(getResources().getColor(theme.color));
		wordCounter.setTextColor(getResources().getColor(theme.colorShade));
		edit_text.setTypeface(theme.font);
		edit_text.setTextSize(theme.size);
        };
    }

    /**
     * Pretty generic Android <code>onKeyDown</code> method. It processes the events that happen on
     * button pressed. In particular it launches the {@link OptionsActivity} activity on
     * <code>menu</code> button pressed and exits on <code>back</code> button pressed. Pretty
     * straight-forward stuff. However it also performs saving the contains of the
     * <code>EditText</code> field on exit.
     * @param keyCode key code.
     * @param event key event.
     * @return <code>true</code>, always <code>true</code>.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

    	// Accessing Opt on 'menu' keypress.
        if (keyCode == KeyEvent.KEYCODE_MENU) {
	    	Intent opt = new Intent(this, OptionsActivity.class);
	        startActivity (opt);
        };

        /*
         * Explicit minimize on 'back' keystroke.
         * Note that output gets written to the DB.
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

    /**
     * This is the method, where all the magic with word counting happens.
     * @param string the input string.
     * @return Number of words.
     */
    public static int countWords(String string){
        int counter = 0;
        boolean word = false;
        int endOfLine = string.length() - 1;

        for (int i = 0; i < string.length(); i++) {

            if (Character.isLetter(string.charAt(i)) == true && i != endOfLine) {
                word = true;

            } else if (Character.isLetter(string.charAt(i)) == false && word == true) {
                counter++;
                word = false;
                
            } else if (Character.isLetter(string.charAt(i)) && i == endOfLine) {
                counter++;
            }
        }
        return counter;
    }
    
}
