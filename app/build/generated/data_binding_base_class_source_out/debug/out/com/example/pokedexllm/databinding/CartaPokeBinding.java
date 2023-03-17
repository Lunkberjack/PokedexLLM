// Generated by view binder compiler. Do not edit!
package com.example.pokedexllm.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.pokedexllm.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CartaPokeBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout fondoCarta;

  @NonNull
  public final TextView nombre;

  @NonNull
  public final TextView numero;

  @NonNull
  public final ImageView sprite;

  private CartaPokeBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout fondoCarta,
      @NonNull TextView nombre, @NonNull TextView numero, @NonNull ImageView sprite) {
    this.rootView = rootView;
    this.fondoCarta = fondoCarta;
    this.nombre = nombre;
    this.numero = numero;
    this.sprite = sprite;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CartaPokeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CartaPokeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.carta_poke, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CartaPokeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout fondoCarta = (LinearLayout) rootView;

      id = R.id.nombre;
      TextView nombre = ViewBindings.findChildViewById(rootView, id);
      if (nombre == null) {
        break missingId;
      }

      id = R.id.numero;
      TextView numero = ViewBindings.findChildViewById(rootView, id);
      if (numero == null) {
        break missingId;
      }

      id = R.id.sprite;
      ImageView sprite = ViewBindings.findChildViewById(rootView, id);
      if (sprite == null) {
        break missingId;
      }

      return new CartaPokeBinding((LinearLayout) rootView, fondoCarta, nombre, numero, sprite);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}