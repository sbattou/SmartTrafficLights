package sofianebattou.smarttrafficlights;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Map extends FragmentActivity implements OnMapReadyCallback {
    final LatLng thirdIntersection = new LatLng(45.425329, -75.682833); //intersection between laurier and king E
    final LatLng fourthIntersection = new LatLng(45.426029, -75.681261); //end destination
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        final LatLng thirdIntersection = new LatLng(45.425329, -75.682833);
        final LatLng fourthIntersection = new LatLng(45.426029, -75.681261);
        mMap = googleMap;

        // Moving the camera
        LatLngBounds Ottawa = new LatLngBounds(new LatLng(45.421536,-75.682823), new LatLng(45.426280, -75.679894));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(Ottawa, 0));

        // Adding the path
        Polyline polyline = mMap.addPolyline(new PolylineOptions().width(8).color(Color.RED).clickable(true).add(
            new LatLng(45.422159, -75.680215),
            new LatLng(45.425329, -75.682833),
            new LatLng(45.426029, -75.681261)
        ));

        // Creating a custom marker
        Marker marker1 = mMap.addMarker(new MarkerOptions().position(new LatLng(45.422159, -75.680215)).title("Current Location"));
        marker1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dot));

        // Moving the marker
        animateMarker(marker1,thirdIntersection, fourthIntersection);
    }

    public void changeColorToGreen(Marker marker){
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dot_green));
    }

    public void changeColorToBlack(Marker marker){
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dot));
    }

    public synchronized void animateMarker(final Marker marker, final LatLng toPosition1, final LatLng toPosition2) {
        final int duration = 30000;
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * toPosition1.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * toPosition1.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            final long start2 = SystemClock.uptimeMillis();
            int numFlash = 99;
            @Override
            public void run() {

                changeColorToGreen(marker);
                changeColorToBlack(marker);
                long elapsed = SystemClock.uptimeMillis() - (start2 + duration);
                float t = interpolator.getInterpolation((float) elapsed / (duration));
                double lng = t * toPosition2.longitude + (1 - t) * toPosition1.longitude;
                double lat = t * toPosition2.latitude + (1 - t) * toPosition1.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler2.postDelayed(this, 16);
                }
            }
        },duration);


    }
}
