package zhou.com.wanvejin.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kinggrid.iappoffice.IAppOffice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import zhou.com.wanvejin.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
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
        setContentView(R.layout.activity_main);

        Button open_document = (Button) findViewById(R.id.open_document);
        open_document.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openDocument();
            }
        });

        //设置需要打开的目标文件;
        fileName = SDCARD_ROOT_PATH + "/发文稿.doc";

        //拷贝文件到指定路径下;
        try {
            copyAssetsFileToSDCard("fwg.doc", fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化iAppOffice, 注：先设置授权码，后调用init();
        iappoffice = new IAppOffice(this);
        iappoffice.setCopyRight(COPY_RIGHT);
        iappoffice.init();

        iappoffice.setFileProviderAuthor("zhou.com.wanvejin.fileProvider");
        //设置文档打开时是否直接进入留痕模式。
        iappoffice.setIsReviseMode(true);


    }


    @Override
    protected void onDestroy() {
        if(iappoffice!=null){
            iappoffice.unInit();
        }
        super.onDestroy();
    }


    /**
     * 打开文档
     */
    protected void openDocument() {

        if(iappoffice.isWPSInstalled()){
            iappoffice.setFileName(fileName);
            iappoffice.setIsReviseMode(false);
            iappoffice.appOpen(true);
        }else{
            Toast.makeText(this, "请安装WPS专业版！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从assets目录拷贝文件到SD卡
     * @param fileName
     * @param toFilePath
     * @throws IOException
     */
    private void copyAssetsFileToSDCard(String fileName, String toFilePath)
            throws IOException {
        File file = new File(toFilePath);
        if (file.exists()) {
            return;
        }
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(toFilePath);
        myInput = getAssets().open(fileName);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
    }
}
