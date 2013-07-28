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
 * Options screen is represented by the <code>OptionsActivity</code>.
 * {@link #onCreate(android.os.Bundle) onCreate()} method is the generic
 * Android method and contains all the necessary runtime code. It also assigns
 * selected buttons on startup, retrieving the values from the database and applying them trough
 * {@link #selectedTheme(int Theme, OptionsActivity optionsActivity) selectedTheme()},
 * {@link #selectedFont(int Font, OptionsActivity optionsActivity) selectedFont()} and
 * {@link #selectedSize(int Size, OptionsActivity optionsActivity) selectedSize()} methods.
 * {@link #onKeyDown(int, android.view.KeyEvent) onKeyDown()} is also quite usual and it implements exiting the
 * screen when <code>Back</code> or <code>Menu</code> buttons are pressed.
 * <br /><br />
 * Visually, <code>OptionsActivity</code> contains eleven buttons:
 * <ol>
 *     <li><code>Clear</code> calls the {@link #ClearText(View view) ClearText()}.</li>
 *     <li><code>BtThemeGrey</code> calls the {@link #changeTheme(View v) changeTheme()} method.</li>
 *     <li><code>BtThemeBlue</code> calls the {@link #changeTheme(View v) changeTheme()} method.</li>
 *     <li><code>BtThemeDark</code> calls the {@link #changeTheme(View v) changeTheme()} method.</li>
 *     <li><code>BtFontSans</code> calls the {@link #changeFont(View v) changeFont()} method.</li>
 *     <li><code>BtFontSerif</code> calls the {@link #changeFont(View v) changeFont()} method.</li>
 *     <li><code>BtFontMono</code> calls the {@link #changeFont(View v) changeFont()} method.</li>
 *     <li><code>BtSizeSmall</code> calls the {@link #changeSize(View v) changeSize()} method.</li>
 *     <li><code>BtSizeMid</code> calls the {@link #changeSize(View v) changeSize()} method.</li>
 *     <li><code>BtSizeBig</code> calls the {@link #changeSize(View v) changeSize()} method.</li>
 *     <li><code>ReportBug</code> calls {@link #AddBug(View view) AddBug()} method.</li>
 * </ol>
 * Note that {@link #changeTheme(View v) changeTheme()}, {@link #changeFont(View v) changeFont()},
 * {@link #changeSize(View v) changeSize()} are working the same way only for different visual
 * style variables (i.e. Theme, Size, Font): they implement a switch for one of the three buttons
 * in the row pressed, and writes the corresponding value to the database. It also writes an
 * <code>opt_changed</code> parameter, that when revealed by
 * {@link net.wswld.dtype.writer.DTypeActivity} works as a signal, that the visual parameters
 * should be renewed, based on the database values. These methods also call the corresponding
 * {@link #selectedTheme(int Theme, OptionsActivity optionsActivity) selectedTheme()},
 * {@link #selectedFont(int Font, OptionsActivity optionsActivity) selectedFont()} or
 * {@link #selectedSize(int Size, OptionsActivity optionsActivity) selectedSize()} methods that change visual style of
 * the selected button. I know this is far from ideal, but for now it is the way it is.
 * <br /><br />
 * {@link #ClearText(View view) ClearText()} works in a somehow similar way: it writes a null string to the database
 * along with <code>cleared</code> boolean which then clears the output field in
 * {@link net.wswld.dtype.writer.DTypeActivity}.
 * <br /><br />
 * {@link #AddBug(View view) AddBug()} simply opens the URL of the page for adding a new bug in the default
 * browser. It calls the {@link #goToUrl(String) goToUrl()} method with the URL passed as a string.
 */
public class OptionsActivity extends DTypeActivity {
    /**
     * Generic Android <code>onCreate</code> method.
     * @param savedInstanceState
     */
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
     * This method predictably handles the hardware key events. More particularly it handles exiting
     * on <code>Menu</code> or <code>Back</code> buttons pressed.
     * @param keyCode key code.
     * @param event key event.
     * @return
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

    /**
     * This method changes the <code>int</code> value in the database, representing the color theme
     * of the {@link net.wswld.dtype.writer.DTypeActivity}.
     * @param v view.
     */
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

    /**
     * This method changes the <code>int</code> value in the database, representing the main font
     * of the {@link net.wswld.dtype.writer.DTypeActivity}.
     * @param v view.
     */
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

    /**
     * This method changes the <code>int</code> value in the database, representing the font size
     * of the {@link net.wswld.dtype.writer.DTypeActivity}.
     * @param v view.
     */
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

    /**
     * This methods opens the URL of the page for adding new bugs to the tracking system in the
     * default browser by calling {@link #goToUrl(String) goToUrl()} method.
     * @param view view.
     */
    public void AddBug (View view) {
        goToUrl ( "https://github.com/wswld/dtype/issues/new");
    };

    /**
     * Clears the <code>doutput_preserved</code> string in the database, therefore clearing the
     * <code>EditText</code> field in {@link DTypeActivity}. It also writes the <code>cleared</code>
     * boolean to notify the {@link DTypeActivity} of the changes.
     * @param view view.
     */
    public void ClearText(View view) {
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	String dOutput = "";
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putString("doutput_preserved", dOutput);
        editor.commit(); // apply changes
        
        editor.putBoolean("cleared", true);
        editor.commit(); // apply changes
    }

    /**
     * Launches the default browser and then opens the passed URL.
     * @param url the URL.
     */
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    /**
     * Reapplies normal visual styles to all the buttons in the theme row and then applies the
     * corresponding distinctive style to the selected button.
     * @param Theme theme integer.
     * @param optionsActivity activity.
     */
    public void selectedTheme(int Theme, OptionsActivity optionsActivity) {
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

    /**
     * Reapplies normal visual styles to all the buttons in the font row and then applies the
     * corresponding distinctive style to the selected button.
     * @param Font font integer.
     * @param optionsActivity activity.
     */
    public void selectedFont(int Font, OptionsActivity optionsActivity) {
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

    /**
     * Reapplies normal visual styles to all the buttons in the size row and then applies the
     * corresponding distinctive style to the selected button.
     * @param Size size integer.
     * @param optionsActivity activity.
     */
    public void selectedSize(int Size, OptionsActivity optionsActivity) {
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