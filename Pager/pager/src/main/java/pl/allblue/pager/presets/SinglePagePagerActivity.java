package pl.allblue.pager.presets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import pl.allblue.pager.PagesData;
import pl.allblue.pager.PagesManager;
import pl.allblue.pager.R;
import pl.allblue.pager.activities.PagerActivity;
import pl.allblue.pager.pagers.ListPager;

abstract public class SinglePagePagerActivity extends PagerActivity
{

    protected PagesData pages = null;
    protected PagesManager pagesManager = null;


    abstract public String getPageViewId();
    abstract public Fragment onCreatePage();


    /* Activity Overrides */
    @Override
    public void onBackPressed()
    {
        Log.d("SinglePagePagerActivity", "Hello?");

        if (this.pagesManager.onBackPressed())
            return;

        this.finish();
    }

    @Override
    public void onCreate(@Nullable Bundle saved_instance_state)
    {
        super.onCreate(saved_instance_state);
        this.setContentView(R.layout.activity_singlepagepager);
    }
    /* / Activity Overrides */


    /* PagerActivity Overrides */
    @Override
    public PagesManager onCreatePager()
    {
        final SinglePagePagerActivity self = this;

        this.pages = new PagesData(this.getPageViewId(), this,
                R.id.activity_main_Page);

        this.pages.addPage("Main", new PagesData.PageInstance() {
            @Override
            public Fragment onCreate() {
                return self.onCreatePage();
            }

            @Override
            public void onSet() {

            }
        });

        this.pages.setDefaultPage("Main");

        this.pagesManager = new ListPager(this.pages);

        return this.pagesManager;
    }
    /* / PagerActivity Overrides */

}
