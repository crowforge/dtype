package net.wswld.dtype.writer;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

/**
 * dType Splash
 * @author vsevolod
 *
 */
public class IntroSplashActivity extends DTypeActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        /**
         * Enabling dithering for the whole window to ensure gradients shown right later.
         */
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		
		/** Running the splash for 5000 (ms?)*/
        new Handler().postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 5000);
        
    }
	
}
