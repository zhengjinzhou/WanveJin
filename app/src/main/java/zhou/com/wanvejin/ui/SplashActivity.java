package zhou.com.wanvejin.ui;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kinggrid.iappoffice.IAppOffice;

import zhou.com.wanvejin.R;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    /*
            * 文件的绝对路径
            */
    private String fileName;

    /*
     * SD卡根目录
     */
    private static final String SDCARD_ROOT_PATH = Environment
            .getExternalStorageDirectory().getPath().toString();

    /*
     * 授权码；过期时间=2018-12-19
     */
    private static final String COPY_RIGHT = "SxD/phFsuhBWZSmMVtSjKZmm/c/3zSMrkV2Bbj5tznSkEVZmTwJv0wwMmH/+p6wLiUHbjadYueX9v51H9GgnjUhmNW1xPkB++KQqSv/VKLDsR8V6RvNmv0xyTLOrQoGzAT81iKFYb1SZ/Zera1cjGwQSq79AcI/N/6DgBIfpnlwiEiP2am/4w4+38lfUELaNFry8HbpbpTqV4sqXN1WpeJ7CHHwcDBnMVj8djMthFaapMFm/i6swvGEQ2JoygFU3W8onCO1AgMAD2SkxfJXM/mX1uF23u5oNhx5kpmkBkb3x6aD2yiupr6ji7hzsE6/Qng3l3AbK2vtwyJLdcl2md6r5JJO51PJS2vAlVxcmvGGVWEbHWAH22+t7LdPt+jENOIq5GN/n4KME0L/SFgUD1b8zb/8DFI+sDLA8bVOqHBiSgCNRP4FpYjl8hG/IVrYXOzDNrpoUGsPwMMlLKBA40uW8fXpxdRHfEuWC1PB9ruQ=";

    /*
     * 调用打开文档的iAppOffice对象；
     */
    private IAppOffice iappoffice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d(TAG, "onCreate: "+Environment.getRootDirectory());
        Log.d(TAG, "onCreate: "+getFilesDir().getAbsolutePath());
        Log.d(TAG, "onCreate: "+getCacheDir().getAbsolutePath());
        Log.d(TAG, "onCreate: "+getDir("myFile", MODE_PRIVATE).getAbsolutePath() );

        //初始化iAppOffice, 注：先设置授权码，后调用init();
        iappoffice = new IAppOffice(this);
        iappoffice.setCopyRight(COPY_RIGHT);
        iappoffice.init();

        iappoffice.setFileProviderAuthor("zhou.com.wanvejin.fileProvider");
        //设置文档打开时是否直接进入留痕模式。
        iappoffice.setIsReviseMode(true);
        openDocument();
    }

    private void openDocument() {
        String name = Environment.getExternalStorageDirectory().getPath().toString()+ "/收文呈批表.doc";
        Log.d("-----------", "openDocument: "+name);
        if (iappoffice.isWPSInstalled()){
            iappoffice.setFileName(name);
            iappoffice.setIsReviseMode(false);
            iappoffice.appOpen(true);
            Log.d("09090909", "downloadBySystem: ---------------------");
        }
    }
}
