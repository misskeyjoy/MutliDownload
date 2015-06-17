package misskey.com.download_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import misskey.com.entities.FileInfo;
import misskey.com.misskey.com.server.DownLoadService;

public class MainActivity extends Activity {
    private TextView mTvFileName=null;
    private ProgressBar mPbProgrgss=null;
    private Button mBtStop=null;
    private Button mBtStart=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPbProgrgss= (ProgressBar) findViewById(R.id.pbprogressBar);
        mBtStart= (Button) findViewById(R.id.btstart);
        mBtStop= (Button) findViewById(R.id.btstop);
        mTvFileName= (TextView) findViewById(R.id.tv_filename);
        //创建一个文件信息对象
        final FileInfo fileInfo=new FileInfo(0,
                "http://www.imooc.com/mobile/imooc.apk",
                "imooc.apk",0,false
                );
        //创建事件监听
        mBtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //通过Intent 传递参数给Service
                Intent intent =new Intent(MainActivity.this, DownLoadService.class);
                intent.setAction(DownLoadService.ACTION_START);
                intent.putExtra("fileinfo", fileInfo);
                startService(intent);

            }
        });
        mBtStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过Intent 传递参数给Service
                Intent intent =new Intent(MainActivity.this, DownLoadService.class);
                intent.setAction(DownLoadService.ACTION_STOP);
                intent.putExtra("fileinfo",fileInfo);
                startService(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
