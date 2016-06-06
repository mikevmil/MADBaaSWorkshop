package nl.mikevanmil.workshopmadbaas.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nl.mikevanmil.workshopmadbaas.BuildConfig;
import nl.mikevanmil.workshopmadbaas.Model.PhotoEntity;
import nl.mikevanmil.workshopmadbaas.R;

/**
 * Created by Mike on 31-05-16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private List<Bitmap> images = new ArrayList<>();
    private List<PhotoEntity> entities = new ArrayList<>();

    public ImageAdapter(Context c) {
        mContext = c;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public void add(Bitmap bitmap, PhotoEntity photoEntity) {
        images.add(bitmap);
        entities.add(photoEntity);
        notifyDataSetChanged();
    }

    public Bitmap getItem(int position) {
        return images.get(position);
    }

    public PhotoEntity getEntity(int position) {
        return entities.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_gallery, null);
            holder = new ViewHolder();
            holder.mImage = (ImageView) convertView.findViewById(R.id.item_gallery_image);
            holder.mMapImage = (ImageView) convertView.findViewById(R.id.item_gallery_map);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mImage.setImageBitmap(getItem(position));
        Glide.with(mContext)
             .load("https://maps.googleapis.com/maps/api/staticmap?center=" + getEntity(position).getLatitude() + "," + getEntity(position).getLongitude() + "&zoom=12&size=500x500&api=" + BuildConfig.GOOGLE_MAPS_API_KEY)
             .crossFade()
             .into(holder.mMapImage);
        return convertView;
    }

    static class ViewHolder {
        ImageView mImage;
        ImageView mMapImage;
    }
}

