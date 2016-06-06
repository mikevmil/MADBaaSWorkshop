package nl.mikevanmil.workshopmadbaas;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import nl.mikevanmil.workshopmadbaas.Adapter.ImageAdapter;
import nl.mikevanmil.workshopmadbaas.Model.PhotoEntity;

public class GalleryActivity extends AppCompatActivity {
    private static ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GridView gridView = (GridView) findViewById(R.id.gallery_grid_view);
        imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        Toast.makeText(this, "Downloading images...", Toast.LENGTH_SHORT).show();

        //TODO: Use the Backendless API to download all of the images of the logged in user.

//                        for (PhotoEntity photoEntity : photoEntities) {
//                            Message message = new Message();
//                            ArrayList<Object> messageContainer = new ArrayList<>();
//                            try {
//                                //TODO: Download the photo here and put it in messageContainer[0]
//                                InputStream input = null;
//                                messageContainer.add(0, BitmapFactory.decodeStream(input));
//                                messageContainer.add(1, photoEntity);
//                            } catch (Exception e) {
//                                messageContainer.add(0, e);
//                            }
//                            message.obj = messageContainer;
//
//                            imagesHandler.sendMessage(message);
//                        }

    }

    private Handler imagesHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Object result = message.obj;

            if (result instanceof ArrayList){
                if (!((ArrayList) result).isEmpty()){
                    if (((ArrayList) result).get(0) instanceof Bitmap){
                        imageAdapter.add((Bitmap)((ArrayList) result).get(0), (PhotoEntity) ((ArrayList) result).get(1));
                    }
                }
            } else {
                Toast.makeText(GalleryActivity.this, ((Exception) result).getMessage(), Toast.LENGTH_SHORT).show();
            }

            return true;
        }
    });


}
