package zhou.com.wanvejin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zhou
 * on 2018/9/30.
 */

public class SaveReceiver extends BroadcastReceiver {
    public SaveReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("===============", "onReceive: ");
    }
}
