package dev.momo.library.view.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dev.momo.library.core.util.SimpleCallback;
import dev.momo.library.view.R;


/**
 * a text only button with badge
 **/
public class TabImageButton extends RelativeLayout implements TabButtonInterface {

    public int ID = 0;

    private int res;
    private int resOn;

    //    public ImageView badge;
    private ImageView tabImageView;

    private TabBar.TabButtonStyle style;


    public TabImageButton(Context context) {
        super(context);
        if (!isInEditMode()) init();
    }

    public TabImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) init();
    }

    public TabImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.tab_image_button, this, true);
        tabImageView = (ImageView) findViewById(R.id.tabImage);
    }

    @Deprecated
    @Override
    public TabImageButton title(String title) {
        return this;
    }

    @Override
    public TabImageButton res(int res) {
        this.res = res;
        if (resOn == 0) {
            resOn = this.res;
        }
        tabImageView.setImageResource(res);
        return this;
    }

    @Override
    public TabImageButton resOn(int res) {
        this.resOn = res;
        return this;
    }

    @Override
    public TabImageButton ID(int id) {
        this.ID = id;
        return this;
    }

    @Override
    @Deprecated
    public TabImageButton setBadge(int count) {
        if (count > 0) {
            //            badge.setVisibility(View.VISIBLE);
        } else {
            //            badge.setVisibility(View.GONE);
        }
        return this;
    }

    @Override
    @Deprecated
    public TabImageButton setBadge(String count) {
        if (!count.isEmpty()) {
            //            badge.setVisibility(View.VISIBLE);
        } else {
            //            badge.setVisibility(View.GONE);
        }
        return this;
    }


    @Override
    public TabImageButton setStyle(TabBar.TabButtonStyle style) {
        this.style = style;
        return this;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Deprecated
    @Override
    public TextView getTextView() {
        return null;
    }

    @Override
    public ImageView getImageView() {
        return tabImageView;
    }

    @Override
    public void setListener(final SimpleCallback<TabButtonInterface> listener) {
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (listener != null) {
                    listener.execute(TabImageButton.this);
                }
            }
        });
    }


    @Override
    public void onSelect() {
        tabImageView.setImageResource(resOn);
        if (style != null) {
            style.onSelect(ID, this);
        }
    }

    @Override
    public void offSelect() {
        tabImageView.setImageResource(res);
        if (style != null) {
            style.offSelect(ID, this);
        }
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