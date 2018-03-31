package dev.momo.library.view.tab;

import android.widget.ImageView;
import android.widget.TextView;

import dev.momo.library.core.util.SimpleCallback;


public interface TabButtonInterface {

    boolean onClickPreCheck();

    void setListener(final SimpleCallback<TabButtonInterface> listener);

    void onSelect();

    void offSelect();

    boolean clickable();

    TabButtonInterface title(String title);

    TabButtonInterface res(int res);

    TabButtonInterface resOn(int res);

    TabButtonInterface ID(int id);

    TabButtonInterface setBadge(int count);

    TabButtonInterface setBadge(String count);

    TabButtonInterface setStyle(TabBar.TabButtonStyle style);

    int getID();

    TextView getTextView();

    ImageView getImageView();

}