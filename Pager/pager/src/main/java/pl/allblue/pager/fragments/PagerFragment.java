package pl.allblue.pager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import pl.allblue.pager.Pager;

public abstract class PagerFragment extends Fragment implements
        Pager.OnBackPressedListener
{

    private Pager pager = null;

    abstract public Pager onCreatePager();

    public void onCreatePagerView(@Nullable Bundle savedInstanceState)
    {
        this.pager.onCreateView(savedInstanceState);
    }

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
    public void onDestroyView()
    {
        this.pager.onDestroyView();

        super.onDestroyView();
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
