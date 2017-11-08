package gmalv.cr.fi.ejercicio4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText user;
    EditText password;
    Persona persona = new Persona();
    ArrayList<Persona> listaPersona = new ArrayList<Persona>();
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences(
                getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        leer();
    }

    public void Registrar(View view) {
        setPersona();

        switch (view.getId()) {
            case R.id.enterButton:
                if (buscar(persona.getUsuario())) {
                    if (comparePass(persona.getContraseña())) {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle(getResources().getString(R.string.user_exist));
                        alertDialog.setMessage(persona.getUsuario() + "\n" + persona.getNombre() + "\n" + persona.getEdad() + "\n" + persona.getCorreo());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle(getResources().getString(R.string.error));
                        alertDialog.setMessage(getResources().getString(R.string.wrong_password));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(getResources().getString(R.string.error));
                    alertDialog.setMessage(getResources().getString(R.string.person_no_exist));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                break;
            case R.id.registerButton:
                if (!buscar(persona.getUsuario())) {
                    Intent intent = new Intent(this, RegistrarUsuarioActivity.class);
                    intent.putExtra(getResources().getString(R.string.preference_file_key), persona);
                    startActivity(intent);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(getResources().getString(R.string.user_exist));
                    alertDialog.setMessage(persona.getUsuario() + " " + getResources().getString(R.string.user_exist_dialog));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                break;
            case R.id.buttonListar:
                Intent intent = new Intent(this, ListarUsuariosActivity.class);
                startActivity(intent);
                break;
        }


    }

    private void leer() {
        listaPersona.clear();
        Gson gson = new Gson();
        String json = sharedPref.getString(getResources().getString(R.string.preference_file_key), null);
        Type type = new TypeToken<ArrayList<Persona>>() {
        }.getType();
        listaPersona = gson.fromJson(json, type);
    }

    private void setPersona() {
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);


        persona.setUsuario(user.getText().toString());
        persona.setContraseña(password.getText().toString());

    }

    public Boolean buscar(String user) {
        if (listaPersona != null) {
            for (Persona pers : listaPersona) {
                if (pers.getUsuario() != null && pers.getUsuario().equals(user)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean comparePass(String password) {
        for (Persona pers : listaPersona) {
            if (pers.getContraseña() != null && pers.getContraseña().equals(password)) {
                persona=pers;
                return true;
            }
        }
        return false;
    }
}
