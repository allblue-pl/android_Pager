package pl.allblue.pager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.allblue.pager.Pager;

public abstract class PagerFragment extends Fragment implements
        Pager.OnBackPressedListener
{

    private Pager pager = null;


    public Pager getPager()
    {
        return this.pager;
    }

//    public void onCreatePagerView(@Nullable Bundle savedInstanceState)
//    {
//        this.pager.onCreateView(savedInstanceState);
//    }


    abstract public Pager onCreatePager();


    /* Fragment Overrides */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.pager = this.onCreatePager();
        if (this.pager == null) {
            throw new AssertionError("`Pager` not set. Override `onCreatePager` in `" +
                    this.getClass().getName() + "`.");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        this.pager.onSaveInstanceState(outState);
    }

    /* / Fragment Overrides */


    /* Pager.OnBackPressedListener */
    @Override
    public boolean onFragmentBackPressed()
    {
        return this.pager.onPagerBackPressed();
    }
    /* / Pager.OnBackPressedListener */

}
