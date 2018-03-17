package pl.allblue.pager.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import pl.allblue.pager.Pager;
import pl.allblue.pager.PagerInstance;

abstract public class PagerActivity extends AppCompatActivity
{

    private PagerInstance pagerInstance = null;


    abstract public PagerInstance onCreatePager(@Nullable Bundle savedInstanceState);


    /* AppCompatActivity Overrides */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.pagerInstance = this.onCreatePager(savedInstanceState);
        if (this.pagerInstance == null)
            throw new AssertionError("`Pager` returned by `onCreatePager`" +
                    " cannot be null.");

//        Log.d("[Test]", "PagerActivity - savedInstanceState: " +
//                (savedInstanceState == null));
//        if (savedInstanceState != null)
//            this.pager.loadInstanceState(savedInstanceState);

        // this.pager.onCreateView();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState)
//    {
//        super.onSaveInstanceState(outState);
//
//        this.pager.onSaveInstanceState(outState);
//    }
    /* / AppCompatActivity Overrides */

}
