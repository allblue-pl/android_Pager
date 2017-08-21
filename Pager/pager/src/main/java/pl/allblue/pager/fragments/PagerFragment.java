package pl.allblue.pager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import pl.allblue.pager.PagesManager;

public class PagerFragment extends Fragment implements
        PagesManager.OnBackPressedListener
{

    private PagesManager pagesManager = null;

    public PagesManager onCreatePager()
    {
        return null;
    }

    public void onCreatePagerView(@Nullable Bundle saved_instance_state)
    {
        this.pagesManager.onCreateView(saved_instance_state);
    }

    /* Fragment Overrides */
    @Override
    public void onCreate(@Nullable Bundle saved_instance_state)
    {
        super.onCreate(saved_instance_state);

        this.pagesManager = this.onCreatePager();
        if (this.pagesManager == null)
            throw new AssertionError("`PagesManager` not set. Override `onCreatePager`.");
    }

    @Override
    public void onDestroyView()
    {
        this.pagesManager.onDestroyView();

        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle out_state)
    {
        super.onSaveInstanceState(out_state);

        this.pagesManager.onSaveInstanceState(out_state);
    }

    /* / Fragment Overrides */


    /* PagesManager.OnBackPressedListener */
    @Override
    public boolean onBackPressed()
    {
        if (this.pagesManager.onBackPressed())
            return true;

        return false;
    }
    /* / PagesManager.OnBackPressedListener */

}
