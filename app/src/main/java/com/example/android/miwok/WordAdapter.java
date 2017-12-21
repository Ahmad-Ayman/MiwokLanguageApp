package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 12/8/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    /** Resource ID  for the background color of this list  of words **/
    private int mColorResourceId;


    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param words A List of AndroidFlavor objects to display in a list
     */
    public WordAdapter(Context context,ArrayList<Word> words , int categoryColor) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        mColorResourceId = categoryColor;
    }
    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);
        // Find the TextView in the list_item.xml layout with the ID text1
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.text1);
        // Get the Engish Word from the current Word object and
        // set this text on the english TextView
        englishTextView.setText(currentWord.getDefaultTranslation());
        // Find the TextView in the list_item.xml layout with the ID text2
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.text2);
        // Get the Miwok Word from the current Word object and
        // set this text on the miwok TextView
        miwokTextView.setText(currentWord.getMiwokTranslation());
//        // Find the ImageView in the list_item.xml layout with the ID imageicon
        ImageView iconImage = (ImageView) listItemView.findViewById(R.id.imageicon);

        if(currentWord.hasImage()) {
            //        // Get the icon image  from the current Word object and
//        // set this image on the icon image
            iconImage.setImageResource(currentWord.getImageResourceId());
            //make sure the image is visible
            iconImage.setVisibility(View.VISIBLE);
        }
        else{
            // hide the image view
            iconImage.setVisibility(View.GONE);
        }
        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        View playicon = listItemView.findViewById(R.id.playicon);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);
        playicon.setBackgroundColor(color);
        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;

    }
}
