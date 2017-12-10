package pl.allblue.pager;

import android.support.v4.app.Fragment;

public interface Page
{
    Fragment onCreate();
    void onSet();
}
