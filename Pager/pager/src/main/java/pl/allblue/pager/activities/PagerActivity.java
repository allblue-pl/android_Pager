package pl.allblue.pager.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import pl.allblue.pager.Pager;

abstract public class PagerActivity extends AppCompatActivity
{

    private Pager pager = null;


    abstract public Pager onCreatePager();

    /* AppCompatActivity Overrides */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.pager = this.onCreatePager();
        if (this.pager == null)
            throw new AssertionError("`Pager` returned by `onCreatePager`" +
                    " cannot be null.");

        this.pager.onCreateView(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        this.pager.onSaveInstanceState(outState);
    }
    /* / AppCompatActivity Overrides */

}
