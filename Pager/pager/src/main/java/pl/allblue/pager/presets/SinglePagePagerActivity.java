package pl.allblue.pager.presets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import pl.allblue.pager.Page;
import pl.allblue.pager.Pager;
import pl.allblue.pager.PagerInstance;
import pl.allblue.pager.R;
import pl.allblue.pager.activities.PagerActivity;
import pl.allblue.pager.pagers.ListPager;

abstract public class SinglePagePagerActivity extends PagerActivity
{

    private Pager pager = null;
    private Page page = null;


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
    { final SinglePagePagerActivity self = this;
        this.setContentView(R.layout.activity_singlepagepager);

        super.onCreate(savedInstanceState);
    }
    /* / Activity Overrides */


    /* PagerActivity Overrides */
    @Override
    public PagerInstance onCreatePager(Bundle savedInstanceState)
    { final SinglePagePagerActivity self = this;

        this.pager = new Pager(this.getPageViewId(), this,
                R.id.activity_main_Page);

        this.page = new Page() {
            @Override
            public Fragment onPageCreate() {
                return self.onCreatePage();
            }

            @Override
            public void onPageSet(Fragment pageFragment) {

            }
        };

        this.pager.set("Main", this.page);

        return this.pager;
    }
    /* / PagerActivity Overrides */

}
