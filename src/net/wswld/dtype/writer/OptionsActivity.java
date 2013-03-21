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
		switch(Theme){
		case 1:
			SelectThemeGrey (this);
		break;
		case 2:
			SelectThemeBlue (this);		
		break;
		case 3:
			SelectThemeDark (this);
		break;
		}
		
		/** Selecting font */
		switch(Font){
		case 1:
			SelectFontSans (this);
		break;
		case 2:
			SelectFontSerif (this);		
		break;
		case 3:
			SelectFontMono (this);
		break;
		}
		
		/** Selecting font size */
		switch(Size){
		case 1:
			SelectSizeSmall (this);
		break;
		case 2:
			SelectSizeMid (this);		
		break;
		case 3:
			SelectSizeBig (this);
		break;
		}
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
    
	/** Going to the AddBug page*/
    public void AddBug (View view) {
        goToUrl ( "https://bitbucket.org/wswld/dtype/issues/new");
    };
    
    /**
     * Change theme to grey.
     */
    public void ChThemeGrey (View view) {
    	int Theme = 1;
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("theme", Theme);
        editor.commit(); // apply changes
        
		editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
        SelectThemeGrey (this);
    }
    
    /**
     * Change theme to blue.
     */
    public void ChThemeBlue (View view) {
    	int Theme = 2;
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("theme", Theme);
        editor.commit(); // apply changes
        
		editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
        SelectThemeBlue (this);
    }
    
    /**
     * Change theme to dark.
     */
    public void ChThemeDark (View view) {
    	int Theme = 3;
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("theme", Theme);
        editor.commit(); // apply changes
        
		editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
        SelectThemeDark (this);
    }
    
    /**
     * Change font to sans-serif.
     */
    public void ChFontSans (View view) {
    	int Font = 1;
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("font", Font);
        editor.commit(); // apply changes
        
		editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
        SelectFontSans (this);
    }
    
    /**
     * Change font to serif.
     */
    public void ChFontSerif (View view) {
    	int Font = 2;
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("font", Font);
        editor.commit(); // apply changes
        
		editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
        SelectFontSerif (this);
    }
    
    /**
     * Change font to mono.
     */
    public void ChFontMono (View view) {
    	int Font = 3;
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("font", Font);
        editor.commit(); // apply changes
        
		editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
        SelectFontMono (this);
    }
    
    /**
     * Change font size to big.
     */
    public void ChSizeSmall (View view) {
    	int Size = 1;
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("size", Size);
        editor.commit(); // apply changes
        
		editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
        SelectSizeSmall (this);
    }

    /**
     * Change font size to medium.
     */
    public void ChSizeMid (View view) {
    	int Size = 2;
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("size", Size);
        editor.commit(); // apply changes
        
		editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
        SelectSizeMid (this);
    }
    
    /**
     * Change font size to small.
     */
    public void ChSizeBig (View view) {
    	int Size = 3;
    	SharedPreferences prefs = getSharedPreferences("com.dtype.writer", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("size", Size);
        editor.commit(); // apply changes
        
		editor.putBoolean("opt_changed", true);
        editor.commit(); // apply changes
        SelectSizeBig (this);
    }
    
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
    
    /**
     * Implementation of selected state on theme buttons. 
     * @param optionsActivity
     */
	public void SelectThemeGrey (OptionsActivity optionsActivity) {
		final Button BtThemeGrey = (Button) findViewById(R.id.BtThemeGrey);
		final Button BtThemeBlue = (Button) findViewById(R.id.BtThemeBlue);
		final Button BtThemeDark = (Button) findViewById(R.id.BtThemeDark);
		BtThemeGrey.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
		BtThemeBlue.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button_layout));
		BtThemeDark.setBackgroundDrawable(getResources().getDrawable(R.drawable.dark_button_layout));
	}

    /**
     * Implementation of selected state on theme buttons. 
     * @param optionsActivity
     */
	public void SelectThemeBlue (OptionsActivity optionsActivity) {
		final Button BtThemeGrey = (Button) findViewById(R.id.BtThemeGrey);
		final Button BtThemeBlue = (Button) findViewById(R.id.BtThemeBlue);
		final Button BtThemeDark = (Button) findViewById(R.id.BtThemeDark);
		BtThemeGrey.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
		BtThemeBlue.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button_selected_layout));
		BtThemeDark.setBackgroundDrawable(getResources().getDrawable(R.drawable.dark_button_layout));
	}

    /**
     * Implementation of selected state on theme buttons. 
     * @param optionsActivity
     */
	public void SelectThemeDark (OptionsActivity optionsActivity) {
		final Button BtThemeGrey = (Button) findViewById(R.id.BtThemeGrey);
		final Button BtThemeBlue = (Button) findViewById(R.id.BtThemeBlue);
		final Button BtThemeDark = (Button) findViewById(R.id.BtThemeDark);
		BtThemeGrey.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
		BtThemeBlue.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_button_layout));
		BtThemeDark.setBackgroundDrawable(getResources().getDrawable(R.drawable.dark_button_selected_layout));
	}
	
    /**
     * Implementation of selected state on theme buttons. 
     * @param optionsActivity
     */
	public void SelectFontSans (OptionsActivity optionsActivity) {
		final Button BtFontSans = (Button) findViewById(R.id.BtFontSans);
		final Button BtFontSerif = (Button) findViewById(R.id.BtFontSerif);
		final Button BtFontMono = (Button) findViewById(R.id.BtFontMono);
		BtFontSans.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
		BtFontSerif.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
		BtFontMono.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
	}
	
    /**
     * Implementation of selected state on theme buttons. 
     * @param optionsActivity
     */
	public void SelectFontSerif (OptionsActivity optionsActivity) {
		final Button BtFontSans = (Button) findViewById(R.id.BtFontSans);
		final Button BtFontSerif = (Button) findViewById(R.id.BtFontSerif);
		final Button BtFontMono = (Button) findViewById(R.id.BtFontMono);
		BtFontSans.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
		BtFontSerif.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
		BtFontMono.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
	}

    /**
     * Implementation of selected state on theme buttons. 
     * @param optionsActivity
     */
	public void SelectFontMono (OptionsActivity optionsActivity) {
		final Button BtFontSans = (Button) findViewById(R.id.BtFontSans);
		final Button BtFontSerif = (Button) findViewById(R.id.BtFontSerif);
		final Button BtFontMono = (Button) findViewById(R.id.BtFontMono);
		BtFontSans.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
		BtFontSerif.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_layout));
		BtFontMono.setBackgroundDrawable(getResources().getDrawable(R.drawable.grey_button_selected_layout));
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