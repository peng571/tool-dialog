package dev.momo.library.view.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import dev.momo.library.core.log.Logger;
import dev.momo.library.core.tool.PreferenceHelper;
import dev.momo.library.core.util.SimpleCallback;

public class TabBar<T extends TabButtonInterface> extends LinearLayout {

    private final static String TAG = TabBar.class.getSimpleName();

    public String savingKey = "";

    private int lastTabId = 0;//"";
    private int currentTabId = 0;// "";

    private ArrayList<T> tabButtons = new ArrayList<>();
    private TabBarListener tabBarListener;

    public TabButtonStyle style;


    public TabBar(Context context) {
        super(context);
    }

    public TabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressWarnings("unchecked")
    public TabBar addTab(T button) {
        addTab(tabButtons.size(), button);
        return this;
    }

    @SuppressWarnings("unchecked")
    public TabBar addTab(int order, final T button) {

        if (button == null) {
            Logger.ES(TAG, "get null button");
            return this;
        }

        if (style != null) {
            button.setStyle(style);
        }
        button.ID(order);
        tabButtons.add(order, button);
        button.setListener(new SimpleCallback<TabButtonInterface>() {
            public void execute(TabButtonInterface tab) {
                if (!tab.clickable()) {
                    return;
                }

                if (currentTabId == tab.getID()) {
                    return;
                }

                if (!tab.onClickPreCheck()) {
                    return;
                }
                lastTabId = currentTabId;
                currentTabId = tab.getID();
                reloadUI();

                tabBarListener.onTabChange(tab.getID(), lastTabId);
                //                tabBarListener.onBadgeChange(tab, 0);

                saveTabPosition();

            }
        });

        if (button instanceof View) {
            View buttonView = (View) button;
            addView(buttonView,
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1f));
        }
        return this;
    }

    /**
     * Set saveKey if need to save last tab status
     * @param key Name of saving key
     * @return this
     */
    public TabBar saveWith(String key) {
        this.savingKey = key;
        return this;
    }

    public int size() {
        return tabButtons.size();
    }

    public TabBar setStyle(TabButtonStyle style) {
        this.style = style;
        for (TabButtonInterface button : tabButtons) {
            button.setStyle(style);
        }
        return this;
    }

    public TabBar setListener(TabBarListener tabBarListener) {
        this.tabBarListener = tabBarListener;
        return this;
    }

    public int getCurrentTabID() {
        return currentTabId;
    }


    //    public void setBadge(int id, String s) {
    //        tabButtons.get(id).setBadge(s);
    //    }
    //
    //    public void setBadge(int id, int num) {
    //        Logger.D("setBadge " + id + ": " + num);
    //        tabButtons.get(id).setBadge(num);
    //    }


    public void setCurrentTabID(int newTabId) {
        if (tabButtons.size() <= newTabId) {
            return;
        }
        currentTabId = newTabId;
        reloadUI();
        saveTabPosition();

    }

    private void saveTabPosition() {
        if (!savingKey.isEmpty()) {
            PreferenceHelper.set(savingKey, currentTabId);
        }
    }

    private void reloadUI() {
        for (TabButtonInterface button : tabButtons) {
            if (button.getID() == currentTabId) {
                //                button.tabImageView.setImageResource(button.resOn);
                button.onSelect();
            } else {
                //                button.tabImageView.setImageResource(button.resOff);
                button.offSelect();
            }
        }
    }


    private TabButtonInterface getTab(int tabID) {
        if (tabID >= tabButtons.size()) {
            return null;
        }

        return tabButtons.get(tabID);
    }


    public int getLastTabID() {
        return getLastTabID(0);
    }


    public int getLastTabID(int defaultTab) {
        if (savingKey.isEmpty()) {
            return 0;
        }
        return PreferenceHelper.getInt(savingKey, defaultTab);
    }


    public interface TabBarListener {
        void onTabChange(int currentID, int lastID);

        //        void onBadgeChange(TabButtonInterface button, int badgeCount);
    }

    public interface TabButtonStyle<T extends TabButtonInterface> {
        void onSelect(int id, T button);

        void offSelect(int id, T button);
    }

}
