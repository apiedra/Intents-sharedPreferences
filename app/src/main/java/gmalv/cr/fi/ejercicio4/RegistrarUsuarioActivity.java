package gmalv.cr.fi.ejercicio4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    Context context = this;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    EditText nombreEditText;
    EditText edadEditText;
    EditText correoEditText;
    EditText usuarioEditText;
    EditText passEditText;
    Persona persona;

    ArrayList<Persona> listaPersona = new ArrayList<Persona>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        getPersona();
        initComponents();

        sharedPref = context.getSharedPreferences(
                this.context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();


    }

    public void Onclick(View view) {
        listaPersona.add(persona);
        guardar();
    }

    private void guardar() {
        leer();
        setPersona();
        Gson gson = new Gson();
        String json = gson.toJson(listaPersona);
        editor.putString(this.context.getResources().getString(R.string.preference_file_key), json);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    public void initComponents() {

        nombreEditText = (EditText) findViewById(R.id.nombreEditText);
        edadEditText = (EditText) findViewById(R.id.edadEditText);
        correoEditText = (EditText) findViewById(R.id.correoEditText);
        usuarioEditText = (EditText) findViewById(R.id.usuarioEditText);
        passEditText = (EditText) findViewById(R.id.passEditText);
        if (persona != null && !persona.getUsuario().equals(""))
            usuarioEditText.setText(persona.getUsuario());

    }

    public void getPersona() {
        persona = new Persona();
        persona = (Persona) getIntent().getSerializableExtra(getResources().getString(R.string.preference_file_key));
    }

    public void setPersona() {

        persona.setNombre(nombreEditText.getText().toString());
        persona.setEdad(edadEditText.getText().toString());
        persona.setCorreo(correoEditText.getText().toString());
        persona.setUsuario(usuarioEditText.getText().toString());
        persona.setContraseña(passEditText.getText().toString());
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void leer() {
        listaPersona.clear();
        Gson gson = new Gson();
        String json = sharedPref.getString(getResources().getString(R.string.preference_file_key), null);
        Type type = new TypeToken<ArrayList<Persona>>() {
        }.getType();
        listaPersona = gson.fromJson(json, type);
    }
}
