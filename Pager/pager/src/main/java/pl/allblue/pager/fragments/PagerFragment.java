package pl.allblue.pager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import pl.allblue.pager.Pager;
import pl.allblue.pager.PagerInstance;

public abstract class PagerFragment extends Fragment implements
        Pager.BackPressedListener
{

    private PagerInstance pagerInstance = null;


    abstract public PagerInstance onCreatePager(@Nullable Bundle savedInstanceState);


    /* Fragment Overrides */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        this.pagerInstance = this.onCreatePager(savedInstanceState);
        if (this.pagerInstance == null) {
            throw new AssertionError("`Pager` not set. Override `onCreatePager` in `" +
                    this.getClass().getName() + "`.");
        }

        super.onCreate(savedInstanceState);
    }
    /* / Fragment Overrides */


    /* Pager.BackPressedListener */
    @Override
    public boolean onPagerBackPressed()
    {
        return this.pagerInstance.onPagerBackPressed();
    }
    /* / Pager.BackPressedListener */

}
