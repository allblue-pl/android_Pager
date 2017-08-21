package pl.allblue.pager.pagers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.Stack;

import pl.allblue.pager.PagesData;
import pl.allblue.pager.PagesManager;

public class StackPager implements PagesManager
{

    static public final String StateExts_PagesStack = "PagesStack";


    private PagesData pages = null;
    private Stack<String> pages_Stack = new Stack<>();


    public StackPager(PagesData pages)
    {
        this.pages = pages;
    }

    public void push(String page_name)
    {
        PagesData.Page page = this.pages.getPage(page_name);

        Fragment page_fragment = page.getInstance().onCreate();

        this.pages.getFragmentManager().beginTransaction()
            .replace(this.pages.getViewId(), page_fragment)
            .addToBackStack(page.getTag())
            .commit();

        this.pages_Stack.push(page_name);

        this.pages.setFragment(page_name, page_fragment);
    }

    public void pop()
    {
        String page_name = this.pages_Stack.pop();
        FragmentManager fm = this.pages.getFragmentManager();

        int back_stack_count = fm.getBackStackEntryCount();
        if (back_stack_count == 0)
            throw new AssertionError("Stack empty.");

        this.pages.getFragmentManager().popBackStack();
    }

    private Fragment getFragment(PagesData.Page page)
    {
        return page.getInstance().onCreate();
    }


    /* FragmentManager Overrides */
    @Override
    public boolean onBackPressed()
    {
        if (this.pages.onBackPressed())
            return true;

        if (this.pages_Stack.size() <= 1)
            return false;

        this.pop();

        return true;
    }

    public void onCreateView(@Nullable Bundle saved_instance_state)
    {
        if (saved_instance_state != null) {
            String[] pages_stack = saved_instance_state.getStringArray(
                    this.pages.getStateKey(StackPager.StateExts_PagesStack));

            for (int i = 0; i < pages_stack.length; i++)
                this.push(pages_stack[i]);
        } else if (this.pages_Stack.size() == 0)
            this.push(this.pages.getDefaultPage().getName());
    }

    @Override
    public void onDestroyView()
    {
        this.pages.onDestroyView();
    }

    public void onSaveInstanceState(Bundle out_state)
    {
        String[] pages_stack_array = new String[this.pages_Stack.size()];
        this.pages_Stack.toArray(pages_stack_array);

        out_state.putStringArray(this.pages.getStateKey(
                StackPager.StateExts_PagesStack), pages_stack_array);
    }
    /* / FragmentManager Overrides */

}
