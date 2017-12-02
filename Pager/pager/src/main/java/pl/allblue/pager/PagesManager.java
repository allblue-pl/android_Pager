package pl.allblue.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

public interface PagesManager
{

    boolean onBackPressed();
    void onCreateView(@Nullable Bundle saved_instance_state);
    void onDestroyView();
    boolean onOptionsItemSelected(MenuItem item);
    void onSaveInstanceState(Bundle out_state);


    interface OnBackPressedListener
    {
        boolean onBackPressed();
    }

    interface OnOptionsItemSelected
    {
        boolean onOptionsItemSelected(MenuItem item);
    }

}
