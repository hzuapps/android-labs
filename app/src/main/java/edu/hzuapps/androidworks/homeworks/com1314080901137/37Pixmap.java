package edu.hzuapps.androidworks.homeworks.com1314080901137;

import edu.hzuapps.androidworks.homeworks.com1314080901137.Graphics.PixmapFormat;

public interface Pixmap {
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
