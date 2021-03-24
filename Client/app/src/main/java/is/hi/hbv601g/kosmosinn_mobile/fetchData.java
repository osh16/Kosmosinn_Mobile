package is.hi.hbv601g.kosmosinn_mobile;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class fetchData extends AsyncTask<Void,Void,Void> {
    private static final String TAG = "fetchData";
    String data ="";
    String dataParsed = "";
    String singleParsed ="";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://192.168.0.103:8080/api/boards/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }


            JSONArray JA = new JSONArray(data);
            for(int i =0 ;i <JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                singleParsed =  "Id:" + JO.get("id") + "\n"+
                        "Name:" + JO.get("name") + "\n"+
                        "Description:" + JO.get("description") + "\n"+
                        "Topics:" + JO.get("topicCount") + "\n"+
                        "Error:" + JO.get("error") +  "\n";

                dataParsed = dataParsed + singleParsed +"\n" ;

                Log.d(TAG, singleParsed);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
