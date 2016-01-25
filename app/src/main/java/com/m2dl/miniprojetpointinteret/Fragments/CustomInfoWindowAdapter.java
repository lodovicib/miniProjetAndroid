package com.m2dl.miniprojetpointinteret.Fragments;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.m2dl.miniprojetpointinteret.MainActivity;
import com.m2dl.miniprojetpointinteret.R;

import java.io.File;
import java.util.List;

class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Marker marker;
    private View view;
    private List<Marker> markers;
    LayoutInflater inflater = null;

    public CustomInfoWindowAdapter(LayoutInflater inflater) {

        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View popup = inflater.inflate(R.layout.custom_info_window, null);
        TextView title = (TextView) popup.findViewById(R.id.title);
        TextView snippet = (TextView) popup.findViewById(R.id.snippet);
        ImageView imageView = (ImageView) popup.findViewById(R.id.badge);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());
        if (marker.getSnippet() != null) {
            File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
            imageView.setImageURI(Uri.fromFile(photo));
        }

        return popup;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
      /*  if (marker.getId() != null && markers != null && markers.size() > 0) {
            if ( markers.get(marker.getId()) != null &&
                    markers.get(marker.getId()) != null) {
                url = markers.get(marker.getId());
            }
        }
        final ImageView image = ((ImageView) view.findViewById(R.id.badge));

        if (url != null && !url.equalsIgnoreCase("null")
                && !url.equalsIgnoreCase("")) {
            imageLoader.displayImage(url, image, options,
                    new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri,
                                                      View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view,
                                    loadedImage);
                            getInfoContents(marker);
                        }
                    });
        } else {
            image.setImageResource(R.drawable.ic_launcher);
        }

        final String title = marker.getTitle();
        final TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            titleUi.setText(title);
        } else {
            titleUi.setText("");
        }

        final String snippet = marker.getSnippet();
        final TextView snippetUi = ((TextView) view
                .findViewById(R.id.snippet));
        if (snippet != null) {
            snippetUi.setText(snippet);
        } else {
            snippetUi.setText("");
        }

        return view;
    }
}

    private void initImageLoader(int memClass) {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
         /*   int memClass = ((ActivityManager)
                    getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();*/
       /*     memoryCacheSize = (memClass / 8) * 1024 * 1024;
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(memoryCacheSize)
                .memoryCache(new FIFOLimitedMemoryCache(memoryCacheSize-1000000))
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
                .build();

        ImageLoader.getInstance().init(config);
        */
}
