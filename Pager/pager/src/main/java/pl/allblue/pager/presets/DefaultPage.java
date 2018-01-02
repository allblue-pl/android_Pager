package pl.allblue.pager.presets;

import android.support.v4.app.Fragment;

import pl.allblue.pager.Page;

public class DefaultPage implements Page
{

    private Class<? extends Fragment> fragmentClass = null;
    private PageSetListener pageSetListener = null;


    public DefaultPage(Class<? extends Fragment> fragmentClass, PageSetListener pageSetListener)
    {
        this.fragmentClass = fragmentClass;
        this.pageSetListener = pageSetListener;
    }

    public DefaultPage(Class<? extends Fragment> fragmentClass)
    {
        this(fragmentClass, null);
    }

    @Override
    public Fragment onPageCreate()
    {
        try {
            return this.fragmentClass.newInstance();
        } catch (InstantiationException e) {
            throw new AssertionError("Fragment class doesn't have" +
                    " default constructor.");
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public void onPageSet(Fragment pageFragment)
    {
        if (this.pageSetListener == null)
            return;

        this.pageSetListener.onPageSet(pageFragment);
    }

    @Override
    public void onPageUnset(Fragment pageFragment)
    {
        if (this.pageSetListener == null)
            return;

        this.pageSetListener.onPageUnset(pageFragment);
    }


    public interface PageSetListener
    {

        void onPageSet(Fragment pageFragment);
        void onPageUnset(Fragment pageFragment);

    }

}
