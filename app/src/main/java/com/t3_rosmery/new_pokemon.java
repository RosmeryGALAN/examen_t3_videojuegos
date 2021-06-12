package com.t3_rosmery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.t3_rosmery.clases.Pokemon;
import com.t3_rosmery.services.PokemonServices;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class new_pokemon extends AppCompatActivity {
    ImageView imagenes;
    String imgDecodableString;
    EditText imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_pokemon);
        EditText nombre = findViewById(R.id.name);
        EditText tipo = findViewById(R.id.tipe);
        imagen = (EditText) findViewById(R.id.image);
        EditText latitud = findViewById(R.id.latitud);
        EditText longitud = findViewById(R.id.longitud);
        Button crear = findViewById(R.id.newPokemon);

        Log.i("MIPPIN","dsd: " + imgDecodableString);

        crear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = nombre.getText().toString().trim();
                String tipos = tipo.getText().toString().trim();
                String image = imagen.getText().toString().trim();
                double latitude = Double.parseDouble(latitud.getText().toString().trim());
                double longitude = Double.parseDouble(longitud.getText().toString().trim());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://upn.lumenes.tk")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PokemonServices service = retrofit.create(PokemonServices.class);

                Pokemon pokemon = new Pokemon();
                pokemon.setNombre(name);
                pokemon.setTipo(tipos);
                pokemon.setUrl_imagen(image);
                pokemon.setLatitude(latitude);
                pokemon.setLongitude(longitude);

                Call<Pokemon> call = service.create(pokemon);

                call.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        if(response.code() == 200){
                            Log.i("Web","Conexion ok");
                        }else {
                            Log.i("Web","Mala conexion");
                        }
                    }
                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable t) {
                        Log.i("Web","No se puede conectar al servidor");
                    }
                });
                Intent intent = new Intent(new_pokemon.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void cargarGaleria() { // retorna verdadero si la condicion se cumple, si tiene permisos true, sino false, se cambia en CALL_PHONE
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione aplicacion"),5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                Uri path = data.getData();
                imagenes.setImageURI(path);

                final InputStream imageStream = getContentResolver().openInputStream(path);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgDecodableString = encodeImage(selectedImage);
                Log.i("MY_APP", "Despues de codificar: "+imgDecodableString);

            }else {
                Toast.makeText(this, "No haz escogido una imagen",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Algo salio mal", Toast.LENGTH_LONG)
                    .show();
        }
        Log.i("MY_APP", String.valueOf(resultCode));

    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        imgDecodableString = Base64.encodeToString(b, Base64.DEFAULT);
        imagen.setText(imgDecodableString);
        return imgDecodableString;
    }

}
