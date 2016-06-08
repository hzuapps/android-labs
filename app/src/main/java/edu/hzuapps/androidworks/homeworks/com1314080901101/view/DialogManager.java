package edu.hzuapps.androidworks.homework.com1314080901101.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.hzuapps.androidworks.homework.com1314080901101.R;

public class DialogManager {
    private Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLable;

    private Context mContext;

    public DialogManager(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 显示录音的对话框
     */
    public void showRecordingDialog() {
        mDialog = new Dialog(mContext, R.style.Theme_AudioDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.com1314080901101_dialog_recorder, null);
        mDialog.setContentView(view);

        mIcon = (ImageView) mDialog.findViewById(R.id.recorder_dialog_icon);
        mVoice = (ImageView) mDialog.findViewById(R.id.recorder_dialog_voice);
        mLable = (TextView) mDialog.findViewById(R.id.tv_recorder_dialog_label);

        mDialog.show();
    }

    public void recording() {
        if (mDialog != null && mDialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.com1314080901101_recorder);
            mLable.setText("手指上滑,取消发送");
        }
    }

    /**
     * 显示想取消的对话框
     */
    public void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.com1314080901101_cancel);
            mLable.setText("松开手指,取消发送");
        }
    }

    /**
     * 显示时间过短的对话框
     */
    public void tooShort() {
        if (mDialog != null && mDialog.isShowing()) { //显示状态
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLable.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.com1314080901101_voice_to_short);
            mLable.setText("录音时间过短");
        }
    }

    /**
     * 显示取消的对话框
     */
    public void dimissDialog() {
        if (mDialog != null && mDialog.isShowing()) { //显示状态
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 显示更新音量级别的对话框
     *
     * @param level 1-7
     */
    public void updateVoiceLevel(int level) {
        if (mDialog != null && mDialog.isShowing()) {
         /* mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLable.setVisibility(View.VISIBLE);*/

            int resId = mContext.getResources().getIdentifier("v" + level, "drawable", mContext.getPackageName());
            mVoice.setImageResource(resId);
        }
    }
}
