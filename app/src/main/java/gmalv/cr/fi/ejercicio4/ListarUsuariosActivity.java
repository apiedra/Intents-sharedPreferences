package gmalv.cr.fi.ejercicio4;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListarUsuariosActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ArrayList<Persona> listaPersona = new ArrayList<Persona>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);
        sharedPref = getSharedPreferences(
                getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        leer();

        PersonaAdapter adapter = new PersonaAdapter(this, listaPersona);
// Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(adapter);
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

}
