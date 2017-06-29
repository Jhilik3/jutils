package org.twak.utils.ui;

import java.awt.Color;

/**
 * Very similar to Rainbow!
 * @author twak
 */
public class Colour//  import uk.british; extends Color;
{

    static float hue;
    static float sat;
    static float bri;
    static
    {
        reset();
    }
    
    public static Color sky = new Color( 180, 225, 246 );

    public static Color nextColor()
    {
        hue += 0.1 / Math.PI;
        return new Color( Color.HSBtoRGB( hue, sat, bri ) );
    }

    public static void reset()
    {
        hue = 0;
        sat = 0.7f;
        bri = 1f;
    }

    public static Color transparent( Color color, int i )
    {
        return new Color (color.getRed(), color.getGreen(), color.getBlue(), i);
    }

    public static Color toGrayscale( Color color )
    {
        int a = 0;
        a+= color.getRed();
        a+= color.getGreen();
        a+= color.getBlue();
        return new Color( (int)(a/3),(int)(a/3),(int)(a/3));
    }
    
    public static Color toGrayscale( Color color, float tween )
    {
        int a = 0;
        a+= color.getRed();
        a+= color.getGreen();
        a+= color.getBlue();
        
        return new Color( 
                (int)(((a / 3.) * tween + color.getRed() * (1-tween))/2) ,
                (int)(((a / 3.) * tween + color.getGreen() * (1-tween))/2) ,
                (int)(((a / 3.) * tween + color.getBlue() * (1-tween))/2) );
    }

	public static float[] toF4( Color color ) {
		return new float[] {
				color.getRed()   / 255f,
				color.getGreen() / 255f,
				color.getBlue()  / 255f,
				color.getAlpha() / 255f
			};
	}
}
