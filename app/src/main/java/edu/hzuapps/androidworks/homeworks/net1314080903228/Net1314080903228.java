package edu.hzuapps.androidworks.homeworks.net1314080903228;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;
import android.view.MotionEvent;
import android.media.MediaPlayer;

import java.io.IOException;


public class Net1314080903228 extends Activity {

    private ImageButton imageButton_white1;
    private ImageButton imageButton_white2;
    private ImageButton imageButton_white3;
    private ImageButton imageButton_white4;
    private ImageButton imageButton_white5;
    private ImageButton imageButton_white6;
    private ImageButton imageButton_white7;
    private ImageButton imageButton_white8;

    private ImageButton imageButton_black1;
    private ImageButton imageButton_black2;
    private ImageButton imageButton_black3;
    private ImageButton imageButton_black4;
    private ImageButton imageButton_black5;
    private MediaPlayer mediaPlayer01;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.net1314080903228);

        mediaPlayer01 = new MediaPlayer();
//        mediaPlayer01 = MediaPlayer.create(Net1314080903228.this, R.raw.net1314080903228white1);

        imageButton_white1 = (ImageButton) findViewById(R.id.white1);
        imageButton_white2 = (ImageButton) findViewById(R.id.white2);
        imageButton_white3 = (ImageButton) findViewById(R.id.white3);
        imageButton_white4 = (ImageButton) findViewById(R.id.white4);
        imageButton_white5 = (ImageButton) findViewById(R.id.white5);
        imageButton_white6 = (ImageButton) findViewById(R.id.white6);
        imageButton_white7 = (ImageButton) findViewById(R.id.white7);
        imageButton_white8 = (ImageButton) findViewById(R.id.white8);

        imageButton_black1 = (ImageButton) findViewById(R.id.black1);
        imageButton_black2 = (ImageButton) findViewById(R.id.black2);
        imageButton_black3 = (ImageButton) findViewById(R.id.black3);
        imageButton_black4 = (ImageButton) findViewById(R.id.black4);
        imageButton_black5 = (ImageButton) findViewById(R.id.black5);

        imageButton_white1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228white1);
                    imageButton_white1.setImageResource(R.drawable.net1314080903228whiteback1);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_white1.setImageResource(R.drawable.net1314080903228white1);
                }
                return false;
            }
        });


        imageButton_white2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    play(R.raw.net1314080903228white2);
                    imageButton_white2.setImageResource(R.drawable.net1314080903228whiteback2);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_white2.setImageResource(R.drawable.net1314080903228white2);
                }
                return false;
            }
        });
//
        imageButton_white3.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228white3);
                    imageButton_white3.setImageResource(R.drawable.net1314080903228whiteback3);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_white3.setImageResource(R.drawable.net1314080903228white3);
                }
                return false;
            }
        });

        imageButton_white4.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228white4);
                    imageButton_white4.setImageResource(R.drawable.net1314080903228whiteback4);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_white4.setImageResource(R.drawable.net1314080903228white4);
                }
                return false;
            }
        });

        imageButton_white5.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228white5);
                    imageButton_white5.setImageResource(R.drawable.net1314080903228whiteback5);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_white5.setImageResource(R.drawable.net1314080903228white5);
                }
                return false;
            }
        });

        imageButton_white6.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228white6);
                    imageButton_white6.setImageResource(R.drawable.net1314080903228whiteback6);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_white6.setImageResource(R.drawable.net1314080903228white6);
                }
                return false;
            }
        });

        imageButton_white7.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228white7);
                    imageButton_white7.setImageResource(R.drawable.net1314080903228whiteback7);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_white7.setImageResource(R.drawable.net1314080903228white7);
                }
                return false;
            }
        });

        imageButton_white8.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228white8);
                    imageButton_white8.setImageResource(R.drawable.net1314080903228whiteback8);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_white8.setImageResource(R.drawable.net1314080903228white8);
                }
                return false;
            }
        });

        imageButton_black1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228black1);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_black1.setImageResource(R.drawable.net1314080903228black1);
                }
                return false;
            }
        });

        imageButton_black2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228black2);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_black2.setImageResource(R.drawable.net1314080903228black2);
                }
                return false;
            }
        });

        imageButton_black3.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228black3);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_black3.setImageResource(R.drawable.net1314080903228black3);
                }
                return false;
            }
        });

        imageButton_black4.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228black4);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_black4.setImageResource(R.drawable.net1314080903228black4);
                }
                return false;
            }
        });

        imageButton_black5.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    play(R.raw.net1314080903228black5);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    imageButton_black5.setImageResource(R.drawable.net1314080903228black5);
                }
                return false;
            }
        });
//
//
        mediaPlayer01.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer arg0) {
                mediaPlayer01.release();
                mediaPlayer01 = null;
                Toast.makeText(Net1314080903228.this, "资源释放了!", Toast.LENGTH_SHORT).show();
            }
        });

        mediaPlayer01.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer arg0, int i, int i1) {
                try {
                    mediaPlayer01.release();
                    Toast.makeText(Net1314080903228.this, "发生错误了!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });


    }

    //-------------------------------------------------------------------------------------
    private void play(int resource) {
        try {

            mediaPlayer01.release();
            mediaPlayer01 = MediaPlayer.create(Net1314080903228.this, resource);
            mediaPlayer01.start();
        } catch (Exception e) {
            Toast.makeText(Net1314080903228.this, "发生错误了:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer01 != null) {
            mediaPlayer01.release();
            mediaPlayer01 = null;
        }

    }

}
