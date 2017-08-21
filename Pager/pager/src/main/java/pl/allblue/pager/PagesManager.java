package pl.allblue.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;

public interface PagesManager
{

    boolean onBackPressed();
    void onCreateView(@Nullable Bundle saved_instance_state);
    void onDestroyView();
    void onSaveInstanceState(Bundle out_state);


    interface OnBackPressedListener
    {
        boolean onBackPressed();
    }

}
