package pl.allblue.pager.pagers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.allblue.pager.PagesData;
import pl.allblue.pager.PagesManager;

public class ListPager implements PagesManager
{

    static public final String StateExts_ActivePage = "ActivePage";


    private PagesData pages = null;
    private String page_Active = null;
    private List<String> pages_Stack = new ArrayList<>();

    public ListPager(PagesData pager)
    {
        final ListPager self = this;

        this.pages = pager;
    }

    public void set(String page_name, boolean add_to_stack)
    {
        PagesData.Page page = this.pages.getPage(page_name);
        FragmentManager fm = this.pages.getFragmentManager();

        Fragment page_fragment = this.pages.getFragmentManager().findFragmentByTag(
                page.getTag());
        boolean is_new_fragment = false;
        if (page_fragment == null) {
            page_fragment = page.getInstance().onCreate();
            is_new_fragment = true;
            Log.d("PagesData", "New " + page.getName());
        } else {
            Log.d("PagesData", "Reusing " + page.getName());
        }

        FragmentTransaction ft = this.pages.getFragmentManager().beginTransaction();
        ft.replace(this.pages.getViewId(), page_fragment, page.getTag());
        if (is_new_fragment)
            ft.addToBackStack(page.getTag());
        ft.commit();

        if (add_to_stack) {
            this.pages_Stack.remove(page_name);
            this.pages_Stack.add(page_name);
        }

        this.page_Active = page_name;

        this.pages.setActivePage(page_name, page_fragment);
    }

    public void set(String page_name)
    {
        this.set(page_name, true);
    }


    /* PagesManager Overrides */
    @Override
    public boolean onBackPressed()
    {
        if (this.pages.onBackPressed())
            return true;

        if (this.pages_Stack.size() <= 1)
            return false;

        String page_name = this.pages_Stack.get(this.pages_Stack.size() - 2);
        this.pages_Stack.remove(this.pages_Stack.size() - 1);

        this.set(page_name, false);

        return true;
    }

    @Override
    public void onCreateView(@Nullable Bundle saved_instance_state)
    {
        if (saved_instance_state != null) {
            this.page_Active = saved_instance_state.getString(
                    this.pages.getStateKey(ListPager.StateExts_ActivePage));
        } else if (this.page_Active == null)
            this.page_Active = this.pages.getDefaultPage().getName();

        Log.d("ListPager", "Creating view: " + this.page_Active);
        this.set(this.page_Active);
    }

    @Override
    public void onDestroyView()
    {
        this.pages.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle out_state)
    {
        out_state.putString(this.pages.getStateKey(ListPager.StateExts_ActivePage),
                this.page_Active);
    }
    /* / PagesManager Overrides */

}
