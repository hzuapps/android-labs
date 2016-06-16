package edu.hzuapps.androidworks.homeworks.com1314080901137;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import android.graphics.Color;
import android.util.Log;

import edu.hzuapps.androidworks.homeworks.com1314080901137.Graphics.PixmapFormat;
import edu.hzuapps.androidworks.homeworks.com1314080901137.Input.KeyEvent;
import edu.hzuapps.androidworks.homeworks.com1314080901137.Input.TouchEvent;

class TestScreen extends Screen {
    long startTime = System.nanoTime();
    int frames;
    Pixmap bob;
    Pixmap bobAlpha;
        
    public TestScreen(Game game) {
        super(game);    
        bob = game.getGraphics().newPixmap("bobrgb888.png", PixmapFormat.RGB565);
        bobAlpha = game.getGraphics().newPixmap("bobargb8888.png", PixmapFormat.ARGB4444);
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(game.getFileIO().readAsset("test.txt")));
            String text = in.readLine();
            in.close();
            
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(game.getFileIO().writeFile("test.txt")));
            out.write("This is a freaking test");
            out.close();
            
            in = new BufferedReader(new InputStreamReader(game.getFileIO().readFile("test.txt")));
            String text2 = in.readLine();
            in.close();
            
            Log.d("MrNom", text + ", " + text2 );
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        Input inp = game.getInput();
        g.clear(Color.RED);
        g.drawLine(0,0,320, 480, Color.BLUE);
        g.drawRect(20,20,100,100, Color.GREEN);
        g.drawPixmap(bob, 100, 100);
        g.drawPixmap(bobAlpha, 100, 200);
        g.drawPixmap(bob, 200, 200, 0, 0, 64, 64);
        for(int i=0; i < 2; i++) {
            if(inp.isTouchDown(i)) {
                g.drawPixmap(bob, inp.getTouchX(i), inp.getTouchY(i), 0, 0, 64, 64);
            }
        }
        
        g.drawPixmap(bob, (int)(inp.getAccelX() * 10) + 160 - 16, (int)(inp.getAccelY() * 10) + 240 - 16, 0, 0, 32, 32 );
        
        List<KeyEvent> keyEvents = inp.getKeyEvents();
        int len = keyEvents.size();
        for(int i = 0; i < len; i++) {
            Log.d("MrNom", keyEvents.get(i).toString());
        }
        
        List<TouchEvent> touchEvents = inp.getTouchEvents();
        len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Log.d("MrNom", touchEvents.get(i).toString());
        }
        
        frames++;
        if(System.nanoTime() - startTime > 1000000000l) {
            Log.d("MrNom", "fps: " + frames + ", delta: " + deltaTime);
            frames = 0;
            startTime = System.nanoTime();
        }
    }

    @Override
    public void pause() {
        Log.d("MrNom", "pause");                
    }

    @Override
    public void resume() {
        Log.d("MrNom", "resume");   
    }

    @Override
    public void dispose() {
        Log.d("MrNom", "dispose");
    }            
}
