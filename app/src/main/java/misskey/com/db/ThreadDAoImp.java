package misskey.com.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import misskey.com.entities.ThreadInfo;

/** 数据访问接口实现
 * Created by misskey on 15-6-17.
 */
public class ThreadDAoImp implements  ThreadDAO {
     private  DBHelper mHelper=null;

     public ThreadDAoImp(Context mContext){
            mHelper=new DBHelper(mContext);
     }
    /**
     * 向数据库插入线程信息
     *
     * @param threadInfo 线程信息
     */
    @Override
    public void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db=mHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",new Object[]{
                threadInfo.getId(),threadInfo.getUrl(),threadInfo.getStart(),threadInfo.getEnd(),false
        });
        db.close();
    }

    /**
     * 向数据库删除线程信息
     *
     * @param url       文件的url
     * @param thread_id 线程的id
     */
    @Override
    public void deleteThreadInfo(String url, int thread_id) {
        SQLiteDatabase db=mHelper.getWritableDatabase();
        db.execSQL("delete from thread_info  where url=? and thread_id=?",new Object[]{url,thread_id});
        db.close();
    }

    /**
     * 更新下载的情况
     *
     * @param url        文件的ur;
     * @param thread_id  线程id
     * @param isFinished 是否下载完成
     */
    @Override
    public void updateThreadInfo(String url, int thread_id, int isFinished) {
        SQLiteDatabase db=mHelper.getWritableDatabase();
        db.execSQL("update  thread_info set finished=? where url=? and thread_id=?",new Object[]{isFinished,url,thread_id});
        db.close();
    }

    /**
     * 返回线程信息的list集合
     *
     * @param url
     * @return
     */
    @Override
    public List<ThreadInfo> getThread(String url) {
        List<ThreadInfo> list =new ArrayList<>();
        SQLiteDatabase db=mHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from thread_info where url=?", new String[]{url});
        while(cursor.moveToNext()){
            ThreadInfo threadInfo=new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setIsFinish(cursor.getInt(cursor.getColumnIndex("finished")));
           list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 线程信息是否存在
     *
     * @param url
     * @param thread_id
     * @return
     */
    @Override
    public boolean isExits(String url, int thread_id) {
        SQLiteDatabase db=mHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from thread_info where url=? and thread_id=?", new String[]{url,
                  thread_id+""});
        boolean exits=cursor.moveToNext();
        cursor.close();
        db.close();
        return exits;
    }
}
