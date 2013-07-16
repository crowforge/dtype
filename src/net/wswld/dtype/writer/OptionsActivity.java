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
 * dType Options Screen (opt)
 * @author vsevolod
 * Yes, I know, that this code can be much more compact. I'working on that.
 */
public class OptionsActivity extends DTypeActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opt);
        
        /**
         * Enabling dithering for the whole window to ensure gradients shown right later.
         */
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		
		/** Theme selection routine */
        SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
        int Theme = 1;
		int Font = 1;
		int Size = 1;
		
		Theme = prefs.getInt("theme", Theme);
		Font = prefs.getInt("font", Font);
		Size = prefs.getInt("size", Size);
		
		/** Selecting theme */
		selectedTheme(Theme, this);
		
		/** Selecting font */
		selectedFont(Font, this);
		
		/** Selecting font size */
		selectedSize(Size, this);
	};
	
	/**
	 * On key down events.
	 */
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		/** Back to main on 'menu' pressed*/
        if (keyCode == KeyEvent.KEYCODE_MENU) {
        	finish();
        };
		
        /** Back to main on 'back' pressed*/
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
                SelectSizeSmall(this);
                break;
            case (R.id.BtSizeMid):
                Size = 2;
                SelectSizeMid(this);
                break;
            case (R.id.BtSizeBig):
                Size = 3;
                SelectSizeBig(this);
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

    /**
     * Implementation of selected state on theme buttons. 
     * @param optionsActivity
     */
	public void SelectSizeSmall (OptionsActivity optionsActivity) {
		final Button BtSizeSmall = (Button) findViewById(R.id.BtSizeSmall);
		final Button BtSizeMid = (Button) findViewById(R.id.BtSizeMid);
		final Button BtSizeBig = (Button) findViewById(R.id.BtSizeBig);
		BtSizeSmall.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
		BtSizeMid.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
		BtSizeBig.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
	}
	
    /**
     * Implementation of selected state on theme buttons. 
     * @param optionsActivity
     */
	public void SelectSizeMid (OptionsActivity optionsActivity) {
		final Button BtSizeSmall = (Button) findViewById(R.id.BtSizeSmall);
		final Button BtSizeMid = (Button) findViewById(R.id.BtSizeMid);
		final Button BtSizeBig = (Button) findViewById(R.id.BtSizeBig);
		BtSizeSmall.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
		BtSizeMid.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
		BtSizeBig.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
	}

    /**
     * Implementation of selected state on theme buttons. 
     * @param optionsActivity
     */
	public void SelectSizeBig (OptionsActivity optionsActivity) {
		final Button BtSizeSmall = (Button) findViewById(R.id.BtSizeSmall);
		final Button BtSizeMid = (Button) findViewById(R.id.BtSizeMid);
		final Button BtSizeBig = (Button) findViewById(R.id.BtSizeBig);
		BtSizeSmall.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
		BtSizeMid.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
		BtSizeBig.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
	}
}