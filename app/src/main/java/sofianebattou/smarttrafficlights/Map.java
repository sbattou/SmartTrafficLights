package sofianebattou.smarttrafficlights;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Map extends FragmentActivity implements OnMapReadyCallback {

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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ottawa = new LatLng(45.422159, -75.680215);
        Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(45.422159, -75.680215),
                        new LatLng(45.425329, -75.682833),
                        new LatLng(45.426029, -75.681261)
                ));

        //mMap.addMarker(new MarkerOptions().position(ottawa).title("Marker in Ottawa"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ottawa));
        // create marker
        MarkerOptions marker3 = new MarkerOptions().position(new LatLng(45.422159, -75.68021)).title("Hello Maps");

// Changing marker icon
        marker3.icon(BitmapDescriptorFactory.fromResource(R.drawable.dot));

// adding marker
        mMap.addMarker(marker3);

         LatLngBounds AUSTRALIA = new LatLngBounds(
                new LatLng(45.421536,-75.682823), new LatLng(45.426280, -75.679894));

// Set the camera to the greatest possible zoom level that includes the
// bounds
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 0));


    }
}
