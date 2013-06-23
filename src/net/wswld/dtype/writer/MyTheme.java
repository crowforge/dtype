package net.wswld.dtype.writer;

import android.graphics.Typeface;

/**
 * <h1>MyTheme Class</h1>
 * MyTheme was designed with the purpose of aggregating all the main parameters of the theme within one object.
 * <br /><br />
 * It is based on the idea, that instead on having separate methods for applying each visual parameter for
 * {@link DTypeActivity#DTypeActivity DTypeActivity}, we could construct an object with this exact set of
 * parameters and then pass it around. {@link DTypeActivity#assembleTheme(int, int, int) assembleTheme}
 * method is the method used for theme assembly (i.e. constructing a theme object with the previously
 * reterieved set of parameters), then the instance of the <code>MyTheme</code> class is passed to the
 * {@link DTypeActivity#setTheme(DTypeActivity, MyTheme) setTheme} where the theme values are applied to
 * the actual layout parameters.
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
	 * @param background background color.
	 * @param alpha alpha value.
	 * @param color text color.
     * @param colorShade darker variant of text color.
     * @param font text typeface.
     * @param size text size.
     *
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
