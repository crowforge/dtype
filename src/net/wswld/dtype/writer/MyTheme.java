package net.wswld.dtype.writer;

import android.graphics.Typeface;

/**
 * MyTheme class.
 * @author vsevolod
 *
 */
public class MyTheme {
	public int background;
	public int alpha;
	public int color;
	public int colorShade;
	public Typeface font;
	public int size;
	/**
	 * Constructor.
	 * @param background
	 * @param alpha
	 * @param color
	 */
	
	public MyTheme(int background, int alpha, int color, int colorShade, Typeface font, int size) {
		this.background = background;
		this.alpha = alpha;
		this.color = color;
		this.colorShade = colorShade;
		this.font = font;
		this.size = size;
		
	}
}
