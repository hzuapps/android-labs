package edu.hzuapps.androidworks.homeworks.net1314080903118;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidwork.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Net1314080903118Activity extends AppCompatActivity {
    //获取阅读API
    private static final String URL_ARITICLE = "http://v3.wufazhuce.com:8000/api/reading/index/0?";
    //获取轮播图片API
    private static final String URL_PHTOT = "http://v3.wufazhuce.com:8000/api/reading/carousel/?";
    private List<Net1314080903118ArticleEntity> mDataList = null;
    private ListView listView;
    private Context mContext;
    private Net1314080903118ListAdapter adapter;


    private ViewPager adViewPager;
    private List<ImageView> imageViews;// 滑动的图片集合
    private List<View> dots; // 图片标题正文的那些点
    private List<View> dotList;

    private int currentItem = 0; // 当前图片的索引号
    // 定义的五个指示点
    private View dot0,dot1,dot2,dot3,dot4,dot5,dot6,dot7,dot8;

    // 定时任务
    private ScheduledExecutorService scheduledExecutorService;

    // 轮播banner的数据
    private List<Net1314080903118PhotoEntity> adList = null;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            adViewPager.setCurrentItem(currentItem);
        };
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net1314080903118);
        setTitle("Net1314080903118");
        mContext = Net1314080903118Activity.this;
        initView();
        getArticleRequest();
        getPhotoRequest();
        //loadViewPager();
        startAd();
    }


    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        imageViews = new ArrayList<ImageView>();
        // 点
        dots = new ArrayList<View>();
        dotList = new ArrayList<View>();
        dot0 = findViewById(R.id.v_dot0);
        dot1 = findViewById(R.id.v_dot1);
        dot2 = findViewById(R.id.v_dot2);
        dot3 = findViewById(R.id.v_dot3);
        dot4 = findViewById(R.id.v_dot4);
        dot5 = findViewById(R.id.v_dot5);
        dot6 = findViewById(R.id.v_dot6);
        dot7 = findViewById(R.id.v_dot7);
        dot8 = findViewById(R.id.v_dot8);
        dots.add(dot0);
        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);
        dots.add(dot5);
        dots.add(dot6);
        dots.add(dot7);
        dots.add(dot8);

        adViewPager = (ViewPager) findViewById(R.id.vp);
    }

    private void addDynamicView() {
        // 动态添加图片和下面指示的圆点
        // 初始化图片资源
        for (int i = 0; i < adList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            // 异步加载图片
            //Picasso.with(getContext()).load(adList.get(i).getCover()).into(imageView);
            new Net1314080903118MyRequest(mContext).getImage(adList.get(i).getCover(),imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
            dots.get(i).setVisibility(View.VISIBLE);
            dotList.add(dots.get(i));
        }

    }


    /**
     * 定时任务，5s更换一次
     */
    private void startAd() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5,
                TimeUnit.SECONDS);
    }


    /**
     * 自定义线程，滑动时通知handle响应
     */
    private class ScrollTask implements Runnable {

        @Override
        public void run() {
            synchronized (adViewPager) {
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // 当Activity不可见的时候停止切换
        scheduledExecutorService.shutdown();
    }

    /**
     * 重写ViewPager，改变圆点是否选中状态
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        private int oldPosition = 0;

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            Net1314080903118PhotoEntity entity = adList.get(position);

            dots.get(oldPosition).setBackgroundResource(R.drawable.net1314080903118_dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.net1314080903118_dot_press);
            oldPosition = position;
        }
    }


    /**
     * 请求轮播图片数据
     */
    private void getPhotoRequest() {
        new Net1314080903118MyRequest(mContext).getRequest(URL_PHTOT, new Net1314080903118MyRequest.HttpListener() {
            @Override
            public void onSuccess(String result) {
                //Log.i("PhotoData",result);
                adList = parse2PhotoJson(result);
                loadViewPager();
            }

            @Override
            public void onError(String errorResult) {
                Log.i("photoRequest", errorResult);
            }
        });

    }


    /**
     * 解析轮播图片数据
     * @param result
     */
    private List<Net1314080903118PhotoEntity> parse2PhotoJson(String result) {
        List<Net1314080903118PhotoEntity> entitiyList = null;
        Net1314080903118PhotoEntity guideEntity = null;
        try {
            entitiyList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++) {
                guideEntity = new Net1314080903118PhotoEntity();
                JSONObject object = jsonArray.getJSONObject(i);
                guideEntity.setId(object.getString("id"));
                guideEntity.setTitle(object.getString("title"));
                guideEntity.setCover(object.getString("cover"));
                guideEntity.setBottom_text(object.getString("bottom_text"));
                guideEntity.setBgcolor(object.getString("bgcolor"));
                guideEntity.setPv_url(object.getString("pv_url"));
                //Log.i("json",guideEntity.getTitle());
                entitiyList.add(guideEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entitiyList;
    }


    /**
     * 请求阅读文章数据
     */
    private void getArticleRequest() {
        new Net1314080903118MyRequest(mContext).getRequest(URL_ARITICLE, new Net1314080903118MyRequest.HttpListener() {
            @Override
            public void onSuccess(String result) {
                //isFirst = false;
                mDataList = parse2Json(result);
                loadListView(mDataList);//装载ListView
                //Log.i("articleData", result);
            }

            @Override
            public void onError(String errorResult) {
                //isFirst = true;
                Toast.makeText(mContext, "Request Error", Toast.LENGTH_SHORT).show();
                Log.i("articleData", errorResult);
            }
        });
    }

    private void loadListView(List<Net1314080903118ArticleEntity> mDataList) {
        adapter = new Net1314080903118ListAdapter(mContext,mDataList);
        listView.setAdapter(adapter);
        //new Net1314080903118Utils().setListViewHeightBasedOnChildren(listView);
    }

    private void loadViewPager() {
        addDynamicView();
        adViewPager.setAdapter(new Net1314080903118MyAdapter(mContext,adList, imageViews));// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        adViewPager.setOnPageChangeListener(new MyPageChangeListener());
    }


    /**
     * 文章数据解析
     * @param result
     * @return
     */
    private List<Net1314080903118ArticleEntity> parse2Json(String result) {
        List<Net1314080903118ArticleEntity> articleEntityList=null;
        try {
            articleEntityList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            Net1314080903118ArticleEntity articleEntity = null;
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                articleEntity= new Net1314080903118ArticleEntity();
                JSONObject jsb = jsonArray.getJSONObject(i);
                articleEntity.setDate(jsb.getString("date"));
                JSONArray jaItem = jsb.getJSONArray("items");
                for (int j = 0; j < jaItem.length(); j++) {
                    JSONObject joItem = jaItem.getJSONObject(j);
                    articleEntity.setTime(joItem.getString("time"));
                    articleEntity.setType(joItem.getInt("type"));

                    if (articleEntity.getType().get(j) == 3){   //类型3
                        JSONObject joContent = joItem.getJSONObject("content");
                        articleEntity.setQuestion_id(joContent.getString("question_id"));
                        articleEntity.setQuestion_title(joContent.getString("question_title"));
                        articleEntity.setAnswer_title(joContent.getString("answer_title"));
                        articleEntity.setAnswer_content(joContent.getString("answer_content"));
                        articleEntity.setQuestion_makettime(joContent.getString("question_makettime"));
                        //Log.i("json",articleEntity.getQuestion_title());

                    } else if (articleEntity.getType().get(j) == 2){  //类型2
                        JSONObject joContent = joItem.getJSONObject("content");
                        articleEntity.setId(joContent.getString("id"));
                        articleEntity.setSerial_id(joContent.getString("serial_id"));
                        articleEntity.setNumber(joContent.getString("number"));
                        articleEntity.setTitle(joContent.getString("title"));
                        articleEntity.setExcerpt(joContent.getString("excerpt"));
                        articleEntity.setRead_num(joContent.getString("read_num"));
                        articleEntity.setMaketime(joContent.getString("maketime"));
                        articleEntity.setHas_audio(joContent.getBoolean("has_audio"));

                        JSONObject joAuthor = joContent.getJSONObject("author");
                        articleEntity.setUser_id(joAuthor.getString("user_id"));
                        articleEntity.setUser_name(joAuthor.getString("user_name"));
                        articleEntity.setWeb_url(joAuthor.getString("web_url"));
                        articleEntity.setDesc(joAuthor.getString("desc"));


                    } else if (articleEntity.getType().get(j) == 1){  //类型1
                        JSONObject joContent = joItem.getJSONObject("content");
                        articleEntity.setContent_id(joContent.getString("content_id"));
                        articleEntity.setHp_title(joContent.getString("hp_title"));
                        articleEntity.setGuide_word(joContent.getString("guide_word"));
                        articleEntity.setHas_audio(joContent.getBoolean("has_audio"));
                        JSONArray jaAuthor = joContent.getJSONArray("author");
                        for (int k=0;k<jaAuthor.length();k++){
                            JSONObject joAuthor = jaAuthor.getJSONObject(k);
                            articleEntity.setUser_id(joAuthor.getString("user_id"));
                            articleEntity.setUser_name(joAuthor.getString("user_name"));
                            articleEntity.setWeb_url(joAuthor.getString("web_url"));
                            articleEntity.setDesc(joAuthor.getString("desc"));
                            articleEntity.setWb_name(joAuthor.getString("wb_name"));
                            //Log.i("json",joAuthor.toString());
                        }

                    }
                    //Log.i("json", articleEntity.getDate());
                }
                articleEntityList.add(articleEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articleEntityList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.net1314080903118_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(Net1314080903118Activity.this,Net1314080903118Camera.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
