package com.aberezovskaya.etherapists.fragments;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.aberezovskaya.etherapists.R;

/**
 * Stub Fragment for empty tabs
 */
public class StubFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stub, container, false);
    }
}
