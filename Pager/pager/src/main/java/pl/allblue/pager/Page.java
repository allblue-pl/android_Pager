package pl.allblue.pager;

import android.support.v4.app.Fragment;

public interface Page
{
    Fragment onPageCreate();
    void onPageSet(Fragment pageFragment);
    void onPageUnset(Fragment pageFragment);
}
