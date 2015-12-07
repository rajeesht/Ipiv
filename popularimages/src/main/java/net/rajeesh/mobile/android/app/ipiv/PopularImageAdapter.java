package net.rajeesh.mobile.android.app.ipiv;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rajeesh on 12/6/15.
 */
public class PopularImageAdapter extends ArrayAdapter<PopularImage> {
    public PopularImageAdapter(Context context, List<PopularImage> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PopularImage popularImage = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.popular_image, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>");
        stringBuilder.append(popularImage.getUsername());
        stringBuilder.append("</b>");
        stringBuilder.append(" - ");
        stringBuilder.append(popularImage.getCaption());
        tvCaption.setText(Html.fromHtml(stringBuilder.toString()));

        ImageView ivPopularImage = (ImageView) convertView.findViewById(R.id.ivPopularPhoto);
        ivPopularImage.setImageResource(0);
        Picasso.with(getContext()).load(popularImage.getImageUrl()).into(ivPopularImage);

        return convertView;
    }
}
