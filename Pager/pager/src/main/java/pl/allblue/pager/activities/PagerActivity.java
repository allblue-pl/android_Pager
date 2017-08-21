package pl.allblue.pager.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import pl.allblue.pager.PagesManager;

public class PagerActivity extends AppCompatActivity
{

    private PagesManager pagesManager = null;

    public PagesManager onCreatePager()
    {
        return null;
    }


    /* AppCompatActivity Overrides */
    @Override
    public void onCreate(@Nullable Bundle saved_instance_state)
    {
        super.onCreate(saved_instance_state);

        this.pagesManager = this.onCreatePager();
        if (this.pagesManager == null)
            throw new AssertionError("`PagesManager` not set. Override `onCreatePager`.");

        this.pagesManager.onCreateView(saved_instance_state);
    }

    @Override
    public void onDestroy()
    {
        this.pagesManager.onDestroyView();

        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle out_state)
    {
        super.onSaveInstanceState(out_state);

        this.pagesManager.onSaveInstanceState(out_state);
    }

    /* / AppCompatActivity Overrides */

}
