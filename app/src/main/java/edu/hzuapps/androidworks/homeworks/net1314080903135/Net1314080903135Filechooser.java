package edu.hzuapps.androidworks.homeworks.Net1314080903135;


import java.io.File;
import java.util.ArrayList;
import edu.hzuapps.androidworks.homeworks.Net1314080903135.Net1314080903135Fileadapter.FileInfo;
import edu.hzuapps.androidworks.homeworks.Net1314080903135.Net1314080903135Activitymain;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.widget.TextView;
import android.support.v7.appcompat.*;
import android.os.Bundle;

public class Net1314080903135Filechooser extends Activity {

    private GridView mGridView;
    private View mBackView;
    private View mBtExit;
    private TextView mTvPath;

    private String mSdcardRootPath;  //sdcard 根路径
    private String mLastFilePath;    //当前显示的路径

    private ArrayList<FileInfo> mFileLists;
    private Net1314080903135Fileadapter mAdatper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_net1314080903135_filechooser);

        mSdcardRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        mBackView = findViewById(R.id.imgBackFolder);
        mBackView.setOnClickListener(mClickListener);
        mBtExit = findViewById(R.id.btExit);
        mBtExit.setOnClickListener(mClickListener);

        mTvPath = (TextView) findViewById(R.id.tvPath);

        mGridView = (GridView) findViewById(R.id.gvFileChooser);
        mGridView.setEmptyView(findViewById(R.id.tvEmptyHint));
        mGridView.setOnItemClickListener(mItemClickListener);
        mGridView.setOnItemLongClickListener(mlongclicklistener);
        setGridViewAdapter(mSdcardRootPath);
    }

    //配置适配器
    private void setGridViewAdapter(String filePath) {
        updateFileItems(filePath);
        mAdatper = new Net1314080903135Fileadapter(this, mFileLists);
        mGridView.setAdapter(mAdatper);
    }

    //根据路径更新数据
    private void updateFileItems(String filePath) {
        mLastFilePath = filePath;
        mTvPath.setText(mLastFilePath);

        if (mFileLists == null)
            mFileLists = new ArrayList<FileInfo>();
        if (!mFileLists.isEmpty())
            mFileLists.clear();

        File[] files = folderScan(filePath);
        if (files == null)
            return;

        for (int i = 0; i < files.length; i++) {
            if (files[i].isHidden())
                continue;

            String fileAbsolutePath = files[i].getAbsolutePath();
            String fileName = files[i].getName();
            boolean isDirectory = false;
            if (files[i].isDirectory()) {
                isDirectory = true;
            }
            FileInfo fileInfo = new FileInfo(fileAbsolutePath, fileName, isDirectory);
            mFileLists.add(fileInfo);
        }
        //When first enter , the object of mAdatper don't initialized
        if (mAdatper != null)
            mAdatper.notifyDataSetChanged();
    }

    //获得当前路径的所有文件
    private File[] folderScan(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        return files;
    }

    private View.OnClickListener mClickListener = new OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgBackFolder:
                    backProcess();
                    break;
                case R.id.btExit:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    //gridview点击事件
    private AdapterView.OnItemClickListener mItemClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                long id) {
            FileInfo fileInfo = (FileInfo) (((Net1314080903135Fileadapter) adapterView.getAdapter()).getItem(position));
            if (fileInfo.isDirectory())
                updateFileItems(fileInfo.getFilePath());
            else if (fileInfo.isPPTFile()) {
                Intent intent = new Intent();
                intent.putExtra(Net1314080903135Activitymain.EXTRA_FILE_CHOOSER, "File" + fileInfo.getFilePath());
                setResult(RESULT_OK, intent);
                finish();
            } else {
                toast(getText(R.string.open_file_error_format));
            }
        }
    };

    //gridview长按时间
    private AdapterView.OnItemLongClickListener mlongclicklistener = new OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            // TODO Auto-generated method stub

            FileInfo fileInfo = (FileInfo) (((Net1314080903135Fileadapter) parent.getAdapter()).getItem(position));
            if (fileInfo.isDirectory()) {
                Intent intent = new Intent();
                intent.putExtra(Net1314080903135Activitymain.EXTRA_FILE_CHOOSER, "Dir" + fileInfo.getFilePath());
                setResult(RESULT_OK, intent);
                finish();
            }
            return false;

        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode()
                == KeyEvent.KEYCODE_BACK) {
            backProcess();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回上一层目录的操作
    public void backProcess() {
        if (!mLastFilePath.equals(mSdcardRootPath)) {
            File thisFile = new File(mLastFilePath);
            String parentFilePath = thisFile.getParent();
            updateFileItems(parentFilePath);
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private void toast(CharSequence hint) {
        Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
    }
}