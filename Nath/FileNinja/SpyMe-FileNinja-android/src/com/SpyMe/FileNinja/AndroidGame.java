package com.SpyMe.FileNinja;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Starter classe pour android.
 * <p>
 * D�sactive le mat�riel non n�cessaire et co�teux en batterie.
 * @author Nathanael
 * @version 0.1
 * 
 * 
 */
public class AndroidGame extends AndroidApplication {
	@Override
    public void onCreate (android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;

        initialize(new FileNinja(), cfg);
    }
}