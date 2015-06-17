package misskey.com.misskey.com.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


import misskey.com.entities.FileInfo;

/**
 * Created by misskey on 15-6-13.
 */
public class DownLoadService extends Service {
    public  static final String ACTION_START="ACTION_START";
    public  static final String ACTION_STOP="ACTION_STOP";
    public static final  String ACTION_UPDATE="ACTION_UPDATE";
    public  static  final  int MSG_INt=0;
    private DownloadTask mTask;
    public  static final  String DOWNLOAD_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/downloads/";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       //获得Activity传来的值
        if(ACTION_START.equals(intent.getAction())){
            FileInfo fileInfo= (FileInfo) intent.getSerializableExtra("fileinfo");
            Log.i("test", "Start:" + fileInfo.toString());
            //启动线程
            new InitThread(fileInfo).start();
        }else if(ACTION_STOP.equals(intent.getAction())){
            FileInfo fileInfo= (FileInfo) intent.getSerializableExtra("fileinfo");
            Log.i("test","Stop"+fileInfo.toString());
            if(mTask!=null){
                mTask.isPaused=true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     * <p/>
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    Handler mHanler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
           switch(msg.what){
               case MSG_INt:
                    FileInfo fileInfo= (FileInfo) msg.obj;
                   Log.i("test","Init:"+fileInfo);
                   //启动下载任务
                   mTask=new DownloadTask(DownLoadService.this,fileInfo);
                   mTask.download();
                   break;
           }
        }
    };
    /**
     * 初始化的子线程
     */
    class InitThread extends  Thread{
        private FileInfo mFileInfo=null;
        public InitThread(FileInfo mFileInfo){
            this.mFileInfo=mFileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn=null;
            RandomAccessFile raf=null;
            int length=0;
            try{
                 //连接网路文件
                URL url =new URL(mFileInfo.getUrl());
                conn= (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                if(conn.getResponseCode()== HttpStatus.SC_OK){
                    //获取文件长度
                    length=conn.getContentLength();
                }
                if(length<=0){
                    return;
                }
                File dir=new File(DOWNLOAD_PATH);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                //在本地创建文件
                File file =new File(dir,mFileInfo.getFileName());
                raf=new RandomAccessFile(file,"rwd");
                raf.setLength(length);
                mFileInfo.setLength(length);
                mHanler.obtainMessage(MSG_INt,mFileInfo).sendToTarget();
                   //设置文件长度

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    conn.disconnect();
                    if(raf!=null){
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
