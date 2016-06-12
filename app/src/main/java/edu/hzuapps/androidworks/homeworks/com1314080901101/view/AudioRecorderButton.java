package edu.hzuapps.androidworks.homework.com1314080901101.view;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import edu.hzuapps.androidworks.homework.com1314080901101.R;

public class AudioRecorderButton extends Button implements AudioManager.AudioStateListener {
    private static final int DISTANCE_Y_CANCEL = 50; //正常开发情况下,应该使用dp,然后将dp转换为px
    private static final int STATE_NORMAL = 1; //默认状态
    private static final int STATE_RECORDING = 2; //正在录音
    private static final int STATE_WANT_TO_CANCEL = 3; //希望取消

    private static final int MSG_AUDIO_PREPARED = 0X110;
    private static final int MSG_VOICE_CHANGED = 0X111;
    private static final int MSG_DIALOG_DIMISS = 0X112;

    private int mCurState = STATE_NORMAL; //当前的状态
    //已经开始录音
    private boolean isRecording = false; //是否已经开始录音
    private DialogManager mDialogManger;
    private AudioManager mAudioManger;

    private float mTime;
    //是否触发longClick方法
    private boolean mReady;

    public AudioRecorderButton(Context context) {
        this(context, null);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDialogManger = new DialogManager(getContext());
        //TODO 判断SD卡是否存在
        String dir = Environment.getExternalStorageDirectory() + "/zane_recorder_audios";
        mAudioManger = AudioManager.getmInstance(dir);
        mAudioManger.setOnAudioStateListener(this);

        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mReady = true;
                mAudioManger.prepareAudio();
                return false;
            }
        });
    }

    private AudioFinishRecorderListener mListener;

    /**
     * 录音完成后的回调
     */
    public interface AudioFinishRecorderListener {
        void onFinish(float seconds, String filePath);
    }

    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener) {
        this.mListener = listener;
    }

    /**
     * 获取音量大小的Runnable
     */
    private Runnable mGetVoiceLevelRunnable = new Runnable() {
        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
                    handler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    //显示应该在audio end prepared以后
                    mDialogManger.showRecordingDialog();
                    isRecording = true;

                    new Thread(mGetVoiceLevelRunnable).start();
                    break;
                case MSG_VOICE_CHANGED:
                    mDialogManger.updateVoiceLevel(mAudioManger.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DIMISS:
                    mDialogManger.dimissDialog();
                    break;
            }
        }
    };

    @Override
    public void wellPrepared() {
        handler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording) {
                    //根据x,y的坐标,判断是否取消
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_TO_CANCEL);
                    } else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!mReady) {
                    reset();
                    return super.onTouchEvent(event);
                }

                if (!isRecording || mTime < 0.6f) {
                    mDialogManger.tooShort();
                    mAudioManger.cancel();
                    handler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
                } else if (mCurState == STATE_RECORDING) { //正常录制的时候结束
                    mDialogManger.dimissDialog();
                    //release
                    mAudioManger.release();
                    //callbackToAct
                    if (mListener != null) {
                        mListener.onFinish(mTime, mAudioManger.getCurrentFilePath());
                    }
                } else if (mCurState == STATE_WANT_TO_CANCEL) {
                    mDialogManger.dimissDialog();
                    //cancel
                    mAudioManger.cancel();
                }
                reset();
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 恢复状态及标志位
     */
    private void reset() {
        isRecording = false;
        mReady = false;
        mTime = 0;
        changeState(STATE_NORMAL);
    }

    private boolean wantToCancel(int x, int y) {
        //超过按钮的宽度
        if (x < 0 || x > getWidth()) {
            return true;
        }
        //超过按钮的高度
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }
        return false;
    }

    /**
     * 改变Button的文本以及背景色
     *
     * @param state
     */
    private void changeState(int state) {
        if (mCurState != state) {
            mCurState = state;
            switch (state) {
                case STATE_NORMAL:
                    setBackgroundResource(R.drawable.com1314080901101_button_recorder_normal);
                    setText(R.string.str_recorder_normal);
                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.com1314080901101_button_recorder);
                    setText(R.string.str_recorder_recorder);
                    if (isRecording) {
                        mDialogManger.recording();
                    }
                    break;
                case STATE_WANT_TO_CANCEL:
                    setBackgroundResource(R.drawable.com1314080901101_button_recorder);
                    setText(R.string.str_recorder_want_cancel);
                    mDialogManger.wantToCancel();
                    break;
            }
        }
    }
}
