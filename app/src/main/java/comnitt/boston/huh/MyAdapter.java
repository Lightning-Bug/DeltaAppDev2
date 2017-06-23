package comnitt.boston.huh;

/**
 * Created by HP on 21-Jun-17.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
public class MyAdapter extends BaseAdapter {



    private Context Context;
    private List<MyImage> MyImage;

    public MyAdapter(Context Context, List<MyImage> MyImage) {
        this.Context = Context;
        this.MyImage = MyImage;
    }



    @Override
    public int getCount() {
        return MyImage.size();
    }

    @Override
    public Object getItem(int position) {
        return MyImage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(Context,R.layout.list_layout,null);
        TextView txt = (TextView) v.findViewById(R.id.txtdisp);
        ImageView img = (ImageView) v.findViewById(R.id.disp);

        txt.setText(MyImage.get(position).getcaption());
        img.setImageBitmap(MyImage.get(position).getimg());


        return v;
    }


}