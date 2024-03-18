package org.ecloga.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ecloga.dynamico.Dynamico;
import org.ecloga.dynamico.DynamicoException;
import org.ecloga.dynamico.DynamicoListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ActivityMain extends AppCompatActivity {

    private static final String TAG = "Dynamico Demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Option 1 - Create layout based on remote file
//         String res = "https://ecloga.org/projects/dynamico";

        // Option 2 - Create layout based on local String
//        String res = "{\n" +
//                "  \"views\":[\n" +
//                "    {  \n" +
//                "      \"class\":\"android.widget.TextView\",\n" +
//                "      \"attributes\":{  \n" +
//                "        \"text\":\"Sample text\",\n" +
//                "        \"textColor\":\"#FF69B4\"\n" +
//                "      }\n" +
//                "    },\n" +
//                "    {  \n" +
//                "      \"class\":\"android.widget.ImageView\",\n" +
//                "      \"attributes\":{\n" +
//                "        \"src\": \"https://www.letribunaldunet.fr/wp-content/uploads/2013/12/Cute-Panda-Bears-animals-34916401-1455-1114.jpg\"\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";

        // Option 3 - Create layout based on local file
        String res = null;
        try {
            InputStream inputStream = getAssets().open("1.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
            reader.close();
            inputStream.close();
            res = stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(TextUtils.isEmpty(res)) {
            Toast.makeText(this, "Error loading layout", Toast.LENGTH_SHORT);
            return;
        }


        try {
            new Dynamico(res, "activity_main", (ViewGroup) findViewById(R.id.mainLayout))
                    .setListener(new DynamicoListener() {
                        @Override
                        public void onSuccess(String message) {
                            // everything is okay
                            Log.v(TAG, "onSuccess: " + message);
                        }

                        @Override
                        public void onError(String message) {
                            // notify user
                            Log.v(TAG, "onError: " + message);
                        }
                    })
                    .setAsyncPause(500000)
                    .initialize();
        }catch(DynamicoException e) {
            finish();
        }
    }

    // parameters cannot be primitive types
    public static void onImageClick(String name, Integer age, Context context) {
        Toast.makeText(context, "Name: " + name + "\nAge: " + age, Toast.LENGTH_SHORT).show();
    }

    // first argument must be boolean and it will be used as 'checked' flag
    public static void onButtonCheck(Boolean isChecked, String message, Context context) {
        Toast.makeText(context, "Checked: " + isChecked + "\nMessage: " + message, Toast.LENGTH_SHORT).show();
    }
}
