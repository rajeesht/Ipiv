package net.rajeesh.mobile.android.app.ipiv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotosActivity extends Activity {

    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<PopularImage> popularImages;
    private PopularImageAdapter popularImageAdapter;
    private ListView lvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        popularImages = new ArrayList<PopularImage>();
        popularImageAdapter = new PopularImageAdapter(this, popularImages);
        lvPhoto = (ListView) findViewById(R.id.lvPhoto);
        lvPhoto.setAdapter(popularImageAdapter);
        fetchPopularPhotos();
    }

    public void fetchPopularPhotos() {

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray data = response.getJSONArray("data");
                    for(int i = 0; i < data.length(); i++) {
                        JSONObject photo = data.getJSONObject(i);
                        PopularImage popularImage = new PopularImage();
                        popularImage.setUsername(photo.getJSONObject("user").getString("username"));
                        popularImage.setCaption(photo.getJSONObject("caption").getString("text"));
                        popularImage.setImageUrl(photo.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        popularImage.setImageHeight(photo.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        popularImage.setLikeCount(photo.getJSONObject("likes").getInt("count"));
                        popularImages.add(popularImage);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                popularImageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
