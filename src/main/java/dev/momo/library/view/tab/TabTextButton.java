package dev.momo.library.view.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dev.momo.library.core.tool.ResourceHelper;
import dev.momo.library.core.util.SimpleCallback;
import dev.momo.library.view.R;


/**
 * a text only button with badge
 **/
public class TabTextButton extends RelativeLayout implements TabButtonInterface {

    public int ID = 0;

    TabBar.TabButtonStyle style;
    TextView tabTextView;


    public TabTextButton(Context context) {
        super(context);
        if (!isInEditMode()) init();
    }

    public TabTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) init();
    }

    public TabTextButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) init();
    }


    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.tab_text_button, this, true);

        tabTextView = (TextView) findViewById(R.id.tabText);

        style = new TabBar.TabButtonStyle<TabTextButton>() {
            @Override
            public void onSelect(int id, TabTextButton button) {
                tabTextView.setTextColor(ResourceHelper.getColor(R.color.colorPrimary));
            }

            @Override
            public void offSelect(int id, TabTextButton button) {
                tabTextView.setTextColor(ResourceHelper.getColor(R.color.gray));
            }
        };
    }


    @Override
    public TabTextButton title(String title) {
        tabTextView.setText(title);
        return this;
    }

    @Override
    @Deprecated
    public TabButtonInterface res(int res) {
        return null;
    }

    @Override
    @Deprecated
    public TabButtonInterface resOn(int res) {
        return null;
    }

    @Override
    public TabTextButton ID(int id) {
        this.ID = id;
        return this;
    }

    @Override
    @Deprecated
    public TabButtonInterface setBadge(int count) {
        return null;
    }

    @Override
    @Deprecated
    public TabButtonInterface setBadge(String count) {
        return null;
    }

    @Override
    public TabButtonInterface setStyle(TabBar.TabButtonStyle style) {
        if (style != null) {
            this.style = style;
        }
        return this;
    }

    //    @Override
    //    public TabTextButton setBadge(int count) {
    //        if (count > 0) {
    //            badge.setVisibility(View.VISIBLE);
    //        } else {
    //            badge.setVisibility(View.GONE);
    //        }
    //        return this;
    //    }
    //
    //    @Override
    //    public TabTextButton setBadge(String count) {
    //        if (!count.isEmpty()) {
    //            badge.setVisibility(View.VISIBLE);
    //        } else {
    //            badge.setVisibility(View.GONE);
    //        }
    //        return this;
    //    }


    @Override
    public int getID() {
        return ID;
    }

    @Override
    public TextView getTextView() {
        return tabTextView;
    }

    @Override
    public ImageView getImageView() {
        return null;
    }


    @Override
    public void setListener(final SimpleCallback<TabButtonInterface> listener) {
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (listener != null) {
                    listener.execute(TabTextButton.this);
                }
            }
        });
    }


    public void onSelect() {
        style.onSelect(ID, this);
    }

    public void offSelect() {
        style.offSelect(ID, this);
    }

    @Override
    public boolean clickable() {
        return true;
    }


    @Override
    public boolean onClickPreCheck() {
        return true;
    }

}