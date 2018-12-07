package fr.tas.esipe.tasclientmobile.model;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapView;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class CustomOverLay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
    private MapView mapView;

    public CustomOverLay(Drawable drawable, MapView mapView) {
        super(boundCenter(drawable), new ResourceProxy() {
            @Override
            public String getString(string string) {
                return null;
            }

            @Override
            public String getString(string string, Object... objects) {
                return null;
            }

            @Override
            public Bitmap getBitmap(bitmap bitmap) {
                return null;
            }

            @Override
            public Drawable getDrawable(bitmap bitmap) {
                return null;
            }

            @Override
            public float getDisplayMetricsDensity() {
                return 0;
            }
        });
        this.mapView = mapView;
    }

    public void addOverlayItem(OverlayItem item) {
        overlayItems.add(item);
        populate();
    }

    public static Drawable boundCenter(Drawable d)
    {
        d.setBounds(d.getIntrinsicWidth() /- 2, d.getIntrinsicHeight() / -2,
                d.getIntrinsicWidth() / 2, d.getIntrinsicHeight() / 2);
        return d;
    }

    @Override
    protected OverlayItem createItem(int index) {
        return overlayItems.get(index);
    }

    @Override
    public int size() {
        return overlayItems.size();
    }

    @Override
    protected boolean onTap(int index) {
        return true;
    }

    @Override
    public boolean onSnapToItem(int i, int i1, Point point, IMapView iMapView) {
        return false;
    }
}