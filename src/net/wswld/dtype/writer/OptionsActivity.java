package net.wswld.dtype.writer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * <h1>dType Options Screen</h1>
 * Options screen is represented by the <code>OptionsActivity</code>. Visually, it contains eleven buttons:
 * <ol>
 *     <li><code>Clear</code> for clearing the text preserved in the <code>doutput_preserved</code>
 *     field of the database. Since the <code>EditText</code> field of the
 *     {@link DTypeActivity dTypeActivity} is overwritten with the
 *     contents of the <code>doutput_preserved</code> in both
 *     {@link DTypeActivity#onCreate(android.os.Bundle)} and {@link DTypeActivity#onResume()} - it
 *     is safe to assume that it is also cleared.</li>
 *     <li><code>BtThemeGrey</code> calls the {@link #changeTheme(View v)} method, which changes the theme value in 
 *     the database, therefore changing the color theme of {@link DTypeActivity dTypeActivity}.</li>
 *     <li><code>BtThemeBlue</code> calls the {@link #changeTheme(View v)} method, which changes the theme value in 
 *     the database, therefore changing the theme value in the database, therefore changing the
 *     color theme of {@link DTypeActivity dTypeActivity}.</li>
 *     <li><code>BtThemeDark</code> calls the {@link #changeTheme(View v)} method, which changes the theme value in 
 *     the database, therefore changing the theme value in the database, therefore changing the
 *     color theme of {@link DTypeActivity dTypeActivity}.</li>
 *     <li><code>BtFontSans</code> calls the {@link #changeFont(View v)} method, which changes the theme value in 
 *     the database, therefore changing the font value in the database, therefore changing the
 *     typeface of {@link DTypeActivity dTypeActivity}.</li>
 *     <li><code>BtFontSerif</code> calls the {@link #changeFont(View v)} method, which changes the theme value in 
 *     the database, therefore changing the font value in the database, therefore changing the
 *     typeface of {@link DTypeActivity dTypeActivity}.</li>
 *     <li><code>BtFontMono</code> calls the {@link #changeFont(View v)} method, which changes the theme value in 
 *     the database, therefore changing the font value in the database, therefore changing the
 *     typeface of {@link DTypeActivity dTypeActivity}.</li>
 *     <li><code>BtSizeSmall</code> calls the {@link #changeSize(View v)} method, which changes the theme value in 
 *     the database, therefore changing the size value in the database, therefore changing the
 *     text size of {@link DTypeActivity dTypeActivity}.</li>
 *     <li><code>BtSizeMid</code> calls the {@link #changeSize(View v)} method, which changes the theme value in 
 *     the database, therefore changing the size value in the database, therefore changing the
 *     text size of {@link DTypeActivity dTypeActivity}.</li>
 *     <li><code>BtSizeBig</code> calls the {@link #changeSize(View v)} method, which changes the theme value in 
 *     the database, therefore changing the size value in the database, therefore changing the
 *     text size of {@link DTypeActivity dTypeActivity}.</li>
 *     <li><code>ReportBug</code> calls {@link #AddBug (View view)} method, which opens URL of a "Report a Bug" page in 
 *     browser.</li>
 * </ol>
 * There are also a couple of methods not really paired with any of the buttons, such as:
 * <ol>
 * <li>{@link #OnKeyDown(int keyCode, KeyEvent event)} override, that implements exiting the options screen on 
 * <code>Menu</code> or <code>Back</code> button pressed. Pretty standard.</li>
 * <li>{@link #selectedTheme (int Theme, OptionsActivity optionsActivity)} that applies special visual style to the 
 * active theme button.</li>
 * <li>{@link #selectedFont (int Theme, OptionsActivity optionsActivity)} that applies special visual style to the 
 * active font button.</li>
 * <li>{@link #selectedSize (int Theme, OptionsActivity optionsActivity)} that applies special visual style to the 
 * active size button.</li>
 * </ol>
 */
public class OptionsActivity extends DTypeActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opt);
        
        // Enabling dithering for the whole window to ensure gradients shown right later.
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		
		// Theme selection routine
        SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
        int Theme = 1;
		int Font = 1;
		int Size = 1;
		
		Theme = prefs.getInt("theme", Theme);
		Font = prefs.getInt("font", Font);
		Size = prefs.getInt("size", Size);
		
		// Selecting theme
		selectedTheme(Theme, this);
		
		// Selecting font
		selectedFont(Font, this);
		
		// Selecting font size
		selectedSize(Size, this);
	};
	
	/**
	 * On key down events.
	 */
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		// Back to main on 'menu' pressed
        if (keyCode == KeyEvent.KEYCODE_MENU) {
        	finish();
        };
		
        // Back to main on 'back' pressed
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	finish();
        };
        return true;
    }

    public void changeTheme(View v) {

        SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int Theme = 0;

        switch (v.getId()) {
            case (R.id.BtThemeGrey):
                Theme = 1;
                break;
            case (R.id.BtThemeBlue):
                Theme = 2;
                break;
            case (R.id.BtThemeDark):
                Theme = 3;
                break;
        }

        selectedTheme(Theme, this);

        editor.putInt("theme", Theme);
        editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
    }

    public void changeFont(View v) {

        SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int Font = 0;

        switch (v.getId()) {
            case (R.id.BtFontSans):
                Font = 1;
                break;
            case (R.id.BtFontSerif):
                Font = 2;
                break;
            case (R.id.BtFontMono):
                Font = 3;
                break;
        }

        selectedFont(Font, this);

        editor.putInt("font", Font);
        editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
    }

    public void changeSize(View v) {

        SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int Size = 0;

        switch (v.getId()) {
            case (R.id.BtSizeSmall):
                Size = 1;
                break;
            case (R.id.BtSizeMid):
                Size = 2;
                break;
            case (R.id.BtSizeBig):
                Size = 3;
                break;
        }

        selectedSize(Size, this);

        editor.putInt("size", Size);
        editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
    }

	/** Going to the AddBug page*/
    public void AddBug (View view) {
        goToUrl ( "https://github.com/wswld/dtype/issues/new");
    };

    /**
     * Clear text.
     */
    public void ClearText (View view) {
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	String dOutput = "";
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putString("doutput_preserved", dOutput);
        editor.commit(); // apply changes
        
        editor.putBoolean("cleared", true);
        editor.commit(); // apply changes
    }
    
    /**
     * goToUrl
     */
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void selectedTheme (int Theme, OptionsActivity optionsActivity) {
        final Button BtThemeGrey = (Button) findViewById(R.id.BtThemeGrey);
        final Button BtThemeBlue = (Button) findViewById(R.id.BtThemeBlue);
        final Button BtThemeDark = (Button) findViewById(R.id.BtThemeDark);
        BtThemeGrey.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
        BtThemeBlue.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button_layout));
        BtThemeDark.setBackgroundDrawable(getResources().getDrawable(R.drawable.dark_button_layout));

        switch(Theme) {
            case(1):
                BtThemeGrey.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
                break;
            case(2):
                BtThemeBlue.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button_selected_layout));
                break;
            case(3):
                BtThemeDark.setBackgroundDrawable(getResources().getDrawable(R.drawable.dark_button_selected_layout));
                break;
        }
    }

    public void selectedFont (int Font, OptionsActivity optionsActivity) {
        final Button BtFontSans = (Button) findViewById(R.id.BtFontSans);
        final Button BtFontSerif = (Button) findViewById(R.id.BtFontSerif);
        final Button BtFontMono = (Button) findViewById(R.id.BtFontMono);
        BtFontSans.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
        BtFontSerif.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
        BtFontMono.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));

        switch(Font) {
            case(1):
                BtFontSans.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
                break;
            case(2):
                BtFontSerif.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
                break;
            case(3):
                BtFontMono.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
                break;
        }
    }

    public void selectedSize (int Size, OptionsActivity optionsActivity) {
        final Button BtSizeSmall = (Button) findViewById(R.id.BtSizeSmall);
        final Button BtSizeMid = (Button) findViewById(R.id.BtSizeMid);
        final Button BtSizeBig = (Button) findViewById(R.id.BtSizeBig);
        BtSizeSmall.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
        BtSizeMid.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
        BtSizeBig.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));

        switch(Size) {
            case(1):
                BtSizeSmall.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
                break;
            case(2):
                BtSizeMid.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
                break;
            case(3):
                BtSizeBig.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
                break;
        }
    }

}