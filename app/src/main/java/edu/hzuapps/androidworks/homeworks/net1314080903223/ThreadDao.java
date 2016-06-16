package single_thread_download.skyward.com.db;

import java.util.List;

import single_thread_download.skyward.com.bean.ThreadInfo;

/**
 * 数据访问接口
 * Created by skyward on 2016/6/15.
 */
public interface ThreadDao {
    /**
     * 插入线程信息
     */
    public void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程
     * @param url
     * @param thread_id
     */
    public void deleteThread(String url, int thread_id);

    /**
     * 更新线程下载进度
     * @param url
     * @param thread_id
     * @param finished
     */
    public void updateThread(String url, int thread_id, int finished);

    /**
     * 查询线程信息
     * @param url
     * @return
     */
    public List<ThreadInfo> getThreads(String url);

    /**
     * 线程信息是否已存在
     * @param url
     * @param thread_id
     * @return
     */
    public boolean isExists(String url, int thread_id);
}
