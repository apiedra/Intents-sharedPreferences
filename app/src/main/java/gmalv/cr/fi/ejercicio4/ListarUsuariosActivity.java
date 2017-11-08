package gmalv.cr.fi.ejercicio4;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListarUsuariosActivity extends ExpandableListActivity implements
        ExpandableListView.OnChildClickListener {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ArrayList<String> groupItem = new ArrayList<String>();
    ArrayList<Object> childItem = new ArrayList<Object>();
    ArrayList<Persona> listaPersona = new ArrayList<Persona>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_listar_usuarios);
        sharedPref = getSharedPreferences(
                getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        leer();

        ExpandableListView expandbleLis = getExpandableListView();
        expandbleLis.setDividerHeight(2);
        expandbleLis.setGroupIndicator(null);
        expandbleLis.setClickable(true);
        NewAdapter mNewAdapter = new NewAdapter(groupItem, childItem);
        mNewAdapter
                .setInflater(
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                        this);
        getExpandableListView().setAdapter(mNewAdapter);
        expandbleLis.setOnChildClickListener(this);
        setGroupData();
        setChildGroupData();
    }


    private void leer() {
        listaPersona.clear();
        Gson gson = new Gson();
        String json = sharedPref.getString(getResources().getString(R.string.preference_file_key), null);
        Type type = new TypeToken<ArrayList<Persona>>() {
        }.getType();
        listaPersona = gson.fromJson(json, type);
        if (listaPersona == null)
            listaPersona = new ArrayList<Persona>();
    }

    public void setGroupData() {

        for (Persona persona : listaPersona)
            groupItem.add(persona.getUsuario());
    }

    public void setChildGroupData() {
        for (Persona persona : listaPersona) {
            ArrayList<String> child = new ArrayList<String>();
            child.add(persona.getNombre());
            child.add(persona.getCorreo());
            child.add(persona.getEdad());
            child.add(persona.getTelefono());
            childItem.add(child);
        }


    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        Toast.makeText(ListarUsuariosActivity.this, "Clicked On Child",
                Toast.LENGTH_SHORT).show();
        return true;
    }
}
