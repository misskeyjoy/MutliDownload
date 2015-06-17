package misskey.com.db;

import java.util.List;

import misskey.com.entities.ThreadInfo;

/**
 *
 * Created by misskey on 15-6-17.
 */
public interface ThreadDAO {
    /**
     * 向数据库插入线程信息
     * @param threadInfo 线程信息
     */
    public void insertThread(ThreadInfo threadInfo);

    /**
     * 向数据库删除线程信息
     * @param url 文件的url
     * @param thread_id 线程的id
     */
    public  void deleteThreadInfo(String url,int thread_id);

    /**
     * 更新下载的情况
     * @param url 文件的ur;
     * @param thread_id 线程id
     * @param isFinished 是否下载完成
     */
    public  void updateThreadInfo(String url,int thread_id,int isFinished);

    /**
     * 返回线程信息的list集合
     * @param url
     * @return
     */
    public List<ThreadInfo>  getThread(String url);

    /**
     *线程信息是否存在
     * @param url
     * @param thread_id
     * @return
     */
    public boolean isExits(String url,int thread_id);
}
