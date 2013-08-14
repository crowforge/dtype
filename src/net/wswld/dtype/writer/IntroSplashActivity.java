package net.wswld.dtype.writer;

import android.os.Bundle;
import android.os.Handler;

/**
 * <h1>dType Splash</h1>
 * This is the splash activity. It is called on the very first launch from
 * the {@link DTypeActivity#DTypeActivity DTypeActivity} class which is
 * always the first to load. It then produces a short delay to show off 
 * some basic application info and passes the flow of control back to the  
 * {@link DTypeActivity#DTypeActivity DTypeActivity} activity.
 */
public class IntroSplashActivity extends DTypeActivity {
	/**
	 * Simple and pretty common method for all Android activity classes.
	 * Overridden <code>Handler().postDelayed()</code> is the mechanism that
	 * enables the short delay before passing the flow of control to the main
	 * activity. Note how the delay time now equals <code>5000</code>.
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
		
        new Handler().postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 20000);
        
    }
	
}
