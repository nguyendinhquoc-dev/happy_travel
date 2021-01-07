package com.example.happytravelapp.ultil;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseMethod;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytravelapp.R;
import com.example.happytravelapp.model.User;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class BindingUtils {

    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(ImageView view, String imgurl) {
        Picasso.get().load(imgurl).placeholder(R.drawable.ic_image_black_24dp).into(view);
    }

    @BindingAdapter("app:errorText")
    public static void setErrorMessage(TextInputLayout view, String errorMessage) {
        view.setError(errorMessage);
    }

    @InverseMethod("buttonIdToGenderType")
    public static int genderTypeToButtonId(User.Gender gender) {
        int selectedButtonId = -1;

        if (gender == null) {
            return selectedButtonId;
        }

        switch (gender) {
            case MALE: {
                selectedButtonId = R.id.male;
                break;
            }
            case FEMALE: {
                selectedButtonId = R.id.female;
                break;
            }
        }
        return selectedButtonId;
    }

    public static User.Gender buttonIdToGenderType(int selectedButtonId) {
        User.Gender gender = null;
        switch (selectedButtonId) {
            case R.id.male: {
                gender = User.Gender.MALE;
                break;
            }
            case R.id.female: {
                gender = User.Gender.FEMALE;
            }
        }
        return gender;
    }

    //text format
    @BindingAdapter("android:text")
    public static void setText(TextView view, int value) {
        if (view.getText() != null
                && ( !view.getText().toString().isEmpty() )
                && Integer.parseInt(view.getText().toString()) != value) {
            view.setText(Integer.toString(value));
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(TextView view) {
        return Integer.parseInt(view.getText().toString());
    }

    @BindingAdapter("android:text")
    public static void setFloat(TextView view, float value) {
        if (view.getText() != null
                && ( !view.getText().toString().isEmpty() )
                && Float.parseFloat(view.getText().toString()) != value) {
            view.setText(Float.toString(value));
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static float getFloat(TextView view) {
        String num = view.getText().toString();
        if(num.isEmpty()) return 0.0F;
        try {
            return Float.parseFloat(num);
        } catch (NumberFormatException e) {
            return 0.0F;
        }
    }

    //date format
    @BindingAdapter("bindServerDate")
    public static void bindServerDate(@NonNull TextView textView, Object timestamp) {
        /*Parse string data and set it in another format for your textView*/
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm:ss, EEE, dd MMMM yyyy");

        String timestampString= myFormat.format(new Date((long) timestamp));
        textView.setText(timestampString);
    }

    @BindingAdapter("android:rating")
    public static void setRating(RatingBar view, String rating) {

        if (view!=null)
        {
            float rate= Float.parseFloat(rating);
            view.setRating(rate);
        }
    }

    //recycleview
    @BindingAdapter({"fixedSize", "itemAnimator", "layoutManager"})
    public static void configure(RecyclerView view, boolean fixedSize, RecyclerView.ItemAnimator itemAnimator, RecyclerView.LayoutManager layoutManager) {
        view.setHasFixedSize(fixedSize);
        view.setItemAnimator(itemAnimator);
        view.setLayoutManager(layoutManager);
    }

    @BindingAdapter({"adapter"})
    public static void adapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("query")
    public static void setQuery(SearchView searchView, String queryText) {
        searchView.setQuery(queryText, false);
    }




}
