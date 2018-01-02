package pl.allblue.pager.presets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import pl.allblue.pager.Page;
import pl.allblue.pager.Pager;
import pl.allblue.pager.R;
import pl.allblue.pager.activities.PagerActivity;
import pl.allblue.pager.pagers.ListPager;

abstract public class SinglePagePagerActivity extends PagerActivity
{

    protected Pager pager = null;


    abstract public String getPageViewId();
    abstract public Fragment onCreatePage();


    /* Activity Overrides */
    @Override
    public void onBackPressed()
    {
        if (this.pager.onPagerBackPressed())
            return;

        this.finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_singlepagepager);
    }
    /* / Activity Overrides */


    /* PagerActivity Overrides */
    @Override
    public Pager onCreatePager()
    { final SinglePagePagerActivity self = this;

        this.pager = new ListPager(this.getPageViewId(), this,
                R.id.activity_main_Page);

        this.pager.getPages()
            .add("Main", new Page() {
                @Override
                public Fragment onPageCreate() {
                    return self.onCreatePage();
                }

                @Override
                public void onPageSet(Fragment pageFragment) {

                }

                @Override
                public void onPageUnset(Fragment pageFragment)
                {

                }
            })
            .setDefault("Main");

        return this.pager;
    }
    /* / PagerActivity Overrides */

}
