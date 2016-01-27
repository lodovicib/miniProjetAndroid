package com.m2dl.miniprojetpointinteret.utils;

import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class BasicListPoints {

    private List<MarkerOptions> listPoints;
    private List<PolygonOptions> listPolyPoints;

    private List<PolygonOptions> listPolyCarton;
    private List<PolygonOptions> listPolyPile;
    private List<PolygonOptions> listPolyPapier;

    public BasicListPoints() {
        listPoints = new ArrayList<>();
        initPoints();
        listPolyPoints = new ArrayList<>();
        listPolyCarton = new ArrayList<>();
        listPolyPile = new ArrayList<>();
        listPolyPapier = new ArrayList<>();
        initPolyPoints();
    }

    private void initPolyPoints() {
        //Carton
        listPolyCarton.add(new PolygonOptions()
                .add(new LatLng(43.55773, 1.46920), new LatLng(43.55780, 1.46934), new LatLng(43.55745, 1.46969), new LatLng(43.55738, 1.46954))
                .strokeColor(Color.parseColor("#FFFF05")).fillColor(Color.YELLOW));
        listPolyCarton.add(new PolygonOptions()
                .add(new LatLng(43.55992, 1.47172), new LatLng(43.56000, 1.47186), new LatLng(43.55948, 1.47236), new LatLng(43.55942, 1.47220))
                .strokeColor(Color.parseColor("#FFFF05")).fillColor(Color.YELLOW));
        listPolyCarton.add(new PolygonOptions()
                .add(new LatLng(43.5622, 1.46751), new LatLng(43.56231, 1.4677), new LatLng(43.56206, 1.46795), new LatLng(43.56197, 1.46772))
                .strokeColor(Color.parseColor("#FFFF05")).fillColor(Color.YELLOW));
        listPolyCarton.add(new PolygonOptions()
                .add(new LatLng(43.56449, 1.4656), new LatLng(43.56455, 1.46576), new LatLng(43.56406, 1.46625), new LatLng(43.56398, 1.46609))
                .strokeColor(Color.parseColor("#FFFF05")).fillColor(Color.YELLOW));
        listPolyCarton.add(new PolygonOptions()
                .add(new LatLng(43.56577, 1.46736), new LatLng(43.56593, 1.46769), new LatLng(43.56577, 1.46785), new LatLng(43.5656, 1.46753))
                .strokeColor(Color.parseColor("#FFFF05")).fillColor(Color.YELLOW));
        listPolyCarton.add(new PolygonOptions()
                .add(new LatLng(43.56665, 1.4695), new LatLng(43.56674, 1.46968), new LatLng(43.56657, 1.46985), new LatLng(43.5665, 1.46965))
                .strokeColor(Color.parseColor("#FFFF05")).fillColor(Color.YELLOW));

        //Pile
        listPolyPile.add(new PolygonOptions()
                .add(new LatLng(43.56362,1.46487), new LatLng(43.56381,1.46537), new LatLng(43.56354,1.4657), new LatLng(43.5633,1.46522))
                .strokeColor(Color.parseColor("#DF6D14")).fillColor(Color.rgb(237, 127, 16)));
        listPolyPile.add(new PolygonOptions()
                .add(new LatLng(43.56456, 1.46589), new LatLng(43.56472, 1.46617), new LatLng(43.56458, 1.46629), new LatLng(43.56446, 1.46602))
                .strokeColor(Color.parseColor("#DF6D14")).fillColor(Color.rgb(237, 127, 16)));

        //Papier
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56758, 1.46950), new LatLng(43.56773, 1.46989), new LatLng(43.56733, 1.47026), new LatLng(43.56716, 1.46991))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56666, 1.46950), new LatLng(43.56674, 1.46967), new LatLng(43.56667, 1.46975), new LatLng(43.56657, 1.46958))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56497, 1.46513), new LatLng(43.56504, 1.46529), new LatLng(43.56461, 1.4657), new LatLng(43.56454, 1.46554))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56542, 1.46363), new LatLng(43.56554, 1.4639), new LatLng(43.5652, 1.46422), new LatLng(43.56508, 1.46391))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56372, 1.46478), new LatLng(43.56393, 1.46529), new LatLng(43.56426, 1.46496), new LatLng(43.56403, 1.46448))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56349, 1.46428), new LatLng(43.56362, 1.4646), new LatLng(43.56323, 1.46499), new LatLng(43.56311, 1.46473))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.5632, 1.465620), new LatLng(43.56335, 1.4659), new LatLng(43.56263, 1.46661), new LatLng(43.56249, 1.46632))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56377, 1.46168), new LatLng(43.56384, 1.46181), new LatLng(43.56375, 1.46195), new LatLng(43.56367, 1.4618))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56123, 1.46364), new LatLng(43.56137, 1.46392), new LatLng(43.56086, 1.46442), new LatLng(43.56072, 1.46414))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56151, 1.46595), new LatLng(43.56161, 1.46623), new LatLng(43.56186, 1.46599), new LatLng(43.56175, 1.46571))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56146, 1.46600), new LatLng(43.56154, 1.46615), new LatLng(43.56112, 1.46656), new LatLng(43.56104, 1.46641))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56202, 1.46603), new LatLng(43.5621, 1.46618), new LatLng(43.5615, 1.46678), new LatLng(43.56142, 1.46663))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56146, 1.46709), new LatLng(43.56156, 1.4673), new LatLng(43.5614, 1.46745), new LatLng(43.5613, 1.46725))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56245, 1.4669), new LatLng(43.56253, 1.46706), new LatLng(43.5623, 1.46728), new LatLng(43.56222, 1.46713))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.5618, 1.4677), new LatLng(43.56197, 1.46805), new LatLng(43.56174, 1.46827), new LatLng(43.56157, 1.46793))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56263, 1.46908), new LatLng(43.56277, 1.46939), new LatLng(43.5625, 1.46965), new LatLng(43.56236, 1.46932))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56189, 1.46903), new LatLng(43.56201, 1.4693), new LatLng(43.56185, 1.46944), new LatLng(43.56173, 1.46916))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56134, 1.46839), new LatLng(43.56141, 1.46855), new LatLng(43.56101, 1.46896), new LatLng(43.56093, 1.46881))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56141, 1.46894), new LatLng(43.56169, 1.46951), new LatLng(43.56105, 1.47007), new LatLng(43.56079, 1.46955))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56153, 1.47001), new LatLng(43.56088, 1.47064), new LatLng(43.56107, 1.47108), new LatLng(43.56173, 1.47041))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.55933, 1.47231), new LatLng(43.55939, 1.47246), new LatLng(43.55886, 1.47299), new LatLng(43.55879, 1.47284))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56076, 1.46724), new LatLng(43.56084, 1.46739), new LatLng(43.55942, 1.46883), new LatLng(43.55934, 1.46868))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.56025, 1.46732), new LatLng(43.56032, 1.46748), new LatLng(43.55976, 1.46805), new LatLng(43.55968, 1.46787))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
        listPolyPapier.add(new PolygonOptions()
                .add(new LatLng(43.55844, 1.46891), new LatLng(43.55852, 1.46906), new LatLng(43.55798, 1.4696), new LatLng(43.5579, 1.46945))
                .strokeColor(Color.parseColor("#1E7FCB")).fillColor(Color.parseColor("#007FFF")));
    }

    private void initPoints() {
        ArrayList<LatLng> listVerre = new ArrayList<>(7);
        listVerre.add(new LatLng(43.55891,1.47303));listVerre.add(new LatLng(43.55999,1.47194));
        listVerre.add(new LatLng(43.55995,1.47195));listVerre.add(new LatLng(43.56439,1.47053));
        listVerre.add(new LatLng(43.56355,1.47579));listVerre.add(new LatLng(43.55509,1.46816));
        listVerre.add(new LatLng(43.56295,1.46311));listVerre.add(new LatLng(43.56305,1.45939));
        listVerre.add(new LatLng(43.56068,1.45738));listVerre.add(new LatLng(43.56376,1.45531));
        listVerre.add(new LatLng(43.56850,1.46620));listVerre.add(new LatLng(43.56735,1.46726));
        listVerre.add(new LatLng(43.57124,1.46269));listVerre.add(new LatLng(43.56731, 1.46477));

        addListPoints(listVerre, "Recyclage : verre", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        ArrayList<LatLng> listTextille = new ArrayList<>(2);
        listTextille.add(new LatLng(43.55505, 1.46811));
        listTextille.add(new LatLng(43.56305, 1.45935));

        addListPoints(listTextille, "Recyclage : textille", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

    }

    public void addListPoints( ArrayList<LatLng> list, String title, BitmapDescriptor icon) {
        for (LatLng s : list) {
            listPoints.add(new MarkerOptions().position(s).title(title)
                    .icon(icon));
        }
    }

    public List<MarkerOptions> getListPoints() {
        return listPoints;
    }

    public List<PolygonOptions> getListPolyPoints() {
        for (PolygonOptions p : listPolyCarton) {
            listPolyPoints.add(p);
        }
        for (PolygonOptions p : listPolyPile) {
            listPolyPoints.add(p);
        }
        for (PolygonOptions p : listPolyPapier) {
            listPolyPoints.add(p);
        }
        return listPolyPoints;
    }

    public List<PolygonOptions> getListPolyPoints(String find) {
        if (find.contains("carton"))
            return listPolyCarton;
        else if (find.contains("pile"))
            return listPolyPile;
        else if (find.contains("papier"))
            return listPolyPapier;
        else if (find.equals("recyclage"))
            return getListPolyPoints();
        return new ArrayList<>();
    }

}
