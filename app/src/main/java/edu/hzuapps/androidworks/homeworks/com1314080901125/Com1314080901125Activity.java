package edu.hzuapp.androidworks.homeworks.com1314080901125;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Com1314080901125Activity extends AppCompatActivity implements View.OnClickListener{

    private File currentDirFile;
    private File[] fileList;
    private ListView myListView;
    private TextView showMyDir;
    private List<Map<String, Object>> CurrentFiles;
    private static String targetDir = "/"; //启动默认进入根目录
    private Button btn1,btn2,btn_quit;
    private ImageButton btn_back;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901125);
        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn_quit =(Button)findViewById(R.id.button_quit);
        btn_back = (ImageButton)findViewById(R.id.back);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn_quit.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        showMyDir = (TextView)findViewById(R.id.MyDir);
        showMyDir.setText(targetDir);
        myListView = (ListView)findViewById(R.id.listView);
        myListView.setAdapter(new myListAdapter(this));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((int)CurrentFiles.get(position).get("icon")==R.drawable.ic_directory){
                    targetDir = (String)CurrentFiles.get(position).get("fileDir");
                    showMyDir.setText(targetDir);
                    myListView.setAdapter(new myListAdapter(getApplicationContext()));
                }
                else{
                    if ((int)CurrentFiles.get(position).get("icon")==R.drawable.ic_file)
                    showTipBox("这是一个文件");
                }
            }
        });
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //长按监听
                if ((int)CurrentFiles.get(position).get("icon")==R.drawable.ic_file){
                    //弹出选项框
                    final String[] items = getResources().getStringArray(R.array.items);
                    showDeleteBox(items);
                }
                return true;
            }
        });
    }

    private class ListItem {
        public ImageView icon;
        public TextView fileName;
        public TextView fileDir;
    }

    private List<Map<String, Object>> GetCurrentFiles(String targetDir){
        if (targetDir.equals("/")){
            currentDirFile=new File("/");
            fileList=currentDirFile.listFiles();
        }
        else if (targetDir.equals("/storage")){
            boolean existed= Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
            if(existed){
                //currentDirFile = Environment.getExternalStorageDirectory();//这个现在仅获取得到手机自带存储卡
                currentDirFile = new File("/storage");
                fileList = currentDirFile.listFiles();
            }
            else
                showTipBox("SD卡不存在");
        }
        else{
            currentDirFile=new File(targetDir);
            fileList=currentDirFile.listFiles();
        }

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        if (!targetDir.equals("/")){
                map = new HashMap<>();
                map.put("fileName","back to ../");
                map.put("fileDir",currentDirFile.getParent());
                map.put("icon",R.drawable.ic_directory);
                list.add(map);
        }
        if(fileList!=null){
            for (int i = 0; i < fileList.length; i++) {
                map = new HashMap<String, Object>();
                map.put("fileName", fileList[i].getName());
                map.put("fileDir",fileList[i].getPath());
                if (fileList[i].isDirectory())
                    map.put("icon", R.drawable.ic_directory);
                else
                    map.put("icon", R.drawable.ic_file);
                list.add(map);
            }
        }
        return list;
    }

    private class myListAdapter extends BaseAdapter {
        private Context myContext;
        private LayoutInflater inflater;
        public myListAdapter(Context myContext) {
            this.myContext = myContext;
            inflater = LayoutInflater.from(myContext);
        }

        @Override
        public int getCount() {
            return GetCurrentFiles(targetDir).size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItem itemHolder = null;
            CurrentFiles = GetCurrentFiles(targetDir);
            if(convertView==null){
                itemHolder=new ListItem();
                convertView = inflater.inflate(R.layout.list_item_view, null);
                itemHolder.icon = (ImageView) convertView.findViewById(R.id.imageView);
                itemHolder.fileName = (TextView) convertView.findViewById(R.id.fileName);
                itemHolder.fileDir = (TextView) convertView.findViewById(R.id.fileDir);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ListItem) convertView.getTag();
            }
            itemHolder.icon.setBackgroundResource((Integer) CurrentFiles.get(position).get("icon"));
            itemHolder.fileName.setText((String) CurrentFiles.get(position).get("fileName"));
            itemHolder.fileDir.setText((String) CurrentFiles.get(position).get("fileDir"));

            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                toast = Toast.makeText(this,"进入根目录",Toast.LENGTH_SHORT);
                toast.show();
                targetDir = "/";
                myListView.setAdapter(new myListAdapter(this));
                showMyDir.setText(targetDir);//同时更新当前路径的显示
                break;
            case R.id.button2:
                toast = Toast.makeText(this,"进入SD卡",Toast.LENGTH_SHORT);
                toast.show();
                targetDir = "/storage";
                myListView.setAdapter(new myListAdapter(this));
                showMyDir.setText(targetDir);
                break;
            case R.id.button_quit:
                showQuitBox("确定退出应用？");
                break;
            case R.id.back:
                if (!targetDir.equals("/")) {
                    //更新返回的目录列表
                    File file = new File(targetDir);
                    targetDir = file.getParent();
                    myListView.setAdapter(new myListAdapter(this));
                    showMyDir.setText(targetDir);
                }
                else {
                    showQuitBox("已是最顶层目录，是否退出管理器？");
                }
        }
    }

    public void showTipBox(String tip) {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(tip)
                .setPositiveButton("OK", null)
                .show();
    }

    public void showQuitBox(String tip){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(tip)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void showDeleteBox(final String[] items){
        new AlertDialog.Builder(this)
                .setTitle("选项")
                .setItems(items,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (items[which]){
                            case "删除文件":
                                dialog.dismiss();
                                new AlertDialog.Builder(Com1314080901125Activity.this)
                                        .setTitle("提示")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                File f = new File(targetDir);
                                                if (f.exists()) {
                                                    System.out.println("文件存在");
                                                    if (f.delete())   //删除不了，仍在找原因
                                                        System.out.println("删除文件成功");
                                                    else System.out.println("删除失败");
                                                }
                                            }
                                        })
                                        .setNegativeButton("取消",null)
                                        .show();
                                break;
                            //case 2
                        }
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }
}
